package com.test.demo.elasticsearch;

import java.net.InetSocketAddress;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


public class ESTemplate {
    
    private TransportClient client;
    
    private String clusterName;
    
    private Map<String,Integer> map;
    
    private String esdbName;
    
    public String getEsdbName() {
        return esdbName;
    }

    public void setEsdbName(String esdbName) {
        this.esdbName = esdbName;
    }

    public ESTemplate(String clusterName, Map<String,Integer> map) {
        this.clusterName = clusterName;
        this.map = map;
        //ES2.1.0版本API
        //Settings settings = Settings.settingsBuilder().put("cluster.name", Constant.ES_CLUSTER_NAME).build();
        //client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(Constant.ES_HOST_NAME, Constant.ES_PORT)));
    }
    
    public TransportClient getClient() {
      //ES5.5.0版本API
        if(client == null) {
            Settings settings = Settings.builder().put("cluster.name", clusterName).build();
            TransportClient pbtClient = new PreBuiltTransportClient(settings);
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                pbtClient = pbtClient.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(entry.getKey(), entry.getValue())));
            }
            client = pbtClient;
        }
        return client;
    }
    
    public void close() {
        if(client != null) {
            client.close();
        }
    }
    
}

