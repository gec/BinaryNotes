package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class Intersection {
    
    public ArrayList<ConstraintElements> cnsElemList;
    public ArrayList<ConstraintElements> exceptCnsElem;
    public boolean                       isExcept;
    public boolean                       isInterSection;

    public Intersection() {
        cnsElemList   = new ArrayList<ConstraintElements>();
        exceptCnsElem = new ArrayList<ConstraintElements>();
    }

    @Override
    public String toString() {
        String ts = "";

        for (ConstraintElements c: cnsElemList) {
            ts += "\t" + c;
        }

        if (isExcept) {
            ts += " EXCEPT";

            for (ConstraintElements c: exceptCnsElem) {
                ts += "\t" + c;
            }
        }

        return ts;
    }
}
