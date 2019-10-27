package menu1.restcontrollers;

import menu1.entities.Menu;
import menu1.entities.Order;
import menu1.entities.User;
import menu1.repositories.MenuRepository;
import menu1.repositories.OrderRepo;
import menu1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
@Autowired
    UserRepository userRepository;
@RequestMapping("/users")
public Iterable<User>getUsers() {
    return userRepository.findAll();
}
@Autowired
    MenuRepository menuRepository;
@RequestMapping("/menu")
    public Iterable<Menu>getMenu()
{
return menuRepository.findAll();
}
@Autowired
OrderRepo orderRepo;
@RequestMapping("/orders")
    public Iterable<Order>getOrders()
{
    return orderRepo.findAll();
}
}
