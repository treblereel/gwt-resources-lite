/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.treblereel.gwt.resources.rg;

import javax.lang.model.element.ExecutableElement;
import org.treblereel.gwt.resources.ext.AbstractResourceGenerator;
import org.treblereel.gwt.resources.ext.ResourceContext;
import org.treblereel.gwt.resources.ext.TreeLogger;
import org.treblereel.gwt.resources.ext.UnableToCompleteException;

/** Provides implementations of DataResource. */
public final class DataResourceGenerator extends AbstractResourceGenerator {
  @Override
  public String createAssignment(
      TreeLogger logger, ResourceContext context, ExecutableElement method, String locale)
      throws UnableToCompleteException {
    throw new Error(" DataResource is not implemented");
    /*    ResourceOracle resourceOracle = context.getGeneratorContext().getResourcesOracle();
    URL[] resources = resourceOracle.findResources(logger, method, locale);

    if (resources.length != 1) {
      logger.log(TreeLogger.ERROR, "Exactly one resource must be specified", null);
      throw new UnableToCompleteException();
    }

    // Determine if a MIME Type has been specified
    DataResource.MimeType mimeTypeAnnotation = method.getAnnotation(DataResource.MimeType.class);
    String mimeType = mimeTypeAnnotation != null ? mimeTypeAnnotation.value() : null;

    // Determine if resource should not be embedded
    boolean forceExternal = false;

    URL resource = resources[0];
    String outputUrlExpression = context.deploy(resource, mimeType, forceExternal);

    SourceWriter sw = new StringSourceWriter();
    // Convenience when examining the generated code.
    if (!AbstractResourceGenerator.STRIP_COMMENTS) {
      sw.println("// " + resource.toExternalForm());
    }
    sw.println("new " + DataResourcePrototype.class.getName() + "(");
    sw.indent();
    sw.println('"' + method.getSimpleName().toString() + "\",");
    sw.println(
        UriUtils.class.getCanonicalName() + ".fromTrustedString(" + outputUrlExpression + ")");
    sw.outdent();
    sw.print(")");

    return sw.toString();*/
  }
}
