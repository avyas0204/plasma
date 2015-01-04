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
package org.plasma.provisioning.cli;

public enum SDOToolAction implements OptionEnum {
    /** 
     * Generate SDO interfaces, classes and enumerations
     * based on the current configuration.
     */
	create("Generate SDO interfaces, classes and enumerations based on the current configuration"),
	/**
	 * Exports the configured repository artifacts as a merged
	 * XML based provisioning or "technical" model. This can be
	 * used for application specific transformations.  
	 */
    export("Exports the configured repository artifacts as a merged XML based provisioning or \"technical\" model. This can be used for application specific transformations");
    
	private String description;
	   
	private SDOToolAction(String description) {
	    this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
    public static String asString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < SDOToolAction.values().length; i++) {
			if (i > 0)
				buf.append(", ");
			buf.append(SDOToolAction.values()[i].name());
		}
		return buf.toString();
    }
}
