package org.bn.compiler.parser.model;

public class AsnNamedNumber {
    
    public AsnDefinedValue definedValue;
    public boolean         isSignedNumber;
    public String          name;
    public AsnSignedNumber signedNumber;

    public AsnNamedNumber() {
        name = "";
    }

    @Override
    public String toString() {
        String ts = name + "\t(";

        if (isSignedNumber) {
            ts += signedNumber;
        } else {
            ts += definedValue;
        }

        ts += ")";

        return ts;
    }
}
