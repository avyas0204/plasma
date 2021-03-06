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

package org.plasma.sdo.xpath;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

public class XPathDataValue extends DataGraphNodeAdapter {

  private Object target;

  public XPathDataValue(Object target, DataObject source, Property sourceProperty) {
    super(source, sourceProperty);
    this.target = target;
  }

  public Object getValue() {
    return target;
  }

  public Object get() {
    return this.target;
  }

  public void set(Object value) {
    throw new IllegalStateException("cannot set a value for a XPATH value endpoint");
  }
}
