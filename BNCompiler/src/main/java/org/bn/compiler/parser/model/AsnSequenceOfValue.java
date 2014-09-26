package org.bn.compiler.parser.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

//~--- classes ----------------------------------------------------------------

public class AsnSequenceOfValue {
    boolean                    isValPresent;
    public ArrayList<AsnValue> value;

    //~--- constructors -------------------------------------------------------

    // Default Constructor
    public AsnSequenceOfValue() {
        value = new ArrayList<AsnValue>();
    }

    //~--- methods ------------------------------------------------------------

    // toString Method
    @Override
    public String toString() {
        String ts = "";
        
        for (AsnValue val: value) {
            ts += val;
        }

        return ts;
    }
}
