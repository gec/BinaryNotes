package org.bn.compiler.parser.model;

public class AsnEmbedded {
    
    final String  BUILTINTYPE = "EMBEDDED PDV";
    public String name;

    public AsnEmbedded() {
    }

    @Override
    public String toString() {
        return BUILTINTYPE;
    }
}
