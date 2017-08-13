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

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.QueryException;
import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationalOperator", propOrder = {
    "value"
})
@XmlRootElement(name = "RelationalOperator")
public class RelationalOperator implements org.plasma.query.Operator {

	private static Map<String, RelationalOperatorName> operMap = new HashMap<>();
    static {
		operMap.put("=", RelationalOperatorName.EQUALS);
		operMap.put("!=", RelationalOperatorName.NOT_EQUALS);
		operMap.put(">", RelationalOperatorName.GREATER_THAN);
		operMap.put(">=", RelationalOperatorName.GREATER_THAN_EQUALS);
		operMap.put("<", RelationalOperatorName.LESS_THAN);
		operMap.put("<=", RelationalOperatorName.LESS_THAN_EQUALS);
    }
	
    @XmlValue
    protected RelationalOperatorName value;

    public RelationalOperator() {
        super();
    } 

    public RelationalOperator(String content) {
        this();
        setValue(RelationalOperatorName.valueOf(content));
    } 

    public RelationalOperator(RelationalOperatorName content) {
        this();
        setValue(content);
    } 
    
    public static RelationalOperator valueOf(String value) {
    	RelationalOperatorName oper = operMap.get(value);
    	if (oper != null)
    		return new RelationalOperator(oper);
    	else
    	    throw new QueryException("invalid operator '" 
    	    		+ value + "'");
    }
    
    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link RelationalOperatorName }
     *     
     */
    public RelationalOperatorName getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationalOperatorName }
     *     
     */
    public void setValue(RelationalOperatorName value) {
        this.value = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }
}
