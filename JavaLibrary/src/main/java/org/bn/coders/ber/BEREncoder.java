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
package org.bn.coders.ber;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import org.bn.annotations.ASN1EnumItem;
import org.bn.coders.CoderUtils;
import org.bn.coders.DecodedObject;
import org.bn.coders.ElementInfo;
import org.bn.coders.ElementType;
import org.bn.coders.Encoder;
import org.bn.coders.TagClass;
import org.bn.coders.UniversalTag;
import org.bn.metadata.ASN1SequenceOfMetadata;
import org.bn.types.BitString;
import org.bn.types.ObjectIdentifier;
import org.bn.utils.ReverseByteArrayOutputStream;

public class BEREncoder extends Encoder {

    public BEREncoder() {
    }

    @Override
    public void encode(Object object, OutputStream stream) throws Exception {
        ReverseByteArrayOutputStream reverseStream = new ReverseByteArrayOutputStream();
        super.encode(object, reverseStream);
        reverseStream.writeTo(stream);
    }

    @Override
    public int encodeSequence(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        Field[] fields = elementInfo.getFields(object.getClass());

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[ fields.length - 1 - i];
            resultSize += encodeSequenceField(object, fields.length - 1 - i, field, stream, elementInfo);
        }
        if (!CoderUtils.isSequenceSet(elementInfo)) {
            resultSize += encodeHeader(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.CONSTRUCTED, UniversalTag.Sequence), resultSize, stream);
        } else {
            resultSize += encodeHeader(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.CONSTRUCTED, UniversalTag.Set), resultSize, stream);
        }
        return resultSize;
    }

    @Override
    public int encodeChoice(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        int sizeOfChoiceField = super.encodeChoice(object, stream, elementInfo);

        if ((elementInfo.hasPreparedInfo() && elementInfo.hasPreparedASN1ElementInfo() && elementInfo.getPreparedASN1ElementInfo().hasTag())
                || (elementInfo.getASN1ElementInfo() != null && elementInfo.getASN1ElementInfo().hasTag())) {
            resultSize += encodeHeader(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.CONTEXT_SPECIFIC, ElementType.CONSTRUCTED, UniversalTag.LastUniversal), sizeOfChoiceField, stream);
        }
        resultSize += sizeOfChoiceField;
        return resultSize;
    }

    @Override
    public int encodeEnumItem(Object enumConstant, Class enumClass, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        ASN1EnumItem enumObj = elementInfo.getAnnotatedClass().getAnnotation(ASN1EnumItem.class);
        int szOfInt = encodeIntegerValue(enumObj.tag(), stream);
        resultSize += szOfInt;
        resultSize += encodeLength(szOfInt, stream);
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, UniversalTag.Enumerated), stream);
        return resultSize;
    }

    @Override
    public int encodeBoolean(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 1;
        stream.write((Boolean) object ? 0xFF : 0x00);

        resultSize += encodeLength(1, stream);
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, UniversalTag.Boolean), stream);
        return resultSize;
    }

    @Override
    public int encodeAny(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        byte[] buffer = (byte[]) object;
        stream.write(buffer);
        
        CoderUtils.checkConstraints(buffer.length, elementInfo);
        return buffer.length;
    }

    protected int encodeIntegerValue(long value, OutputStream stream) throws Exception {
        int resultSize = CoderUtils.getIntegerLength(value);
        for (int i = 0; i < resultSize; i++) {
            stream.write((byte) value);
            value = value >> 8;
        }
        return resultSize;
    }

    @Override
    public int encodeInteger(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int szOfInt = 0;
        if (object instanceof Integer) {
            Integer value = (Integer) object;
            CoderUtils.checkConstraints(value, elementInfo);
            szOfInt = encodeIntegerValue(value, stream);
        } else if (object instanceof Long) {
            Long value = (Long) object;
            CoderUtils.checkConstraints(value, elementInfo);
            szOfInt = encodeIntegerValue(value, stream);
        }
        
        int resultSize = szOfInt;
        resultSize += encodeLength(szOfInt, stream);
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, UniversalTag.Integer), stream);
        return resultSize;
    }

    @Override
    public int encodeReal(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        Double value = (Double) object;
        //CoderUtils.checkConstraints(value,elementInfo);
        int szOfInt = 0;
        long asLong = Double.doubleToLongBits(value);
        if (asLong == 0x7ff0000000000000L) { // positive infinity
            stream.write(0x40); // 01000000 Value is PLUS-INFINITY
            szOfInt = 1;
        } else if (asLong == 0xfff0000000000000L) { // negative infinity            
            stream.write(0x41); // 01000001 Value is MINUS-INFINITY
            szOfInt = 1;
        } else if (asLong != 0) {
            long exponent = ((0x7ff0000000000000L & asLong) >> 52) - 1023 - 52;
            long mantissa = 0x000fffffffffffffL & asLong;
            mantissa |= 0x10000000000000L; // set virtual delimeter

            // pack mantissa for base 2
            while ((mantissa & 0xFFL) == 0x0) {
                mantissa >>= 8;
                exponent += 8; //increment exponent to 8 (base 2)
            }
            while ((mantissa & 0x01L) == 0x0) {
                mantissa >>= 1;
                exponent += 1; //increment exponent to 1
            }

            szOfInt += encodeIntegerValue(mantissa, stream);
            int szOfExp = CoderUtils.getIntegerLength(exponent);
            szOfInt += encodeIntegerValue(exponent, stream);

            int realPreamble = 0x80 | (byte)(szOfExp - 1);
            if ((asLong & 0x8000000000000000L) == 0x8000000000000000L) {
                realPreamble |= 0x40; // Sign
            }
            stream.write(realPreamble);
            szOfInt += 1;
        }
        resultSize += szOfInt;
        resultSize += encodeLength(szOfInt, stream);
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, UniversalTag.Real), stream);
        return resultSize;
    }

    @Override
    public int encodeOctetString(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        byte[] buffer = (byte[]) object;
        stream.write(buffer);

        CoderUtils.checkConstraints(buffer.length, elementInfo);
        
        int resultSize = buffer.length;
        resultSize += encodeLength(buffer.length, stream);
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, UniversalTag.OctetString), stream);
        return resultSize;
    }

    @Override
    public int encodeBitString(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        BitString str = (BitString) object;
        CoderUtils.checkConstraints(str.getLengthInBits(), elementInfo);

        byte[] buffer = str.getValue();
        stream.write(buffer);
        stream.write(str.getTrailBitsCnt());
        int sizeOfString = buffer.length + 1;

        int resultSize = sizeOfString;
        resultSize += encodeLength(sizeOfString, stream);
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, UniversalTag.Bitstring), stream);
        return resultSize;
    }

    @Override
    public int encodeString(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        byte[] strBuf = CoderUtils.ASN1StringToBuffer(object, elementInfo);
        stream.write(strBuf);
        int sizeOfString = strBuf.length;

        CoderUtils.checkConstraints(sizeOfString, elementInfo);
        
        int resultSize = sizeOfString;
        resultSize += encodeLength(sizeOfString, stream);
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, CoderUtils.getStringTagForElement(elementInfo)), stream);
        return resultSize;
    }

    @Override
    public int encodeSequenceOf(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        @SuppressWarnings("unchecked")
        Object[] collection = ((java.util.Collection<Object>) object).toArray();
        
        int sizeOfCollection = 0;
        for (int i = 0; i < collection.length; i++) {
            Object obj = collection[collection.length - 1 - i];
            ElementInfo info = new ElementInfo();
            info.setAnnotatedClass(obj.getClass());
            info.setParentAnnotated(elementInfo.getAnnotatedClass());
            if (elementInfo.hasPreparedInfo()) {
                ASN1SequenceOfMetadata seqOfMeta = (ASN1SequenceOfMetadata) elementInfo.getPreparedInfo().getTypeMetadata();
                info.setPreparedInfo(seqOfMeta.getItemClassMetadata());
            }
            sizeOfCollection += encodeClassType(obj, stream, info);
        }
        
        CoderUtils.checkConstraints(collection.length, elementInfo);
        
        int resultSize = sizeOfCollection;
        resultSize += encodeLength(sizeOfCollection, stream);
        if (!CoderUtils.isSequenceSetOf(elementInfo)) {
            resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.CONSTRUCTED, UniversalTag.Sequence), stream);
        } else {
            resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.CONSTRUCTED, UniversalTag.Set), stream);
        }
        return resultSize;
    }

    protected int encodeHeader(DecodedObject<Integer> tagValue, int contentLen, OutputStream stream) throws Exception {
        int resultSize = encodeLength(contentLen, stream);
        resultSize += encodeTag(tagValue, stream);
        return resultSize;
    }

    protected int encodeTag(DecodedObject<Integer> tagValue, OutputStream stream) throws Exception {
        int resultSize = tagValue.getSize();
        int value = tagValue.getValue();
        for (int i = 0; i < tagValue.getSize(); i++) {
            stream.write((byte) value);
            value = value >> 8;
        }
        return resultSize;
    }

    protected int encodeLength(int length, OutputStream stream) throws IOException {
        int resultSize = 0;

        if (length < 0) {
            throw new IllegalArgumentException();
        } else if (length < 128) {
            stream.write(length);
            resultSize++;
        } else if (length < 256) {
            stream.write(length);
            stream.write((byte) 0x81);
            resultSize += 2;
        } else if (length < 65536) {
            stream.write((byte) (length));
            stream.write((byte) (length >> 8));
            stream.write((byte) 0x82);
            resultSize += 3;
        } else if (length < 16777126) {
            stream.write((byte) (length));
            stream.write((byte) (length >> 8));
            stream.write((byte) (length >> 16));
            stream.write((byte) 0x83);
            resultSize += 4;
        } else {
            stream.write((byte) (length));
            stream.write((byte) (length >> 8));
            stream.write((byte) (length >> 16));
            stream.write((byte) (length >> 24));
            stream.write((byte) 0x84);
            resultSize += 5;
        }
        return resultSize;
    }

    @Override
    public int encodeNull(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        stream.write(0);
        int resultSize = 1;
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, UniversalTag.Null), stream);
        return resultSize;
    }

    @Override
    public int encodeObjectIdentifier(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        ObjectIdentifier oid = (ObjectIdentifier) object;
        int[] ia = oid.getIntArray();
        byte[] buffer = BERObjectIdentifier.Encode(ia);
        stream.write(buffer, 0, buffer.length);
        int resultSize = buffer.length;
        resultSize += encodeLength(resultSize, stream);
        resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.PRIMITIVE, UniversalTag.ObjectIdentifier), stream);
        return resultSize;
    }
}
