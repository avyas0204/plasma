/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.sdo.access.provider.common;

// java imports
import java.util.Comparator;

import org.plasma.query.model.GroupBy;
import org.plasma.query.model.OrderBy;
import org.plasma.sdo.PlasmaDataGraph;

import commonj.sdo.Type;

public class DataGraphComparatorAssembler extends DataComparatorAssembler {
  public DataGraphComparatorAssembler(OrderBy orderBy, Type rootType) {
    super(orderBy, rootType, new DataGraphComparator());
  }

  public DataGraphComparatorAssembler(GroupBy groupBy, Type rootType) {
    super(groupBy, rootType, new DataGraphComparator());
  }

  public Comparator<PlasmaDataGraph> getComparator() {
    return (DataGraphComparator) this.dataComparator;
  }

}