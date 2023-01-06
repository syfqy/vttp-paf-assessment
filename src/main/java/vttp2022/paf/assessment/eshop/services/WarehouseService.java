package vttp2022.paf.assessment.eshop.services;

import jakarta.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.utils.Utils;

@Service
public class WarehouseService {

  private final String WAREHOUSE_URL = "http://paf.chuklee.com/dispatch";
  private final String SYAFIQ = "Muhammad Syafiq Bin Md Yusof";

  // You cannot change the method's signature
  // You may add one or more checked exceptions
  public OrderStatus dispatch(Order order) {
    // TODO: Task 4

    // build url
    String url = UriComponentsBuilder
      .fromUriString(WAREHOUSE_URL)
      .path("/")
      .path(order.getOrderId())
      .toUriString();

    System.out.println("URL >>> " + url);
    // build request payload
    JsonObject requestPayload = Utils.orderToJson(order, SYAFIQ);
    System.out.println("Request payload >>> " + requestPayload);

    RequestEntity<String> req = RequestEntity
      .post(url)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(requestPayload.toString());

    // create orderStatus
    OrderStatus orderStatus = new OrderStatus();
    orderStatus.setOrderId(order.getOrderId());

    RestTemplate template = new RestTemplate();
    try {
      // send request to server, if successful, set deliveryId and status to dispatched
      ResponseEntity<String> resp = template.exchange(req, String.class);
      JsonObject payload = Utils.bodyToJson(resp.getBody());
      orderStatus.setDeliveryId(payload.getString("deliveryId"));
      orderStatus.setStatus("dispatched");
    } catch (ResourceAccessException ex) {
      ex.printStackTrace();
      return orderStatus;
    }

    return orderStatus;
  }
}
