package ehss.elasticsearch_to_rssatom_feed;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String elasticsearchHost;


    @Value("${elasticsearch.port}")
    private Integer elasticsearchPort;
    @Bean
    public RestHighLevelClient restHighLevelClient() {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticsearchHost,elasticsearchPort)).build());

        return client;

    }


}
