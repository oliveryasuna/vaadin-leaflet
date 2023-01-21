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

import java.util.concurrent.CompletableFuture;

/**
 * Represents the Leaflet API {@code Layer} type.
 *
 * @author Oliver Yasuna
 */
// TODO: extends LEvented?
public abstract class LLayer<T extends LLayer<T>> implements StoredLeafletPojo {

  // Constructors
  //--------------------------------------------------

  protected LLayer(final Class<T> type, final UI ui, final int id) {
    super();

    this.type = type;

    this.ui = ui;
    this.id = id;
  }

  // TODO: JavaScript constructors.

  // Fields
  //--------------------------------------------------

  private final Class<T> type;

  private final UI ui;

  private final int id;

  // Methods
  //--------------------------------------------------

  public CompletableFuture<T> addTo(final LMap map) {
    return callJsFunction(getType(), "addTo", StoredLeafletPojo.buildClientSideGetExpression(map))
        .thenApply(ignored -> getType().cast(this));
  }

  // TODO: addTo(LayerGroup).

  // TODO: More methods.

  // Getters/setters
  //--------------------------------------------------

  public Class<T> getType() {
    return type;
  }

  @Override
  public UI getUi() {
    return ui;
  }

  @Override
  public int getId() {
    return id;
  }

}
