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
package org.plasma.sdo.core;

// java imports
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Holds internally managed concurrency property values and data 
 * store generated keys, for return back to clients, resulting 
 * from insert, update or delete operations, in association with
 * a single time stamp value. The time stamp holds the time
 * value used for all relevant concurrency operations, e.g. last-modified
 * optimistic concurrency dates or time stamps.   
 */
public class SnapshotMap
    implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Map<String, Object> values = new HashMap<String, Object>();
    private Timestamp snapshotDate;

    @SuppressWarnings("unused")
	private SnapshotMap() {}
    
    public SnapshotMap(Timestamp snapshotDate)
    {
        this.snapshotDate = snapshotDate;
    }
    
    public Timestamp getSnapshotDate() { return snapshotDate; }

    public String[] getKeys() 
    {
        Set<String> keys = values.keySet();
        String[] result = new String[keys.size()];
        keys.toArray(result); 
        return result; 
    }

    public void put(String key, Object value)
    {
        values.put(key, value);
    }

    public Object get(String key)
    {
        return values.get(key);
    }  

    public Object remove(String key)
    {
        return values.remove(key);
    }  
}