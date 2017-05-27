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

using System.Linq;

namespace org.bn.types
{
    public class BitString
    {
        private byte[] bitStrValue;
        public byte[] Value
        {
            get { return this.bitStrValue; }
            set { this.bitStrValue = value; }
        }

        private int trailBitsCnt; // count of buffer bit's trail
        public int TrailBitsCnt
        {
            get { return this.trailBitsCnt; }
            set { this.trailBitsCnt = value; }
        }

        public BitString()
        {
            this.Value = new byte[0];
            this.TrailBitsCnt = 0;
        }

        public BitString(byte[] bitStrValue, int trailBitsCnt)
        {
            this.Value = bitStrValue;
            this.TrailBitsCnt = trailBitsCnt;
        }

        public BitString(BitString src) : this(src.Value, src.TrailBitsCnt)
        {
        }

        public int getLength()
        {
            return this.Value.Length;
        }

        public int getTrailBitsCnt()
        {
            return this.TrailBitsCnt;
        }

    	public int getLengthInBits()
        {
	        return getLength()*8 - getTrailBitsCnt();
    	}

        public override bool Equals(object obj)
        {
            return obj is BitString && ((BitString)obj).trailBitsCnt == this.trailBitsCnt
                && (this.bitStrValue == null ? ((BitString)obj).bitStrValue == null : ((BitString)obj).bitStrValue != null && this.bitStrValue.SequenceEqual(((BitString)obj).bitStrValue));
        }

        public override int GetHashCode()
        {
            return this.bitStrValue == null ? this.trailBitsCnt : this.bitStrValue.GetHashCode() ^ this.trailBitsCnt;
        }
    }
}
