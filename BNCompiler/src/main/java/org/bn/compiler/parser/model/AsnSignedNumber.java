package org.bn.compiler.parser.model;

import java.math.BigInteger;

public class AsnSignedNumber {
    
    public BigInteger num;
    public boolean    positive;

    public AsnSignedNumber() {
        positive = true;
    }

    @Override
    public String toString() {
        return num==null ? "Signed Number is Null" : num.toString();
    }
}
