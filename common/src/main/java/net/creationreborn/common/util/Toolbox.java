/*
 * Copyright 2019 creationreborn.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.creationreborn.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.creationreborn.api.util.Reference;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Toolbox {
    
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;
    private static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();
    
    public static boolean isBlank(CharSequence charSequence) {
        int stringLength;
        if (charSequence == null || (stringLength = charSequence.length()) == 0) {
            return true;
        }
        
        for (int index = 0; index < stringLength; index++) {
            if (!Character.isWhitespace(charSequence.charAt(index))) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }
    
    public static <T> RestActionImpl<T> newRestAction(Request request, ThrowingFunction<Response, T> function) {
        return new RestActionImpl<>(request, function);
    }
    
    public static HttpUrl.Builder newHttpUrlBuilder() {
        return new HttpUrl.Builder().scheme("https").host("api.creationreborn.net").addPathSegments("v2");
    }
    
    public static Request.Builder newRequestBuilder() {
        return new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK).addHeader("User-Agent", Reference.NAME + "/" + Reference.VERSION);
    }
    
    public static InputStream getInputStream(Response response) {
        if (response.body() != null) {
            return response.body().byteStream();
        }
        
        return null;
    }
    
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            int read;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            
            while ((read = inputStream.read(buffer)) != EOF) {
                outputStream.write(buffer, 0, read);
            }
            
            return outputStream.toByteArray();
        }
    }
    
    public static JsonElement toJsonElement(InputStream inputStream) throws IOException, JsonParseException {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return getGson().fromJson(reader, JsonElement.class);
        }
    }
    
    private static String toString(InputStream inputStream) throws IOException {
        return new String(toByteArray(inputStream), StandardCharsets.UTF_8);
    }
    
    public static <T> Optional<T> parseJson(String json, Class<T> type) {
        try {
            return Optional.of(getGson().fromJson(json, type));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }
    
    public static <T> Optional<T> parseJson(JsonElement jsonElement, Class<T> type) {
        try {
            return Optional.of(getGson().fromJson(jsonElement, type));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }
    
    public static <T> Optional<T> newInstance(Class<? extends T> typeOfT) {
        try {
            return Optional.of(typeOfT.newInstance());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
    
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Stream.of(elements).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }
    
    public static Gson getGson() {
        return GSON;
    }
}