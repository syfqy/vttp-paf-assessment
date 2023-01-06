package vttp2022.paf.assessment.eshop.respositories;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import vttp2022.paf.assessment.eshop.models.OrderStatus;

@Repository
public class OrderStatusRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean createOrderStatus(OrderStatus orderStatus) {
    return (
      jdbcTemplate.update(
        SQL_INSERT_INTO_ORDER_STATUS,
        orderStatus.getOrderId(),
        orderStatus.getDeliveryId(),
        orderStatus.getStatus(),
        new Date() // timestamp
      ) >
      0
    );
  }

  public Map<String, Integer> getOrderCounts(String name) {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_SELECT_ORDER_STATUS_COUNTS,
      name
    );
    Map<String, Integer> orderCountsMap = new HashMap<String, Integer>();
    while (rs.next()) {
      orderCountsMap.put(rs.getString("status"), rs.getInt("count"));
    }
    return orderCountsMap;
  }
}
