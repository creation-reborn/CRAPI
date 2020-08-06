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

package net.creationreborn.api.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.creationreborn.api.CRAPI;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Toolbox {
    
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();
    
    public static HttpUrl.Builder newHttpUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("api.creationreborn.net")
                .addPathSegments("v2");
    }
    
    public static Request.Builder newRequestBuilder() {
        return new Request.Builder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .addHeader("User-Agent", CRAPI.USER_AGENT);
    }
    
    public static <T> T newInstance(Class<? extends T> type) {
        try {
            return type.newInstance();
        } catch (Throwable ex) {
            return null;
        }
    }
    
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }
    
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Stream.of(elements).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static <E> HashSet<E> newHashSet() {
        return new HashSet<>();
    }
    
    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... elements) {
        return Stream.of(elements).collect(Collectors.toCollection(HashSet::new));
    }
}