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
package org.bn.compiler;

import antlr.ANTLRException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;
import org.bn.compiler.parser.ASNLexer;
import org.bn.compiler.parser.ASNParser;
import org.bn.compiler.parser.model.ASN1Model;
import org.bn.compiler.parser.model.ASNModule;
import org.lineargs.LineArgsParser;

public class Main {

    private static final String VERSION = "1.5.4";
    
    private CompilerArgs arguments = null;

    public static void main(String args[]) {
        try {
            System.out.println("BinaryNotes compiler v" + VERSION);
            System.out.println("        (c) 2006-2014 Abdulla G. Abdurakhmanov, Pavel Drasil");
            new Main().start(args);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void start(String[] args) throws Exception {
        LineArgsParser parser = new LineArgsParser();
        if (args.length > 0) {
            arguments = parser.parse(CompilerArgs.class, args);
            Module module = new Module(arguments.getModuleName(), arguments.getOutputDir());
            startForModule(module);
        } else {
            parser.printHelp(CompilerArgs.class, System.out);
        }
    }

    private void startForModule(Module module) throws TransformerException, JAXBException, IOException, ANTLRException {
        if (!arguments.getGenerateModelOnly()) {
            System.out.println("Current directory: " + new File(".").getCanonicalPath());
            System.out.println("Compiling file: " + arguments.getInputFileName());
            
            ByteArrayOutputStream outputXml = new ByteArrayOutputStream(65535);
            createModel(outputXml, module);
            module.translate(new ByteArrayInputStream(outputXml.toByteArray()));
        } else {
            createModel(System.out, null);
        }
    }
    
    private void createModel(OutputStream outputXml, Module module) throws JAXBException, FileNotFoundException, ANTLRException {
        ASN1Model model = createModelFromStream();
        if (module != null) {
            model.outputDirectory = module.getOutputDir();
            if (arguments.getNamespace() != null) {
                model.moduleNS = arguments.getNamespace();
            } else {
                model.moduleNS = model.module.moduleIdentifier.name.toLowerCase();
            }
        }
        
        JAXBContext jc = JAXBContext.newInstance("org.bn.compiler.parser.model");
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(model, outputXml);
    }

    private ASN1Model createModelFromStream() throws FileNotFoundException, ANTLRException {
        InputStream stream = new FileInputStream(arguments.getInputFileName());
        ASNLexer lexer = new ASNLexer(stream);
        ASNParser parser = new ASNParser(lexer);
        
        ASNModule module = new ASNModule();
        parser.module_definition(module);

        ASN1Model model = new ASN1Model();
        model.module = module;
        return model;
    }
}
