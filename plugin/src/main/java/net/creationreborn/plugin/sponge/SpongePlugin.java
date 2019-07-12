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

package net.creationreborn.plugin.sponge;

import com.google.inject.Inject;
import net.creationreborn.api.CRAPI;
import net.creationreborn.api.util.Logger;
import net.creationreborn.api.util.Reference;
import net.creationreborn.common.CRAPIImpl;
import net.creationreborn.plugin.configuration.Config;
import net.creationreborn.plugin.configuration.Configuration;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;
import java.util.Optional;

@Plugin(
        id = Reference.ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        description = Reference.DESCRIPTION,
        authors = {Reference.AUTHORS},
        url = Reference.WEBSITE
)
public class SpongePlugin {
    
    private static SpongePlugin instance;
    
    @Inject
    private PluginContainer pluginContainer;
    
    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path path;
    
    private Configuration configuration;
    
    @Listener
    public void onGameConstruction(GameConstructionEvent event) {
        instance = this;
        configuration = new SpongeConfiguration(getPath());
    }
    
    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
        getConfiguration().loadConfiguration();
        
        CRAPIImpl.init(getConfig().map(Config::getSecret).orElse(null));
        CRAPI.getInstance().getLogger()
                .add(Logger.Level.INFO, LoggerFactory.getLogger(Reference.NAME)::info)
                .add(Logger.Level.WARN, LoggerFactory.getLogger(Reference.NAME)::warn)
                .add(Logger.Level.ERROR, LoggerFactory.getLogger(Reference.NAME)::error)
                .add(Logger.Level.DEBUG, message -> {
                    if (getConfig().map(Config::isDebug).orElse(false)) {
                        LoggerFactory.getLogger(Reference.NAME).info(message);
                    }
                });
        
        getConfiguration().saveConfiguration();
        CRAPI.getInstance().getLogger().info("{} v{} has started.", Reference.NAME, Reference.VERSION);
    }
    
    public static SpongePlugin getInstance() {
        return instance;
    }
    
    public PluginContainer getPluginContainer() {
        return pluginContainer;
    }
    
    public Path getPath() {
        return path;
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