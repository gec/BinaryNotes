package org.bn.compiler.parser.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

//~--- classes ----------------------------------------------------------------

public class AsnCharacterStringValue {
    String                            cStr;
    public ArrayList<CharDef>         charDefsList;
    public boolean                    isCharDefList;
    boolean                           isQuadruple;
    public boolean                    isTuple;
    public ArrayList<AsnSignedNumber> tupleQuad;

    //~--- constructors -------------------------------------------------------

    // Default Constructor
    public AsnCharacterStringValue() {
        charDefsList = new ArrayList<CharDef>();
        tupleQuad    = new ArrayList<AsnSignedNumber>();
    }

    //~--- methods ------------------------------------------------------------

    @Override
    public String toString() {
        String ts = "";

        if (isTuple || isQuadruple) {
            for  ( AsnSignedNumber sn: tupleQuad) {
                ts += sn + "\n";
            }
        } else if (isCharDefList) {
            for ( CharDef chDef: charDefsList ) {
                ts += chDef;
            }
        }

        return ts;
    }
}
