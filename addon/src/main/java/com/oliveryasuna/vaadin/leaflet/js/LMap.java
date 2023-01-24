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

package com.oliveryasuna.vaadin.leaflet.js;

import com.oliveryasuna.vaadin.leaflet.util.SerializationUtils;
import com.vaadin.flow.component.UI;
import elemental.json.JsonValue;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Represents the Leaflet API {@code Map} type.
 *
 * @author Oliver Yasuna
 */
// TODO: extends LEvented?
public class LMap implements SupportedLeafletPojo {

  // Static fields
  //--------------------------------------------------

  public static final String SUPPORT_PROPERTY_NAME = "map";

  // Static instances
  //--------------------------------------------------

  protected static final Map<Integer, LMap> STORE = Collections.synchronizedMap(new WeakHashMap<>());

  public static synchronized LMap get(final int id) {
    return STORE.get(id);
  }

  public static synchronized LMap createAndStore(final UI ui, final int id) {
    final LMap map = new LMap(ui, id);

    STORE.put(id, map);

    return map;
  }

  // Constructors
  //--------------------------------------------------

  protected LMap(final UI ui, final int id) {
    super();

    this.ui = ui;
    this.id = id;
  }

  // Fields
  //--------------------------------------------------

  private final UI ui;

  private final int id;

  // Methods
  //--------------------------------------------------

  // TODO: JavaScript properties
  //

  // JavaScript methods
  //

  // TODO: Many more methods.

  public CompletableFuture<LMap> addLayer(final LLayer<?> layer) {
    return callJsMethodRawArguments(null, "addLayer", SupportedLeafletPojo.buildJsStoreGetExpression(layer))
        .thenApply(ignored -> this);
  }

  public CompletableFuture<LMap> setView(final LLatLngExpression center) {
    final JsonValue centerJson = SerializationUtils.toElementalValue(center);

    return callJsMethod(null, "setView", centerJson)
        .thenApply(ignored -> this);
  }

  public CompletableFuture<LMap> setView(final LLatLngExpression center, final double zoom) {
    final JsonValue centerJson = SerializationUtils.toElementalValue(center);

    return callJsMethod(null, "setView", centerJson, zoom)
        .thenApply(ignored -> this);
  }

  public CompletableFuture<LMap> setView(final LLatLngExpression center, final double zoom, final LZoomPanOptions options) {
    final JsonValue centerJson = SerializationUtils.toElementalValue(center);
    final JsonValue optionsJson = SerializationUtils.toElementalValue(options);

    return callJsMethod(null, "setView", centerJson, zoom, optionsJson)
        .thenApply(ignored -> this);
  }

  // TODO: Many more methods.

  // Miscellaneous
  //

  @Override
  public final String getSupportPropertyName() {
    return SUPPORT_PROPERTY_NAME;
  }

  // Getters/setters
  //--------------------------------------------------

  @Override
  public UI getUi() {
    return ui;
  }

  @Override
  public int getId() {
    return id;
  }

}
