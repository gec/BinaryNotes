package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class AsnSequenceOfValue {
    
    boolean                    isValPresent;
    public ArrayList<AsnValue> value;

    public AsnSequenceOfValue() {
        value = new ArrayList<AsnValue>();
    }

    @Override
    public String toString() {
        String ts = "";
        
        for (AsnValue val: value) {
            ts += val;
        }

        return ts;
    }
}
