/*
 * Copyright 2023 Oliver Yasuna
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without
 *      specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.oliveryasuna.vaadin.leaflet.type;

import com.vaadin.flow.component.UI;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Represents the Leaflet API {@code TileLayer} type.
 *
 * @author Oliver Yasuna
 */
public class LTileLayer extends AbstractLGridLayer<LTileLayer> {

  // Static fields
  //--------------------------------------------------

  public static final String SUPPORT_PROPERTY_NAME = "tileLayer";

  // Static instances
  //--------------------------------------------------

  protected static final Map<Integer, LTileLayer> STORE = Collections.synchronizedMap(new WeakHashMap<>());

  public static synchronized LTileLayer get(final int id) {
    return STORE.get(id);
  }

  public static synchronized LTileLayer createAndStore(final UI ui, final int id) {
    final LTileLayer map = new LTileLayer(ui, id);

    STORE.put(id, map);

    return map;
  }

  // Constructors
  //--------------------------------------------------

  protected LTileLayer(final UI ui, final int id) {
    super(LTileLayer.class, ui, id);
  }

  // Methods
  //--------------------------------------------------

  // TODO: JavaScript properties.
  //

  // TODO: JavaScript methods.
  //

  // Miscellaneous
  //

  @Override
  public String getSupportPropertyName() {
    return SUPPORT_PROPERTY_NAME;
  }

}
