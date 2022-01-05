/*
 *
 * Copyright © ${year} ${name}
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
package org.treblereel.gwt.resources.rg.resource;

import java.util.*;
import org.treblereel.gwt.resources.context.AptContext;
import org.treblereel.gwt.resources.ext.BadPropertyValueException;
import org.treblereel.gwt.resources.ext.ConfigurationProperty;
import org.treblereel.gwt.resources.ext.DefaultConfigurationProperty;

/** @author Dmitrii Tikhomirov Created by treblereel 11/7/18 */
public final class ConfigurationProperties {

  public static final String KEY_CLIENT_BUNDLE_ENABLE_INLINING = "ClientBundle.enableInlining";
  public static final String KEY_CLIENT_BUNDLE_ENABLE_RENAMING = "ClientBundle.enableRenaming";

  private final Map<String, ConfigurationProperty> holder = new HashMap<>();
  private final AptContext context;

  public ConfigurationProperties(AptContext context) {
    this.context = context;

    setDefaultProperties();
  }

  private void setDefaultProperties() {
    set(KEY_CLIENT_BUNDLE_ENABLE_INLINING, Arrays.asList("true"), true);
    set(KEY_CLIENT_BUNDLE_ENABLE_RENAMING, Arrays.asList("true"), true);
  }

  private void set(String propertyName, List<String> defaulValues, boolean override) {
    Set<String> list = new HashSet<>(defaulValues);
    if (holder.containsKey(propertyName)) {
      if (override) {
        holder.put(propertyName, new DefaultConfigurationProperty(propertyName, list));
      } else {
        holder.get(propertyName).getValues().addAll(defaulValues);
      }
    } else {
      holder.put(propertyName, new DefaultConfigurationProperty(propertyName, list));
    }
  }

  public ConfigurationProperty getConfigurationProperty(String key)
      throws BadPropertyValueException {
    if (holder.containsKey(key)) {
      return holder.get(key);
    }
    throw new BadPropertyValueException(key);
  }
}
