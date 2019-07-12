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

package net.creationreborn.common.endpoint;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.creationreborn.api.data.ServerData;
import net.creationreborn.api.endpoint.Direct;
import net.creationreborn.api.util.RestAction;
import net.creationreborn.common.CRAPIImpl;
import net.creationreborn.common.util.Toolbox;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectEndpoint implements Direct {
    
    @Override
    public RestAction<Collection<ServerData>> getServers() {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("direct/getservers.php")
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("servers"), ServerData[].class))
                    .map(values -> Stream.of(values).collect(Collectors.toCollection(ArrayList::new)))
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
}