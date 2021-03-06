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
package org.treblereel.gwt.resources.apt;

import com.google.auto.service.AutoService;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import org.treblereel.gwt.resources.context.AptContext;
import org.treblereel.gwt.resources.ext.TreeLogger;
import org.treblereel.gwt.resources.ext.UnableToCompleteException;
import org.treblereel.gwt.resources.logger.PrintWriterTreeLogger;

/** @author Dmitrii Tikhomirov Created by treblereel on 9/30/18. */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"org.treblereel.gwt.resources.api.client.Resource"})
public class ClientBundleAnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(
      Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
    if (annotations.isEmpty()) {
      return false;
    }

    AptContext context = new AptContext(processingEnv, roundEnvironment);
    PrintWriterTreeLogger logger = new PrintWriterTreeLogger();
    logger.setMaxDetail(TreeLogger.Type.INFO);

    Set<TypeElement> annotatedElements =
        (Set<TypeElement>)
            roundEnvironment.getElementsAnnotatedWith(
                org.treblereel.gwt.resources.api.client.Resource.class);

    try {
      new ClientBundleClassBuilder(logger, context, annotatedElements).process();
    } catch (UnableToCompleteException e) {
      throw new Error(e);
    }
    return true;
  }
}
