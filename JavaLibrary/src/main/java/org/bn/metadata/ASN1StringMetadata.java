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
package org.bn.metadata;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.AnnotatedElement;
import org.bn.annotations.ASN1String;
import org.bn.coders.DecodedObject;
import org.bn.coders.ElementInfo;
import org.bn.coders.IASN1TypesDecoder;
import org.bn.coders.IASN1TypesEncoder;
import org.bn.coders.UniversalTag;

/**
 * @author jcfinley@users.sourceforge.net
 */
public class ASN1StringMetadata extends ASN1FieldMetadata {

    private final boolean isUCS;
    private int stringType = UniversalTag.PrintableString;
    private final boolean hasDefaults;

    public ASN1StringMetadata() {
        this.isUCS = false;
        this.hasDefaults = true;
    }

    public ASN1StringMetadata(ASN1String annotation) {
        this(annotation.name(), annotation.isUCS(), annotation.stringType());
    }

    public ASN1StringMetadata(String name, boolean isUCS, int stringType) {
        super(name);
        this.isUCS = isUCS;
        this.stringType = stringType;
        this.hasDefaults = false;
    }

    public boolean isUCS() {
        return this.isUCS;
    }

    public int getStringType() {
        return this.stringType;
    }

    @Override
    public void setParentAnnotated(AnnotatedElement parent) {
        if (parent != null && parent.isAnnotationPresent(ASN1String.class)) {
            this.stringType = parent.getAnnotation(ASN1String.class).stringType();
        }
    }

    @Override
    public int encode(IASN1TypesEncoder encoder, Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        return encoder.encodeString(object, stream, elementInfo);
    }

    @Override
    public DecodedObject<?> decode(IASN1TypesDecoder decoder, DecodedObject<Integer> decodedTag, Class<?> objectClass, ElementInfo elementInfo, InputStream stream) throws Exception {
        return decoder.decodeString(decodedTag, objectClass, elementInfo, stream);
    }
}
