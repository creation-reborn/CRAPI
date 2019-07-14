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

import net.creationreborn.api.CRAPI;
import net.creationreborn.api.plugin.configuration.Config;
import net.creationreborn.api.plugin.configuration.Configuration;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Path;

public class SpongeConfiguration implements Configuration {
    
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private ObjectMapper<Config>.BoundInstance objectMapper;
    private Config config;
    
    public SpongeConfiguration(Path path) {
        try {
            this.configurationLoader = HoconConfigurationLoader.builder().setPath(path).build();
            this.objectMapper = ObjectMapper.forClass(Config.class).bindToNew();
        } catch (Exception ex) {
            CRAPI.getInstance().getLogger().error("Encountered an error initializing {}", getClass().getSimpleName(), ex);
        }
    }
    
    @Override
    public boolean loadConfiguration() {
        try {
            config = getObjectMapper().populate(getConfigurationLoader().load());
            CRAPI.getInstance().getLogger().info("Successfully loaded configuration file.");
            return true;
        } catch (IOException | ObjectMappingException | RuntimeException ex) {
            CRAPI.getInstance().getLogger().error("Encountered an error processing {}::loadConfiguration", getClass().getSimpleName(), ex);
            return false;
        }
    }
    
    @Override
    public boolean saveConfiguration() {
        try {
            ConfigurationNode configurationNode = getConfigurationLoader().createEmptyNode();
            getObjectMapper().serialize(configurationNode);
            getConfigurationLoader().save(configurationNode);
            CRAPI.getInstance().getLogger().info("Successfully saved configuration file.");
            return true;
        } catch (IOException | ObjectMappingException | RuntimeException ex) {
            CRAPI.getInstance().getLogger().error("Encountered an error processing {}::saveConfiguration", getClass().getSimpleName(), ex);
            return false;
        }
    }
    
    private ConfigurationLoader<CommentedConfigurationNode> getConfigurationLoader() {
        return configurationLoader;
    }
    
    private ObjectMapper<Config>.BoundInstance getObjectMapper() {
        return objectMapper;
    }
    
    @Override
    public Config getConfig() {
        return config;
    }
}