package org.bn.compiler.parser.model;

public class AsnCharacterString {
    
    public final String  BUILTINTYPE = "CHARACTER STRING";
    public AsnConstraint constraint;
    public boolean       isUCSType;    // Is Unrestricted Character String Type
    public String        name;
    public String        stringtype;

    public AsnCharacterString() {
        name       = "";
        stringtype = "";
    }

    @Override
    public String toString() {
        String ts = name + "\t" + stringtype;

        if (constraint != null) {
            ts += constraint;
        }

        return ts;
    }
}

