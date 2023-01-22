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

import com.oliveryasuna.vaadin.leaflet.util.FrontendUtils;
import com.vaadin.flow.component.page.PendingJavaScriptResult;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * Leaflet POJOs that are stored in the client-side.
 *
 * @author Oliver Yasuna
 */
public interface SupportedLeafletPojo extends LeafletPojo {

  // Static fields
  //--------------------------------------------------

  String SUPPORT_PROPERTY_NAME = "support";

  String GET_FUNCTION_NAME = "get";

  String ADD_FUNCTION_NAME = "add";

  // Static methods
  //--------------------------------------------------

  static String buildJsGetExpression(final SupportedLeafletPojo supportedLeafletPojo) {
    return FrontendUtils.buildJsFunctionCall(
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            "window", FrontendUtils.LEAFLET_ADDON_PROPERTY_NAME, SUPPORT_PROPERTY_NAME, supportedLeafletPojo.getSupportPropertyName(), GET_FUNCTION_NAME),
        supportedLeafletPojo.getId()
    );
  }

  static String buildJsPropertyAccessorExpression(final SupportedLeafletPojo supportedLeafletPojo, final String propertyName) {
    return FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
        buildJsGetExpression(supportedLeafletPojo), propertyName);
  }

  static String buildJsFunctionCallExpression(final SupportedLeafletPojo supportedLeafletPojo, final String functionName, final Serializable... arguments) {
    return FrontendUtils.buildJsFunctionCall(
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            buildJsGetExpression(supportedLeafletPojo), functionName),
        FrontendUtils.buildJsFunctionParametersExpression(IntStream.range(0, arguments.length).toArray())
    );
  }

  static String buildJsFunctionCall(final SupportedLeafletPojo supportedLeafletPojo, final String functionName, final String rawArguments) {
    return FrontendUtils.buildJsFunctionCall(
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            buildJsGetExpression(supportedLeafletPojo), functionName),
        rawArguments
    );
  }

  // Methods
  //--------------------------------------------------

  @Override
  default <T extends Serializable> CompletableFuture<T> getJsProperty(final Class<T> type, final String propertyName) {
    return getUi().getPage().executeJs(buildJsPropertyAccessorExpression(this, propertyName))
        .toCompletableFuture(type);
  }

  @Override
  default CompletableFuture<?> callJsFunction(final Class<?> returnType, final String functionName, final Serializable... arguments) {
    final PendingJavaScriptResult result = getUi().getPage().executeJs(buildJsFunctionCallExpression(this, functionName, arguments), arguments);

    if(returnType != null) {
      return result.toCompletableFuture(returnType);
    } else {
      return result.toCompletableFuture();
    }
  }

  @Override
  default CompletableFuture<?> callJsFunction(final Class<?> returnType, final String functionName, final String rawArguments) {
    final PendingJavaScriptResult result = getUi().getPage().executeJs(buildJsFunctionCall(this, functionName, rawArguments));

    // TODO: Report invalid inspection to JetBrains.
    if(returnType != null) {
      return result.toCompletableFuture(returnType);
    } else {
      return result.toCompletableFuture(Void.class);
    }
  }

  String getSupportPropertyName();

}