package org.bn.compiler.parser.model;

import java.util.ArrayList;

public class AsnTypes {
    
    public ArrayList<AsnAny> anys;
    public ArrayList<AsnBitString> bitStrings;
    public ArrayList<AsnBoolean> booleans;
    public ArrayList<AsnCharacterString> characterStrings;
    public ArrayList<AsnChoice> choices;
    public ArrayList<AsnDefinedType> defineds;
    public ArrayList<AsnEmbedded> embeddeds;
    public ArrayList<AsnEnum> enums;
    public ArrayList<AsnExternal> externals;
    public ArrayList<AsnInteger> integers;
    public ArrayList<ErrorMacro> macroErrors;
    public ArrayList macroObjectTypes;
    public ArrayList<OperationMacro> macroOperations;
    public ArrayList<AsnNull> nulls;
    public ArrayList<AsnObjectIdentifier> objectIdentifiers;
    public ArrayList<AsnOctetString> octetStrings;
    public ArrayList<AsnReal> reals;
    public ArrayList<AsnRelativeOid> relativeOids;
    public ArrayList<AsnSelectionType> selections;
    public ArrayList<AsnSequenceSet> sequenceSets;
    public ArrayList<AsnSequenceOf> sequenceSetsOf;
    public ArrayList<AsnTaggedType> taggeds;

    AsnTypes() {
        anys              = new ArrayList<AsnAny>();
        bitStrings        = new ArrayList<AsnBitString>();
        booleans          = new ArrayList<AsnBoolean>();
        characterStrings  = new ArrayList<AsnCharacterString>();
        choices           = new ArrayList<AsnChoice>();
        enums             = new ArrayList<AsnEnum>();
        integers          = new ArrayList<AsnInteger>();
        nulls             = new ArrayList<AsnNull>();
        objectIdentifiers = new ArrayList<AsnObjectIdentifier>();
        octetStrings      = new ArrayList<AsnOctetString>();
        reals             = new ArrayList<AsnReal>();
        sequenceSets      = new ArrayList<AsnSequenceSet>();
        sequenceSetsOf    = new ArrayList<AsnSequenceOf>();
        externals         = new ArrayList<AsnExternal>();
        relativeOids      = new ArrayList<AsnRelativeOid>();
        selections        = new ArrayList<AsnSelectionType>();
        taggeds           = new ArrayList<AsnTaggedType>();
        defineds          = new ArrayList<AsnDefinedType>();
        macroOperations   = new ArrayList<OperationMacro>();
        macroErrors       = new ArrayList<ErrorMacro>();
        macroObjectTypes  = new ArrayList();
    }

    @Override
    public String toString() {
        return "";
    }
}

