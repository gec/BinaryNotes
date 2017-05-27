package org.bn.compiler.parser.model;

public class AsnTaggedType {
    
    public boolean isDefinedType;    // Distinguish between builtin and defined types
    public String name;
    public AsnTag tag;
    public String tagDefault;
    public String typeName;         // Name of defined type
    public Object typeReference;    // Type Reference

    public AsnTaggedType() {
        name          = "";
        tagDefault    = "";
        typeReference = null;
        isDefinedType = false;
        typeName      = "";
    }

    @Override
    public String toString() {
        String ts = name + "\t" + tag + "\t" + tagDefault + "\t";

        if (isDefinedType) {
            ts += typeName;
        } else {
            ts += typeReference.getClass().getName();
        }

        return ts;
    }
}

