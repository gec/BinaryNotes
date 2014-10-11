package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class ConstraintElements {
    
    public AsnConstraint              constraint;
    public ElementSetSpec             elespec;
    public boolean                    isAlphabetConstraint;
    public boolean                    isElementSetSpec;
    public boolean                    isIncludeType;
    public boolean                    isLEndLess;
    public boolean                    isMaxKw;
    public boolean                    isMinKw;
    public boolean                    isPatternValue;
    public boolean                    isSizeConstraint;
    public boolean                    isTypeConstraint;
    public boolean                    isUEndLess;
    public boolean                    isValue;
    public boolean                    isValueRange;
    public boolean                    isWithComponent;
    public boolean                    isWithComponents;
    public AsnValue                   lEndValue, uEndValue;
    public Object                     type;
    public ArrayList<NamedConstraint> typeConstraintList;
    public AsnValue                   value;

    public ConstraintElements() {
        typeConstraintList = new ArrayList<NamedConstraint>();
    }

    @Override
    public String toString() {
        String ts = "";

        if (isValue) {
            ts += value;
        } else if (isValueRange) {
            ts += lEndValue + ".." + uEndValue;
        } else if (isSizeConstraint) {
            ts += constraint;
        } else if (isElementSetSpec) {
            ts += elespec;
        } else if (isPatternValue) {
            ts += "PATTERN" + value;
        } else if (isWithComponent) {
            ts += "WITH COMPONENT " + constraint;
        } else if (isWithComponents) {
            ts += "WITH COMPONENTS" + "\t";

            for (NamedConstraint namedConstraint: typeConstraintList) {
                ts += namedConstraint;
            }
        } else if (isAlphabetConstraint) {
            ts += "FROM" + constraint;
        }

        return ts;
    }
}
