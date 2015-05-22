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
package org.bn.coders.der;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.SortedMap;
import org.bn.coders.CoderUtils;
import org.bn.coders.ElementInfo;
import org.bn.coders.ElementType;
import org.bn.coders.TagClass;
import org.bn.coders.UniversalTag;
import org.bn.coders.ber.BERCoderUtils;
import org.bn.coders.ber.BEREncoder;

public class DEREncoder extends BEREncoder {

    public DEREncoder() {
    }

    @Override
    public int encodeSequence(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        // ASN1Sequence seqInfo = elementInfo.getAnnotatedClass().getAnnotation(ASN1Sequence.class);
        if (!CoderUtils.isSequenceSet(elementInfo)) {
            return super.encodeSequence(object, stream, elementInfo);
        } else {
            int resultSize = 0;
            Field[] fields;
            if (elementInfo.hasPreparedInfo()) {
                fields = elementInfo.getPreparedInfo().getFields();
            } else {
                SortedMap<Integer, Field> fieldOrder = CoderUtils.getSetOrder(object.getClass());
                fields = fieldOrder.values().toArray(new Field[fieldOrder.size()]);
            }

            for (int i = 0; i < fields.length; i++) {
                resultSize += encodeSequenceField(object, fields.length - 1 - i, fields[fields.length - 1 - i], stream, elementInfo);
            }
            resultSize += encodeHeader(BERCoderUtils.getTagValueForElement(elementInfo, TagClass.UNIVERSAL, ElementType.CONSTRUCTED, UniversalTag.Set), resultSize, stream);
            return resultSize;
        }
    }
}
