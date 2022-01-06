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
package org.treblereel.gwt.resources.context;

import java.io.IOException;
import java.io.OutputStream;
import javax.lang.model.element.TypeElement;
import org.treblereel.gwt.resources.ext.GeneratorContext;
import org.treblereel.gwt.resources.ext.TreeLogger;
import org.treblereel.gwt.resources.ext.UnableToCompleteException;
import org.treblereel.gwt.resources.rg.util.Util;

/** @author Dmitrii Tikhomirov Created by treblereel 11/11/18 */
class StaticResourceContext extends AbstractResourceContext {

  StaticResourceContext(
      TreeLogger logger,
      GeneratorContext context,
      TypeElement resourceBundleType,
      ClientBundleContext clientBundleCtx) {
    super(logger, context, resourceBundleType, clientBundleCtx);
  }

  public String deploy(
      String suggestedFileName, String mimeType, byte[] data, boolean forceExternal)
      throws UnableToCompleteException {
    TreeLogger logger = getLogger();
    GeneratorContext context = getGeneratorContext();

    // See if filename obfuscation should be enabled
    boolean enableRenaming = true;

    // Determine the final filename for the resource's file
    String outputName;
    if (enableRenaming) {
      String strongName = Util.computeStrongName(data);

      // Determine the extension of the original file
      String extension;
      int lastIdx = suggestedFileName.lastIndexOf('.');
      if (lastIdx != -1) {
        extension = suggestedFileName.substring(lastIdx + 1);
      } else {
        extension = "noext";
      }
      // The name will be MD5.cache.ext
      outputName = strongName + ".cache." + extension;
    } else {
      outputName = suggestedFileName.substring(suggestedFileName.lastIndexOf('/') + 1);
    }

    // Ask the context for an OutputStream into the named resource
    OutputStream out = context.tryCreateResource(logger, outputName);

    // This would be null if the resource has already been created in the
    // output (because two or more resources had identical content).
    if (out != null) {
      try {
        out.write(data);

      } catch (IOException e) {
        logger.log(TreeLogger.ERROR, "Unable to write data to output name " + outputName, e);
        throw new UnableToCompleteException();
      }

      // If there's an error, this won't be called and there will be nothing
      // created in the output directory.
      context.commitResource(logger, out);
      if (logger.isLoggable(TreeLogger.DEBUG)) {
        logger.log(TreeLogger.DEBUG, "Copied " + data.length + " bytes to " + outputName, null);
      }
    }

    // Return a Java expression
    return "";
  }

  @Override
  public boolean supportsDataUrls() {
    return false;
  }
}
