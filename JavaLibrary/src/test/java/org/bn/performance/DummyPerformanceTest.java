/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 
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
package org.bn.performance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.bn.CoderFactory;
import org.bn.IDecoder;
import org.bn.IEncoder;
import org.bn.coders.CoderTestUtilities;
import org.bn.coders.ber.BERCoderTestUtils;
import org.bn.coders.per.PERAlignedCoderTestUtils;
import org.bn.coders.per.PERUnalignedCoderTestUtils;
import org.bn.coders.test_asn.*;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DummyPerformanceTest {
    
    protected void runEncoderPerfTest(String encoding) throws Exception {
        IEncoder encoder = CoderFactory.getInstance().newEncoder(encoding);
        assertNotNull(encoder);
        // Create test structure
        DataSeq dt = new BERCoderTestUtils().createDataSeq();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Start test
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++)
        {
            encoder.encode(dt, stream);
        }
        long endTime = System.currentTimeMillis();
        long interval = (endTime-startTime);
        System.out.println("Encode elapsed time for " + encoding + ": " + interval/1000.0 );
    }

    protected void runDecoderPerfTest(String encoding, CoderTestUtilities coderUtils) throws Exception {
        IDecoder decoder = CoderFactory.getInstance().newDecoder(encoding);
        assertNotNull(decoder);
        // Create test structure
        ByteArrayInputStream stream = new ByteArrayInputStream( coderUtils.createDataSeqBytes() );
        // Start test
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++)
        {
            DataSeq dt = decoder.decode(stream, DataSeq.class);
            stream.reset();
        }
        long endTime = System.currentTimeMillis();
        long interval = (endTime-startTime);
        System.out.println("Decode elapsed time for " + encoding + ": " + interval/1000.0 );
    }
    
    @Test
    public void testEncodePerf() throws Exception {
        runEncoderPerfTest("BER");
        runEncoderPerfTest("PER");
        runEncoderPerfTest("PER/Unaligned");
    }

    @Test
    public void testDecodePerf() throws Exception {
        runDecoderPerfTest("BER", new BERCoderTestUtils());
        runDecoderPerfTest("PER", new PERAlignedCoderTestUtils());
        runDecoderPerfTest("PER/Unaligned", new PERUnalignedCoderTestUtils());
    }
}