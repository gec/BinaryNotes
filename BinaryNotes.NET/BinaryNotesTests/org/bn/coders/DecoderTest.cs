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
using org.bn.types;
using org.bn.utils;
using System.Collections.Generic;

namespace org.bn.coders
{
    public abstract class DecoderTest
    {
        protected CoderTestUtilities coderTestUtils;

        protected DecoderTest(CoderTestUtilities coderTestUtils)
        {
            this.coderTestUtils = coderTestUtils;
        }

        protected abstract IDecoder newDecoder();

        private void checkData(Data dec, Data std)
        {
            if (std.isBinarySelected())
            {
                Assert.IsTrue(dec.isBinarySelected());
                ByteTools.checkBuffers(dec.Binary.Value, std.Binary.Value);
            }
            else if (std.isPlainSelected())
            {
                Assert.IsTrue(dec.isPlainSelected());
                Assert.AreEqual(dec.Plain.Value, std.Plain.Value);
            }
            else if (std.isIntTypeSelected())
            {
                Assert.IsTrue(dec.isIntTypeSelected());
                Assert.AreEqual(dec.IntType, std.IntType);
            }
            else if (std.isSimpleOctTypeSelected())
            {
                Assert.IsTrue(dec.isSimpleOctTypeSelected());
                ByteTools.checkBuffers(dec.SimpleOctType, std.SimpleOctType);
            }
        }

        protected void checkArray<T>(ICollection<T> dec, ICollection<T> std)
        {
            Assert.AreEqual(dec.Count, std.Count);
            IEnumerator<T> decIt = dec.GetEnumerator();
            IEnumerator<T> stdIt = std.GetEnumerator();
            for (int i = 0; i < std.Count; i++)
            {
                decIt.MoveNext(); stdIt.MoveNext();
                Assert.AreEqual(decIt.Current, stdIt.Current);
            }
        }

        protected void checkDataArray(ICollection<Data> dec, ICollection<Data> std)
        {
            Assert.AreEqual(dec.Count, std.Count);
            IEnumerator<Data> decIt = dec.GetEnumerator();
            IEnumerator<Data> stdIt = std.GetEnumerator();
            for (int i = 0; i < std.Count; i++)
            {
                decIt.MoveNext(); stdIt.MoveNext();
                checkData(decIt.Current, stdIt.Current);
            }
        }

        /// <seealso cref="Decoder.decode(InputStream,Class)">
        /// </seealso>
        [TestMethod]
        public virtual void testDecode()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(
                (coderTestUtils.createDataSeqBytes()));
            DataSeq seq = decoder.decode<DataSeq>(stream);
            checkDataSeq(seq, coderTestUtils.createDataSeq());
        }

        protected internal virtual void checkDataSeq(DataSeq decoded, DataSeq standard)
        {
            ByteTools.checkBuffers(decoded.Binary.Value, standard.Binary.Value);
            Assert.AreEqual(decoded.BooleanType, standard.BooleanType);
            checkDataArray(decoded.DataArray, standard.DataArray);
            Assert.AreEqual(decoded.IntBndType, standard.IntBndType);
            Assert.AreEqual(decoded.IntType, standard.IntType);
            Assert.AreEqual(decoded.Plain.Value, standard.Plain.Value);
            Assert.AreEqual(decoded.SimpleType, standard.SimpleType);
            ByteTools.checkBuffers(decoded.SimpleOctType, standard.SimpleOctType);
        }

