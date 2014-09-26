package org.bn.compiler.parser.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

//~--- classes ----------------------------------------------------------------

//
//DefinitionofOID_Component_LIST
//
public class AsnOidComponentList {
    public ArrayList<AsnOidComponent> components;
    public AsnDefinedValue            defval;
    public boolean                    isDefinitive;

    //~--- constructors -------------------------------------------------------

    // Default Constructor
    public AsnOidComponentList() {
        components = new ArrayList<AsnOidComponent>();
    }

    //~--- methods ------------------------------------------------------------

    // toString Method
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
