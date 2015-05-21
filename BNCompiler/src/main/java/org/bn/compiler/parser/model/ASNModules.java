package org.bn.compiler.parser.model;

import java.util.ArrayList;

/** Instances of this class hold all the parsed Modules */
public class ASNModules {
    
    final ArrayList<ASNModule> module_list;

    ASNModules() {
        module_list = new ArrayList<>();
    }

    public void add(ASNModule module) {
        module_list.add(module);
    }

    @Override
    public String toString() {
        String ts = "";
        
        for ( ASNModule module: module_list ) {
            ts += module;
        }

        return ts;
    }
}
