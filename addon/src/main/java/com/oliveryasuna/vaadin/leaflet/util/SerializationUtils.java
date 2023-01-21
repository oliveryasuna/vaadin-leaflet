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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.*;
import com.oliveryasuna.commons.language.exception.UnsupportedInstantiationException;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import org.unbrokendome.jackson.beanvalidation.BeanValidationModule;

import javax.validation.Validation;

/**
 * Serialization utilities.
 *
 * @author Oliver Yasuna
 */
public final class SerializationUtils {

  // Static fields
  //--------------------------------------------------

  public static final ObjectMapper JSON_MAPPER = new JsonMapper()
      .registerModule(new BeanValidationModule(Validation.byDefaultProvider().configure().buildValidatorFactory()));

  // Static methods
  //--------------------------------------------------

  public static JsonValue toElementalValue(final Object value) {
    if(value instanceof JsonValue) {
      return (JsonValue)value;
    } else if(value instanceof JsonNode) {
      return jacksonNodeToElementalValue((JsonNode)value);
    } else {
      return javaValueToElementalValue(value);
    }
  }

  private static JsonValue javaValueToElementalValue(final Object value) {
    if(value == null) {
      return Json.createNull();
    } else if(value instanceof Boolean) {
      return Json.create((Boolean)value);
    } else if(value instanceof String) {
      return Json.create((String)value);
    } else if(value instanceof Double) {
      return Json.create((Double)value);
    } else if(value.getClass().isArray()) {
      return javaArrayToElementalArray((Object[])value);
    } else {
      return jacksonNodeToElementalValue(JSON_MAPPER.valueToTree(value));
    }
  }

  private static JsonArray javaArrayToElementalArray(final Object[] array) {
    final JsonArray jsonArray = Json.createArray();

    for(int i = 0; i < array.length; i++) {
      jsonArray.set(i, toElementalValue(array[i]));
    }

    return jsonArray;
  }

  private static JsonValue jacksonNodeToElementalValue(final JsonNode value) {
    if(value instanceof NullNode) {
      return Json.createNull();
    } else if(value instanceof BooleanNode) {
      return Json.create(value.asBoolean());
    } else if(value instanceof NumericNode) {
      return Json.create(value.asDouble());
    } else if(value instanceof TextNode) {
      return Json.create(value.asText());
    } else if(value instanceof ObjectNode) {
      return jacksonObjectToElementalObject((ObjectNode)value);
    } else if(value instanceof ArrayNode) {
      return jacksonArrayToElementalArray((ArrayNode)value);
    }

    throw new IllegalArgumentException("Unsupported type: " + value.getClass());
  }

  private static JsonObject jacksonObjectToElementalObject(final ObjectNode object) {
    final JsonObject jsonObject = Json.createObject();

    object.fields().forEachRemaining(entry -> {
      final String key = entry.getKey();
      final JsonValue value = jacksonNodeToElementalValue(entry.getValue());

      jsonObject.put(key, value);
    });

    return jsonObject;
  }

  private static JsonArray jacksonArrayToElementalArray(final ArrayNode array) {
    final JsonArray jsonArray = Json.createArray();

    for(int i = 0; i < array.size(); i++) {
      jsonArray.set(i, jacksonNodeToElementalValue(array.get(i)));
    }

    return jsonArray;
  }

  // Constructors
  //--------------------------------------------------

  private SerializationUtils() {
    super();

    throw new UnsupportedInstantiationException();
  }

}
