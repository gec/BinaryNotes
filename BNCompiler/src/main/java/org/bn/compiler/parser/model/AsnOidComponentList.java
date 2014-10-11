package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class AsnOidComponentList {
    
    public ArrayList<AsnOidComponent> components;
    public AsnDefinedValue            defval;
    public boolean                    isDefinitive;

    public AsnOidComponentList() {
        components = new ArrayList<AsnOidComponent>();
    }

    @Override
    public String toString() {
        String ts = "";

        if (isDefinitive) {
            ts += defval;
        }

        if (components != null) {
            for ( AsnOidComponent component: components ) {
                ts += component;
            }
        }

        return ts;
    }
}
