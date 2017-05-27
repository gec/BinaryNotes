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
package org.bn.utils;

import java.io.IOException;
import org.junit.Test;

public class BitArrayOutputStreamTest {

    /**
     * @see BitArrayOutputStream#write(int)
     */
    @Test
    public void testWrite() throws IOException {
        BitArrayOutputStream stream = new BitArrayOutputStream();
        stream.write(0xFF);
        stream.writeBit(true);
        stream.writeBit(false);
        stream.writeBit(true);
        stream.writeBit(false);
        stream.write(0xFF);
        stream.write(0x0F);
        stream.writeBit(true);
        stream.writeBit(true);
        stream.writeBit(true);
        stream.writeBit(true);
        System.out.println("Write " + ByteTools.byteArrayToHexString(stream));
        ByteTools.checkBuffers(stream.toByteArray(), new byte[]{
                (byte) 0xFF, (byte) 0xAF, (byte) 0xF0, (byte) 0xFF
            });

        stream.writeBit(true);
        stream.writeBit(false);
        stream.writeBit(true);
        stream.writeBit(false);
        stream.write(new byte[]{(byte) 0xCC, (byte) 0xFF, (byte) 0xFF, (byte) 0xBB});
        System.out.println("After buf write " + ByteTools.byteArrayToHexString(stream));
        ByteTools.checkBuffers(stream.toByteArray(), new byte[]{
                (byte) 0xFF, (byte) 0xAF, (byte) 0xF0, (byte) 0xFF,
                (byte) 0xAC, (byte) 0xCF, (byte) 0xFF, (byte) 0xFB, (byte) 0xB0
            });
        stream.align();
        stream.writeBit(true);
        stream.writeBit(true);
        stream.align();
        stream.write(0xFF);

        System.out.println("After align " + ByteTools.byteArrayToHexString(stream));
        ByteTools.checkBuffers(stream.toByteArray(), new byte[]{
                (byte) 0xFF, (byte) 0xAF, (byte) 0xF0, (byte) 0xFF,
                (byte) 0xAC, (byte) 0xCF, (byte) 0xFF, (byte) 0xFB, (byte) 0xB0,
                (byte) 0xC0, (byte) 0xFF
            });
    }
}
