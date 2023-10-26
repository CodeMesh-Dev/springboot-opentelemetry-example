package dev.codemesh.opentelemetry.orderservice;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api/order")
public class OrderRestController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping()
  public String createOrder(@RequestBody(required = false) String requestBody, @RequestHeader Map<String, String> headers) {
    logger.info("in order service");
    return "Order created";
  }
}