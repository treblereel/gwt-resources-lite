/*
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

package org.treblereel.gwt.resources.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;
import org.treblereel.gwt.resources.api.client.ClientBundle;
import org.treblereel.gwt.resources.api.client.Resource;
import org.treblereel.gwt.resources.api.client.TextResource;

public class TextResourceTest {

  @Resource
  interface TextTestResource extends ClientBundle {

    @Source("small.txt")
    TextResource getSmall();

    @Source("bigtextresource.txt")
    TextResource getBig();
  }

  private final TextTestResource textTestResource = new TextResourceTest_TextTestResourceImpl();

  @Resource
  interface TextEmptyTestResource extends ClientBundle {}

  private final TextEmptyTestResource textEmptyTestResource =
      new TextResourceTest_TextEmptyTestResourceImpl();

  @Test
  public void testEmpty() {
    assertNotNull(textEmptyTestResource);
  }

  @Test
  public void testGetSmall() {
    assertNotNull(textTestResource);
    assertEquals("getSmall", textTestResource.getSmall().getName());
    assertEquals("Hello, World!", textTestResource.getSmall().getText());
  }

  @Test
  public void testGetBig() {
    assertEquals("getBig", textTestResource.getBig().getName());

    Path fileName =
        Path.of("src/test/java/org/treblereel/gwt/resources/client/bigtextresource.txt");
    try {
      String actual = Files.readString(fileName);
      assertEquals(actual, textTestResource.getBig().getText());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
