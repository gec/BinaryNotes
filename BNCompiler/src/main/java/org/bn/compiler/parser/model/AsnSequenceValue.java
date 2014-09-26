package org.bn.compiler.parser.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

//~--- classes ----------------------------------------------------------------

public class AsnSequenceValue {
    public boolean                  isValPresent;
    public ArrayList<AsnNamedValue> namedValueList;

    //~--- constructors -------------------------------------------------------

    // Default Constructor
    public AsnSequenceValue() {
        namedValueList = new ArrayList<AsnNamedValue>();
    }

    //~--- methods ------------------------------------------------------------

    // toString Method
    @Override
    public String toString() {
        String ts = "";
        
        for (AsnNamedValue nv: namedValueList) {
            ts += nv;
        }

        return ts;
    }
}
