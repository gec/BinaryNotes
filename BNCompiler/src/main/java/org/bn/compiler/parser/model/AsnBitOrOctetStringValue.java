package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class AsnBitOrOctetStringValue {
    
    public String            bhStr;
    public ArrayList<String> idlist;
    public boolean           isBString;
    public boolean           isHString;

    public AsnBitOrOctetStringValue() {
        idlist = new ArrayList<>();
    }

    @Override
    public String toString() {
        String ts = "";

        if (isHString || isBString) {
            ts += bhStr;
        } else {
            if (idlist != null) {
                for (String s: idlist) {
                    ts += s;
                }
            }
        }

        return ts;
    }
}
