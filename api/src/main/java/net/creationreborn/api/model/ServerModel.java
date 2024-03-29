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

package net.creationreborn.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class ServerModel {
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("direct_connects")
    private Collection<String> directConnects;
    
    @SerializedName("lobby")
    private boolean lobby;
    
    @SerializedName("restricted")
    private boolean restricted;
    
    public String getName() {
        return name;
    }
    
    public Collection<String> getDirectConnects() {
        return directConnects;
    }
    
    public boolean isLobby() {
        return lobby;
    }
    
    public boolean isRestricted() {
        return restricted;
    }
}