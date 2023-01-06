package vttp2022.paf.assessment.eshop.respositories;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vttp2022.paf.assessment.eshop.models.LineItem;

@Repository
public class LineItemRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean createLineItem(String orderId, LineItem lineItem) {
    return (
      jdbcTemplate.update(
        SQL_INSERT_INTO_LINE_ITEMS,
        orderId,
        lineItem.getItem(),
        lineItem.getQuantity()
      ) >
      0
    );
  }
}
