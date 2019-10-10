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

package net.creationreborn.api.common.endpoint;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.creationreborn.api.common.CRAPIImpl;
import net.creationreborn.api.common.util.Toolbox;
import net.creationreborn.api.data.DonationData;
import net.creationreborn.api.data.IdentityData;
import net.creationreborn.api.data.PostData;
import net.creationreborn.api.endpoint.Forum;
import net.creationreborn.api.util.RestAction;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForumEndpoint implements Forum {
    
    @Override
    public RestAction<Collection<String>> getAllGroups() {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getallgroups.php")
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            if (response.code() == 204) {
                return null;
            }
            
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("groups"), String[].class))
                    .map(values -> Stream.of(values).collect(Collectors.toCollection(HashSet::new)))
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<Collection<DonationData>> getLatestDonations(long timestamp, TimeUnit unit) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getlatestdonations.php")
                .addQueryParameter("timestamp", String.valueOf(unit.toSeconds(timestamp)))
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("donations"), DonationData[].class))
                    .map(values -> Stream.of(values).collect(Collectors.toCollection(ArrayList::new)))
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<Collection<String>> getGroups(long userId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getgroups.php")
                .addQueryParameter("user_id", String.valueOf(userId))
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            if (response.code() == 204) {
                return null;
            }
            
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("groups"), String[].class))
                    .map(values -> Stream.of(values).collect(Collectors.toCollection(HashSet::new)))
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<Collection<PostData>> getLatestPosts(long timestamp, TimeUnit unit) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getlatestposts.php")
                .addQueryParameter("timestamp", String.valueOf(unit.toSeconds(timestamp)))
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("posts"), PostData[].class))
                    .map(values -> Stream.of(values).collect(Collectors.toCollection(HashSet::new)))
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<PostData> getPost(int postId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getpost.php")
                .addQueryParameter("post_id", String.valueOf(postId))
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, PostData.class)
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<IdentityData> getIdentity(long discordId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getidentity.php")
                .addQueryParameter("discord_id", String.valueOf(discordId))
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, IdentityData.class)
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<IdentityData> getIdentity(UUID minecraftUniqueId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getidentity.php")
                .addQueryParameter("minecraft_unique_id", minecraftUniqueId.toString())
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, IdentityData.class)
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<IdentityData> getIdentity(String minecraftUsername) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getidentity.php")
                .addQueryParameter("minecraft_username", minecraftUsername)
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, IdentityData.class)
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<IdentityData> getIdentity(int userId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getidentity.php")
                .addQueryParameter("user_id", String.valueOf(userId))
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, IdentityData.class)
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<Collection<String>> getUsersOfGroup(String group) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/getusersofgroup.php")
                .addQueryParameter("group_name", group)
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .flatMap(jsonObject -> Toolbox.parseJson(jsonObject.get("users"), String[].class))
                    .map(values -> Stream.of(values).collect(Collectors.toCollection(HashSet::new)))
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<Boolean> updateMinecraftUser(UUID uniqueId, String username) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("forum/updateminecraftuser.php")
                .build();
        
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("unique_id", uniqueId.toString());
        formBodyBuilder.add("username", username);
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .post(formBodyBuilder.build()).build();
        
        return Toolbox.newRestAction(request, response -> response.code() == 200);
    }
}