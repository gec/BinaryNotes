package org.bn.compiler.parser.model;

public class AsnBoolean {
    
    public final String BUILTINTYPE = "BOOLEAN";
    public String       name;

    public AsnBoolean() {
        name = "";
    }

    @Override
    public String toString() {
        return name + "\t::=\t" + BUILTINTYPE;
    }
}

