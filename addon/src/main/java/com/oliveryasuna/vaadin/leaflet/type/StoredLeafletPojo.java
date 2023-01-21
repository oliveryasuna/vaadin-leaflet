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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.PendingJavaScriptResult;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * Leaflet POJOs that are stored in the client-side.
 *
 * @author Oliver Yasuna
 */
public interface StoredLeafletPojo extends LeafletPojo {

  // Static methods
  //--------------------------------------------------

  static String buildClientSideGetExpression(final StoredLeafletPojo leafletPojo) {
    String expression;

    expression = FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
        "window", FrontendUtils.LEAFLET_ADDON_PROPERTY_NAME, leafletPojo.getStorageAccessorFunctionName());
    expression = FrontendUtils.buildJsFunctionCall(expression,
        leafletPojo.getId());

    return expression;
  }

  // Methods
  //--------------------------------------------------

  @Override
  default CompletableFuture<?> callJsFunction(final Class<?> returnType, final String functionName, final Serializable... arguments) {
    String expression = buildClientSideGetExpression(this);
    expression = FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
        expression, functionName);
    expression = FrontendUtils.buildJsFunctionCall(expression,
        FrontendUtils.buildJsFunctionParametersExpression(IntStream.range(0, arguments.length).toArray()));

    final PendingJavaScriptResult result = getUi().getPage().executeJs(expression, arguments);

    // TODO: Report invalid inspection to JetBrains.
    if(returnType != null) {
      return result.toCompletableFuture(returnType);
    } else {
      return result.toCompletableFuture(Void.class);
    }
  }

  @Override
  default CompletableFuture<?> callJsFunction(final Class<?> returnType, final String functionName, final String rawArguments) {
    return null;
  }

  @Override
  default UI getUi() {
    return null;
  }

  @Override
  default int getId() {
    return 0;
  }

  String getStorageAccessorFunctionName();

}
