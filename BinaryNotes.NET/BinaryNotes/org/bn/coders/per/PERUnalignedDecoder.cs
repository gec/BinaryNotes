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
using org.bn.utils;
using System;
using System.IO;
using System.Text;

namespace org.bn.coders.per
{
	
	public class PERUnalignedDecoder:PERAlignedDecoder
	{
		
		protected override void skipAlignedBits(Stream stream)
		{
			// Do nothing! Unaligned encoding ;)
		}
		
		protected override long decodeConstraintNumber(long min, long max, BitArrayInputStream stream)
		{
            long result = 0;
			long valueRange = max - min;
			// !!! int narrowedVal = value - min; !!!
			int maxBitLen = PERCoderUtils.getMaxBitLength(valueRange);
			
			if (valueRange == 0)
			{
				return max;
			}
			// For the UNALIGNED variant the value is always encoded in the minimum 
			// number of bits necessary to represent the range (defined in 10.5.3). 
			int currentBit = maxBitLen;
			while (currentBit > 7)
			{
				currentBit -= 8;
                result |= (uint)(stream.ReadByte() << currentBit);
			}
			if (currentBit > 0)
			{
                result |= (uint)stream.readBits(currentBit);
			}
			result += min;
			return result;
		}
		
		public override DecodedObject<object> decodeString(DecodedObject<object> decodedTag, Type objectClass, ElementInfo elementInfo, Stream stream)
		{
            if (!PERCoderUtils.is7BitEncodedString(elementInfo))
            {
                return base.decodeString(decodedTag, objectClass, elementInfo, stream);
            }
            else
            {
                DecodedObject<object> result = new DecodedObject<object>();
                int strLen = decodeLength(elementInfo, stream);
                if (strLen <= 0)
                {
                    result.Value = "";
                }
                else
                {
                    BitArrayInputStream bitStream = (BitArrayInputStream)stream;
                    // 7-bit decoding of string
                    byte[] buffer = new byte[strLen];
                    for (int i = 0; i < strLen; i++)
                    {
                        buffer[i] = (byte)bitStream.readBits(7);
                    }
                    result.Value = new string(ASCIIEncoding.ASCII.GetChars(buffer));
                }
                return result;
            }
		}
	}
}