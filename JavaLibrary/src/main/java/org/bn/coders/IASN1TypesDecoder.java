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

import java.io.InputStream;
import java.lang.reflect.Field;
import org.bn.types.BitString;
import org.bn.types.ObjectIdentifier;

public interface IASN1TypesDecoder {
    DecodedObject<Integer> decodeTag(InputStream stream) throws Exception ;
    DecodedObject<?> decodeClassType(DecodedObject<Integer> decodedTag, Class<?> objectClass, ElementInfo elementInfo, InputStream stream) throws Exception;
    DecodedObject decodeSequence(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception;
    DecodedObject decodeChoice(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream)  throws Exception;    
    DecodedObject<Boolean> decodeBoolean(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception;
    DecodedObject<byte[]> decodeAny(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    <T> DecodedObject<T> decodeNull(DecodedObject<Integer> decodedTag, Class<T> objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    DecodedObject decodeInteger(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    DecodedObject<Double> decodeReal(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    DecodedObject<byte[]> decodeOctetString(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    DecodedObject<BitString> decodeBitString(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    DecodedObject<ObjectIdentifier> decodeObjectIdentifier(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    DecodedObject<String> decodeString(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    DecodedObject decodeSequenceOf(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;    
    <T> DecodedObject<T> decodeEnum(DecodedObject<Integer> decodedTag, Class<T> objectClass, ElementInfo elementInfo, InputStream stream) throws Exception;
    DecodedObject<Integer> decodeEnumItem(DecodedObject<Integer> decodedTag, Class objectClass, Class enumClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    DecodedObject decodeBoxedType(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception;
    DecodedObject decodeElement(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception;
    DecodedObject decodePreparedElement(DecodedObject<Integer> decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception;
    void invokeSetterMethodForField(Field field, Object object, Object param, ElementInfo elementInfo) throws Exception;
    void invokeSelectMethodForField(Field field, Object object, Object param, ElementInfo elementInfo) throws Exception;
}
