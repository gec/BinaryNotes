package org.bn.compiler.parser.model;

public class AsnOidComponent {
    
    public AsnDefinedValue defval;
    public boolean         isDefinedValue;
    public String          name;
    public boolean         nameAndNumberForm;
    public boolean         nameForm;
    public Integer         num;
    public boolean         numberForm;

    public AsnOidComponent() {
        name = "";
        num  = null;
    }

    @Override
    public String toString() {
        String ts = "";

        if (numberForm) {
            ts += num + "\t";
        } else if (nameForm) {
            ts += name;

            if (nameAndNumberForm) {
                ts += "(" + num + ")\t";
            }
        } else if (isDefinedValue) {
            ts += defval;
        }

        return ts;
    }
}
