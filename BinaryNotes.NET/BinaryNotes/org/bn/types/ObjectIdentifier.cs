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

namespace org.bn.types
{
    public class ObjectIdentifier
    {
        private string oidString;

        public ObjectIdentifier(string oidString)
        {
            this.Value = oidString;
        }

        public string Value
        {
            get { return this.oidString; }
            set { this.oidString = value; }
        }   

        public int[] getIntArray()
        {
            string[] sa = oidString.Split('.');
            int[] ia = new int[sa.Length];
            for (int i=0; i < sa.Length; i++)
            {
                ia[i] = int.Parse(sa[i]);
            }
            return ia;
        }

        public override bool Equals(object obj)
        {
            return obj is ObjectIdentifier && ((ObjectIdentifier)obj).Value==this.Value;
        }

        public override int GetHashCode()
        {
            return this.Value==null ? 0 : this.Value.GetHashCode();
        }
    }
}
