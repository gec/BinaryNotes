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
package org.bn.coders.per;

import org.bn.coders.CoderTestUtilities;

public class PERUnalignedCoderTestUtils extends CoderTestUtilities {

    @Override
    public byte[] createNullSeqBytes() {
        return new byte[0];
    }

    @Override
    public byte[] createTaggedNullSeqBytes() {
        return new byte[0];
    }

    @Override
    public byte[] createEnumBytes() {
        return new byte[]{0x20};
    }

    @Override
    public byte[] createITUSeqBytes() {
        return new byte[]{(byte) 0x82, (byte) 0xE1, (byte) 0xC3, (byte) 0x87,
            0x0E, 0x10, 0x5C, 0x58, (byte) 0xB1, 0x62, (byte) 0xC4,
            0x0B, (byte) 0x8F, 0x1E, 0x3C, 0x78, (byte) 0xC1,
            0x71, (byte) 0xE3, (byte) 0xC7, (byte) 0x8F,
            0x18, 0x2E, 0x3C, 0x78, (byte) 0xF1, (byte) 0xE3,
            0x05, (byte) 0xC9, (byte) 0x93, 0x26, 0x4C, (byte) 0x80,
            (byte) 0xB9, 0x72, (byte) 0xE5, (byte) 0xCB, (byte) 0x94};
    }

    @Override
    public byte[] createDataSeqBytes() {
        return new byte[]{0x40, 0x00, 0x01, (byte) 0xF0, (byte) 0xE1, (byte) 0xC3,
            (byte) 0x87, 0x0E, 0x1C, 0x20, 0x37, 0x40, 0x10, 0x04, 0x40, 0x20, 0x6C, 0x58, (byte) 0xB1,
            0x62, (byte) 0xC5, (byte) 0x88, 0x17, 0x1E, 0x3C, 0x78, (byte) 0xF1, (byte) 0x80, (byte) 0x80,
            0x7C, (byte) 0xB9, 0x72, (byte) 0xE5, (byte) 0xCB, (byte) 0x97, 0x2F, (byte) 0xF8};
    }

    @Override
    public byte[] createSequenceWithEnumBytes() {
        return new byte[]{0x05, (byte) 0xC3, (byte) 0x87, 0x0E, 0x1C, 0x24, (byte) 0x80};
    }

    @Override
    public byte[] createTestInteger1Bytes() {
        return new byte[]{0x0F};
    }

    @Override
    public byte[] createTestInteger2Bytes() {
        return new byte[]{0x0F, (byte) 0xF0};
    }

    @Override
    public byte[] createTestInteger3Bytes() {
        return new byte[]{(byte) 0xFF, (byte) 0xF0};
    }

    @Override
    public byte[] createTestInteger4Bytes() {
        return new byte[]{
            0x00, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0
        };
    }

    @Override
    public byte[] createSeqWithNullBytes() {
        return new byte[]{0x03, (byte) 0xE7, (byte) 0xCF, (byte) 0x98, 0x1E, 0x4C, (byte) 0x99, 0x00};
    }

    @Override
    public byte[] createTestRecursiveDefinitionBytes() {
        return new byte[]{(byte) 0x82, (byte) 0xE1, (byte) 0xC3, (byte) 0x87,
            0x0E, 0x10, 0x2E, 0x2C, 0x58, (byte) 0xB1, 0x62};
    }

    @Override
    public byte[] createUnboundedTestIntegerBytes() {
        return new byte[]{
            0x04, 0x00, (byte) 0xFA, (byte) 0xFB, (byte) 0xFC
        };
    }

    @Override
    public byte[] createTestIntegerRBytes() {
        return new byte[]{0x40};
    }

    @Override
    public byte[] createTestInteger2_12Bytes() {
        return new byte[]{0x3f, (byte) 0xe2};
    }

    @Override
    public byte[] createTestPRNBytes() {
        return new byte[]{0x05, (byte) 0x91, (byte) 0x97, 0x66, (byte) 0xCD, (byte) 0xE0};
    }

    @Override
    public byte[] createTestOCTBytes() {
        return new byte[]{0x05, 0x01, 0x02, (byte) 0xFF, 0x03, 0x04};
    }

