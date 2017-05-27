package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class AsnNamedNumberList {
    
    public ArrayList<AsnNamedNumber> namedNumbers;

    public AsnNamedNumberList() {
        namedNumbers = new ArrayList<>();
    }

    /** Returns the total number of elements in the list */
    public int count() {
        return namedNumbers.size();
    }
}
