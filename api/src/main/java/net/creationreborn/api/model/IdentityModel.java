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

import java.util.UUID;

public class IdentityModel {
    
    @SerializedName("discord_id")
    private long discordId;
    
    @SerializedName("minecraft_unique_id")
    private UUID minecraftUniqueId;
    
    @SerializedName("minecraft_username")
    private String minecraftUsername;
    
    @SerializedName("user_id")
    private int userId;
    
    public long getDiscordId() {
        return discordId;
    }
    
    public UUID getMinecraftUniqueId() {
        return minecraftUniqueId;
    }
    
    public String getMinecraftUsername() {
        return minecraftUsername;
    }
    
    public int getUserId() {
        return userId;
    }
}