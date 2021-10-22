package com.morziz.network.config;

import com.apollographql.apollo.ApolloClient;
import com.morziz.network.graphql.ApiClient;
import com.morziz.network.graphql.GraphQLClient;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static ConfigManager configManager;
    private Map<String, NetworkConfig> configMap;

    private ConfigManager() {
        if (configMap == null) {
            configMap = new HashMap<>();
        }
    }

    public static ConfigManager getInstance() {
        if (configManager == null) {
            configManager = new ConfigManager();
        }
        return configManager;
    }

    public void addConfig(NetworkConfig networkConfig) {
        if (networkConfig.getBaseUrl() == null) {
            throw new IllegalArgumentException("Network config must have base Url");
        }
        configMap.put(networkConfig.getIdentity(), networkConfig);
        switch (networkConfig.getClientType()) {
            case GRAPHQL:
                GraphQLClient.getInstance().createClient(networkConfig);
                break;
            case RETROFIT:
                // Functionality yet to add
        }
    }

    public ApolloClient getApolloClient(String identity) {
        return GraphQLClient.getInstance().getClient(identity);
    }

}
