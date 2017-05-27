/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.bn.coders;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import org.bn.annotations.ASN1Any;
import org.bn.annotations.ASN1BoxedType;
import org.bn.annotations.ASN1Element;
import org.bn.annotations.ASN1Enum;
import org.bn.annotations.ASN1Null;
import org.bn.annotations.ASN1Sequence;
import org.bn.annotations.ASN1SequenceOf;
import org.bn.annotations.ASN1String;
import org.bn.annotations.constraints.ASN1SizeConstraint;
import org.bn.annotations.constraints.ASN1ValueRangeConstraint;
import org.bn.metadata.ASN1AnyMetadata;
import org.bn.metadata.ASN1NullMetadata;
import org.bn.metadata.ASN1SequenceMetadata;
import org.bn.metadata.ASN1SequenceOfMetadata;
import org.bn.metadata.ASN1StringMetadata;
import org.bn.types.BitString;

public class CoderUtils {
    
    // do not create instances of this class
    private CoderUtils() {
    }

    public static int getIntegerLength(int value) {
        long mask = 0x7f800000L;
        int sizeOfInt = 4;
        if (value < 0) {
            while ((mask & value) == mask && sizeOfInt > 1) {
                mask >>= 8;
                sizeOfInt--;
            }
        } else {
            while ((mask & value) == 0 && sizeOfInt > 1) {
                mask >>= 8;
                sizeOfInt--;
            }
        }
        return sizeOfInt;
    }

    public static int getIntegerLength(long value) {
        long mask = 0x7f80000000000000L;
        int sizeOfInt = 8;
        if (value < 0) {
            while ((mask & value) == mask && sizeOfInt > 1) {
                mask >>= 8;
                sizeOfInt--;
            }
        } else {
            while ((mask & value) == 0 && sizeOfInt > 1) {
                mask >>= 8;
                sizeOfInt--;
            }
        }
        return sizeOfInt;
    }

    public static int getPositiveIntegerLength(int value) {
        if (value < 0) {
            long mask = 0x7f800000L;
            int sizeOfInt = 4;
            while ((mask & ~value) == mask && sizeOfInt > 1) {
                mask >>= 8;
                sizeOfInt--;
            }
            return sizeOfInt;
        } else {
            return getIntegerLength(value);
        }
    }

    public static int getPositiveIntegerLength(long value) {
        if (value < 0) {
            long mask = 0x7f80000000000000L;
            int sizeOfInt = 8;
            while ((mask & ~value) == mask && sizeOfInt > 1) {
                mask >>= 8;
                sizeOfInt--;
            }
            return sizeOfInt;
        } else {
            return getIntegerLength(value);
        }
    }

    public static BitString defStringToOctetString(String bhString) {
        if (bhString.length() < 4) {
            return new BitString();
        }
        if (bhString.lastIndexOf('B') == bhString.length() - 1) {
            return bitStringToOctetString(bhString.substring(1, bhString.length() - 2));
        } else {
            return hexStringToOctetString(bhString.substring(1, bhString.length() - 2));
        }
    }

    private static BitString bitStringToOctetString(String bhString) {
        boolean hasTrailBits = bhString.length() % 2 != 0;
        int trailBits = 0;
        byte[] resultBuf = new byte[bhString.length() / 8 + (hasTrailBits ? 1 : 0)];
        int currentStrPos = 0;
        for (int i = 0; i < resultBuf.length; i++) {
            byte bt = 0x00;
            int bitCnt = currentStrPos;
            while (bitCnt < currentStrPos + 8 && bitCnt < bhString.length()) {
                if (bhString.charAt(bitCnt) != '0') {
                    bt |= (0x01 << (7 - (bitCnt - currentStrPos)));
                }
                bitCnt++;
            }
            currentStrPos += 8;
            if (bitCnt != currentStrPos) {
                trailBits = 8 - (currentStrPos - bitCnt);
            }
            // hi byte
            resultBuf[i] = bt;
        }
        BitString result = new BitString(resultBuf, trailBits);
        return result;
    }

