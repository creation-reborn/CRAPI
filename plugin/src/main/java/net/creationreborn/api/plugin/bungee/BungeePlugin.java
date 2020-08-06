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

package net.creationreborn.api.plugin.bungee;

import net.creationreborn.api.CRAPI;
import net.creationreborn.api.common.CRAPIImpl;
import net.creationreborn.api.plugin.configuration.Config;
import net.creationreborn.api.plugin.configuration.Configuration;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Optional;

public class BungeePlugin extends Plugin {
    
    private static BungeePlugin instance;
    private Configuration configuration;
    
    @Override
    public void onEnable() {
        instance = this;
        
        if (getProxy().getName().equalsIgnoreCase("BungeeCord")) {
            getLogger().severe("\n\n"
                    + "  BungeeCord is not supported - https://github.com/SpigotMC/BungeeCord/pull/1877\n"
                    + "\n"
                    + "  Use Waterfall - https://github.com/PaperMC/Waterfall\n"
            );
            return;
        }
        
        this.configuration = new Configuration(getDataFolder().toPath());
        
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
    
    @Override
    public void onDisable() {
        if (!CRAPI.isAvailable()) {
            return;
        }
        
        CRAPIImpl.getInstance().getLogger().info("{} v{} unloaded", CRAPI.NAME, CRAPI.VERSION);
    }
    
    public static BungeePlugin getInstance() {
        return instance;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public Optional<Config> getConfig() {
        return Optional.ofNullable(getConfiguration().getConfig());
    }
}