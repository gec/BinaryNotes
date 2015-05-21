package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class AsnSequenceValue {
    
    public boolean                  isValPresent;
    public ArrayList<AsnNamedValue> namedValueList;

    public AsnSequenceValue() {
        namedValueList = new ArrayList<>();
    }

    @Override
    public String toString() {
        String ts = "";
        
        for (AsnNamedValue nv: namedValueList) {
            ts += nv;
        }

        return ts;
    }
}