    @Override
    public byte[] createDataSeqMOBytes() {
        return new byte[]{0x7F, 0x01, 0x40, 0x00, 0x00, (byte) 0xF8, 0x70, (byte) 0xE1,
            (byte) 0xC3, (byte) 0x87, 0x0E, 0x10, 0x1A, (byte) 0xB8, 0x08, 0x00, 0x00, 0x10,
            0x36, 0x2C, 0x58, (byte) 0xB1, 0x62, (byte) 0xC4, 0x0B, (byte) 0x8F,
            0x1E, 0x3C, 0x78, (byte) 0xC0, (byte) 0x80, 0x3E, 0x5C,
            (byte) 0xB9, 0x72, (byte) 0xE5, (byte) 0xCB, (byte) 0x94, 0x02,
            0x66, (byte) 0xCD, (byte) 0x9B, 0x35, 0x50, 0x0B, 0x04, (byte) 0xC9, (byte) 0x93, 0x26, 0x40};
    }

    @Override
    public byte[] createDataChoiceTestOCTBytes() {
        return new byte[]{0x40, 0x3f, (byte) 0xe0};
    }

    @Override
    public byte[] createDataChoiceSimpleTypeBytes() {
        return new byte[]{0x60, (byte) 0xF8, 0x70, (byte) 0xE1, (byte) 0xC3, (byte) 0x87,
            0x0E, 0x10};
    }

    @Override
    public byte[] createDataChoiceBooleanBytes() {
        return new byte[]{(byte) 0xB0};
    }

    @Override
    public byte[] createDataChoiceIntBndBytes() {
        return new byte[]{(byte) 0xE0, (byte) 0xE0};
    }

    @Override
    public byte[] createDataChoicePlainBytes() {
        return new byte[]{0x00, (byte) 0xD8, (byte) 0xB1, 0x62, (byte) 0xC5, (byte) 0x8B,
            0x10};
    }

    @Override
    public byte[] createStringArrayBytes() {
        return new byte[]{0x02, 0x06, (byte) 0xC5, (byte) 0x8B, 0x16, 0x2C, 0x58, (byte) 0x81, 0x71, (byte) 0xE3, (byte) 0xC7, (byte) 0x8F,
            0x18};
    }

    @Override
    public byte[] createTestNIBytes() {
        return new byte[]{0x3c, 0x00};
    }

    @Override
    public byte[] createTestNI2Bytes() {
        return new byte[]{0x01, (byte) 0x80};
    }

    @Override
    public byte[] createSetBytes() {
        return new byte[]{(byte) 0x82, 0x61, (byte) 0xC3, (byte) 0x87, 0x08, 0x10, 0x05, 0x50, 0x26, 0x2C, 0x58, (byte) 0xB1,
            0x00};
    }
    
    @Override
    public byte[] createSetWithDefaultValueBytes() {
        return new byte[]{0x02, 0x61, (byte) 0xC3, (byte) 0x87, 0x08, 0x10, 0x05, 0x50};
    }
    
    @Override
    public byte[] createSequenceWithDefaultValuesBytes() {
        return new byte[]{0x00, 0x01, 0x00, 0x55, 0x00};
    }

    @Override
    public byte[] createTestBitStrBytes() {
        return new byte[]{0x24, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xF0};
    }

    @Override
    public byte[] createTestBitStrSmallBytes() {
        return new byte[]{0x0C, (byte) 0xAA, (byte) 0xB0};
    }

    @Override
    public byte[] createUnicodeStrBytes() {
        return new byte[]{0x06, (byte) 0xD1, (byte) 0xA5, (byte) 0xD1, (byte) 0xA4, (byte) 0xD1, (byte) 0xA6};
    }

    @Override
    public byte[] createTestSequenceV12Bytes() {
        return new byte[]{(byte) 0xB0, 0x3C, 0x38, (byte) 0xB0, (byte) 0xF8, (byte) 0xF1, (byte) 0xE3, (byte) 0xC6, 0x0D, (byte) 0x93,
            0x26, 0x4C, (byte) 0x99, 0x32, 0x10, 0x4C, 0x38, 0x70, (byte) 0xE1, 0x03, (byte) 0xC5, (byte) 0x8B,
            0x10, 0x7C, (byte) 0xCC, 0x3F, 0x3A, 0x0A, 0x0B, 0x0C, 0x0D};
    }

