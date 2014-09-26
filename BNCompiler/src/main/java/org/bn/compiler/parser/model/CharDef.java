package org.bn.compiler.parser.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;

//~--- classes ----------------------------------------------------------------

public class CharDef {
    public String                     cStr;
    public AsnDefinedValue            defval;
    public boolean                    isCString;
    boolean                           isDefinedValue;
    public boolean                    isQuadruple;
    public boolean                    isTuple;
    public ArrayList<AsnSignedNumber> tupleQuad;

    //~--- constructors -------------------------------------------------------

    // Default Constructor
    public CharDef() {
        tupleQuad = new ArrayList<AsnSignedNumber>();
    }

    //~--- methods ------------------------------------------------------------

    @Override
    public String toString() {
        String ts = "";

        if (isCString) {
            ts += ("\t" + cStr);
        } else if (isTuple || isQuadruple) {
            for (AsnSignedNumber sn: tupleQuad) {
                ts += sn + "\n";
            }
        } else if (isDefinedValue) {
            ts += ("\t" + defval);
        }

        return ts;
    }
}
