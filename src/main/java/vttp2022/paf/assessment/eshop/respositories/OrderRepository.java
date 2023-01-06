package vttp2022.paf.assessment.eshop.respositories;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vttp2022.paf.assessment.eshop.models.Order;

@Repository
public class OrderRepository {

  // TODO: Task 3

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean createOrder(Order order) {
    return (
      jdbcTemplate.update(
        SQL_INSERT_INTO_ORDERS,
        order.getOrderId(),
        order.getDeliveryId(),
        order.getName(),
        order.getOrderDate()
      ) >
      0
    );
  }
}