    private static BitString hexStringToOctetString(String bhString) {
        boolean hasTrailBits = bhString.length() % 2 != 0;
        BitString result = new BitString(new byte[bhString.length() / 2 + (hasTrailBits ? 1 : 0)], hasTrailBits ? 4 : 0);
        final byte hex[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF};

        for (int i = 0; i < result.getLength(); i++) {
            // high byte
            result.getValue()[i] = (byte) (hex[((int) (bhString.charAt(i * 2)) - 0x30)] << 4);
            if (!hasTrailBits || (hasTrailBits && i < result.getLength() - 1)) {
                result.getValue()[i] |= (byte) (hex[((int) (bhString.charAt(i * 2 + 1)) - 0x30)] & 0x0F);
            }
        }
        return result;
    }

    public static SortedMap<Integer, Field> getSetOrder(Class<?> objectClass) {
        SortedMap<Integer, Field> fieldOrder = new TreeMap<>();
        int tagNA = -1;
        for (Field field : objectClass.getDeclaredFields()) {
            ASN1Element element = field.getAnnotation(ASN1Element.class);
            if (element != null) {
                if (element.hasTag()) {
                    fieldOrder.put(element.tag(), field);
                } else {
                    fieldOrder.put(tagNA--, field);
                }
            }
        }
        return fieldOrder;
    }

