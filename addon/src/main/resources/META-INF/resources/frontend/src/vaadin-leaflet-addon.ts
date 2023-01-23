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
import {addLatLng, getLatLng, latLng_wrap} from './leaflet-lat-lng';
import {addMarker, getMarker} from './leaflet-marker';

interface LeafletAddon {
  support: {
    latLng: {
      store: {
        values: L.LatLng[];
        get: (index: number) => L.LatLng | undefined;
        add: (latitude: number, longitude: number, altitude?: number) => number;
      },
      methods: {
        // TODO: If I write about these support methods,
        //       note that I specifically chose to lookup by index
        //       on the client-side, instead of passing the
        //       expression built on the server-side.
        //       Why build the expression on the server-side
        //       and then execute it on the client-side?
        wrap: (storeIndex: number) => number,
      }
    },
    gridLayer: {
      store: {
        values: L.GridLayer[];
        get: (index: number) => L.GridLayer | undefined;
        add: (options?: L.GridLayerOptions) => number;
      }
    },
    tileLayer: {
      store: {
        values: L.TileLayer[];
        get: (index: number) => L.TileLayer | undefined;
        add: (urlTemplate: string, options?: L.TileLayerOptions) => number;
      }
    },
    map: {
      store: {
        values: L.Map[];
        get: (index: number) => L.Map | undefined;
        add: (elementId: string, options?: L.MapOptions) => number;
      }
    },
    marker: {
      store: {
        values: L.Marker[];
        get: (index: number) => L.Marker | undefined;
        add: (latLng: L.LatLngExpression, options?: L.MarkerOptions) => number;
      }
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
        },
        methods: {
          wrap: latLng_wrap
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
        store: {
          values: [],
          get: getTileLayer,
          add: addTileLayer
        }
      },
      map: {
        store: {
          values: [],
          get: getMap,
          add: addMap
        }
      },
      marker: {
        store: {
          values: [],
          get: getMarker,
          add: addMarker
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
