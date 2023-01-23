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
 * Gets the {@link L.Marker} at the given index.
 *
 * @param index The index of the {@link L.Marker} in the store.
 *
 * @return The {@link L.Marker} at the given index, or `undefined` if the index is out of bounds.
 */
function getMarker(index: number): L.Marker | undefined {
  return window.LeafletAddon.support.marker.store.values[index];
}

/**
 * Creates and adds a {@link L.Marker} to the store.
 *
 * @param latLng The coordinates.
 * @param options The options.
 *
 * @return The index of the {@link L.Marker} in the store.
 */
function addMarker(latLng: L.LatLngExpression, options?: L.MarkerOptions): number {
  return __addMarker(L.marker(latLng, options));
}

function __addMarker(marker: L.Marker): number {
  const store: L.Marker[] = window.LeafletAddon.support.marker.store.values;
  const id: number = store.length;

  store.push(marker);

  return id;
}

export {
  getMarker,
  addMarker
};
