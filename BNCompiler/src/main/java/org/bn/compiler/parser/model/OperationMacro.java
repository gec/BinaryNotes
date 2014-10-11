package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class OperationMacro {
    
    public String            argumentName;            // Argument Type Name
    public Object            argumentType;            // Holds the argument Type
    public String            argumentTypeIdentifier;  // Argument NamedType identifier
    public ArrayList<Object> errorList;
    public boolean           isArgumentName;
    public boolean           isErrors;
    public boolean           isLinkedOperation;
    public boolean           isResult;
    public boolean           isResultName;
    public ArrayList<Object> linkedOpList;
    public String            name;
    public String            resultName;              // Result Type Name
    public Object            resultType;              // Holds the resultType
    public String            resultTypeIdentifier;    // Result NamedType identifier

    public OperationMacro() {
        errorList    = new ArrayList<Object>();
        linkedOpList = new ArrayList<Object>();
    }

    // Get the first linked operationName
    public String get_firstLinkedOpName() {
        Object obj = linkedOpList.get(0);
        if (obj instanceof AsnValue) {
            return "isValue";
        } else if (obj instanceof AsnDefinedType) {
            return ((AsnDefinedType) obj).typeName;
        } else {
            try {
                return (String)obj.getClass().getField("name").get(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public String toString() {
        String ts = name + "\t::=\t OPERATION";

        if (isArgumentName) {
            ts += "ARGUMENT\t\t" + argumentName;
        }

        if (isResult) {
            ts += "RESULT\t\t" + resultName;
        }

        if (isLinkedOperation) {
            ts += "LINKED OPERATION\t";
            for (Object obj: linkedOpList) {
                ts += obj;
            }
            ts += "}";
        }

        if (isErrors) {
            ts += "ERRORS\t{";
            for (Object obj: errorList) {
                ts += obj;
            }
            ts += "}";
        }

        return ts;
    }
}
