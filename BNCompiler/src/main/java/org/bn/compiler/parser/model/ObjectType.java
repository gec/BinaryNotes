package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class ObjectType {
    
    public final String BUILTINTYPE = "OBJECT-TYPE";
    
    public String            accessPart;
    public ArrayList<Object> elements;
    public String            statusPart;
    public Object            type;
    public AsnValue          value;

    public ObjectType() {
        elements = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "AccessPart is " + accessPart;
    }
}
