package org.bn.compiler.parser.model;

public class AsnSequenceSet {
    
    final String              BUILTINTYPE  = "SEQUENCE";
    final String              BUILTINTYPE1 = "SET";
    public AsnElementTypeList elementTypeList;
    public boolean            isSequence;
    public String             name;    // Name of Sequence

    public AsnSequenceSet() {
        name       = "";
        isSequence = false;
    }

    @Override
    public String toString() {
        String ts = name;

        if (isSequence) {
            ts += "\t::=" + BUILTINTYPE + "\t";
        } else {
            ts += "\t::=" + BUILTINTYPE1 + "\t";
        }

        ts += "{";

        if (elementTypeList != null) {
            for (AsnElementType elementType: elementTypeList.elements) {
                ts += elementType;
            }
        }

        ts += "}";

        return ts;
    }
}
