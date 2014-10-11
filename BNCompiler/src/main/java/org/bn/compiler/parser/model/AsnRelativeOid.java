package org.bn.compiler.parser.model;

public class AsnRelativeOid {
    
    final String  BUILTINTYPE = "RELATIVE-OID";
    public String name;

    public AsnRelativeOid() {
    }

    @Override
    public String toString() {
        return BUILTINTYPE;
    }
}

