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

import * as L from 'leaflet';

/**
 * Gets the {@link L.TileLayer} at the given index.
 *
 * @param index The index of the {@link L.TileLayer} in the store.
 *
 * @return The {@link L.TileLayer} at the given index, or `undefined` if the index is out of bounds.
 */
function getTileLayer(index: number): L.TileLayer | undefined {
  return window.LeafletAddon.support.tileLayer.store[index];
}

/**
 * Creates and adds a {@link L.TileLayer} to the store.
 *
 * @param urlTemplate The URL template of the {@link L.TileLayer}.
 *
 * @return The index of the {@link L.TileLayer} in the store.
 */
// TODO: More parameters.
function addTileLayer(urlTemplate: string): number {
  const store: L.TileLayer[] = window.LeafletAddon.support.tileLayer.store;

  const id: number = store.length;
  const tileLayer: L.TileLayer = L.tileLayer(urlTemplate);

  store.push(tileLayer);

  return id;
}

export {
  getTileLayer,
  addTileLayer
};
