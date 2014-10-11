package org.bn.compiler.parser.model;

public class AsnNamedValue {
    
    public String   name;
    public AsnValue value;

    public AsnNamedValue() {
    }

    @Override
    public String toString() {
        return name + "\t" + value;
    }

    public String toString(boolean name) {
        return name ? this.name : String.valueOf(this.value);
    }
}
