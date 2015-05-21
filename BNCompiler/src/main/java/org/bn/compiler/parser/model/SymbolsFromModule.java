package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class SymbolsFromModule {
    
    public AsnOidComponentList cmplist;
    public AsnDefinedValue     defval;
    public boolean             isDefinedValue;
    public boolean             isOidValue;
    public String              modref;
    public ArrayList<String>   symbolList;

    public SymbolsFromModule() {
        symbolList = new ArrayList<>();
    }

    @Override
    public String toString() {
        String ts = "Following SYMBOLS ::\n";
        
        for (String s: symbolList) {
            ts += s + "\n";
        }

        ts += "ARE IMPORTED FROM \n";
        ts += modref;

        if (isOidValue) {
            ts += cmplist;
        }

        if (isDefinedValue) {
            ts += defval;
        }

        return ts;
    }
}
