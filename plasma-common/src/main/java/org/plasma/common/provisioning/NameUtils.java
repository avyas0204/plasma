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

package org.plasma.common.provisioning;

public class NameUtils {
  /**
   * Creates a capitalized, underscore delimited name from the given camel case
   * name.
   * 
   * @param name
   *          the name
   * @return the result name
   */
  public static String toCamelCase(String name) {
    StringBuilder buf = new StringBuilder();
    if (name.indexOf("_") >= 0) {
      char[] array = name.toLowerCase().toCharArray();
      for (int i = 0; i < array.length; i++) {
        if (i > 0) {
          if (array[i] == '_') {
            int next = i + 1;
            if (next < array.length)
              array[next] = Character.toUpperCase(array[next]);
            continue;
          } else
            buf.append(array[i]);
        } else {
          buf.append(Character.toUpperCase(array[i]));
        }
      }
    } else {
      char[] array = name.toCharArray();
      if (hasUpper(array)) {
        if (hasLower(array)) {
          buf.append(name);
        } else
          buf.append(name.toLowerCase());
      } else {
        buf.append(name);
      }
    }

    return buf.toString();
  }

  private static boolean hasDelimiter(char[] array, char c) {
    for (int i = 0; i < array.length; i++)
      if (array[i] == c)
        return true;
    return false;
  }

  private static boolean hasLower(char[] array) {
    for (int i = 0; i < array.length; i++)
      if (Character.isLowerCase(array[i]))
        return true;
    return false;
  }

  private static boolean hasUpper(char[] array) {
    for (int i = 0; i < array.length; i++)
      if (Character.isUpperCase(array[i]))
        return true;
    return false;
  }

  /**
   * Creates a capitalized, underscore delimited name from the given camel case
   * name.
   * 
   * @param name
   *          the name
   * @return the result name
   */
  public static String toConstantName(String name) {
    StringBuilder buf = new StringBuilder();
    char[] array = name.toCharArray();
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        if (Character.isLetter(array[i]) && Character.isUpperCase(array[i]))
          buf.append("_");
      }
      if (Character.isLetterOrDigit(array[i])) {
        buf.append(Character.toUpperCase(array[i]));
      } else
        buf.append("_");
    }
    return buf.toString();
  }

  /**
   * Creates a capitalized, underscore delimited name from the given camel case
   * name and removes consonant characters where the name exceeds the given max.
   * 
   * @param name
   *          the name
   * @return
   */
  public static String toAbbreviatedName(String name) {
    return toAbbreviatedName(name, 4);
  }

  /**
   * Creates a capitalized, underscore delimited name from the given camel case
   * name and removes consonant characters where the name exceeds the given max.
   * 
   * @param name
   *          the name
   * @param max
   *          the max size to allow before abbreviation begins.
   * @return
   */
  public static String toAbbreviatedName(String name, int max) {

    String constantName = toConstantName(name);
    if (constantName.length() <= max)
      return constantName;
    StringBuilder buf = new StringBuilder();
    char[] array = constantName.toCharArray();
    for (int i = 0; i < array.length; i++) {
      if (i == 0) {
        buf.append(array[i]);
        continue;
      }
      if (array[i] == 'A' || array[i] == 'E' || array[i] == 'I' || array[i] == 'O'
          || array[i] == 'U') {
        continue;
      } else
        buf.append(array[i]);
    }
    return buf.toString();
  }

  public static String firstToUpperCase(String name) {
    if (Character.isLowerCase(name.charAt(0))) {
      return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    return name;
  }

  public static String firstToLowerCase(String name) {
    if (Character.isUpperCase(name.charAt(0))) {
      return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    return name;
  }
}
