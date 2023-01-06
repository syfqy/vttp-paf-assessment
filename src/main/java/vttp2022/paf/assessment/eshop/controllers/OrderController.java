package vttp2022.paf.assessment.eshop.controllers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vttp2022.paf.assessment.eshop.controllers.exceptions.OrderException;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.LineItemRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderStatusRepository;
import vttp2022.paf.assessment.eshop.services.OrderService;
import vttp2022.paf.assessment.eshop.services.WarehouseService;
import vttp2022.paf.assessment.utils.Utils;

@RestController
@RequestMapping(
  path = "/api/order",
  produces = MediaType.APPLICATION_JSON_VALUE
)
public class OrderController {

  //TODO: Task 3

  @Autowired
  private CustomerRepository customerRepo;

  @Autowired
  private OrderStatusRepository orderStatusRepo;

  @Autowired
  private OrderService orderSvc;

  @Autowired
  private WarehouseService warehouseSvc;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postOrder(@RequestBody String orderRequest) {
    System.out.println("order >>>: " + orderRequest);
    JsonReader jsonReader = Json.createReader(new StringReader(orderRequest));
    JsonObject orderJson = jsonReader.readObject();

    //check if customer exists
    String customerName = orderJson.getString("name");
    Optional<Customer> opt = customerRepo.findCustomerByName(customerName);

    // if customer does not exist return error
    if (opt.isEmpty()) {
      JsonObject errJson = Json
        .createObjectBuilder()
        .add("error", "Customer %s not found".formatted(customerName))
        .build();
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(errJson.toString());
    }

    Customer customer = opt.get();

    // create order

    /*
    eg. request body
    {"name":"fred",
    "lineItems":[
    	{"item":"item1","quantity":20}
    	]
    }
    */
    Order order = new Order();
    order.setOrderId(UUID.randomUUID().toString().substring(0, 8));
    order.setName(customer.getName());
    order.setAddress(customer.getAddress());
    order.setEmail(customer.getEmail());

    // create and add line items
    JsonArray lineItemsJsonArr = orderJson.getJsonArray("lineItems");

    List<LineItem> lineItems = new LinkedList<>();

    List<JsonObject> lineItemsJsonObjArr = lineItemsJsonArr
      .stream()
      .map(li -> (JsonObject) li)
      .toList();

    for (JsonObject lineItemJson : lineItemsJsonObjArr) {
      LineItem lineItem = new LineItem();
      lineItem.setItem(lineItemJson.getString("item"));
      lineItem.setQuantity(lineItemJson.getInt("quantity"));
      lineItems.add(lineItem);
    }
    order.setLineItems(lineItems);

    // save order and line items to db
    try {
      orderSvc.createOrder(order);
    } catch (OrderException ex) {
      JsonObject errJson = Json
        .createObjectBuilder()
        .add("error", "Cannot create order %s".formatted(order.getOrderId()))
        .build();
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(errJson.toString());
    }

    // dispatch order
    OrderStatus orderStatus = warehouseSvc.dispatch(order);

    // save order status
    orderStatusRepo.createOrderStatus(orderStatus);

    // return response
    JsonObject resp = Utils.orderStatusToJson(orderStatus);
    System.out.println("order status >>> " + resp);
    return ResponseEntity.ok(resp.toString());
  }

  @GetMapping("/{name}/status")
  public ResponseEntity<String> getOrderCounts(@PathVariable String name) {
    Map<String, Integer> orderCountsMap = orderStatusRepo.getOrderCounts(name);

    JsonObject resp = Json
      .createObjectBuilder()
      .add("name", name)
      .add("dispatched", orderCountsMap.getOrDefault("dispatched", 0))
      .add("pending", orderCountsMap.getOrDefault("pending", 0))
      .build();
    return ResponseEntity.ok(resp.toString());
  }
}
