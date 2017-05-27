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

public class ObjectIdentifier {

    private String oidString;

    public ObjectIdentifier(String oidAsStr) {
        this.oidString = oidAsStr;
    }

    public ObjectIdentifier() {
        this.oidString = null;
    }

    public String getValue() {
        return this.oidString;
    }

    public void setValue(String value) {
        this.oidString = value;
    }

    public int[] getIntArray() {
        String[] sa = oidString.split("\\.");
        int[] ia = new int[sa.length];
        for (int i = 0; i < sa.length; i++) {
            ia[i] = Integer.parseInt(sa[i]);
        }
        return ia;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ObjectIdentifier && (this.oidString == null ? ((ObjectIdentifier)obj).oidString == null : this.oidString.equals(((ObjectIdentifier)obj).oidString));
    }

    @Override
    public int hashCode() {
        return this.oidString == null ? 0 : this.oidString.hashCode();
    }
}
