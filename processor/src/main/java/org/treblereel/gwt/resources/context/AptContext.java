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

import com.google.auto.common.MoreElements;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.treblereel.gwt.resources.api.client.ClientBundle;
import org.treblereel.gwt.resources.api.client.DataResource;
import org.treblereel.gwt.resources.api.client.ImageResource;
import org.treblereel.gwt.resources.api.client.TextResource;
import org.treblereel.gwt.resources.ext.ResourceGenerator;
import org.treblereel.gwt.resources.rg.BundleResourceGenerator;
import org.treblereel.gwt.resources.rg.DataResourceGenerator;
import org.treblereel.gwt.resources.rg.ImageResourceGenerator;
import org.treblereel.gwt.resources.rg.TextResourceGenerator;

/** @author Dmitrii Tikhomirov <chani.liet@gmail.com> Created by treblereel on 10/26/18. */
public class AptContext {
  public final Messager messager;
  public final Filer filer;
  public final Elements elements;
  public final Types types;
  public final RoundEnvironment roundEnvironment;
  public final ProcessingEnvironment processingEnv;

  public final Map<Element, Class<? extends ResourceGenerator>> generators = new HashMap<>();

  public AptContext(
      final ProcessingEnvironment processingEnv, final RoundEnvironment roundEnvironment) {
    this.filer = processingEnv.getFiler();
    this.messager = processingEnv.getMessager();
    this.elements = processingEnv.getElementUtils();
    this.types = processingEnv.getTypeUtils();
    this.roundEnvironment = roundEnvironment;

    this.processingEnv = processingEnv;
    initGenerators();
  }

  public Set<TypeElement> getClassesWithAnnotation(Class<? extends Annotation> annotation) {
    Set<TypeElement> rez =
        roundEnvironment
            .getElementsAnnotatedWith(annotation)
            .stream()
            .map(element -> MoreElements.asType(element))
            .collect(Collectors.toSet());
    return rez;
  }

  private void initGenerators() {
    preBuildGenerators();
  }

  private void preBuildGenerators() {
    generators.put(
        elements.getTypeElement(ClientBundle.class.getCanonicalName()),
        BundleResourceGenerator.class);
    generators.put(
        elements.getTypeElement(DataResource.class.getCanonicalName()),
        DataResourceGenerator.class);
    generators.put(
        elements.getTypeElement(ImageResource.class.getCanonicalName()),
        ImageResourceGenerator.class);
    generators.put(
        elements.getTypeElement(TextResource.class.getCanonicalName()),
        TextResourceGenerator.class);
  }
}
