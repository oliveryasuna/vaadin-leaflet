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

import com.oliveryasuna.vaadin.leaflet.util.SerializationUtils;
import com.vaadin.flow.component.UI;
import elemental.json.JsonValue;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Represents the Leaflet API {@code LatLng} type.
 *
 * @author Oliver Yasuna
 */
public class LLatLng implements SupportedLeafletPojo {

  // Static fields
  //--------------------------------------------------

  public static final String SUPPORT_PROPERTY_NAME = "latLng";

  // Static instances
  //--------------------------------------------------

  protected static final Map<Integer, LLatLng> STORE = Collections.synchronizedMap(new WeakHashMap<>());

  public static synchronized LLatLng get(final int id) {
    return STORE.get(id);
  }

  public static synchronized LLatLng createAndStore(final UI ui, final int id) {
    final LLatLng latLng = new LLatLng(ui, id);

    STORE.put(id, latLng);

    return latLng;
  }

  // Constructors
  //--------------------------------------------------

  protected LLatLng(final UI ui, final int id) {
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

  // JavaScript properties
  //

  public CompletableFuture<Double> lat() {
    return getJsProperty(Double.class, "lat");
  }

  public CompletableFuture<Double> lng() {
    return getJsProperty(Double.class, "lng");
  }

  public CompletableFuture<Double> alt() {
    return getJsProperty(Double.class, "alt");
  }

  // JavaScript functions
  //

  public CompletableFuture<Boolean> jsEquals(final LLatLngExpression otherLatLng) {
    final JsonValue otherLatLngJson = SerializationUtils.toElementalValue(otherLatLng);

    return callJsFunction(Boolean.class, "equals", otherLatLngJson)
        .thenApply(Boolean.class::cast);
  }

  public CompletableFuture<Boolean> jsEquals(final LLatLngExpression otherLatLng, final double maxMargin) {
    final JsonValue otherLatLngJson = SerializationUtils.toElementalValue(otherLatLng);

    return callJsFunction(Boolean.class, "equals", otherLatLngJson, maxMargin)
        .thenApply(Boolean.class::cast);
  }

  public CompletableFuture<String> jsToString() {
    return callJsFunction(String.class, "toString")
        .thenApply(String.class::cast);
  }

  public CompletableFuture<Double> distanceTo(final LLatLngExpression otherLatLng) {
    final JsonValue otherLatLngJson = SerializationUtils.toElementalValue(otherLatLng);

    return callJsFunction(Double.class, "distanceTo", otherLatLngJson)
        .thenApply(Double.class::cast);
  }

  // TODO: wrap(): LatLng.

  // TODO: toBounds(number): LatLngBounds.

  // TODO: jsClone(): LatLng.

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
