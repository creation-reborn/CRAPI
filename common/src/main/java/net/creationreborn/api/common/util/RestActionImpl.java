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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.creationreborn.api.common.CRAPIImpl;
import net.creationreborn.api.exception.APIException;
import net.creationreborn.api.util.RestAction;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.function.Consumer;

public class RestActionImpl<T> implements RestAction<T> {
    
    private final Request request;
    private final ThrowingFunction<Response, T> function;
    
    public RestActionImpl(Request request, ThrowingFunction<Response, T> function) {
        this.request = request;
        this.function = function;
    }
    
    @Override
    public void async(Consumer<T> success, Consumer<Throwable> failure) {
        CRAPIImpl.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException ex) {
                failure.accept(ex);
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    checkResponse(response);
                    success.accept(function.applyThrows(response));
                    response.close();
                } catch (Exception ex) {
                    response.close();
                    failure.accept(ex);
                }
            }
        });
    }
    
    @Override
    public T sync() throws Exception {
        try (Response response = CRAPIImpl.getInstance().getOkHttpClient().newCall(request).execute()) {
            checkResponse(response);
            return function.applyThrows(response);
        }
    }
    
    private void checkResponse(Response response) throws APIException {
        if (response.isSuccessful()) {
            return;
        }
        
        try {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            JsonObject jsonObject = Toolbox.parseJson(jsonElement, JsonObject.class).orElseThrow(() -> new APIException("Failed to parse API response"));
            throw new APIException(Toolbox.parseJson(jsonObject.get("message"), String.class).orElse("No message provided"));
        } catch (IOException | JsonParseException ex) {
            throw new APIException("Failed to read API response", ex);
        }
    }
}