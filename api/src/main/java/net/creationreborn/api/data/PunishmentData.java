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

package net.creationreborn.api.data;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class PunishmentData {
    
    @SerializedName("unique_id")
    private UUID uniqueId;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("last_login")
    private long lastLogin;
    
    @SerializedName("ban_reason")
    private String banReason;
    
    @SerializedName("mute_reason")
    private String muteReason;
    
    public UUID getUniqueId() {
        return uniqueId;
    }
    
    public String getName() {
        return name;
    }
    
    public long getLastLogin() {
        return lastLogin;
    }
    
    public String getBanReason() {
        return banReason;
    }
    
    public String getMuteReason() {
        return muteReason;
    }
}