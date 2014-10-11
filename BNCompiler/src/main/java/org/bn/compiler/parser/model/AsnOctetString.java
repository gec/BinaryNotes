package org.bn.compiler.parser.model;

public class AsnOctetString {
    
    public final String  BUILTINTYPE = "OCTET STRING";
    public AsnConstraint constraint;
    public String        name;

    public AsnOctetString() {
        name = "";
    }

    @Override
    public String toString() {
        String ts = name + "\t::=" + BUILTINTYPE + "\t";

        if (constraint != null) {
            ts += constraint;
        }

        return ts;
    }
}
