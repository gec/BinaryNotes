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
package org.bn.compiler.parser;

import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.bn.compiler.parser.model.ASN1Model;
import org.bn.compiler.parser.model.ASNModule;
import static org.junit.Assert.*;
import org.junit.Test;

public class ASNParserTest {

    private ASN1Model createFromStream() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/test.asn");
        ASNLexer lexer = new ASNLexer(stream);
        ASNParser parser = new ASNParser(lexer);
        ASNModule module = new ASNModule();

        parser.module_definition(module);

        ASN1Model model = new ASN1Model();
        model.module = module;
        return model;
    }

    @Test
    public void testJaxb() throws Exception {
        ASN1Model model = createFromStream();
        model.outputDirectory = "testworkdir" + File.separator + "output";
        model.moduleNS = "test_asn";
        
        Marshaller marshaller = JAXBContext.newInstance("org.bn.compiler.parser.model").createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(model, System.out);
        //marshaller.marshal(model, new FileOutputStream("temp.xml"));
    }

    /**
     * @see ASNParser#module_definition(ASNModule)
     */
    @Test
    public void testModule_definition() throws Exception {
        ASN1Model model = createFromStream();

        assertEquals("TEST_ASN", model.module.moduleIdentifier.name);
        assertEquals(20, model.module.asnTypes.sequenceSets.size());
        assertEquals(2, model.module.asnTypes.enums.size());
        assertEquals(7, model.module.asnTypes.characterStrings.size());
        assertEquals(1, model.module.asnTypes.octetStrings.size());
        assertEquals(8, model.module.asnTypes.sequenceSetsOf.size());
    }
}
