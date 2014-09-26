package org.bn.compiler.parser.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

//~--- classes ----------------------------------------------------------------

//To hold all the parsed Modules

/**This class defines the class holding for ASN.1 modules and basic Types
 */
public class ASNModules {
    ArrayList<ASNModule> module_list;

    //~--- constructors -------------------------------------------------------

    // Default Constructor
    ASNModules() {
        module_list = new ArrayList<ASNModule>();
    }

    //~--- methods ------------------------------------------------------------

    public void add(ASNModule module) {
        module_list.add(module);
    }

    // toString Method
    @Override
    public String toString() {
        String ts = "";
        
        for ( ASNModule module: module_list ) {
            ts += module;
        }

        return ts;
    }
}
