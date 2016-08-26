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
package org.plasma.text.lang3gl.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.Package;
import org.plasma.provisioning.adapter.ProvisioningModel;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;

public class DSLAssembler extends DefaultStreamAssembler {

    private static Log log =LogFactory.getLog(
    		DSLAssembler.class); 
    
	public DSLAssembler(ProvisioningModel provisioningModel, Lang3GLFactory factory,
			Lang3GLOperation operation, File dest) {
		super(provisioningModel, factory, operation, dest);
	}

	@Override
	public void createEnumerationClasses() throws IOException 
    {
		// noop
    }
	
	@Override
	public void createInterfaceClasses() throws IOException 
    {
		// noop
    }
    
	@Override
	public void createImplementationClasses() throws IOException 
    {
    	ClassFactory classFactory = factory.getClassFactory();
    	
    	for (Package pkg : this.provisioningModel.getLeafPackages()) {
			File dir = new File(dest, classFactory.createDirectoryName(pkg));
			log.debug("processing package: " + dir.getAbsolutePath());
    		for (Class clss : pkg.getClazzs()) {    			
    			if (!PlasmaConfig.getInstance().generateQueryDSL(clss.getUri(), clss.getName()))
    				continue; 			
    			
    			if (!dir.exists()) {
                    if (!dir.mkdirs())
            		    throw new IllegalArgumentException("package directory '"
                            + dir.getAbsolutePath() + "' could not be created");  
        			log.debug("created package: " + dir.getAbsolutePath());
    			}

    			File file = new File(dir, classFactory.createFileName(clss, pkg));
    			log.debug("creating file: " + file.getAbsolutePath());
    			FileOutputStream stream = new FileOutputStream(file);
    			
    			StringBuilder buf = new StringBuilder();
    			buf.append(classFactory.createContent(pkg, clss));    			
    			
    			stream.write(buf.toString().getBytes());
            	stream.flush();
            	stream.close();
            	
            	this.resultClassesCount++;
    		}
    	}    	
    }

	@Override
	public void createInterfacePackageDocs() throws IOException {
		// noop
	}   
	

}
