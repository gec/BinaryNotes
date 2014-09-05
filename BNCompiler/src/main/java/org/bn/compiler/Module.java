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
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Module {

    private final String moduleName;
    private final String outputDir;
    private final String modulesPath;
    private final File[] moduleFiles;

    public Module(String modulesPath, String moduleName, String outputDir) throws FileNotFoundException {
        if (!new File(modulesPath).isDirectory()) {
            throw new FileNotFoundException("modulesPath must be an existing directory!");
        }
        if (!new File(modulesPath, moduleName).isDirectory()) {
            throw new FileNotFoundException("modulesPath directory does not contain directory for given moduleName!");
        }
        
        this.modulesPath = modulesPath;
        this.moduleName = moduleName;
        this.outputDir = outputDir;
        this.moduleFiles = new File(modulesPath, moduleName).listFiles();
    }
    
    public void translate(InputStream stream) throws TransformerException {
        new File(this.outputDir).mkdirs();
        
        TransformerFactory factory = TransformerFactory.newInstance();

        for (File xslFile : this.moduleFiles) {
            if (xslFile.isFile()) {
                Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
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

                File outputFile = createOutputFileForInput(xslFile);
                transformer.transform(new StreamSource(stream), new StreamResult(outputFile));
            }
        }
    }
    
    private File createOutputFileForInput(File input) {
        String fileName = input.getName().substring(0, input.getName().lastIndexOf(".")) + "." + getModuleName();
        return new File(getOutputDir(), fileName);
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public String getModulesPath() {
        return this.modulesPath;
    }

    public String getOutputDir() {
        return this.outputDir;
    }
}