    @Override
    public byte[] createTestBitStrBndBytes() {
        return new byte[]{0x3f};
    }

    @Override
    public byte[] createChoiceInChoiceBytes() {
        return new byte[]{0x20, 0x20, (byte) 0xA0};
    }

    @Override
    public byte[] createTaggedSeqInSeqBytes() {
        return new byte[]{0x04, (byte) 0xC3, (byte) 0x87, 0x0E, 0x10, 0x4C, 0x58, (byte) 0xB1, 0x62};
    }

    @Override
    public byte[] createTestReal0_5Bytes() {
        return new byte[]{0x03, (byte) 0x80, (byte) 0xFF, 0x01};
    }

    @Override
    public byte[] createTestReal1_5Bytes() {
        return new byte[]{0x03, (byte) 0x80, (byte) 0xFF, 0x03};
    }

    @Override
    public byte[] createTestReal2Bytes() {
        return new byte[]{0x03, (byte) 0x80, (byte) 0x01, 0x01};
    }

    @Override
    public byte[] createTestRealBigBytes() {
        return new byte[]{0x05, (byte) 0x80, (byte) 0xFD, 0x18, 0x6D, 0x21};
    }
    
    @Override
    public byte[] createTestRealPosInfBytes() {
        return new byte[] {0x01, 0x40};
    }

    @Override
    public byte[] createTestRealNegInfBytes() {
        return new byte[] {0x01, 0x41};
    }

    @Override
    public byte[] createTestRealNeg1_5Bytes() {
        return new byte[] {0x03, (byte) 0xC0, (byte) 0xFF, 0x03};
    }

    @Override
    public byte[] createChoiceInChoice2Bytes() {
        return new byte[]{0x01, 0x20, 0x20, (byte) 0xA0};
    }

    @Override
    public byte[] createChoiceInChoice3Bytes() {
        return new byte[]{0x02, (byte) 0x80, 0x20, 0x10, 0x05, (byte) 0x90};
    }

    @Override
    public byte[] createTestLongTagBytes() {
        return new byte[]{0x02, 0x00, (byte) 0xAA};
    }

    @Override
    public byte[] createTestLongTag2Bytes() {
        return new byte[]{0x03, 0x00, (byte) 0xFE, (byte) 0xED};
    }

    @Override
    public byte[] createUTF8StringArrayBytes() {
        return new byte[]{0x02, 0x06, 0x62, 0x62, 0x62, 0x62, 0x62, 0x62, 0x05, 0x63, 0x63, 0x63, 0x63, 0x63};
    }

    @Override
    public byte[] createTestOID1Bytes() {
        return new byte[]{0x03, 0x55, 0x04, 0x06};
    }

    @Override
    public byte[] createTestOID2Bytes() {
        return new byte[]{0x09, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7,
            0x0D, 0x01, 0x01, 0x05};
    }

    @Override
    public byte[] createTestOID3Bytes() {
        return new byte[]{0x04, 0x28, (byte) 0xC2, 0x7B, 0x02};
    }

    @Override
    public byte[] createTestOID4Bytes() {
        return new byte[]{0x04, 0x67, 0x2A, 0x03, 0x00};
    }

    @Override
    public byte[] createTaggedSetBytes() {
        return new byte[]{0x01, (byte) 0xC0, (byte) 0x80, 0x2A, (byte) 0x80, (byte) 0x80, 0x2E, (byte) 0xC0, (byte) 0x80, 0x33, 0x00};
    }

    @Override
    public byte[] createTaggedSetInSetBytes() {
        return new byte[]{0x01, (byte) 0xC0, (byte) 0x80, 0x2A, (byte) 0x80, (byte) 0x80, 0x2E, (byte) 0xC0, (byte) 0x80, 0x33, 0x00, 0x70, 0x20, 0x0A, (byte) 0xA0, 0x20, 0x0B, (byte) 0xB0, 0x20, 0x0C, (byte) 0xC0};
    }

    @Override
    public byte[] createSet7Bytes() {
        return new byte[]{0x01, 0x01, 0x01, 0x44, 0x01, 0x01, 0x01, 0x44};
    }
}
