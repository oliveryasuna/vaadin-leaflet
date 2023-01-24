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

package com.oliveryasuna.vaadin.leaflet.component;

import com.oliveryasuna.commons.language.condition.Arguments;
import com.oliveryasuna.vaadin.leaflet.Leaflet;
import com.oliveryasuna.vaadin.leaflet.js.LLatLngExpression;
import com.oliveryasuna.vaadin.leaflet.js.LLayer;
import com.oliveryasuna.vaadin.leaflet.js.LMap;
import com.oliveryasuna.vaadin.leaflet.js.LZoomPanOptions;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * Leaflet map component.
 *
 * @author Oliver Yasuna
 */
public class Map extends AbstractLeafletComponent<Div, LMap> implements HasSize {

  // Static fields
  //--------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger(Map.class);

  // Constructors
  //--------------------------------------------------

  public Map(final String elementId) {
    super(Leaflet.getInstance().map(UI.getCurrent(), Arguments.requireNotNull(elementId, "Must specify an element ID.")));

    setId(elementId);
    setSizeFull();
  }

  // Methods
  //--------------------------------------------------

  // TODO: JavaScript properties
  //

  // JavaScript methods
  //

  // TODO: More methods.

  public void addLayer(final LLayer<?> layer) {
    schedule(map -> {
      try {
        map.addLayer(layer);

        System.out.println("LAYER"); // TODO: REMOVE ME!
      } catch(final Exception e) {
        LOGGER.error("Failed to add layer.", e);
      }
    });
  }

  public void addLayer(final CompletableFuture<? extends LLayer<?>> layer) {
    schedule(map -> {
      layer.thenApply(map::addLayer);
    });
  }

  public void setView(final LLatLngExpression center) {
    schedule(map -> map.setView(center));
  }

  public void setView(final LLatLngExpression center, final int zoom) {
    schedule(map -> {
      try {
        map.setView(center, zoom).get();

        System.out.println("VIEW"); // TODO: REMOVE ME!
      } catch(final Exception e) {
        logger().error("Failed to set view.", e);
      }
    });
  }

  public void setView(final LLatLngExpression center, final int zoom, final LZoomPanOptions options) {
    schedule(map -> map.setView(center, zoom, options));
  }

  // TODO: More methods.

  // Miscellaneous
  //

  @Override
  protected Div initContent() {
    return new Div();
  }

  @Override
  protected Logger logger() {
    return LOGGER;
  }

}
