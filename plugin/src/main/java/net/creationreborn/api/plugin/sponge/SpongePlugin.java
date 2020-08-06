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

package net.creationreborn.api.plugin.sponge;

import com.google.inject.Inject;
import net.creationreborn.api.CRAPI;
import net.creationreborn.api.common.CRAPIImpl;
import net.creationreborn.api.plugin.configuration.Config;
import net.creationreborn.api.plugin.configuration.Configuration;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;
import java.util.Optional;

@Plugin(
        id = CRAPI.ID,
        name = CRAPI.NAME,
        version = CRAPI.VERSION,
        description = CRAPI.DESCRIPTION,
        authors = {CRAPI.AUTHORS},
        url = CRAPI.WEBSITE
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
        this.configuration = new Configuration(getPath());
    }
    
    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
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
        return Optional.ofNullable(getConfiguration().getConfig());
    }
}