/*
 * Copyright 2010 Google Inc.
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
package org.treblereel.gwt.resources.client;

import static org.junit.Assert.assertEquals;
import static org.treblereel.gwt.resources.api.client.DataResource.*;

import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;
import org.treblereel.gwt.resources.api.client.ClientBundle;
import org.treblereel.gwt.resources.api.client.DataResource;
import org.treblereel.gwt.resources.api.client.Resource;

/** Tests for {@link MimeType @MimeType} resource annotations. */
@J2clTestInput(DataResourceMimeTypeTest.class)
public class DataResourceMimeTypeTest {

  @Resource
  interface DataResourceMimeType extends ClientBundle {

    /**
     * This is a binary file containing four 0x00 bytes, which is small enough to be embeddable, and
     * contains insufficient information for a determination of a recognizable MIME Type.
     */
    String FOUR_ZEROS_SOURCE = "fourZeros.dat";

    /** A simple MIME Type as per RFC 1521. */
    String MIME_TYPE_AUDIO_OGG = "audio/ogg";

    /** MIME Type with a single codecs specification as per RFC 4281. */
    String MIME_TYPE_WITH_CODECS = "audio/3gpp; codecs=samr";

    /** MIME Type with a multiple codecs specification as per RFC 4281. */
    String MIME_TYPE_WITH_QUOTED_CODECS_LIST = "video/3gpp; codecs=\"s263, samr\"";

    // Purposely missing a @MimeType annotation
    @Source(FOUR_ZEROS_SOURCE)
    DataResource resourceMimeTypeNoAnnotation();

    @MimeType(MIME_TYPE_AUDIO_OGG)
    @Source(FOUR_ZEROS_SOURCE)
    DataResource resourceMimeTypeAnnotationAudioOgg();

    @MimeType(MIME_TYPE_WITH_CODECS)
    @Source(FOUR_ZEROS_SOURCE)
    DataResource resourceMimeTypeAnnotationWithCodecs();

    @MimeType(MIME_TYPE_WITH_QUOTED_CODECS_LIST)
    @Source(FOUR_ZEROS_SOURCE)
    DataResource resourceMimeTypeAnnotationWithQuotedCodecsList();
  }

  @Test
  public void testMimeTypeAnnotationMissingDefaultsToContentUnknown() {
    DataResourceMimeType r = new DataResourceMimeTypeTest_DataResourceMimeTypeImpl();
    String url = r.resourceMimeTypeNoAnnotation().asString();
    if (url.startsWith("http")) {
      // Skip test, MIME Type will be provided by the HTTP server
      return;
    }
    assertEquals("data:content/unknown;base64,AAAAAA==", url);
  }

  @Test
  public void testMimeTypeAnnotationOverridesDefaultMimeType() {
    DataResourceMimeType r = new DataResourceMimeTypeTest_DataResourceMimeTypeImpl();
    String url = r.resourceMimeTypeAnnotationAudioOgg().asString();
    if (url.startsWith("http")) {
      // Skip test, MIME Type will be provided by the HTTP server
      return;
    }
    assertEquals("data:audio/ogg;base64,AAAAAA==", url);
  }

  @Test
  public void testMimeTypeAnnotationWithCodecs() {
    DataResourceMimeType r = new DataResourceMimeTypeTest_DataResourceMimeTypeImpl();
    String url = r.resourceMimeTypeAnnotationWithCodecs().asString();
    if (url.startsWith("http")) {
      // Skip test, MIME Type will be provided by the HTTP server
      return;
    }
    assertEquals("data:audio/3gpp; codecs=samr;base64,AAAAAA==", url);
  }

  @Test
  public void testMimeTypeAnnotationWithQuotedCodecsList() {
    DataResourceMimeType r = new DataResourceMimeTypeTest_DataResourceMimeTypeImpl();
    String url = r.resourceMimeTypeAnnotationWithQuotedCodecsList().asString();
    if (url.startsWith("http")) {
      // Skip test, MIME Type will be provided by the HTTP server
      return;
    }
    assertEquals("data:video/3gpp; codecs=\"s263, samr\";base64,AAAAAA==", url);
  }
}
