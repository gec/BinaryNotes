package org.bn.compiler.parser.model;

public class AsnReal {
    
    public final String BUILTINTYPE = "REAL";
    public String       name;

    public AsnReal() {
        name = "";
    }

    @Override
    public String toString() {
        return name + "\t::=\t" + BUILTINTYPE;
    }
}
