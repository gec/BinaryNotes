package org.bn.compiler.parser.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement public class ASN1Model implements Serializable {    
    public String    outputDirectory;
    public String    moduleNS;
    public ASNModule module;
}
