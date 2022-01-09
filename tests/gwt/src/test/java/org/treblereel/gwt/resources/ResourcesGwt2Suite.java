/*
 * Copyright © 2019 The GWT Authors
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
package org.treblereel.gwt.resources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.treblereel.gwt.resources.client.DataResourceMimeTypeTest;
import org.treblereel.gwt.resources.client.ImageResourceTest;
import org.treblereel.gwt.resources.client.TextResourceTest;

/** All JSON tests. */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  DataResourceMimeTypeTest.class,
  ImageResourceTest.class,
  TextResourceTest.class
})
public class ResourcesGwt2Suite {}
