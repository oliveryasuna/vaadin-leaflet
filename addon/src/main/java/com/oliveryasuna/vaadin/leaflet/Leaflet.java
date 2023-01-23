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

package com.oliveryasuna.vaadin.leaflet;

import com.oliveryasuna.vaadin.leaflet.type.*;
import com.oliveryasuna.vaadin.leaflet.util.FrontendUtils;
import com.oliveryasuna.vaadin.leaflet.util.SerializationUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.NpmPackage;
import elemental.json.JsonValue;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * Represents {@code L}.
 *
 * @author Oliver Yasuna
 */
@NpmPackage(value = "leaflet", version = "1.9.0")
@NpmPackage(value = "@types/leaflet", version = "1.9.0")
public class Leaflet {

  // Static methods
  //--------------------------------------------------

  protected static <R extends Serializable> CompletableFuture<R> executeSupportFunction(final UI ui, final Class<R> returnType,
      final String supportPropertyName, final String functionName, final Serializable... arguments) {
    final String functionNamePart = FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
        "window", FrontendUtils.LEAFLET_ADDON_PROPERTY_NAME, "support", supportPropertyName, functionName);
    final String functionParametersExpression = FrontendUtils.buildJsFunctionParametersExpression(IntStream.range(0, arguments.length).toArray());
    final String functionCallExpression = FrontendUtils.buildJsFunctionCall(functionNamePart, functionParametersExpression);

    final String expression;

    if(returnType != null) {
      expression = "return " + functionCallExpression;
    } else {
      expression = functionCallExpression;
    }

    return ui.getPage().executeJs(expression, arguments)
        .toCompletableFuture(returnType);
  }

  protected static <R extends Serializable> CompletableFuture<R> executeSupportFunctionRawArguments(final UI ui, final Class<R> returnType,
      final String supportPropertyName, final String functionName, final String rawArguments) {
    final String functionNamePart = FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
        "window", FrontendUtils.LEAFLET_ADDON_PROPERTY_NAME, "support", supportPropertyName, functionName);
    final String functionCallExpression = FrontendUtils.buildJsFunctionCall(functionNamePart, rawArguments);

    final String expression;

    if(returnType != null) {
      expression = "return " + functionCallExpression;
    } else {
      expression = functionCallExpression;
    }

    return ui.getPage().executeJs(expression)
        .toCompletableFuture(returnType);
  }

  protected static CompletableFuture<Integer> executeSupportStoreAddFunction(final UI ui, final String supportPropertyName, final Serializable... arguments) {
    return executeSupportFunction(
        ui,
        Integer.class,
        supportPropertyName,
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            SupportedLeafletPojo.STORE_PROPERTY_NAME, SupportedLeafletPojo.ADD_FUNCTION_NAME),
        arguments);
  }

  protected static CompletableFuture<Integer> executeSupportStoreAddFunctionRawArguments(final UI ui, final String supportPropertyName, final String rawArguments) {
    return executeSupportFunctionRawArguments(
        ui,
        Integer.class,
        supportPropertyName,
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            SupportedLeafletPojo.STORE_PROPERTY_NAME, SupportedLeafletPojo.ADD_FUNCTION_NAME),
        rawArguments);
  }

  // Singleton
  //--------------------------------------------------

  private static Leaflet instance;

  public static Leaflet getInstance() {
    if(instance == null) {
      instance = new Leaflet();
    }

    return instance;
  }

  // Constructors
  //--------------------------------------------------

  protected Leaflet() {
    super();
  }

  // Methods
  //--------------------------------------------------

  // TODO: transformation().

  public CompletableFuture<LLatLng> latLng(final UI ui, final double latitude, final double longitude) {
    return executeSupportStoreAddFunction(ui, LLatLng.SUPPORT_PROPERTY_NAME, latitude, longitude)
        .thenApply(id -> LLatLng.createAndStore(ui, id));
  }

  public CompletableFuture<LLatLng> latLng(final UI ui, final double latitude, final double longitude, final double altitude) {
    return executeSupportStoreAddFunction(ui, LLatLng.SUPPORT_PROPERTY_NAME, latitude, longitude, altitude)
        .thenApply(id -> LLatLng.createAndStore(ui, id));
  }

  // TODO: Other latLng() methods.

  // TODO: latLngBounds().

  // TODO: point().

  // TODO: bounds().

  public CompletableFuture<LGridLayer> gridLayer(final UI ui) {
    return executeSupportStoreAddFunction(ui, LGridLayer.SUPPORT_PROPERTY_NAME)
        .thenApply(id -> LGridLayer.createAndStore(ui, id));
  }

  // TODO: gridLayer(UI, GridLayerOptions).

  public CompletableFuture<LTileLayer> tileLayer(final UI ui, final String urlTemplate) {
    return executeSupportStoreAddFunction(ui, LTileLayer.SUPPORT_PROPERTY_NAME, urlTemplate)
        .thenApply(id -> LTileLayer.createAndStore(ui, id));
  }

  // TODO: tileLayer(UI, String, TileLayerOptions).

  public CompletableFuture<LMap> map(final UI ui, final String elementId) {
    return executeSupportStoreAddFunction(ui, LMap.SUPPORT_PROPERTY_NAME, elementId)
        .thenApply(id -> LMap.createAndStore(ui, id));
  }

  // TODO: map(UI, String, MapOptions).

  // TODO: icon(IconOptions).

  // TODO: divIcon().
  // TODO: divIcon(DivIconOptions).

  public CompletableFuture<LMarker> marker(final UI ui, final LLatLngExpression latLng) {
    final JsonValue latLngJson = SerializationUtils.toElementalValue(latLng);

    return executeSupportStoreAddFunction(ui, LMarker.SUPPORT_PROPERTY_NAME, latLngJson)
        .thenApply(id -> LMarker.createAndStore(ui, id));
  }

  // TODO: marker(LatLngExpression, MarkerOptions).

}
