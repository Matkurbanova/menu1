package menu1.restcontrollers;

import menu1.entities.Order;
import menu1.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    OrderRepo orderRepo;

    @RequestMapping("/{uid}")
    public Iterable<Order> getOrders(@PathVariable("uid") long userId) {
        return orderRepo.findByUserId(userId);
    }

}
