import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = getHTTPClient();
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            ObjectMapper mapper = new ObjectMapper();
            List<Fact> facts = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
            });
            Stream<Fact> stream = facts.stream();
            stream.filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0)
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static CloseableHttpClient getHTTPClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
    }
}
