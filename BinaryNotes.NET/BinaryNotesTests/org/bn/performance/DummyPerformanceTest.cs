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
using org.bn.coders;
using org.bn.coders.ber;
using org.bn.coders.per;
using org.bn.coders.test_asn;
using System;

namespace org.bn.performance
{
    [TestClass]
    public class DummyPerformanceTest
    {
        protected void runEncoderPerfTest(string encoding)
        {
            IEncoder encoder = CoderFactory.getInstance().newEncoder(encoding);
            Assert.IsNotNull(encoder);
            // Create test structure
            DataSeq dt = new BERCoderTestUtils().createDataSeq();
            System.IO.Stream stream = new System.IO.MemoryStream();
            // Start test
            DateTime startTime = System.DateTime.Now;
            for (int i = 0; i < 100; i++)
            {
                encoder.encode<DataSeq>(dt, stream);
            }
            DateTime endTime = System.DateTime.Now;
            TimeSpan interval = (endTime-startTime);
            System.Console.WriteLine("Encode elapsed time for " + encoding + ": " + interval.TotalSeconds );
        }

        protected void runDecoderPerfTest(string encoding, CoderTestUtilities coderUtils)
        {
            IDecoder encoder = CoderFactory.getInstance().newDecoder(encoding);
            Assert.IsNotNull(encoder);
            // Create test structure
            System.IO.Stream stream = new System.IO.MemoryStream(
                    coderUtils.createDataSeqBytes()
            );
            // Start test
            DateTime startTime = System.DateTime.Now;
            for (int i = 0; i < 100; i++)
            {
                DataSeq dt = encoder.decode<DataSeq>(stream);
                stream.Position = 0;
            }
            DateTime endTime = System.DateTime.Now;
            TimeSpan interval = (endTime - startTime);
            System.Console.WriteLine("Decode elapsed time for " + encoding + ": " + interval.TotalSeconds);
        }

        [TestMethod]
        public void testEncodePerf()
        {
            runEncoderPerfTest("BER");
            runEncoderPerfTest("PER");
            runEncoderPerfTest("PER/Unaligned");
        }

        [TestMethod]
        public void testDecodePerf(){
            runDecoderPerfTest("BER", new BERCoderTestUtils());
            runDecoderPerfTest("PER", new PERAlignedCoderTestUtils());
            runDecoderPerfTest("PER/Unaligned", new PERUnalignedCoderTestUtils());
        }
    }
}
