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

package com.oliveryasuna.vaadin.leaflet.util;

import com.oliveryasuna.commons.language.exception.UnsupportedInstantiationException;

import java.util.stream.IntStream;

/**
 * Frontend utilities.
 *
 * @author Oliver Yasuna
 */
// TODO: When I release the next version of `flow-commons`, I'll be able to integrate the JS stuff here.
public final class FrontendUtils {

  // Static fields
  //--------------------------------------------------

  public static final String LEAFLET_ADDON_PROPERTY_NAME = "LeafletAddon";

  // Static methods
  //--------------------------------------------------

  public static String buildJsFunctionParametersExpression(final int... indices) {
    if(indices == null || indices.length == 0) {
      return "";
    }

    return IntStream.of(indices)
        .mapToObj(index -> "$" + index)
        .reduce((a, b) -> a + ", " + b)
        .orElse("");
  }

  public static String buildJsFunctionCall(final Object name, final Object parameters) {
    if(parameters != null) {
      return (name + "(" + parameters + ")");
    } else {
      return (name + "()");
    }
  }

  public static String buildJsPropertyAccessor(final JsPropertyAccessorNotation notation, final Object firstPropertyName, final Object... propertyNames) {
    final StringBuilder builder = new StringBuilder();

    builder.append(firstPropertyName);

    if(propertyNames != null && propertyNames.length > 0) {
      final String format = notation.getFormat();

      for(final Object propertyName : propertyNames) {
        builder.append(String.format(format, propertyName));
      }
    }

    return builder.toString();
  }

  // Constructors
  //--------------------------------------------------

  private FrontendUtils() {
    super();

    throw new UnsupportedInstantiationException();
  }

  // Nested
  //--------------------------------------------------

  /**
   * JavaScript property accessor notations.
   *
   * @author Oliver Yasuna
   */
  public enum JsPropertyAccessorNotation {

    // Values
    //--------------------------------------------------

    DOT(".%s"),

    INDEX("[%s]"),

    BRACKET("['%s']");

    // Constructors
    //--------------------------------------------------

    JsPropertyAccessorNotation(final String format) {
      this.format = format;
    }

    // Fields
    //--------------------------------------------------

    private final String format;

    // Getters/setters
    //--------------------------------------------------

    public String getFormat() {
      return format;
    }
  }

}
