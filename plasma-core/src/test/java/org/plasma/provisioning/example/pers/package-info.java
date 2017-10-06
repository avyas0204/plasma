@Alias(physicalName = "PERSON")
@Namespace(uri = "http://plasma-example/person")
@NamespaceProvisioning(rootPackageName = "examples.pojo.model")
@NamespaceService(storeType = DataStoreType.RDBMS, providerName = DataAccessProviderName.JDBC)
package org.plasma.provisioning.example.pers;

import org.plasma.runtime.DataAccessProviderName;
import org.plasma.runtime.DataStoreType;
import org.plasma.runtime.annotation.NamespaceProvisioning;
import org.plasma.runtime.annotation.NamespaceService;
import org.plasma.sdo.annotation.Namespace;
import org.plasma.sdo.annotation.Alias;

