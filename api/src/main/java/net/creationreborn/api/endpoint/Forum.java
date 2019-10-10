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

package net.creationreborn.api.endpoint;

import net.creationreborn.api.data.DonationData;
import net.creationreborn.api.data.IdentityData;
import net.creationreborn.api.data.PostData;
import net.creationreborn.api.util.RestAction;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface Forum {
    
    RestAction<Collection<String>> getAllGroups();
    
    default RestAction<Collection<DonationData>> getLatestDonations() {
        return getLatestDonations(0L, TimeUnit.SECONDS);
    }
    
    RestAction<Collection<DonationData>> getLatestDonations(long timestamp, TimeUnit unit);
    
    RestAction<Collection<String>> getGroups(long userId);
    
    default RestAction<Collection<PostData>> getLatestPosts() {
        return getLatestPosts(0L, TimeUnit.SECONDS);
    }
    
    RestAction<Collection<PostData>> getLatestPosts(long timestamp, TimeUnit unit);
    
    RestAction<PostData> getPost(int postId);
    
    RestAction<IdentityData> getIdentity(long discordId);
    
    RestAction<IdentityData> getIdentity(UUID minecraftUniqueId);
    
    RestAction<IdentityData> getIdentity(String minecraftUsername);
    
    RestAction<IdentityData> getIdentity(int userId);
    
    RestAction<Collection<String>> getUsersOfGroup(String group);
    
    RestAction<Boolean> updateMinecraftUser(UUID uniqueId, String username);
}