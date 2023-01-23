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

package com.oliveryasuna.vaadin.leaflet.demo.ui.view;

import com.oliveryasuna.vaadin.leaflet.Leaflet;
import com.oliveryasuna.vaadin.leaflet.type.LLatLngTuple;
import com.oliveryasuna.vaadin.leaflet.type.LMap;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
@JsModule("./src/vaadin-leaflet-addon.ts")
public class DemoView extends Composite<VerticalLayout> {

  @Override
  protected VerticalLayout initContent() {
    final Div mapContainer = new Div();
    mapContainer.setId("map");
    mapContainer.setSizeFull();

    Leaflet.getInstance().map(UI.getCurrent(), "map")
        .thenAccept(map -> map.setView(new LLatLngTuple(42.2250837, -71.6016022), 12)
            .thenRun(() -> Leaflet.getInstance().tileLayer(map.getUi(), "https://tile.openstreetmap.org/{z}/{x}/{y}.png")
                .thenAccept(tileLayer -> tileLayer.addTo(map)
                    .thenRun(() -> Leaflet.getInstance().marker(map.getUi(), new LLatLngTuple(42.2250837, -71.6016022))
                        .thenAccept(marker -> marker.addTo(map))))));

    final VerticalLayout content = new VerticalLayout();
    content.add(mapContainer);
    content.setSizeFull();
    content.setPadding(false);

    return content;
  }

  private Component[] controls() {
    return new Component[] {
        new Button("Reset View", event -> LMap.get(0).setView(new LLatLngTuple(51.505, -0.09), 13))
    };
  }

}
