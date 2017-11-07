package com.test.demo.elasticsearch;

import java.net.InetSocketAddress;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


public class TransportClientFactoryBean implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

    private static final Logger logger = Logger.getLogger(TransportClientFactoryBean.class);
    private String clusterNodes = "127.0.0.1:9300";
    private String clusterName = "elasticsearch";
    private Boolean clientTransportSniff = true;
    private Boolean clientIgnoreClusterName = Boolean.FALSE;
    private String clientPingTimeout = "5s";
    private String clientNodesSamplerInterval = "5s";
    private TransportClient client;
    private Properties properties;
    static final String COLON = ":";
    static final String COMMA = ",";

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticSearch client");
            if (client != null) {
                 client.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
    
    protected void buildClient() throws Exception {
        client = new PreBuiltTransportClient(settings());
        Assert.hasText(clusterNodes, "[Assertion failed] clusterNodessettings missing.");
        for (String clusterNode : split(clusterNodes, COMMA)) {
            String hostName = clusterNode.split(COLON)[0];
            String port = clusterNode.split(COLON)[1];
            Assert.hasText(hostName, "[Assertion failed] missing host name in'clusterNodes'");
            Assert.hasText(port, "[Assertion failed] missing port in'clusterNodes'");
            logger.info("adding transport node : " + clusterNode);
            //client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName),Integer.valueOf(port)));
            client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(hostName, Integer.valueOf(port))));
         }
        //client.connectedNodes();
     }

    private String[] split(String src, String splitStr) {
        if(StringUtils.isNotEmpty(src)) {
            return src.split(splitStr);
        }
        return null;
    }

    private Settings settings() {
        /*if (properties != null) {
            return Settings.builder().put(properties).build();
        }*/
        if (clusterName != null) {
            return Settings.builder().put("cluster.name", clusterName).build();
        }
        return Settings.EMPTY;
 /*       return Settings.builder()
                 .put("cluster.name",clusterName)
                .put("client.transport.sniff", clientTransportSniff)
                .put("client.transport.ignore_cluster_name",clientIgnoreClusterName)
                .put("client.transport.ping_timeout", clientPingTimeout)
                 .put("client.transport.nodes_sampler_interval",clientNodesSamplerInterval)
                 .build();*/
     }
    
    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
     }
  
     public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
     }

    public void setClientTransportSniff(Boolean clientTransportSniff) {
        this.clientTransportSniff = clientTransportSniff;
     }
  
    public String getClientNodesSamplerInterval() {
        return clientNodesSamplerInterval;
     }
  
    public void setClientNodesSamplerInterval(String clientNodesSamplerInterval) {
        this.clientNodesSamplerInterval = clientNodesSamplerInterval;
     }
  
    public String getClientPingTimeout() {
        return clientPingTimeout;
     }
  
    public void setClientPingTimeout(String clientPingTimeout) {
        this.clientPingTimeout = clientPingTimeout;
     }
  
    public Boolean getClientIgnoreClusterName() {
        return clientIgnoreClusterName;
     }
  
    public void setClientIgnoreClusterName(Boolean clientIgnoreClusterName){
        this.clientIgnoreClusterName = clientIgnoreClusterName;
     }
  
    public void setProperties(Properties properties) {
         this.properties = properties;
     }

}
