package org.bn.compiler.parser.model;

public class AsnObjectIdentifier {
    
    public final String  BUILTINTYPE = "OBJECT IDENTIFIER";
    public String name;

    public AsnObjectIdentifier() {
        name = "";
    }

    @Override
    public String toString() {
        return name + "\t::=\t" + BUILTINTYPE;
    }
}
