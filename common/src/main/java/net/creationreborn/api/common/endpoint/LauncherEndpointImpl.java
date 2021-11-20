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
import net.creationreborn.api.common.util.RestActionImpl;
import net.creationreborn.api.common.util.Toolbox;
import net.creationreborn.api.endpoint.LauncherEndpoint;
import net.creationreborn.api.util.RestAction;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.Reader;

public class LauncherEndpointImpl implements LauncherEndpoint {
    
    @Override
    public RestAction<JsonObject> getPackages() {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("launcher/getpackages.php")
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .method("GET", null)
                .build();
        
        return new RestActionImpl<>(request, response -> {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IllegalStateException("ResponseBody is unavailable");
            }
            
            try (Reader reader = responseBody.charStream()) {
                return Toolbox.GSON.fromJson(reader, JsonObject.class);
            }
        });
    }
}