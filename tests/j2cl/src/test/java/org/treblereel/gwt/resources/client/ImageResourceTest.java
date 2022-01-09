/*
 * Copyright © 2019 The GWT Project Authors
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
import static org.treblereel.gwt.resources.api.client.ImageResource.*;

import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;
import org.treblereel.gwt.resources.api.client.ClientBundle;
import org.treblereel.gwt.resources.api.client.ImageResource;
import org.treblereel.gwt.resources.api.client.Resource;

/** @author Dmitrii Tikhomirov <chani.liet@gmail.com> Created by treblereel on 10/22/18. */
@J2clTestInput(ImageResourceTest.class)
public class ImageResourceTest {

  private final ImageResources imageResources = new ImageResourceTest_ImageResourcesImpl();

  @Resource
  interface ImageResources extends ClientBundle {
    @Source("animated.gif")
    ImageResource animated();

    /**
     * This image shouldn't be re-encoded as a PNG or it will dramatically increase in size,
     * although it's still small enough to be encoded as a data URL as-is.
     */
    ImageResource complexLossy();

    @Source("16x16.png")
    ImageResource i16x16();

    @Source("32x32.png")
    ImageResource i32x32();

    @Source("64x64.png")
    ImageResource i64x64();

    @Source("64x64.png")
    ImageResource i64x64Dup();

    @Source("64x64-dup.png")
    ImageResource i64x64Dup2();

    @Source("64x64.png")
    @ImageOptions(width = 32)
    ImageResource scaledDown();

    @Source("64x64.png")
    @ImageOptions(width = 128)
    ImageResource scaledUp();
  }

  @Test
  public void testAnimated() {
    assertNotNull(imageResources);
    assertEquals("animated", imageResources.animated().getName());
    assertEquals(16, imageResources.animated().getHeight());
    assertEquals(16, imageResources.animated().getWidth());
  }

  @Test
  public void testScaledDown() {
    assertNotNull(imageResources);
    assertEquals("scaledDown", imageResources.scaledDown().getName());
    assertEquals(32, imageResources.scaledDown().getHeight());
    assertEquals(32, imageResources.scaledDown().getWidth());
  }

  @Test
  public void testScaledUp() {
    assertNotNull(imageResources);
    assertEquals("scaledUp", imageResources.scaledUp().getName());
    assertEquals(128, imageResources.scaledUp().getHeight());
    assertEquals(128, imageResources.scaledUp().getWidth());
  }
}
