package org.bn.compiler.parser.model;

public class NamedConstraint {
    
    public AsnConstraint constraint;
    public boolean       isAbsentKw;
    public boolean       isConstraint;
    public boolean       isOptionalKw;
    public boolean       isPresentKw;
    public String        name;

    public NamedConstraint() {
    }

    @Override
    public String toString() {
        String ts = name;

        if (isConstraint) {
            ts += constraint;
        }

        if (isPresentKw) {
            ts += "\tPRESENT";
        }

        if (isAbsentKw) {
            ts += "\tABSENT";
        }

        if (isOptionalKw) {
            ts += "\tOPTIONAL";
        }

        return ts;
    }
}
