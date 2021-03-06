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

package net.creationreborn.api.plugin.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.creationreborn.api.CRAPI;
import net.creationreborn.api.common.CRAPIImpl;
import net.creationreborn.api.plugin.configuration.Config;
import net.creationreborn.api.plugin.configuration.Configuration;

import java.nio.file.Path;
import java.util.Optional;

@Plugin(
        id = CRAPI.ID,
        name = CRAPI.NAME,
        version = CRAPI.VERSION,
        description = CRAPI.DESCRIPTION,
        url = CRAPI.WEBSITE,
        authors = {CRAPI.AUTHORS}
)
public class VelocityPlugin {
    
    private static VelocityPlugin instance;
    
    @Inject
    private ProxyServer proxy;
    
    @Inject
    @DataDirectory
    private Path path;
    
    private Configuration configuration;
    
    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        instance = this;
        this.configuration = new Configuration(getPath());
        
        CRAPIImpl.init();
        
        if (!getConfiguration().loadConfiguration()) {
            CRAPIImpl.getInstance().getLogger().error("Failed to load");
            return;
        }
        
        if (CRAPIImpl.getInstance().getSecret() == null) {
            getConfig().map(Config::getSecret).ifPresent(CRAPIImpl.getInstance()::setSecret);
        }
        
        getConfiguration().saveConfiguration();
        CRAPIImpl.getInstance().getLogger().info("{} v{} has started.", CRAPI.NAME, CRAPI.VERSION);
    }
    
    public static VelocityPlugin getInstance() {
        return instance;
    }
    
    public ProxyServer getProxy() {
        return proxy;
    }
    
    public Path getPath() {
        return path;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public Optional<Config> getConfig() {
        return Optional.ofNullable(getConfiguration().getConfig());
    }
}