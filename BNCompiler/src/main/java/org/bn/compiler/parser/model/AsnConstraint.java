package org.bn.compiler.parser.model;

public class AsnConstraint {
    
    public ElementSetSpec  addElemSetSpec;
    public AsnDefinedValue definedValue;
    public ElementSetSpec  elemSetSpec;
    public boolean         isAdditionalElementSpec;
    public boolean         isColonValue;
    public boolean         isCommaDotDot;
    public boolean         isDefinedValue;
    public boolean         isElementSetSpecs;
    public boolean         isExceptionSpec;
    public boolean         isSignedNumber;
    public AsnSignedNumber signedNumber;
    public Object          type;
    public AsnValue        value;

    public AsnConstraint() {
    }

    // Return AllExcept additional Constraint Elements
    public ConstraintElements addElemSS_allExceptConstraintElem() {
        return addElemSetSpec.allExceptCnselem;
    }

    // Return if All Except additional Constraint Elements exists
    public boolean addElemSS_isAllExcept() {
        return addElemSetSpec.isAllExcept;
    }

    // Return AllExcept Constraint Elements
    public ConstraintElements elemSS_allExceptConstraintElem() {
        return elemSetSpec.allExceptCnselem;
    }

    // Return if All Except Constraint Elements exists
    public boolean elemSS_isAllExcept() {
        return elemSetSpec.isAllExcept;
    }

    // Return the required additional intersection element
    public Intersection get_addElemSS_IntsectElem(int i) {
        return addElemSetSpec.intersectionList.get(i);
    }

    // Returns first additional constraint Element in the first IntersectionList
    public ConstraintElements get_addElemSS_firstConstraintElem() {
        Intersection intersect = get_addElemSS_firstIntsectElem();
        return intersect.cnsElemList.get(0);
    }

    // Return the first additional intersection element
    public Intersection get_addElemSS_firstIntsectElem() {
        return addElemSetSpec.intersectionList.get(0);
    }

    // Returns specified additional Constraint Elements in the specified IntersectionList
    public ConstraintElements get_addElemSS_intersectionConstraintElems(int intersectElem, int constraintElem) {
        Intersection intersect = get_addElemSS_IntsectElem(intersectElem);
        return intersect.cnsElemList.get(constraintElem);
    }

    // Return the required intersection element
    public Intersection get_elemSS_IntsectElem(int i) {
        return elemSetSpec.intersectionList.get(i);
    }

    // Returns first constraint Element in the first IntersectionList
    public ConstraintElements get_elemSS_firstConstraintElem() {
        Intersection intersect = get_elemSS_firstIntsectElem();
        return intersect.cnsElemList.get(0);
    }

    // Return the first intersection element
    public Intersection get_elemSS_firstIntsectElem() {
        return elemSetSpec.intersectionList.get(0);
    }

    // Returns specified Constraint Elements in the specified IntersectionList
    public ConstraintElements get_elemSS_intersectionConstraintElems(int intersectElem, int constraintElem) {
        Intersection intersect = get_elemSS_IntsectElem(intersectElem);
        return intersect.cnsElemList.get(constraintElem);
    }

    // Return the total intersection elements in the add element Spec list
    public int sz_addElemSS_IntsectList() {
        return addElemSetSpec.intersectionList.size();
    }

    // Returns Number of additional Constraint Elements in the specified IntersectionList
    public int sz_addElemSS_intersectionConstraintElems(int i) {
        Intersection intersect = get_addElemSS_IntsectElem(i);
        return intersect==null ? -1 : intersect.cnsElemList.size();
    }

    // Return the total intersection elements in the element Spec list
    public int sz_elemSS_IntsectList() {
        return elemSetSpec.intersectionList.size();
    }

    // Returns Number of Constraint Elements in the specified IntersectionList
    public int sz_elemSS_intersectionConstraintElems(int i) {
        Intersection intersect = get_elemSS_IntsectElem(i);
        return intersect==null ? -1 : intersect.cnsElemList.size();
    }

    @Override
    public String toString() {
        String ts = "";

        if (isElementSetSpecs) {
            ts += elemSetSpec;
        } else if (isAdditionalElementSpec) {
            ts += addElemSetSpec;
        }

        if (isExceptionSpec) {
            ts += " !";

            if (isSignedNumber) {
                ts += signedNumber;
            } else if (isDefinedValue) {
                ts += definedValue;

                // ??????? Type to returned as string also ???????
            } else if (isColonValue) {
                ts += value;
            }
        }

        return ts;
    }
}
