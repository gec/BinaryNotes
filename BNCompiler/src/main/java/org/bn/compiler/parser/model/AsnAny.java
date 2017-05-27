package org.bn.compiler.parser.model;

public class AsnAny {
    
    public final String BUILTINTYPE = "ANY";
    public String       definedByType;
    public boolean      isDefinedBy;
    public String       name;

    public AsnAny() {
        name          = "";
        isDefinedBy   = false;
        definedByType = "";
    }

    @Override
    public String toString() {
        return name;
    }
}