    public static int getStringTagForElement(ElementInfo elementInfo) {
        int result = UniversalTag.PrintableString;
        if (elementInfo.hasPreparedInfo()) {
            result = ((ASN1StringMetadata) elementInfo.getPreparedInfo().getTypeMetadata()).getStringType();
        } else if (elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1String.class)) {
            ASN1String value = elementInfo.getAnnotatedClass().getAnnotation(ASN1String.class);
            result = value.stringType();
        } else if (elementInfo.getParentAnnotated() != null && elementInfo.getParentAnnotated().isAnnotationPresent(ASN1String.class)) {
            ASN1String value = elementInfo.getParentAnnotated().getAnnotation(ASN1String.class);
            result = value.stringType();
        }
        return result;
    }

    public static void checkConstraints(long value, ElementInfo elementInfo) throws Exception {
        if (elementInfo.hasPreparedInfo()) {
            if (elementInfo.getPreparedInfo().hasConstraint() && !elementInfo.getPreparedInfo().getConstraint().checkValue(value)) {
                throw new Exception("Value of '" + elementInfo.getAnnotatedClass().toString() + "' out of bounds");
            }
        } else {
            if (elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1ValueRangeConstraint.class)) {
                ASN1ValueRangeConstraint constraint = elementInfo.getAnnotatedClass().getAnnotation(ASN1ValueRangeConstraint.class);
                if (value > constraint.max() || value < constraint.min()) {
                    throw new Exception("Value of '" + elementInfo.getAnnotatedClass().toString() + "' out of bounds");
                }
            }
            if (elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1SizeConstraint.class)) {
                ASN1SizeConstraint constraint = elementInfo.getAnnotatedClass().getAnnotation(ASN1SizeConstraint.class);
                if (value != constraint.max()) {
                    throw new Exception("Value of '" + elementInfo.getAnnotatedClass().toString() + "' out of bounds");
                }
            }
        }
    }

    public static boolean isAnyField(Field field, ElementInfo elementInfo) {
        return elementInfo.hasPreparedInfo() ? elementInfo.getPreparedInfo().getTypeMetadata() instanceof ASN1AnyMetadata : field.isAnnotationPresent(ASN1Any.class);
    }

    public static boolean isNullField(Field field, ElementInfo elementInfo) {
        return elementInfo.hasPreparedInfo() ? elementInfo.getPreparedInfo().getTypeMetadata() instanceof ASN1NullMetadata : field.isAnnotationPresent(ASN1Null.class);
    }

    public static boolean isOptionalField(Field field, ElementInfo elementInfo) {
        if (elementInfo.hasPreparedInfo()) {
            return elementInfo.hasPreparedASN1ElementInfo() && (elementInfo.getPreparedASN1ElementInfo().isOptional() || elementInfo.getPreparedASN1ElementInfo().hasDefaultValue());
        } else if (field.isAnnotationPresent(ASN1Element.class)) {
            ASN1Element info = field.getAnnotation(ASN1Element.class);
            return info.isOptional() || info.hasDefaultValue();
        } else {
            return false;
        }
    }
    
    /** Returns true when the given field has a default value assigned in given elementInfo */
    public static boolean isDefaultField(Field field, ElementInfo elementInfo) {
        if (elementInfo.hasPreparedInfo()) {
            return elementInfo.hasPreparedASN1ElementInfo() && elementInfo.getPreparedASN1ElementInfo().hasDefaultValue();
        } else if (field.isAnnotationPresent(ASN1Element.class)) {
            return field.getAnnotation(ASN1Element.class).hasDefaultValue();
        } else {
            return false;
        }
    }

    public static boolean isOptional(ElementInfo elementInfo) {
        if (elementInfo.hasPreparedInfo()) {
            return elementInfo.getPreparedASN1ElementInfo().isOptional() || elementInfo.getPreparedASN1ElementInfo().hasDefaultValue();
        } else {
            return elementInfo.getASN1ElementInfo() != null && elementInfo.getASN1ElementInfo().isOptional();
        }
    }

    /** Throws IllegalArgumentException when the given field is not marked as optional in given elementInfo */
    public static void checkForOptionalField(Field field, ElementInfo elementInfo) throws Exception {
        if (isOptionalField(field, elementInfo)) {
            return;
        }
        throw new IllegalArgumentException("The mandatory field '" + field.getName() + "' does not have a value!");
    }

    public static boolean isSequenceSet(ElementInfo elementInfo) {
        if (elementInfo.hasPreparedInfo()) {
            return ((ASN1SequenceMetadata) elementInfo.getPreparedInfo().getTypeMetadata()).isSet();
        } else {
            return elementInfo.getAnnotatedClass().getAnnotation(ASN1Sequence.class).isSet();
        }
    }

    public static boolean isSequenceSetOf(ElementInfo elementInfo) {
        if (elementInfo.hasPreparedInfo()) {
            return ((ASN1SequenceOfMetadata) elementInfo.getPreparedInfo().getTypeMetadata()).isSetOf();
        } else {
            return elementInfo.getAnnotatedClass().getAnnotation(ASN1SequenceOf.class).isSetOf();
        }
    }

    public static Method findMethodForField(String methodName, Class<?> objectClass, Class<?> paramClass) throws NoSuchMethodException {
        try {
            return objectClass.getMethod(methodName, new Class[]{paramClass});
        } catch (NoSuchMethodException ex) {
            Method[] methods = objectClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method;
                }
            }
            throw ex;
        }
    }

    public static Method findSetterMethodForField(Field field, Class<?> objectClass, Class<?> paramClass) throws NoSuchMethodException {
        String methodName = "set" + field.getName().toUpperCase().substring(0, 1) + field.getName().substring(1);
        return findMethodForField(methodName, objectClass, paramClass);
    }

    public static Method findDoSelectMethodForField(Field field, Class<?> objectClass, Class<?> paramClass) throws NoSuchMethodException {
        String methodName = "select" + field.getName().toUpperCase().substring(0, 1) + field.getName().substring(1);
        return findMethodForField(methodName, objectClass, paramClass);
    }

    public static Method findGetterMethodForField(Field field, Class<?> objectClass) throws NoSuchMethodException {
        String getterMethodName = "get" + field.getName().toUpperCase().substring(0, 1) + field.getName().substring(1);
        return objectClass.getMethod(getterMethodName, (java.lang.Class[]) null);
    }

    public static Method findIsSelectedMethodForField(Field field, Class<?> objectClass) throws NoSuchMethodException {
        String methodName = "is" + field.getName().toUpperCase().substring(0, 1) + field.getName().substring(1) + "Selected";
        return objectClass.getMethod(methodName, (java.lang.Class[]) null);
    }

    public static boolean isMemberClass(Class<?> objectClass, ElementInfo elementInfo) {
        return elementInfo.hasPreparedInfo() ? elementInfo.getPreparedInfo().isMemberClass() : objectClass.isMemberClass();
    }

    public static byte[] ASN1StringToBuffer(Object obj, ElementInfo elementInfo) throws UnsupportedEncodingException {
        int stringTag = getStringTagForElement(elementInfo);
        if (stringTag == UniversalTag.UTF8String) {
            return obj.toString().getBytes("utf-8");
        } else if (stringTag == UniversalTag.BMPString) {
            return obj.toString().getBytes("UnicodeBigUnmarked");
        } else {
            return obj.toString().getBytes();
        }
    }

    public static String bufferToASN1String(byte[] byteBuf, ElementInfo elementInfo) throws UnsupportedEncodingException {
        int stringTag = getStringTagForElement(elementInfo);
        if (stringTag == UniversalTag.UTF8String) {
            return new String(byteBuf, "utf-8");
        } else if (stringTag == UniversalTag.BMPString) {
            return new String(byteBuf, "UnicodeBigUnmarked");
        } else {
            return new String(byteBuf);
        }
    }

    public static Class<?> getCollectionType(ElementInfo elementInfo) {
        ParameterizedType tp = (ParameterizedType) elementInfo.getGenericInfo();
        return getCollectionType(tp);
    }

    public static Class<?> getCollectionType(ParameterizedType tp) {
        Type tpParam = tp.getActualTypeArguments()[0];
        Class<?> paramType;
        if (tpParam instanceof GenericArrayType) {
            paramType = (Class<?>) ((GenericArrayType) tpParam).getGenericComponentType();
            if (paramType.equals(byte.class)) {
                paramType = byte[].class;
            }
        } else {
            paramType = (Class<?>) tp.getActualTypeArguments()[0];
        }
        return paramType;
    }
    
    /** Sets the default values for the fields of given object */
    public static void initDefaultValues(Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (object instanceof IASN1PreparedElement) {
            ((IASN1PreparedElement) object).initWithDefaults();
        } else {
            Method method = object.getClass().getMethod("initWithDefaults");
            method.invoke(object);
        }
    }
    
    /**
     * Compares given objects for equality.
     * Alternatively, the equals() method could be added to all generated classes.
     */
    public static boolean equals(Object obj1, Object obj2) throws ReflectiveOperationException {
        if ( obj1==null && obj2==null ) {
            return true;
        } else if ( (obj1==null && obj2!=null) || (obj1!=null && obj2==null) ) {
            return false;
        } else if ( obj1 instanceof byte[] && obj2 instanceof byte[] ) {
            return Arrays.equals((byte[])obj1, (byte[])obj2);
        } else if ( obj1 instanceof Collection && obj2 instanceof Collection ) {
            // compare individual collection items using this method
            if ( ((Collection)obj1).size()!=((Collection)obj2).size() ) {
                return false;
            } else {
                Iterator it1 = ((Iterable)obj1).iterator();
                Iterator it2 = ((Iterable)obj2).iterator();
                while ( it1.hasNext() ) {
                    if ( !equals(it1.next(), it2.next()) ) {
                        return false;
                    }
                }
                return true;
            }
        } else if ( (obj1.getClass().isAnnotationPresent(ASN1BoxedType.class) || obj1.getClass().isAnnotationPresent(ASN1Enum.class)) && obj1.getClass().equals(obj2.getClass()) ) {
            // compare boxed values using this method
            Method getValueMethod = obj1.getClass().getMethod("getValue");
            return equals(getValueMethod.invoke(obj1), getValueMethod.invoke(obj2));
        } else if ( obj1.getClass().isAnnotationPresent(ASN1Sequence.class) && obj1.getClass().equals(obj2.getClass()) ) {
            // compare all sequence fields using this method
            for (Field field : obj1.getClass().getDeclaredFields()) {
                if ( !field.isSynthetic() && !field.getType().equals(IASN1PreparedElementData.class) ) {
                    Object fieldValue1 = findGetterMethodForField(field, obj1.getClass()).invoke(obj1);
                    Object fieldValue2 = findGetterMethodForField(field, obj1.getClass()).invoke(obj2);
                    if (!equals(fieldValue1, fieldValue2)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return obj1.equals(obj2);
        }
    }
}
