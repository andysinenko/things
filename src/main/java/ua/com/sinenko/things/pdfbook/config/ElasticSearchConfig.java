package ua.com.sinenko.things.pdfbook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.time.Duration;


@Configuration
@Profile("stage")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${ELASTICSEARCH_KEY}")
    private String keyFile;

    @Value("${ELASTICSEARCH_KEY_PASSWORD}")
    private String keyFilePassword;

    @Value("${ELASTICSEARCH_HOST}")
    private String host;

    @Value("${ELASTICSEARCH_PORT}")
    private String port;

    @Value("${ELASTICSEARCH_USERNAME}")
    private String username;

    @Value("${ELASTICSEARCH_PASSWORD}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {

        try {
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream(keyFile), keyFilePassword.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            return ClientConfiguration.builder()
                    .connectedTo(host + ":" + port)
                    .usingSsl(sslContext)
                    .withBasicAuth(username, password)
                    .withConnectTimeout(Duration.ofSeconds(5))
                    .withSocketTimeout(Duration.ofSeconds(3))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure SSL for Elasticsearch", e);
        }
    }
}
