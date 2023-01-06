package vttp2022.paf.assessment.eshop.respositories;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import vttp2022.paf.assessment.eshop.models.Customer;

@Repository
public class CustomerRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  // You cannot change the method's signature
  public Optional<Customer> findCustomerByName(String name) {
    // TODO: Task 3

    final SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_SELECT_CUSTOMER_BY_NAME,
      name
    );

    if (!rs.next()) return Optional.empty();
    Customer customer = Customer.create(rs);
    return Optional.of(customer);
  }
}
