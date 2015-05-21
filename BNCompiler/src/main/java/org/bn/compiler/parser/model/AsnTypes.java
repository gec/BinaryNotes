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
        anys              = new ArrayList<>();
        bitStrings        = new ArrayList<>();
        booleans          = new ArrayList<>();
        characterStrings  = new ArrayList<>();
        choices           = new ArrayList<>();
        enums             = new ArrayList<>();
        integers          = new ArrayList<>();
        nulls             = new ArrayList<>();
        objectIdentifiers = new ArrayList<>();
        octetStrings      = new ArrayList<>();
        reals             = new ArrayList<>();
        sequenceSets      = new ArrayList<>();
        sequenceSetsOf    = new ArrayList<>();
        externals         = new ArrayList<>();
        relativeOids      = new ArrayList<>();
        selections        = new ArrayList<>();
        taggeds           = new ArrayList<>();
        defineds          = new ArrayList<>();
        macroOperations   = new ArrayList<>();
        macroErrors       = new ArrayList<>();
        macroObjectTypes  = new ArrayList();
    }

    @Override
    public String toString() {
        return "";
    }
}

