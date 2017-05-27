/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 Original sources are available at www.latestbit.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.bn.coders;

public interface UniversalTag {
    int Reserved0          =   0;
    int Boolean            =   1;
    int Integer            =   2;
    int Bitstring          =   3;
    int OctetString        =   4;
    int Null               =   5;
    int ObjectIdentifier   =   6;
    int ObjectDescriptor   =   7;
    int External           =   8;
    int Real               =   9;
    int Enumerated         =   10;
    int EmbeddedPdv        =   11;
    int UTF8String         =   12;
    int RelativeObject     =   13;
    int Reserved14         =   14;
    int Reserved15         =   15;
    int Sequence           =   16;
    int Set                =   17;
    int NumericString      =   18;
    int PrintableString    =   19;
    int TeletexString      =   20;
    int VideotexString     =   21;
    int IA5String          =   22;
    int UTCTime            =   23;
    int GeneralizedTime    =   24;
    int GraphicString      =   25;
    int VisibleString      =   26;
    int GeneralString      =   27;
    int UniversalString    =   28;
    int UnspecifiedString  =   29;
    int BMPString          =   30;
    int LastUniversal      =   31;
}
