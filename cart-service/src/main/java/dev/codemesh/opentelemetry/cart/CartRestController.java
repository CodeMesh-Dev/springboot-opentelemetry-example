package dev.codemesh.opentelemetry.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@RequestMapping("api/cart")
public class CartRestController {

  @Autowired
  private OrderServiceClient orderServiceClient;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping("/checkout")
  public String checkout(@RequestBody(required = false) String requestBody, @RequestHeader Map<String, String> headers) {
    logger.info("Checking out cart ");
    String response = orderServiceClient.createOrder(requestBody);
    logger.info("response is " + response);
    return response;
  }
}