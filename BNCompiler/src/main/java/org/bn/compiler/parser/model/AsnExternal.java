package org.bn.compiler.parser.model;

public class AsnExternal {
    
    final String  BUILTINTYPE = "EXTERNAL";
    public String name;

    public AsnExternal() {
    }

    @Override
    public String toString() {
        return BUILTINTYPE;
    }
}
