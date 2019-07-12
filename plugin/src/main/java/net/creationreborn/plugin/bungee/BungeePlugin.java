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

package net.creationreborn.plugin.bungee;

import net.creationreborn.api.CRAPI;
import net.creationreborn.api.util.Logger;
import net.creationreborn.api.util.Reference;
import net.creationreborn.common.CRAPIImpl;
import net.creationreborn.plugin.configuration.Config;
import net.creationreborn.plugin.configuration.Configuration;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Optional;

public class BungeePlugin extends Plugin {
    
    private static BungeePlugin instance;
    private Configuration configuration;
    
    @Override
    public void onEnable() {
        instance = this;
        configuration = new BungeeConfiguration(getDataFolder().toPath());
        getConfiguration().loadConfiguration();
        
        CRAPIImpl.init(getConfig().map(Config::getSecret).orElse(null));
        CRAPI.getInstance().getLogger()
                .add(Logger.Level.INFO, getLogger()::info)
                .add(Logger.Level.WARN, getLogger()::warning)
                .add(Logger.Level.ERROR, getLogger()::severe)
                .add(Logger.Level.DEBUG, message -> {
                    if (getConfig().map(Config::isDebug).orElse(false)) {
                        getLogger().info(message);
                    }
                });
        
        getConfiguration().saveConfiguration();
        CRAPI.getInstance().getLogger().info("{} v{} has started.", Reference.NAME, Reference.VERSION);
    }
    
    @Override
    public void onDisable() {
        CRAPI.getInstance().getLogger().info("{} v{} unloaded", Reference.NAME, Reference.VERSION);
    }
    
    public static BungeePlugin getInstance() {
        return instance;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public Optional<Config> getConfig() {
        if (getConfiguration() != null) {
            return Optional.ofNullable(getConfiguration().getConfig());
        }
        
        return Optional.empty();
    }
}