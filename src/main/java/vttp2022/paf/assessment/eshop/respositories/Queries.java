package vttp2022.paf.assessment.eshop.respositories;

public class Queries {

  public static final String SQL_SELECT_CUSTOMER_BY_NAME =
    """
    SELECT *
    FROM customers
    WHERE name =?
    """;

  public static final String SQL_INSERT_INTO_ORDERS =
    """
        INSERT INTO orders(order_id, delivery_id, customer_name, order_date)
        VALUES(?,?,?,?)
        """;
}
