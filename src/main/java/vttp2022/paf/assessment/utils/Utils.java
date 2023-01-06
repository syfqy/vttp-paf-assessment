package vttp2022.paf.assessment.utils;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import java.io.StringReader;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;

public class Utils {

  public static JsonArray lineItemstoJsonArr(List<LineItem> lineItems) {
    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    for (LineItem lineItem : lineItems) {
      arrBuilder.add(lineItemtoJson(lineItem));
    }
    return arrBuilder.build();
  }

  public static JsonObject lineItemtoJson(LineItem lineItem) {
    return Json
      .createObjectBuilder()
      .add("item", lineItem.getItem())
      .add("quantity", lineItem.getQuantity())
      .build();
  }

  public static JsonObject orderToJson(Order order, String createdBy) {
    JsonArray lineItemsArr = lineItemstoJsonArr(order.getLineItems());
    JsonObjectBuilder builder = Json
      .createObjectBuilder()
      .add("orderId", order.getOrderId())
      .add("name", order.getName())
      .add("address", order.getAddress())
      .add("email", order.getEmail())
      .add("lineItems", lineItemsArr);

    if (createdBy != null) {
      builder.add("createdBy", createdBy);
    }
    return builder.build();
  }

  public static JsonObject bodyToJson(String body) {
    JsonReader jsonReader = Json.createReader(new StringReader(body));
    return jsonReader.readObject();
  }

  public static JsonObject orderStatusToJson(OrderStatus orderStatus) {
    JsonObjectBuilder builder = Json
      .createObjectBuilder()
      .add("orderId", orderStatus.getOrderId())
      .add("status", orderStatus.getStatus());

    if (!orderStatus.getDeliveryId().equals("")) {
      builder.add("deliveryId", orderStatus.getDeliveryId());
    }
    return builder.build();
  }

  public static Customer customerFromRs(SqlRowSet rs) {
    Customer customer = new Customer();
    customer.setName(rs.getString("name"));
    customer.setAddress(rs.getString("address"));
    customer.setEmail(rs.getString("email"));
    return customer;
  }
}
