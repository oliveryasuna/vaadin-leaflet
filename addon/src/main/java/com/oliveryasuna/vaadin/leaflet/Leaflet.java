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

import com.oliveryasuna.vaadin.leaflet.type.LMap;
import com.oliveryasuna.vaadin.leaflet.type.LTileLayer;
import com.oliveryasuna.vaadin.leaflet.util.FrontendUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.NpmPackage;

import java.util.concurrent.CompletableFuture;

/**
 * Represents {@code L}.
 *
 * @author Oliver Yasuna
 */
@NpmPackage(value = "leaflet", version = "1.9.0")
public class Leaflet {

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

  // TODO: latLng().

  // TODO: latLngBounds().

  // TODO: point().

  // TODO: bounds().

  // TODO: gridLayer().

  public CompletableFuture<LTileLayer> tileLayer(final UI ui, final String urlTemplate) {
    return FrontendUtils.executeLeafletAddonFunction(ui, int.class, "addTileLayer", urlTemplate)
        .thenApply(id -> LTileLayer.createAndStore(ui, id));
  }

  public CompletableFuture<LMap> map(final UI ui, final String elementId) {
    return FrontendUtils.executeLeafletAddonFunction(ui, int.class, "addMap", elementId)
        .thenApply(id -> LMap.createAndStore(ui, id));
  }

  // TODO: map(UI, String, MapOptions).

  // TODO: More functions.

}
