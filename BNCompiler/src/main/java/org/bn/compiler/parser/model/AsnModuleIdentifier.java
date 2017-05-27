package org.bn.compiler.parser.model;

public class AsnModuleIdentifier {
    
    public AsnOidComponentList componentList;
    public String              name;

    public AsnModuleIdentifier() {
        componentList = new AsnOidComponentList();
    }

    @Override
    public String toString() {
        String ts = name + "  ";

        if (componentList != null) {
            ts += componentList;
        }

        return ts;
    }
}

