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
import net.creationreborn.api.endpoint.Ticket;
import net.creationreborn.api.util.RestAction;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class TicketEndpoint implements Ticket {
    
    @Override
    public RestAction<JsonObject> getOpenTickets() {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("ticket/getopentickets.php")
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
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
    
    @Override
    public RestAction<JsonObject> getTicket(int ticketId) {
        HttpUrl httpUrl = Toolbox.newHttpUrlBuilder()
                .addPathSegments("ticket/getticket.php")
                .addQueryParameter("ticket_id", String.valueOf(ticketId))
                .build();
        
        Request request = Toolbox.newRequestBuilder()
                .url(httpUrl)
                .addHeader("Authorization", CRAPIImpl.getInstance().getSecret())
                .get().build();
        
        return Toolbox.newRestAction(request, response -> {
            JsonElement jsonElement = Toolbox.toJsonElement(Toolbox.getInputStream(response));
            return Toolbox.parseJson(jsonElement, JsonObject.class)
                    .orElseThrow(() -> new JsonParseException("Failed to parse response"));
        });
    }
}