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

using Microsoft.VisualStudio.TestTools.UnitTesting;
using org.bn.coders.test_asn;
using org.bn.utils;

namespace org.bn.coders
{
    public abstract class EncoderTest
	{
		protected internal virtual void printEncoded(System.String details, IEncoder encoder, System.Object obj)
		{
			System.IO.MemoryStream outputStream = new System.IO.MemoryStream();
			encoder.encode(obj, outputStream);
			System.Console.Out.WriteLine("Encoded by "+encoder.ToString()+" (" + details + ") : " + ByteTools.byteArrayToHexString(outputStream.ToArray()));
		}

        private CoderTestUtilities coderTestUtils;
		
		public EncoderTest(CoderTestUtilities coderTestUtils)
		{
			this.coderTestUtils = coderTestUtils;
		}
		
		protected abstract IEncoder newEncoder();
		
		/// <seealso cref="Encoder.encode(T,OutputStream)" />
        [TestMethod]
		public virtual void testEncodeChoice()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			Data choice = new Data();
			
			choice.selectBinary(new TestOCT(new byte[]{(byte) (0xFF)}));
			printEncoded("Choice boxed octet", encoder, choice);
			checkEncoded(encoder, choice, coderTestUtils.createDataChoiceTestOCTBytes());
			
			choice.selectSimpleType("aaaaaaa");
			printEncoded("Choice string", encoder, choice);
			checkEncoded(encoder, choice, coderTestUtils.createDataChoiceSimpleTypeBytes());
			
			choice.selectBooleanType(true);
			printEncoded("Choice boolean", encoder, choice);
			checkEncoded(encoder, choice, coderTestUtils.createDataChoiceBooleanBytes());
			
			choice.selectIntBndType(7);
			printEncoded("Choice boxed int", encoder, choice);
			checkEncoded(encoder, choice, coderTestUtils.createDataChoiceIntBndBytes());
			
			choice.selectPlain(new TestPRN("bbbbbb"));
			printEncoded("Choice plain", encoder, choice);
			checkEncoded(encoder, choice, coderTestUtils.createDataChoicePlainBytes());
			
			choice.selectSimpleOctType(new byte[10]);
			printEncoded("Choice simple octet", encoder, choice);
			choice.selectIntType(7);
			printEncoded("Choice simple int", encoder, choice);
		}
		
		/// <seealso cref="Encoder.encode(T,OutputStream)" />
        [TestMethod]
		public virtual void testEncode()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("SequenceMO test", encoder, coderTestUtils.createDataSeqMO());
			checkEncoded(encoder, coderTestUtils.createDataSeqMO(), coderTestUtils.createDataSeqMOBytes());
			
