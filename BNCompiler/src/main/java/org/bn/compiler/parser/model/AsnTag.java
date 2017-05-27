package org.bn.compiler.parser.model;

public class AsnTag {
    
    public AsnClassNumber classNumber;
    public String         clazz;

    public AsnTag() {
        clazz = "";
    }

    @Override
    public String toString() {
        String ts = "[";

        if (clazz!=null && !clazz.isEmpty()) {
            ts += clazz;
        }

        if (classNumber != null) {
            ts += classNumber;
        }

        ts += "]";

        return ts;
    }
}
