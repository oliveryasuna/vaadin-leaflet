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

package com.oliveryasuna.vaadin.leaflet.js;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

/**
 * Represents the Leaflet API {@code PanOptions} type.
 *
 * @author Oliver Yasuna
 */
public class LPanOptions implements Serializable {

  // Constructors
  //--------------------------------------------------

  public LPanOptions() {
    super();
  }

  @Builder
  public LPanOptions(final Boolean animate, final Double duration, final Double easeLinearity, final Boolean noMoveStart) {
    this();

    this.animate = animate;
    this.duration = duration;
    this.easeLinearity = easeLinearity;
    this.noMoveStart = noMoveStart;
  }

  // Fields
  //--------------------------------------------------

  @JsonProperty("animate")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean animate;

  @JsonProperty("duration")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Double duration;

  @JsonProperty("easeLinearity")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Double easeLinearity;

  @JsonProperty("noMoveStart")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean noMoveStart;

  // Getters/setters
  //--------------------------------------------------

  public Boolean getAnimate() {
    return animate;
  }

  public void setAnimate(final Boolean animate) {
    this.animate = animate;
  }

  public Double getDuration() {
    return duration;
  }

  public void setDuration(final Double duration) {
    this.duration = duration;
  }

  public Double getEaseLinearity() {
    return easeLinearity;
  }

  public void setEaseLinearity(final Double easeLinearity) {
    this.easeLinearity = easeLinearity;
  }

  public Boolean getNoMoveStart() {
    return noMoveStart;
  }

  public void setNoMoveStart(final Boolean noMoveStart) {
    this.noMoveStart = noMoveStart;
  }

}
