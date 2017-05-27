package org.bn.compiler.parser.model;

public class AsnEnum {
    
    final String              BUILTINTYPE = "ENUMERATED";
    public String             name;
    public AsnNamedNumberList namedNumberList;
    public String             snaccName;    // specifically added for snacc code generation
    public Boolean            isEnum = true;

    public AsnEnum() {
        name      = "";
        snaccName = "";
    }

    @Override
    public String toString() {
        String ts = name + "\t::=" + BUILTINTYPE + "\t{";

        if (namedNumberList != null) {
            for (AsnNamedNumber namedNumber: namedNumberList.namedNumbers) {
                ts += namedNumber;
            }
        }

        ts += "}";

        return ts;
    }
}
