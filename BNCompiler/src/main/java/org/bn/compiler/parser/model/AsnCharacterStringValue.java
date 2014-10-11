package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class AsnCharacterStringValue {
    
    String                            cStr;
    public ArrayList<CharDef>         charDefsList;
    public boolean                    isCharDefList;
    boolean                           isQuadruple;
    public boolean                    isTuple;
    public ArrayList<AsnSignedNumber> tupleQuad;

    public AsnCharacterStringValue() {
        charDefsList = new ArrayList<CharDef>();
        tupleQuad    = new ArrayList<AsnSignedNumber>();
    }

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
