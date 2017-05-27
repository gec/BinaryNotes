package org.bn.compiler.parser.model;

public class AsnChoice {
    
    final String              BUILTINTYPE = "CHOICE";
    public AsnElementTypeList elementTypeList;
    public String             name;
    public final boolean      isChoice = true;

    public AsnChoice() {
        name = "";
    }

    @Override
    public String toString() {
        String ts = name + "\t::=\t" + BUILTINTYPE + "\t {";

        if (elementTypeList != null) {
            for (AsnElementType elementType: elementTypeList.elements) {
                ts += elementType;
            }
        }

        ts += "}";

        return ts;
    }
}
