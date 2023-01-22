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
import {addGridLayer, getGridLayer} from './leaflet-grid-layer';
import {addMap, getMap} from './leaflet-map';
import {addTileLayer, getTileLayer} from './leaflet-tile-layer';
import {addLatLng, getLatLng} from './leaflet-lat-lng';
import {Store} from './store';

interface LeafletAddon {
  support: {
    latLng: {
      store: Store<L.LatLng>
    },
    gridLayer: {
      store: Store<L.GridLayer>
    },
    tileLayer: {
      store: Store<L.TileLayer>
    },
    map: {
      store: Store<L.Map>
    }
  };
}

if(!window.LeafletAddon) {
  window.LeafletAddon = {
    support: {
      latLng: {
        store: {
          values: [],
          get: getLatLng,
          add: addLatLng
        }
      },
      gridLayer: {
        store: {
          values: [],
          get: getGridLayer,
          add: addGridLayer
        }
      },
      tileLayer: {
        // store: [],
        // get: getTileLayer,
        // add: addTileLayer
        store: {
          values: [],
          get: getTileLayer,
          add: addTileLayer
        }
      },
      map: {
        // store: [],
        // get: getMap,
        // add: addMap
        store: {
          values: [],
          get: getMap,
          add: addMap
        }
      }
    }
  };
}

declare global {
  interface Window {
    LeafletAddon: LeafletAddon;
  }
}
