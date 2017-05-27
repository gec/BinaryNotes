package org.bn.compiler.parser.model;

public class AsnDefinedValue {
    
    public boolean isDotPresent;
    public String  moduleIdentifier;
    public String  name;

    public AsnDefinedValue() {
        isDotPresent = false;
    }

    @Override
    public String toString() {
        return isDotPresent ? moduleIdentifier + "." + name : name;
    }
}

