package dev.codemesh.opentelemetry.cart;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceClient {
    @Autowired
    private RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${order.url}")
    private String orderUrl;

    private String ORDER_ENDPOINT;

    @PostConstruct
    public void initialize() {
        logger.info("Initializing with endpoint " + orderUrl);
        ORDER_ENDPOINT = orderUrl + "/api/order";
    }

    public String createOrder(String requestBody) {
        logger.info("POST API call to ::: " + ORDER_ENDPOINT);
        String response = restTemplate.postForObject(ORDER_ENDPOINT, createHttpReq(requestBody), String.class);
        return response;
    }

    private HttpEntity createHttpReq(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity<>(requestBody, headers);
        return httpEntity;
    }
}