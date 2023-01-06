package vttp2022.paf.assessment.eshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vttp2022.paf.assessment.eshop.controllers.exceptions.OrderException;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.respositories.LineItemRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderStatusRepository;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepo;

  @Autowired
  private LineItemRepository lineItemRepo;

  @Transactional(rollbackFor = OrderException.class)
  public void createOrder(Order order) throws OrderException {
    if (!orderRepo.createOrder(order)) throw new OrderException(
      "Cannot create order"
    );

    for (LineItem li : order.getLineItems()) {
      if (
        !lineItemRepo.createLineItem(order.getOrderId(), li)
      ) throw new OrderException("Cannot create line item");
    }
  }
}
