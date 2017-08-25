/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PredicateOperator", propOrder = {
    "value"
})
@XmlRootElement(name = "PredicateOperator")
public class PredicateOperator implements org.plasma.query.Operator {

    @XmlValue
    protected PredicateOperatorName value;

    /** 
     * Default constructor for marshalling only !!
     */
    public PredicateOperator() {
        super();
    }  

    public PredicateOperator(PredicateOperatorName operatorName) {
        super();
        value = operatorName;
    }  

     /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link PredicateOperatorName }
     *     
     */
    public PredicateOperatorName getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link PredicateOperatorName }
     *     
     */
    public void setValue(PredicateOperatorName value) {
        this.value = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }

}
