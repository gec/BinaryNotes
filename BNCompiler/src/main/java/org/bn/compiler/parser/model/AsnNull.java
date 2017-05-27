package org.bn.compiler.parser.model;

public class AsnNull {
    
    final String BUILTINTYPE = "NULL";
    public String name;
    public boolean isNull = true;

    public AsnNull() {
        name = "";
    }

    @Override
    public String toString() {
        return name + "\t::=\t" + BUILTINTYPE;
    }
}
