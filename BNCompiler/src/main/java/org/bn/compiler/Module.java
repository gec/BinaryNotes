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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Module {

    /** Module name. Used to locate the XSLs and as a suffix for generated files */
    private final String moduleName;
    private final String outputDir;
    private final List<String> moduleFiles;

    public Module(String moduleName, String outputDir) throws IOException, URISyntaxException {
        this.moduleName = moduleName;
        this.outputDir = outputDir;
        
        this.moduleFiles = new ArrayList<String>(1);
        URL modulesURL = this.getClass().getResource("/modules");
        if ( "file".equals(modulesURL.getProtocol()) ) { // when running tests
            File moduleFolder = new File(new File(modulesURL.toURI()), moduleName);
            if ( moduleFolder.isDirectory() ) {
                for (File file: moduleFolder.listFiles()) {
                    if ( file.isFile() ) {
                        String filePath = file.getCanonicalPath().replace("\\", "/");
                        this.moduleFiles.add(filePath.substring(filePath.lastIndexOf("/modules/")));
                    }
                }
            }
        } else if ( "jar".equals(modulesURL.getProtocol()) ) { // when running from JAR
            String jarPath = modulesURL.getPath().substring(5, modulesURL.getPath().indexOf('!')); //strip out only the JAR file path
            Enumeration<JarEntry> entries = new JarFile(URLDecoder.decode(jarPath, "UTF-8")).entries(); // gives ALL entries in jar
            Pattern moduleFilePattern = Pattern.compile("modules/"+moduleName+"/[^/]+");
            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement().getName();
                if ( moduleFilePattern.matcher(entryName).matches() ) {
                    this.moduleFiles.add('/' + entryName);
                }
            }
        } else {
            throw new IOException("Unexpected resource location: "+modulesURL);
        }
        
        if ( this.moduleFiles.isEmpty() ) {
            throw new IllegalArgumentException("Unknown module name '"+moduleName+"'");
        }
    }
    
    public void translate(InputStream stream) throws TransformerException {
        new File(this.outputDir).mkdirs();
        
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setURIResolver(new URIResolver() {
            @Override
            public Source resolve(String href, String base) throws TransformerException {
                String systemId = base.substring(0, base.lastIndexOf('/')+1) + href;
                String resourcePath = systemId.substring(systemId.indexOf("/modules/"));;
                return new StreamSource(this.getClass().getResourceAsStream(resourcePath), systemId);
            }
        });

        for (String xslFile : this.moduleFiles) {
            Transformer transformer = factory.newTransformer(new StreamSource(this.getClass().getResourceAsStream(xslFile), xslFile));
            transformer.setErrorListener(new ErrorListener() {
                @Override
                public void warning(TransformerException exception) {
                    System.err.println("[W] Warning:" + exception);
                }

                @Override
                public void error(TransformerException exception) {
                    System.err.println("[!] Error:" + exception);
                }

                @Override
                public void fatalError(TransformerException exception) {
                    System.err.println("[!!!] Fatal error:" + exception);
                }
            });

            File outputFile = createOutputFileForInput(xslFile.substring(xslFile.lastIndexOf('/')+1));
            transformer.transform(new StreamSource(stream), new StreamResult(outputFile));
        }
    }
    
    private File createOutputFileForInput(String inputName) {
        return new File(getOutputDir(), inputName.substring(0, inputName.lastIndexOf('.')) + "." + getModuleName());
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public String getOutputDir() {
        return this.outputDir;
    }
}
