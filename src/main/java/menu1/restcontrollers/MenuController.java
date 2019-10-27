package menu1.restcontrollers;

import menu1.Statics;
import menu1.data.Response;
import menu1.entities.Menu;
import menu1.entities.Order;
import menu1.entities.User;
import menu1.repositories.MenuRepository;
import menu1.repositories.OrderRepo;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private OrderRepo orderRepo;

    @RequestMapping("/menu")
    public Iterable<Menu> getMenu() {
        return menuRepository.findAll();

    }

    @RequestMapping("/menu/{type}")
    public Iterable<Menu> getMenu(@PathVariable int type) {
        return menuRepository.findByType(type);

    }

    @RequestMapping(value = "add-to-cart/{menu_id}", method = RequestMethod.POST)
    public Response addToCart(
            @PathVariable("menu_id") int menuId,
            @RequestBody User user

    ) {
        Response response = new Response();
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent()) {
            Order order = new Order();
            order.userId = user.id;
            order.menuId = menuId;
            orderRepo.save(order);
            response.status = Statics.OK;
            response.data = menu.get();


        } else {
            response.status = Statics.NOT_FOUND;
            response.message = "Menu not found";
        }
        return response;
    }
}
