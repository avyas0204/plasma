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
package org.plasma.sdo.access.provider.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.access.DataAccessException;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

public class CreatedObjectCollector extends SimpleCollector 
    implements CommitCollector 
{
    private static Log log = LogFactory.getFactory().getInstance(
            CreatedObjectCollector.class);
    @SuppressWarnings("unused")
    private CreatedObjectCollector() {}
    
    public CreatedObjectCollector(DataGraph dataGraph) {
        
        this.changeSummary = (PlasmaChangeSummary)dataGraph.getChangeSummary();
        collect();
    }
    
    private void collect() {
        List<DataObject> list = changeSummary.getChangedDataObjects();
        for (DataObject changed : list) {
            if (!changeSummary.isCreated(changed))
                continue;
            
            log.debug("processing changed object: "
                    + changed.getType().getName() + "(" 
                    + ((PlasmaDataObject)changed).getUUIDAsString() + ")");
            // convert to array to avoid concurrent mods of collection
            DataObject[] resultArray = new DataObject[result.size()];
            result.toArray(resultArray);
            
            boolean found = false;
            for (int i = 0; i < resultArray.length; i++) {
                // if existing result object is a child of the changed/created 
            	// object, add the changed object ahead of the 
            	// existing, so will be created first
            	if (isRelation(resultArray[i], changed, 
            			AssociationPath.singular)) {            		
                    if (result.contains(changed)) {
                        throw new DataAccessException("unexpected changed object: "
                            + changed.getType().getURI() + "#"+ changed.getType().getName() + "(" 
                            + ((PlasmaDataObject)changed).getUUIDAsString() + ")");
                    }
                    found = true;
                    if (log.isDebugEnabled())
                        log.debug("adding changed object: "
                            + changed.getType().getURI() + "#"+ changed.getType().getName() + "(" 
                            + ((PlasmaDataObject)changed).getUUIDAsString() + ") at position " + i);
                    result.add(i, (PlasmaDataObject)changed);
                    break;
                }
            }
            if (!found) {
                if (log.isDebugEnabled())
                    log.debug("appending changed object: "
                        + changed.getType().getURI() + "#" + changed.getType().getName() + "(" 
                        + ((PlasmaDataObject)changed).getUUIDAsString() + ")");
                result.add((PlasmaDataObject)changed); // append it 
            }
        }
    }

}

