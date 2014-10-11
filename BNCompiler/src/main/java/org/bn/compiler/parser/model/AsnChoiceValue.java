package org.bn.compiler.parser.model;

public class AsnChoiceValue {
    
    public String   name;
    public AsnValue value;

    public AsnChoiceValue() {
    }

    @Override
    public String toString() {
        return name + "\t:" + value;
    }
}
