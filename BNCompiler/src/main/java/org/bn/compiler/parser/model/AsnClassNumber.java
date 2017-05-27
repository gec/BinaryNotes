package org.bn.compiler.parser.model;

public class AsnClassNumber {
    
    public String  name;
    public Integer num;

    public AsnClassNumber() {
    }

    @Override
    public String toString() {
        return num==null ? name : num.toString();
    }
}
