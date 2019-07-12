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

package net.creationreborn.common;

import net.creationreborn.api.CRAPI;
import net.creationreborn.api.endpoint.Direct;
import net.creationreborn.api.endpoint.Forum;
import net.creationreborn.api.endpoint.Launcher;
import net.creationreborn.api.endpoint.Ticket;
import net.creationreborn.api.endpoint.User;
import net.creationreborn.common.endpoint.DirectEndpoint;
import net.creationreborn.common.endpoint.ForumEndpoint;
import net.creationreborn.common.endpoint.LauncherEndpoint;
import net.creationreborn.common.endpoint.TicketEndpoint;
import net.creationreborn.common.endpoint.UserEndpoint;
import net.creationreborn.common.util.LoggerImpl;

public class CRAPIImpl extends CRAPI {
    
    private static final Direct DIRECT_ENDPOINT = new DirectEndpoint();
    private static final Forum FORUM_ENDPOINT = new ForumEndpoint();
    private static final Launcher LAUNCHER_ENDPOINT = new LauncherEndpoint();
    private static final Ticket TICKET_ENDPOINT = new TicketEndpoint();
    private static final User USER_ENDPOINT = new UserEndpoint();
    private final String secret;
    
    private CRAPIImpl(String secret) {
        this.secret = secret;
        this.logger = new LoggerImpl();
    }
    
    public static boolean init(String secret) {
        if (getInstance() != null) {
            return false;
        }
        
        new CRAPIImpl(secret);
        return true;
    }
    
    @Override
    public Direct getDirectEndpoint() {
        return DIRECT_ENDPOINT;
    }
    
    @Override
    public Forum getForumEndpoint() {
        return FORUM_ENDPOINT;
    }
    
    @Override
    public Launcher getLauncherEndpoint() {
        return LAUNCHER_ENDPOINT;
    }
    
    @Override
    public Ticket getTicketEndpoint() {
        return TICKET_ENDPOINT;
    }
    
    @Override
    public User getUserEndpoint() {
        return USER_ENDPOINT;
    }
    
    public static CRAPIImpl getInstance() {
        return (CRAPIImpl) CRAPI.getInstance();
    }
    
    public String getSecret() {
        return secret;
    }
}