package org.bn.compiler.parser.model;

public class AsnInteger {
    
    public final String       BUILTINTYPE = "INTEGER";
    public AsnConstraint      constraint;
    public String             name;
    public AsnNamedNumberList namedNumberList;

    public AsnInteger() {
        name = "";
    }

    @Override
    public String toString() {
        String ts = name + "\t::=" + BUILTINTYPE + "\t";

        if (namedNumberList != null) {
            for (AsnNamedNumber namedNumber: namedNumberList.namedNumbers) {
                ts += namedNumber;
            }
        }

        if (constraint != null) {
            ts += constraint;
        }

        return ts;
    }
}
