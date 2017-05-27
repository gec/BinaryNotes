package org.bn.compiler.parser.model;

public class ErrorMacro {
    
    public boolean isDefinedType;    // Type is Defined or builtin type
    public boolean isParameter;
    public String  name;
    public String  parameterName;
    public String  typeName;         // Name of the Defined Type
    public Object  typeReference;

    public ErrorMacro() {
    }

    @Override
    public String toString() {
        String ts = name + "\t::=\tERROR";

        if (isParameter) {
            if (parameterName != null && !parameterName.isEmpty()) {
                ts += "PARAMETER\t" + parameterName;
            } else {
                ts += "PARAMETER\t" + (isDefinedType ? typeName : typeReference.getClass().getName());
            }
        }

        return ts;
    }
}
