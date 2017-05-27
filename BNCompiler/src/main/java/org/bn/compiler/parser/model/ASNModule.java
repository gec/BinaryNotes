package org.bn.compiler.parser.model;

import java.util.ArrayList;


/** This class defines the class holding for ASN.1 modules and basic Types */
public class ASNModule {
    
    public AsnTypes                     asnTypes;
    public ArrayList<AsnValue>          asnValues;
    public ArrayList<String>            exportSymbolList;
    public boolean                      exported;
    public boolean                      extensible;
    public ArrayList<SymbolsFromModule> importSymbolFromModuleList;
    ArrayList                           importSymbolList;
    public boolean                      imported;
    public AsnModuleIdentifier          moduleIdentifier;    // Name of Module
    public boolean                      tag;
    public String                       tagDefault;

    public ASNModule() {
        exportSymbolList           = new ArrayList<>();
        importSymbolList           = new ArrayList();
        importSymbolFromModuleList = new ArrayList<>();
        asnTypes                   = new AsnTypes();
        asnValues                  = new ArrayList<>();
        tagDefault                 = "";
    }

    @Override
    public String toString() {
        String   ts = "";

        ts += "MODULE NAME ::= \n";
        ts += moduleIdentifier + "\n";
        
        ts += "IMPORT SYMBOL LIST" + "\n";
        for (Object obj: importSymbolList) {
            ts += obj + "\n";
        }

        ts += "IMPORT SYMBOLS FROM MODULE \n";
        for (SymbolsFromModule sfm: importSymbolFromModuleList) {
            ts += sfm + "\n";
        }

        ts += "EXPORT SYMBOL LIST \n";
        for (String exportSymbol: exportSymbolList) {
            ts += exportSymbol + "\n";
        }

        ts += "ASN TYPES LIST \n";
        ts += asnTypes + "\n";
        
        ts += "ASN VALUES LIST \n";
        for (AsnValue val: asnValues) {
            ts += val + "\n";
        }

        return ts;
    }
}