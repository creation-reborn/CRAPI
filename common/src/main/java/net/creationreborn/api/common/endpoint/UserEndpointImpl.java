/*
 * Copyright 2021 creationreborn.net
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

package net.creationreborn.api.common.endpoint;

import com.google.gson.JsonObject;
import net.creationreborn.api.common.CRAPIImpl;
import net.creationreborn.api.common.util.RestActionImpl;
import net.creationreborn.api.common.util.Toolbox;
import net.creationreborn.api.endpoint.UserEndpoint;
import net.creationreborn.api.model.PunishmentModel;
import net.creationreborn.api.util.RestAction;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.Reader;
import java.util.Collection;
import java.util.UUID;

public class UserEndpointImpl implements UserEndpoint {
    
    @Override
    public RestAction<Collection<PunishmentModel>> getAddressPunishments(String address, UUID uniqueId) {
        HttpUrl.Builder httpUrlBuilder = Toolbox.newHttpUrlBuilder()
                .addPathSegments("user/getaddresspunishments.php")
                .addQueryParameter("address", address);
        
        if (uniqueId != null) {
            httpUrlBuilder.addQueryParameter("unique_id", uniqueId.toString());
        }
        
        Request request = Toolbox.newRequestBuilder()
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .url(httpUrlBuilder.build())
                .method("GET", null)
                .build();
        
        return new RestActionImpl<>(request, response -> {
            if (response.code() == 204) {
                return null;
            }
            
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IllegalStateException("ResponseBody is unavailable");
            }
            
            try (Reader reader = responseBody.charStream()) {
                JsonObject jsonObject = Toolbox.GSON.fromJson(reader, JsonObject.class);
                return Toolbox.newArrayList(Toolbox.GSON.fromJson(jsonObject.get("punishments"), PunishmentModel[].class));
            }
        });
    }
    
    @Override
    public RestAction<Long> getBalance(UUID uniqueId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("user/getbalance.php")
                .addQueryParameter("unique_id", uniqueId.toString())
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .url(httpUrl)
                .method("GET", null)
                .build();
        
        return new RestActionImpl<>(request, response -> {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IllegalStateException("ResponseBody is unavailable");
            }
            
            try (Reader reader = responseBody.charStream()) {
                JsonObject jsonObject = Toolbox.GSON.fromJson(reader, JsonObject.class);
                return Toolbox.GSON.fromJson(jsonObject.get("balance"), Long.class);
            }
        });
    }
    
    @Override
    public RestAction<PunishmentModel> getUserPunishments(UUID uniqueId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("user/getuserpunishments.php")
                .addQueryParameter("unique_id", uniqueId.toString())
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .url(httpUrl)
                .method("GET", null)
                .build();
        
        return new RestActionImpl<>(request, response -> {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IllegalStateException("ResponseBody is unavailable");
            }
            
            try (Reader reader = responseBody.charStream()) {
                return Toolbox.GSON.fromJson(reader, PunishmentModel.class);
            }
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
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .url(httpUrl)
                .method("POST", formBodyBuilder.build())
                .build();
        
        return new RestActionImpl<>(request, response -> null);
    }
}