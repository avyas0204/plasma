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

package org.plasma.provisioning.rdb;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.Property;
import org.plasma.sdo.access.client.DefaultServiceContext;
import org.plasma.sdo.access.client.JDBCPojoDataAccessClient;

public class ConverterSupport {
  private static Log log = LogFactory.getLog(ConverterSupport.class);
  protected String[] schemaNames;
  protected String[] namespaces;
  protected Model model;
  /** Maps URI qualified names to classes */
  protected Map<String, Class> classQualifiedNameMap = new HashMap<String, Class>();
  /** Maps URI qualified names to enumerations */
  protected Map<String, Enumeration> enumQualifiedNameMap = new HashMap<String, Enumeration>();

  /**
   * Maps physical schema qualified primary key constraint names to properties.
   */
  protected Map<String, Property> propertyQualifiedPriKeyConstrainatNameMap = new HashMap<String, Property>();
  /** maps classes to properties */
  protected Map<Class, Map<String, Property>> classPropertyMap = new HashMap<Class, Map<String, Property>>();

  protected JDBCPojoDataAccessClient client = new JDBCPojoDataAccessClient(
      new DefaultServiceContext());

  /**
   * Getting bad unicode characters in DB comments which cause sax to barf.
   * "SAXParseException: An invalid XML character (Unicode: 0x92)" No real
   * solution other than escaping them or filtering them. JAXB does not support
   * CDATA sections. There are alot of illegal unicode chars according to many
   * sources, so filtering seems best.
   */
  protected String filter(String src) {

    // Pattern INVALID_XML_CHARS =
    // Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\uD800\uDC00-\uDBFF\uDFFF]");
    // String preCleaned = INVALID_XML_CHARS.matcher(src).replaceAll("");
    Pattern INVALID_XML_CHARS = Pattern.compile("[\\\\u0]");
    String preCleaned = INVALID_XML_CHARS.matcher(src).replaceAll("");

    char[] chars = preCleaned.toCharArray();
    char[] result = new char[chars.length];
    int i = 0;
    for (char ch : chars) {
      if (org.apache.xml.utils.XMLChar.isValid(ch)
          && (Character.isDigit(ch) || Character.isLetter(ch) || Character.isWhitespace(ch)
              || isPunctuation(ch) || isOtherAllowed(ch))) {
        result[i] = ch;
        i++;
      } else
        log.debug("stripped/ignored illegal XMl character " + ch);
    }
    return new String(result, 0, i);
  }

  protected static boolean isPunctuation(char c) {
    return c == ',' || c == '.' || c == '!' || c == '?' || c == ':' || c == ';';
  }

  protected static boolean isOtherAllowed(char c) {
    return c == '\'' || c == '"' || c == '@' || c == '#' || c == '$' || c == '%' || c == '*'
        || c == '&' || c == '(' || c == ')' || c == '-' || c == '+';
  }

}
