package com.dango.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Elasticsearch配置类
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.hostList}")
    private String hostList;

    /**
     * 创建Elasticsearch的RestHighLevelClient客户端Bean
     *
     * @return RestHighLevelClient客户端
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        // 解析hostList配置信息
        String[] hosts = hostList.split(",");

        // 创建HttpHost数组，用于存放ES主机和端口的配置信息
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for(int i=0; i<hosts.length; i++){
            String host = hosts[i];
            String[] hostInfo = host.split(":");
            httpHosts[i] = new HttpHost(hostInfo[0], Integer.parseInt(hostInfo[1]), "http");
        }

        // 创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}