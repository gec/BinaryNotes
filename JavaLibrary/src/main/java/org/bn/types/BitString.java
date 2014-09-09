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

package org.bn.types;

import java.util.Arrays;

/**
 * BitString represents ASN.1 BIT STRING data types
 * @todo Need more functional operations for write/read bits
 */
public class BitString {
    
    private byte[] bitStrValue;
    
    /** count of buffer bit's trail */
    private int trailBitsCnt;
    
    public BitString() {
        this.bitStrValue = new byte[0];
        this.trailBitsCnt = 0;
    }
    
    public BitString(byte[] bitStrValue, int trailBitsCnt) {
        this.bitStrValue = bitStrValue;
        this.trailBitsCnt = trailBitsCnt;
    }

    public BitString(BitString src) {
        this(src.getValue(), src.getTrailBitsCnt());
    }
    
    public int getLength() {
        return this.bitStrValue.length;
    }
    
    public int getTrailBitsCnt() {
        return this.trailBitsCnt;
    }

    public int getLengthInBits() {
	return this.getLength()*8 - this.getTrailBitsCnt();
    }
    
    public byte[] getValue() {
        return this.bitStrValue;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BitString && Arrays.equals(((BitString)obj).bitStrValue, this.bitStrValue) && ((BitString)obj).trailBitsCnt==this.trailBitsCnt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Arrays.hashCode(this.bitStrValue);
        hash = 23 * hash + this.trailBitsCnt;
        return hash;
    }
}
