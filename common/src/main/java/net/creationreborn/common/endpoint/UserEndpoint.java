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
import net.creationreborn.api.data.PunishmentData;
import net.creationreborn.api.endpoint.User;
import net.creationreborn.api.util.RestAction;
import net.creationreborn.common.CRAPIImpl;
import net.creationreborn.common.util.Toolbox;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserEndpoint implements User {
    
    @Override
    public RestAction<Collection<PunishmentData>> getAddressPunishments(String address, UUID uniqueId) {
        HttpUrl.Builder httpUrlBuilder = Toolbox.newHttpUrlBuilder()
                .addPathSegments("user/getaddresspunishments.php")
                .addQueryParameter("address", address);
        
        if (uniqueId != null) {
            httpUrlBuilder.addQueryParameter("unique_id", uniqueId.toString());
        }
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrlBuilder.build())
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("punishments"), PunishmentData[].class))
                    .map(values -> Stream.of(values).collect(Collectors.toCollection(HashSet::new)))
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<Long> getBalance(UUID uniqueId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("user/getbalance.php")
                .addQueryParameter("unique_id", uniqueId.toString())
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("balance"), Long.class))
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<PunishmentData> getUserPunishments(UUID uniqueId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("user/getuserpunishments.php")
                .addQueryParameter("unique_id", uniqueId.toString())
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, PunishmentData.class)
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<Void> updateBalance(UUID uniqueId, long balance, String details) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("user/updatebalance.php")
                .build();
        
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("unique_id", uniqueId.toString());
        formBodyBuilder.add("balance", String.valueOf(balance));
        formBodyBuilder.add("details", details);
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .post(formBodyBuilder.build()).build();
        
        return Toolbox.newRestAction(request, response -> null);
    }
}