			printEncoded("Sequence test", encoder, coderTestUtils.createDataSeq());
			checkEncoded(encoder, coderTestUtils.createDataSeq(), coderTestUtils.createDataSeqBytes());
		}
		
		/// <seealso cref="Encoder.encode(T,OutputStream)" />
        [TestMethod]
		public virtual void testITUEncode()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("ITUSequence test", encoder, coderTestUtils.createITUSeq());
			checkEncoded(encoder, coderTestUtils.createITUSeq(), coderTestUtils.createITUSeqBytes());
		}
		
		protected internal virtual void checkEncoded(IEncoder encoder, System.Object obj, byte[] standard)
		{
			System.IO.MemoryStream outputStream = new System.IO.MemoryStream();
			encoder.encode(obj, outputStream);

			byte[] array = outputStream.ToArray();
            Assert.AreEqual(standard.Length, array.Length);
			for (int i = 0; i < array.Length; i++)
			{
				Assert.AreEqual(standard[i], array[i]);
			}
		}

        [TestMethod]
		public virtual void testNullEncode()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("NullSequence test", encoder, coderTestUtils.createNullSeq());
			checkEncoded(encoder, coderTestUtils.createNullSeq(), coderTestUtils.createNullSeqBytes());
		}

        [TestMethod]
		public virtual void testTaggedNullEncode()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("TaggedNullSequence test", encoder, coderTestUtils.createTaggedNullSeq());
			checkEncoded(encoder, coderTestUtils.createTaggedNullSeq(), coderTestUtils.createTaggedNullSeqBytes());
		}

        [TestMethod]
		public virtual void testSequenceWithNull()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("SequenceWithNull test", encoder, coderTestUtils.createSeqWithNull());
			checkEncoded(encoder, coderTestUtils.createSeqWithNull(), coderTestUtils.createSeqWithNullBytes());
		}

        [TestMethod]
		public virtual void testEnum()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("Enum test", encoder, coderTestUtils.createEnum());
			checkEncoded(encoder, coderTestUtils.createEnum(), coderTestUtils.createEnumBytes());
		}

        [TestMethod]
		public virtual void testSequenceWithEnum()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("Sequence Enum test", encoder, coderTestUtils.createSequenceWithEnum());
			checkEncoded(encoder, coderTestUtils.createSequenceWithEnum(), coderTestUtils.createSequenceWithEnumBytes());
		}

        [TestMethod]
		public virtual void testSequenceOfString()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("Sequence Of String", encoder, coderTestUtils.createStringArray());
			checkEncoded(encoder, coderTestUtils.createStringArray(), coderTestUtils.createStringArrayBytes());
		}

        [TestMethod]
        public virtual void testSequenceOfUTFString()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("Sequence Of UTF8String", encoder, coderTestUtils.createUTF8StringArray());
            checkEncoded(encoder, coderTestUtils.createUTF8StringArray(), coderTestUtils.createUTF8StringArrayBytes());
        }

        [TestMethod]
		public virtual void testRecursiveDefinition()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("Recursive test", encoder, coderTestUtils.createTestRecursiveDefinition());
			checkEncoded(encoder, coderTestUtils.createTestRecursiveDefinition(), coderTestUtils.createTestRecursiveDefinitionBytes());
		}

        [TestMethod]
		public virtual void testEncodeInteger()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("Unbounded integer test", encoder, coderTestUtils.createUnboundedTestInteger());
			checkEncoded(encoder, coderTestUtils.createUnboundedTestInteger(), coderTestUtils.createUnboundedTestIntegerBytes());
			printEncoded("Integer2_12 test", encoder, coderTestUtils.createTestInteger2_12());
			checkEncoded(encoder, coderTestUtils.createTestInteger2_12(), coderTestUtils.createTestInteger2_12Bytes());
			
			printEncoded("IntegerR test", encoder, coderTestUtils.createTestIntegerR());
			checkEncoded(encoder, coderTestUtils.createTestIntegerR(), coderTestUtils.createTestIntegerRBytes());
			
			printEncoded("Integer4 test", encoder, coderTestUtils.createTestInteger4());
			checkEncoded(encoder, coderTestUtils.createTestInteger4(), coderTestUtils.createTestInteger4Bytes());
			printEncoded("Integer3 test", encoder, coderTestUtils.createTestInteger3());
			checkEncoded(encoder, coderTestUtils.createTestInteger3(), coderTestUtils.createTestInteger3Bytes());
			printEncoded("Integer2 test", encoder, coderTestUtils.createTestInteger2());
			checkEncoded(encoder, coderTestUtils.createTestInteger2(), coderTestUtils.createTestInteger2Bytes());
			
			printEncoded("Integer1 test", encoder, coderTestUtils.createTestInteger1());
			checkEncoded(encoder, coderTestUtils.createTestInteger1(), coderTestUtils.createTestInteger1Bytes());
		}

        [TestMethod]
		public virtual void testEncodeString()
		{
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
			printEncoded("TestPRN", encoder, coderTestUtils.createTestPRN());
			checkEncoded(encoder, coderTestUtils.createTestPRN(), coderTestUtils.createTestPRNBytes());
			
			printEncoded("TestOCT", encoder, coderTestUtils.createTestOCT());
			checkEncoded(encoder, coderTestUtils.createTestOCT(), coderTestUtils.createTestOCTBytes());
		}

        [TestMethod]
        public virtual void testNegativeInteger() 
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("Negative integer test",encoder, coderTestUtils.createTestNI());
            checkEncoded(encoder, coderTestUtils.createTestNI(), coderTestUtils.createTestNIBytes());
            printEncoded("Negative integer test 2",encoder, coderTestUtils.createTestNI2());
            checkEncoded(encoder, coderTestUtils.createTestNI2(), coderTestUtils.createTestNI2Bytes());        
        }

        [TestMethod]
        public virtual void testEncodeSet()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("Set test", encoder, coderTestUtils.createSet());
            checkEncoded(encoder, coderTestUtils.createSet(), coderTestUtils.createSetBytes());
        }

        [TestMethod]
        public virtual void testEncodeSetWithDefaultValue()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("Set test with default value", encoder, coderTestUtils.createSetWithDefaultValue());
            checkEncoded(encoder, coderTestUtils.createSetWithDefaultValue(), coderTestUtils.createSetWithDefaultValueBytes());
        }

        [TestMethod]
        public virtual void testEncodeSequenceWithDefaultValues()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("Sequence test with default values", encoder, coderTestUtils.createSequenceWithDefaultValues());
            checkEncoded(encoder, coderTestUtils.createSequenceWithDefaultValues(), coderTestUtils.createSequenceWithDefaultValuesBytes());
        }

        [TestMethod]
        public virtual void testEncodeSequenceWithUntouchedDefaultValues()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("Sequence test with untouched default values", encoder, coderTestUtils.createSequenceWithUntouchedDefaultValues());
            checkEncoded(encoder, coderTestUtils.createSequenceWithUntouchedDefaultValues(), coderTestUtils.createSequenceWithDefaultValuesBytes());
        }

        [TestMethod]
        public virtual void testEncodeBitString() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("TestBitStr test",encoder, coderTestUtils.createTestBitStr());            
            checkEncoded(encoder, coderTestUtils.createTestBitStr(), coderTestUtils.createTestBitStrBytes());
        }

        [TestMethod]
        public virtual void testEncodeBitStringSmall() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("TestBitStrSmall test",encoder, coderTestUtils.createTestBitStrSmall());
            checkEncoded(encoder, coderTestUtils.createTestBitStrSmall(), coderTestUtils.createTestBitStrSmallBytes());
        }

        [TestMethod]
        public virtual void testEncodeUnicodeString() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("TestUnicode test",encoder, coderTestUtils.createUnicodeStr());            
            checkEncoded(encoder, coderTestUtils.createUnicodeStr(), coderTestUtils.createUnicodeStrBytes());
        }

        [TestMethod]
        public void testEncodeBitStringBnd() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("TestBitStrBnd test",encoder, coderTestUtils.createTestBitStrBnd());            
            checkEncoded(encoder, coderTestUtils.createTestBitStrBnd(), coderTestUtils.createTestBitStrBndBytes());
        }

        [TestMethod]
        public virtual void testEncodeVersion1_2() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("TestEncodeVersion_1_2: ",encoder, coderTestUtils.createTestSequenceV12());            
            checkEncoded(encoder, coderTestUtils.createTestSequenceV12(), coderTestUtils.createTestSequenceV12Bytes());        
        }

        [TestMethod]
        public void testEncodeChoiceInChoice() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("EncodeChoiceInChoice: ",encoder, coderTestUtils.createChoiceInChoice());            
            checkEncoded(encoder, coderTestUtils.createChoiceInChoice(), coderTestUtils.createChoiceInChoiceBytes());        
        }

        [TestMethod]
        public void testEncodeTaggedSeqInSeq() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("EncodeTaggedSeqInSeq: ",encoder, coderTestUtils.createTaggedSeqInSeq());            
            checkEncoded(encoder, coderTestUtils.createTaggedSeqInSeq(), coderTestUtils.createTaggedSeqInSeqBytes());        
        }

        [TestMethod]
        public void testEncodeReals() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("EncodeTestReal1.5: ",encoder, coderTestUtils.createTestReal1_5());            
            checkEncoded(encoder, coderTestUtils.createTestReal1_5(), coderTestUtils.createTestReal1_5Bytes());        
            printEncoded("EncodeTestReal0.5: ",encoder, coderTestUtils.createTestReal0_5());            
            checkEncoded(encoder, coderTestUtils.createTestReal0_5(), coderTestUtils.createTestReal0_5Bytes());                
            printEncoded("EncodeTestReal2: ",encoder, coderTestUtils.createTestReal2());            
            checkEncoded(encoder, coderTestUtils.createTestReal2(), coderTestUtils.createTestReal2Bytes());        
            printEncoded("EncodeTestRealBig: ",encoder, coderTestUtils.createTestRealBig());            
            checkEncoded(encoder, coderTestUtils.createTestRealBig(), coderTestUtils.createTestRealBigBytes());
            printEncoded("EncodeTestRealPosInf: ", encoder, coderTestUtils.createTestRealPosInf());
            checkEncoded(encoder, coderTestUtils.createTestRealPosInf(), coderTestUtils.createTestRealPosInfBytes());
            printEncoded("EncodeTestRealNegInf: ", encoder, coderTestUtils.createTestRealNegInf());
            checkEncoded(encoder, coderTestUtils.createTestRealNegInf(), coderTestUtils.createTestRealNegInfBytes());
            printEncoded("EncodeTestReal-1.5: ", encoder, coderTestUtils.createTestRealNeg1_5());
            checkEncoded(encoder, coderTestUtils.createTestRealNeg1_5(), coderTestUtils.createTestRealNeg1_5Bytes());
        }

        [TestMethod]
        public void testEncodeTaggedSequence()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("EncodeTestTaggedSequence: ", encoder, coderTestUtils.createTaggedSequence());
            checkEncoded(encoder, coderTestUtils.createTaggedSequence(), coderTestUtils.createTaggedSequenceBytes());
        }

        [TestMethod]
        public void testEncodeLongTag()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("EncodeTestLongTag: ", encoder, coderTestUtils.createTestLongTag());
            checkEncoded(encoder, coderTestUtils.createTestLongTag(), coderTestUtils.createTestLongTagBytes());

            printEncoded("EncodeTest128Tag: ", encoder, coderTestUtils.createTest128Tag());
            checkEncoded(encoder, coderTestUtils.createTest128Tag(), coderTestUtils.createTest128TagBytes());
        }

        [TestMethod]
        public void testEncodeLongTag2()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("EncodeTestLongTag2: ", encoder, coderTestUtils.createTestLongTag2());
            checkEncoded(encoder, coderTestUtils.createTestLongTag2(), coderTestUtils.createTestLongTag2Bytes());
        }

        [TestMethod]
        public void testEncodeCSEnum()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            printEncoded("EncodeCSEnum: ", encoder, coderTestUtils.createCSEnum());
            checkEncoded(encoder, coderTestUtils.createCSEnum(), coderTestUtils.createCSEnumBytes());
        }

        [TestMethod]
        public virtual void testEncodeOID()
        {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            //
            TestOID testOID = coderTestUtils.createTestOID1();
            printEncoded("OID "+testOID.Value.Value, encoder, testOID);
            checkEncoded(encoder, coderTestUtils.createTestOID1(), coderTestUtils.createTestOID1Bytes());
            //
            testOID = coderTestUtils.createTestOID2();
            printEncoded("OID " + testOID.Value.Value, encoder, testOID);
            checkEncoded(encoder, coderTestUtils.createTestOID2(), coderTestUtils.createTestOID2Bytes());
            //
            testOID = coderTestUtils.createTestOID3();
            printEncoded("OID " + testOID.Value.Value, encoder, testOID);
            checkEncoded(encoder, coderTestUtils.createTestOID3(), coderTestUtils.createTestOID3Bytes());
            //
            testOID = coderTestUtils.createTestOID4();
            printEncoded("OID " + testOID.Value.Value, encoder, testOID);
            checkEncoded(encoder, coderTestUtils.createTestOID4(), coderTestUtils.createTestOID4Bytes());
        }

        [TestMethod]
        public void testEncodeCSSpecific()
        {
            test_asn.Version version = new test_asn.Version();
            Assert.IsNotNull(version);
        }

        [TestMethod]
        public void testEncodeTaggedSet() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);
            //
            Config taggedSet = coderTestUtils.createTaggedSet();
            printEncoded("TaggedSet",encoder, taggedSet);
            checkEncoded(encoder, coderTestUtils.createTaggedSet(), coderTestUtils.createTaggedSetBytes());            
        }

        [TestMethod]
        public void testEncodeTaggedSetInSet() {
            IEncoder encoder = newEncoder();
            Assert.IsNotNull(encoder);

            TestTaggedSetInSet taggedSet = coderTestUtils.createTaggedSetInSet();
            printEncoded("TaggedSetInSet",encoder, taggedSet);
            checkEncoded(encoder, coderTestUtils.createTaggedSetInSet(), coderTestUtils.createTaggedSetInSetBytes());

            Set7 set7 = coderTestUtils.createSet7();
            printEncoded("Set7", encoder, set7);
            checkEncoded(encoder, coderTestUtils.createSet7(), coderTestUtils.createSet7Bytes());
        }
    }
}
