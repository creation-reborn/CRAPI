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

package net.creationreborn.api.endpoint;

import net.creationreborn.api.model.DonationModel;
import net.creationreborn.api.model.IdentityModel;
import net.creationreborn.api.model.PostModel;
import net.creationreborn.api.util.RestAction;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface ForumEndpoint {
    
    RestAction<Collection<String>> getAllGroups();
    
    default RestAction<Collection<DonationModel>> getLatestDonations() {
        return getLatestDonations(0L, TimeUnit.SECONDS);
    }
    
    RestAction<Collection<DonationModel>> getLatestDonations(long timestamp, TimeUnit unit);
    
    RestAction<Collection<String>> getGroups(long userId);
    
    default RestAction<Collection<PostModel>> getLatestPosts() {
        return getLatestPosts(0L, TimeUnit.SECONDS);
    }
    
    RestAction<Collection<PostModel>> getLatestPosts(long timestamp, TimeUnit unit);
    
    RestAction<PostModel> getPost(int postId);
    
    RestAction<IdentityModel> getIdentity(long discordId);
    
    RestAction<IdentityModel> getIdentity(UUID minecraftUniqueId);
    
    RestAction<IdentityModel> getIdentity(String minecraftUsername);
    
    RestAction<IdentityModel> getIdentity(int userId);
    
    RestAction<Collection<String>> getUsersOfGroup(String group);
    
    RestAction<Boolean> updateMinecraftUser(UUID uniqueId, String username);
}