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

  String STORE_PROPERTY_NAME = "store";

  String GET_FUNCTION_NAME = "get";

  String ADD_FUNCTION_NAME = "add";

  String METHODS_PROPERTY_NAME = "methods";

  // Static methods
  //--------------------------------------------------

  static String buildJsStoreGetExpression(final SupportedLeafletPojo supportedLeafletPojo) {
    return FrontendUtils.buildJsFunctionCall(
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            "window", FrontendUtils.LEAFLET_ADDON_PROPERTY_NAME, SUPPORT_PROPERTY_NAME, supportedLeafletPojo.getSupportPropertyName(), STORE_PROPERTY_NAME,
            GET_FUNCTION_NAME),
        supportedLeafletPojo.getId()
    );
  }

  static String buildJsPropertyAccessorExpression(final SupportedLeafletPojo supportedLeafletPojo, final String propertyName) {
    return FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
        buildJsStoreGetExpression(supportedLeafletPojo), propertyName
    );
  }

  static String buildJsMethodCallExpression(final SupportedLeafletPojo supportedLeafletPojo, final String methodName, final Serializable... arguments) {
    return FrontendUtils.buildJsFunctionCall(
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            buildJsStoreGetExpression(supportedLeafletPojo), methodName),
        FrontendUtils.buildJsFunctionParametersExpression(IntStream.range(0, arguments.length).toArray())
    );
  }

  static String buildJsMethodCall(final SupportedLeafletPojo supportedLeafletPojo, final String methodName, final String rawArguments) {
    return FrontendUtils.buildJsFunctionCall(
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            buildJsStoreGetExpression(supportedLeafletPojo), methodName),
        rawArguments
    );
  }

  static String buildJsSupportMethodCall(final SupportedLeafletPojo supportedLeafletPojo, final String methodName, final Serializable... arguments) {
    return FrontendUtils.buildJsFunctionCall(
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            "window", FrontendUtils.LEAFLET_ADDON_PROPERTY_NAME, SUPPORT_PROPERTY_NAME, supportedLeafletPojo.getSupportPropertyName(),
            METHODS_PROPERTY_NAME, methodName),
        supportedLeafletPojo.getId() +
            ((arguments != null && arguments.length > 0)
                ? "," + FrontendUtils.buildJsFunctionParametersExpression(IntStream.range(0, arguments.length).toArray())
                : "")
    );
  }

  static String buildJsSupportMethodCall(final SupportedLeafletPojo supportedLeafletPojo, final String methodName, final String rawArguments) {
    return FrontendUtils.buildJsFunctionCall(
        FrontendUtils.buildJsPropertyAccessor(FrontendUtils.JsPropertyAccessorNotation.DOT,
            "window", FrontendUtils.LEAFLET_ADDON_PROPERTY_NAME, SUPPORT_PROPERTY_NAME, supportedLeafletPojo.getSupportPropertyName(),
            METHODS_PROPERTY_NAME, methodName),
        supportedLeafletPojo.getId() + ((rawArguments != null && !rawArguments.isEmpty()) ? "," + rawArguments : "")
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
  default CompletableFuture<?> callJsMethod(final Class<?> returnType, final String methodName, final Serializable... arguments) {
    final PendingJavaScriptResult result = getUi().getPage().executeJs(buildJsMethodCallExpression(this, methodName, arguments), arguments);

    if(returnType != null) {
      return result.toCompletableFuture(returnType);
    } else {
      return result.toCompletableFuture();
    }
  }

  @Override
  default CompletableFuture<?> callJsMethod(final Class<?> returnType, final String methodName, final String rawArguments) {
    final PendingJavaScriptResult result = getUi().getPage().executeJs(buildJsMethodCall(this, methodName, rawArguments));

    // TODO: Report invalid inspection to JetBrains.
    if(returnType != null) {
      return result.toCompletableFuture(returnType);
    } else {
      return result.toCompletableFuture(Void.class);
    }
  }

  default CompletableFuture<?> callJsSupportMethod(final Class<?> returnType, final String methodName, final Serializable... arguments) {
    final PendingJavaScriptResult result = getUi().getPage().executeJs(buildJsSupportMethodCall(this, methodName, arguments), arguments);

    if(returnType != null) {
      return result.toCompletableFuture(returnType);
    } else {
      return result.toCompletableFuture();
    }
  }

  default CompletableFuture<?> callJsSupportMethod(final Class<?> returnType, final String methodName, final String rawArguments) {
    final PendingJavaScriptResult result = getUi().getPage().executeJs(buildJsSupportMethodCall(this, methodName, rawArguments));

    if(returnType != null) {
      return result.toCompletableFuture(returnType);
    } else {
      return result.toCompletableFuture();
    }
  }

  String getSupportPropertyName();

}
