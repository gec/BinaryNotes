package org.bn.compiler.parser.model;

public class AsnBitString {
    
    public final String       BUILTINTYPE = "BIT STRING";
    public AsnConstraint      constraint;
    public String             name;
    public AsnNamedNumberList namedNumberList;

    public AsnBitString() {
        name = "";
    }

    @Override
    public String toString() {
        String ts = (name + "\t::=\t" + BUILTINTYPE + "\n {");

        if (namedNumberList != null) {
            for (AsnNamedNumber namedNumber: namedNumberList.namedNumbers) {
                ts += namedNumber;
            }
        }

        ts += "}";

        if (constraint != null) {
            ts += constraint;
        }

        return ts;
    }
}
