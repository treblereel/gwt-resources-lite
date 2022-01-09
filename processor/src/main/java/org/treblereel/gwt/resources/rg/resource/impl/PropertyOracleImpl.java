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
package org.treblereel.gwt.resources.rg.resource.impl;

import org.treblereel.gwt.resources.context.AptContext;
import org.treblereel.gwt.resources.ext.*;
import org.treblereel.gwt.resources.rg.resource.ConfigurationProperties;
import org.treblereel.gwt.resources.rg.resource.StandardSelectionProperty;

/** @author Dmitrii Tikhomirov Created by treblereel 12/5/18 */
public class PropertyOracleImpl implements PropertyOracle {
  public final ConfigurationProperties configurationProperties;

  public PropertyOracleImpl(AptContext aptContext) {
    configurationProperties = new ConfigurationProperties(aptContext);
  }

  @Override
  public ConfigurationProperty getConfigurationProperty(TreeLogger logger, String propertyName)
      throws UnableToCompleteException {
    ConfigurationProperty configurationProperty;
    try {
      configurationProperty = configurationProperties.getConfigurationProperty(propertyName);
    } catch (BadPropertyValueException e) {
      logger.log(TreeLogger.Type.ERROR, "Unable to get configuration property : " + propertyName);
      throw new UnableToCompleteException();
    }
    return configurationProperty;
  }

  @Override
  public SelectionProperty getSelectionProperty(TreeLogger logger, String propertyName)
      throws UnableToCompleteException {
    String value = System.getProperty(propertyName);
    if (value != null) {
      return new StandardSelectionProperty(propertyName, value);
    }
    logger.log(TreeLogger.Type.ERROR, "Unable to get selection property : " + propertyName);
    throw new UnableToCompleteException();
  }
}
