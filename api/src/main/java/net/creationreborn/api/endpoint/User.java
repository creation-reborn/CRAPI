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

import net.creationreborn.api.data.PunishmentData;
import net.creationreborn.api.util.RestAction;

import java.util.Collection;
import java.util.UUID;

public interface User {
    
    default RestAction<Collection<PunishmentData>> getAddressPunishments(String address) {
        return getAddressPunishments(address, null);
    }
    
    RestAction<Collection<PunishmentData>> getAddressPunishments(String address, UUID uniqueId);
    
    RestAction<Long> getBalance(UUID uniqueId);
    
    RestAction<PunishmentData> getUserPunishments(UUID uniqueId);
    
    RestAction<Void> updateBalance(UUID uniqueId, long balance, String details);
}