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

package net.creationreborn.api;

import net.creationreborn.api.endpoint.Direct;
import net.creationreborn.api.endpoint.Forum;
import net.creationreborn.api.endpoint.Launcher;
import net.creationreborn.api.endpoint.Ticket;
import net.creationreborn.api.endpoint.User;
import org.slf4j.Logger;

public abstract class CRAPI {
    
    public static final String ID = "crapi";
    public static final String NAME = "CreationReborn API";
    public static final String VERSION = "@version@";
    public static final String DESCRIPTION = "Creation Reborn API";
    public static final String AUTHORS = "LX_Gaming";
    public static final String SOURCE = "https://github.com/creation-reborn/CRAPI";
    public static final String WEBSITE = "https://creationreborn.net/";
    
    private static CRAPI instance;
    protected Logger logger;
    
    protected CRAPI() {
        instance = this;
    }
    
    public abstract Direct getDirectEndpoint();
    
    public abstract Forum getForumEndpoint();
    
    public abstract Launcher getLauncherEndpoint();
    
    public abstract Ticket getTicketEndpoint();
    
    public abstract User getUserEndpoint();
    
    public static CRAPI getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
}