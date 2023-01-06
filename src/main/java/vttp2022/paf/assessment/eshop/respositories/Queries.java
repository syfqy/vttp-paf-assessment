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
        INSERT INTO orders(order_id, customer_name, order_date)
        VALUES(?,?,?)
        """;

  public static final String SQL_INSERT_INTO_ORDER_STATUS =
    """
            INSERT INTO order_status(order_id, delivery_id, status, status_update)
            VALUES(?,?,?,?)
            """;
  public static final String SQL_SELECT_ORDER_STATUS_COUNTS =
    """
            WITH a as (
                SELECT o.order_id, s.status
                FROM orders o
            JOIN order_status s
                ON o.order_id = s.order_id
            WHERE o.customer_name = ? 
            )
            SELECT status, count(order_id) as count
            FROM a
            GROUP BY status;
            """;
}
