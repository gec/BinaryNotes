package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class ElementSetSpec {
    
    public ConstraintElements      allExceptCnselem;
    public ArrayList<Intersection> intersectionList;
    public boolean                 isAllExcept;

    public ElementSetSpec() {
        intersectionList = new ArrayList<Intersection>();
    }

    @Override
    public String toString() {
        String ts = "";
        
        for (Intersection intersection: intersectionList) {
            ts += intersection + "|";
        }

        if (isAllExcept) {
            ts += "ALL EXCEPT  " + allExceptCnselem;
        }

        return ts;
    }
}