        [TestMethod]
        public virtual void testITUDeDecode()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createITUSeqBytes()));
            ITUSequence seq = decoder.decode<ITUSequence>(stream);
            checkITUSeq(seq, coderTestUtils.createITUSeq());
        }

        private void checkITUSeq(ITUSequence decoded, ITUSequence standard)
        {
            Assert.AreEqual(decoded.Type1, standard.Type1);
            Assert.AreEqual(decoded.Type2.Value, standard.Type2.Value);
            Assert.AreEqual(decoded.Type3.Value.Value, standard.Type3.Value.Value);
            Assert.AreEqual(decoded.Type4.Value.Value.Value, standard.Type4.Value.Value.Value);
            Assert.AreEqual(decoded.Type5.Value.Value, standard.Type5.Value.Value);
            Assert.AreEqual(decoded.Type6, standard.Type6);
            Assert.AreEqual(decoded.Type7.Value, standard.Type7.Value);
        }

        [TestMethod]
        public virtual void testNullDecode()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createNullSeqBytes()));
            NullSequence seq = decoder.decode<NullSequence>(stream);
            Assert.IsNotNull(seq);
        }

        [TestMethod]
        public virtual void testTaggedNullDecode()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createTaggedNullSeqBytes()));
            TaggedNullSequence seq = decoder.decode<TaggedNullSequence>(stream);
            Assert.IsNotNull(seq);
        }

        [TestMethod]
        public virtual void testSequenceWithNullDecode()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createSeqWithNullBytes()));
            SequenceWithNull seq = decoder.decode<SequenceWithNull>(stream);
            Assert.IsNotNull(seq);
        }

        [TestMethod]
        public virtual void testEnum()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createEnumBytes()));
            ContentSchema enm = decoder.decode<ContentSchema>(stream);
            Assert.IsNotNull(enm);
            checkContentSchema(enm, coderTestUtils.createEnum());
        }

        private void checkContentSchema(ContentSchema decoded, ContentSchema standard)
        {
            Assert.AreEqual(decoded.Value, standard.Value);
        }

        [TestMethod]
        public virtual void testSequenceWithEnum()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createSequenceWithEnumBytes()));
            SequenceWithEnum seq = decoder.decode<SequenceWithEnum>(stream);
            Assert.IsNotNull(seq);
        }

        [TestMethod]
        public virtual void testRecursiveDefinition()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createTestRecursiveDefinitionBytes()));
            TestRecursiveDefinetion seq = decoder.decode<TestRecursiveDefinetion>(stream);
            Assert.IsNotNull(seq);
            checkRecursiveDefinition(seq, coderTestUtils.createTestRecursiveDefinition());
        }

        private void checkRecursiveDefinition(TestRecursiveDefinetion decoded, TestRecursiveDefinetion standard)
        {
            Assert.AreEqual(decoded.Name, standard.Name);
            if (standard.Value != null)
            {
                Assert.IsNotNull(decoded.Value);
                checkRecursiveDefinition(decoded.Value, standard.Value);
            }
        }

        [TestMethod]
        public virtual void testDecodeInteger()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger4Bytes()));

            TestI32 val = decoder.decode<TestI32>(stream);
            Assert.IsNotNull(val);
            Assert.AreEqual(val.Value, coderTestUtils.createTestInteger4().Value);

            stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger3Bytes()));
            TestI16 val16 = decoder.decode<TestI16>(stream);
            Assert.IsNotNull(val16);
            Assert.AreEqual(val16.Value, coderTestUtils.createTestInteger3().Value);

            stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger2Bytes()));
            val16 = decoder.decode<TestI16>(stream);
            Assert.IsNotNull(val16);
            Assert.AreEqual(val16.Value, coderTestUtils.createTestInteger2().Value);

            stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger1Bytes()));
            TestI8 val8 = decoder.decode<TestI8>(stream);
            Assert.IsNotNull(val8);
            Assert.AreEqual(val8.Value, coderTestUtils.createTestInteger1().Value);

            stream = new System.IO.MemoryStream((coderTestUtils.createTestIntegerRBytes()));
            TestIR valR = decoder.decode<TestIR>(stream);
            Assert.IsNotNull(valR);
            Assert.AreEqual(valR.Value, coderTestUtils.createTestIntegerR().Value);

            stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger2_12Bytes()));
            TestI14 val14 = decoder.decode<TestI14>(stream);
            Assert.IsNotNull(val14);
            Assert.AreEqual(val14.Value, coderTestUtils.createTestInteger2_12().Value);
        }

        [TestMethod]
        public virtual void testDecodeString()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createTestPRNBytes()));
            TestPRN val = decoder.decode<TestPRN>(stream);
            Assert.IsNotNull(val);
            Assert.AreEqual(val.Value, coderTestUtils.createTestPRN().Value);

            stream = new System.IO.MemoryStream((coderTestUtils.createTestOCTBytes()));
            TestOCT valOct = decoder.decode<TestOCT>(stream);
            Assert.IsNotNull(valOct);
            ByteTools.checkBuffers(valOct.Value, coderTestUtils.createTestOCT().Value);
        }

        [TestMethod]
        public virtual void testDecodeStringArray()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createStringArrayBytes()));
            StringArray val = decoder.decode<StringArray>(stream);
            Assert.IsNotNull(val);
            checkArray(val.Value, coderTestUtils.createStringArray().Value);
        }

        [TestMethod]
        public virtual void testDecodeChoice()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createDataChoicePlainBytes()));
            Data choice = new Data();
            Data val = decoder.decode<Data>(stream);
            Assert.IsNotNull(val);
            choice.selectPlain(new TestPRN("bbbbbb"));
            checkData(val, choice);

            stream = new System.IO.MemoryStream((coderTestUtils.createDataChoiceSimpleTypeBytes()));
            val = decoder.decode<Data>(stream);
            Assert.IsNotNull(val);
            choice.selectSimpleType("aaaaaaa");
            checkData(val, choice);

            stream = new System.IO.MemoryStream((coderTestUtils.createDataChoiceTestOCTBytes()));
            val = decoder.decode<Data>(stream);
            Assert.IsNotNull(val);
            choice.selectBinary(new TestOCT(new byte[] { 0xFF }));
            checkData(val, choice);

            stream = new System.IO.MemoryStream((coderTestUtils.createDataChoiceBooleanBytes()));
            val = decoder.decode<Data>(stream);
            Assert.IsNotNull(val);
            choice.selectBooleanType(true);
            checkData(val, choice);

            stream = new System.IO.MemoryStream((coderTestUtils.createDataChoiceIntBndBytes()));
            val = decoder.decode<Data>(stream);
            Assert.IsNotNull(val);
            choice.selectIntBndType(7);
            checkData(val, choice);
        }

        [TestMethod]
        public virtual void testDecodeNegativeInteger()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTestNIBytes());
            TestNI val = decoder.decode<TestNI>(stream);
            Assert.AreEqual(val.Value, coderTestUtils.createTestNI().Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTestNI2Bytes());
            TestNI2 val2 = decoder.decode<TestNI2>(stream);
            Assert.AreEqual(val2.Value, coderTestUtils.createTestNI2().Value);
        }

        [TestMethod]
        public virtual void testDecodeSet()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createSetBytes());
            SetWithDefault val = decoder.decode<SetWithDefault>(stream);
            Assert.AreEqual(val.Nodefault, coderTestUtils.createSet().Nodefault);
            Assert.AreEqual(val.Nodefault2.Value, coderTestUtils.createSet().Nodefault2.Value);
            Assert.AreEqual(val.Default3, coderTestUtils.createSet().Default3);
        }

        [TestMethod]
        public virtual void testDecodeBitStr()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTestBitStrBytes());
            TestBitStr val = decoder.decode<TestBitStr>(stream);
            Assert.AreEqual(val.Value.TrailBitsCnt, coderTestUtils.createTestBitStr().Value.TrailBitsCnt);
            ByteTools.checkBuffers(val.Value.Value, coderTestUtils.createTestBitStr().Value.Value);
        }

        [TestMethod]
        public virtual void testDecodeUnicodeStr()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createUnicodeStrBytes());
            TestUnicodeStr val = decoder.decode<TestUnicodeStr>(stream);
            Assert.AreEqual(val.Value, coderTestUtils.createUnicodeStr().Value);
        }

        protected void checkBitString(BitString decoded, BitString standard)
        {
            ByteTools.checkBuffers(decoded.Value, standard.Value);
            Assert.AreEqual(decoded.TrailBitsCnt, standard.TrailBitsCnt);
        }

        [TestMethod]
        public void testDecodeVersion1_2()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTestSequenceV12Bytes());
            TestSequenceV12 val = decoder.decode<TestSequenceV12>(stream);
            Assert.AreEqual(val.AttrStr, coderTestUtils.createTestSequenceV12().AttrStr);
            Assert.AreEqual(val.AttrStr2.Value, coderTestUtils.createTestSequenceV12().AttrStr2.Value);
            checkArray(val.AttrArr, coderTestUtils.createTestSequenceV12().AttrArr);
            checkBitString(val.AttrBitStr, coderTestUtils.createTestSequenceV12().AttrBitStr);
            TestSequenceV12 valWithDef = coderTestUtils.createTestSequenceV12();
            valWithDef.initWithDefaults();
            checkBitString(val.AttrBitStrDef, valWithDef.AttrBitStrDef);
            checkBitString(val.AttrBoxBitStr.Value, coderTestUtils.createTestSequenceV12().AttrBoxBitStr.Value);
        }

        [TestMethod]
        public void testDecodeChoiceInChoice()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createChoiceInChoiceBytes());
            BugValueType val = decoder.decode<BugValueType>(stream);
            Assert.AreEqual(val.isBugPrimitiveSelected(), coderTestUtils.createChoiceInChoice().isBugPrimitiveSelected());
            Assert.AreEqual(val.BugPrimitive.isBugIntegerSelected(), coderTestUtils.createChoiceInChoice().BugPrimitive.isBugIntegerSelected());
            Assert.AreEqual(val.BugPrimitive.BugInteger, coderTestUtils.createChoiceInChoice().BugPrimitive.BugInteger);
        }

        [TestMethod]
        public void testDecodeTaggedSeqInSeq()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTaggedSeqInSeqBytes());
            TaggedSeqInSeq val = decoder.decode<TaggedSeqInSeq>(stream);
            Assert.AreEqual(val.Value.Field.Param_name, coderTestUtils.createTaggedSeqInSeq().Value.Field.Param_name);
            Assert.AreEqual(val.Value.Field.Param_value, coderTestUtils.createTaggedSeqInSeq().Value.Field.Param_value);
        }

        [TestMethod]
        public void testDecodeReal()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTestReal1_5Bytes());
            TestReal val = decoder.decode<TestReal>(stream);
            Assert.AreEqual(val.Value, coderTestUtils.createTestReal1_5().Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTestReal0_5Bytes());
            val = decoder.decode<TestReal>(stream);
            Assert.AreEqual(val.Value, coderTestUtils.createTestReal0_5().Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTestReal2Bytes());
            val = decoder.decode<TestReal>(stream);
            Assert.AreEqual(val.Value, coderTestUtils.createTestReal2().Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTestRealBigBytes());
            val = decoder.decode<TestReal>(stream);
            Assert.AreEqual(val.Value, coderTestUtils.createTestRealBig().Value);
        }

        [TestMethod]
        public void testDecodeLongTag()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTestLongTagBytes());
            TestLongTag val = decoder.decode<TestLongTag>(stream);
            Assert.AreEqual(val.Value, coderTestUtils.createTestLongTag().Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTest128TagBytes());
            Test128Tag val2 = decoder.decode<Test128Tag>(stream);
            Assert.AreEqual(val2.Value, coderTestUtils.createTest128Tag().Value);
        }

        [TestMethod]
        public void testDecodeLongTag2()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTestLongTag2Bytes());
            TestLongTag2 val = decoder.decode<TestLongTag2>(stream);
            Assert.IsTrue(val.isTestaSelected());
            Assert.AreEqual(val.Testa.Testb, coderTestUtils.createTestLongTag2().Testa.Testb);
        }

        [TestMethod]
        public void testDecodeCSEnum()
        {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createCSEnumBytes());
            CoderTestUtilities.TestCSEnum val = decoder.decode<CoderTestUtilities.TestCSEnum>(stream);
            Assert.AreEqual(val, coderTestUtils.createCSEnum());
        }

        [TestMethod]
        public virtual void testDecodeOID()
        {
            IDecoder decoder = newDecoder();
            Assert.IsNotNull(decoder);

            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTestOID1Bytes());
            ObjectIdentifier oid1 = decoder.decode<ObjectIdentifier>(stream);
            System.Console.Out.WriteLine("Decoded by " + decoder.ToString() + " (OID " + oid1.Value + ") : " + ByteTools.byteArrayToHexString(stream.ToArray()));
            Assert.AreEqual(oid1.Value, coderTestUtils.createTestOID1().Value.Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTestOID1Bytes());
            TestOID oid1_boxed = decoder.decode<TestOID>(stream);
            Assert.AreEqual(oid1_boxed.Value.Value, coderTestUtils.createTestOID1().Value.Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTestOID2Bytes());
            ObjectIdentifier oid2 = decoder.decode<ObjectIdentifier>(stream);
            System.Console.Out.WriteLine("Decoded by " + decoder.ToString() + " (OID " + oid2.Value + ") : " + ByteTools.byteArrayToHexString(stream.ToArray()));
            Assert.AreEqual(oid2.Value, coderTestUtils.createTestOID2().Value.Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTestOID3Bytes());
            ObjectIdentifier oid3 = decoder.decode<ObjectIdentifier>(stream);
            System.Console.Out.WriteLine("Decoded by " + decoder.ToString() + " (OID " + oid3.Value + ") : " + ByteTools.byteArrayToHexString(stream.ToArray()));
            Assert.AreEqual(oid3.Value, coderTestUtils.createTestOID3().Value.Value);

            stream = new System.IO.MemoryStream(coderTestUtils.createTestOID4Bytes());
            ObjectIdentifier oid4 = decoder.decode<ObjectIdentifier>(stream);
            System.Console.Out.WriteLine("Decoded by " + decoder.ToString() + " (OID " + oid4.Value + ") : " + ByteTools.byteArrayToHexString(stream.ToArray()));
            Assert.AreEqual(oid4.Value, coderTestUtils.createTestOID4().Value.Value);
        }

        [TestMethod]
        public void testDecodeCSSpecific()
        {
        }

        [TestMethod]
        public void testDecodeTaggedSet()
        {
            IDecoder decoder = newDecoder();
            Assert.IsNotNull(decoder);

            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTaggedSetBytes());
            Config tset = decoder.decode<Config>(stream);
            Assert.AreEqual(tset.Value.LstVersion.Value.Count, coderTestUtils.createTaggedSet().Value.LstVersion.Value.Count);
        }

        [TestMethod]
        public void testDecodeTaggedSetInSet()
        {
            IDecoder decoder = newDecoder();
            Assert.IsNotNull(decoder);

            System.IO.MemoryStream stream = new System.IO.MemoryStream(coderTestUtils.createTaggedSetInSetBytes());
            TestTaggedSetInSet tset = decoder.decode<TestTaggedSetInSet>(stream);

            stream = new System.IO.MemoryStream(coderTestUtils.createSet7Bytes());
            Set7 set7 = decoder.decode<Set7>(stream);
        }
    }
}
