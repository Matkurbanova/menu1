package menu1.Controllers;

import menu1.entities.Menu;
import menu1.entities.Order;
import menu1.entities.User;
import menu1.repositories.MenuRepository;
import menu1.repositories.OrderRepo;
import menu1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sun.util.calendar.BaseCalendar;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
public class IndexController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private OrderRepo orderRepo;

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        Map<String, String> data = new HashMap<>();
        data.put("message", "");
        if (session.getAttribute("user_id") != null) {


            return getMenu(session);
        }

        return new ModelAndView("index", data);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView hello(

            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password,
            Map<String, String> model,
            HttpSession session
    ) {
        User user = userRepository.getByLoginAndPass(login, password);
        if (user != null) {
            session.setAttribute("user_id", user.id);
            return getMenu(session);
        } else
            model.put("message", "Wrong login or password");
        return new ModelAndView("index");

    }


    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public ModelAndView getMenu(HttpSession session) {
        Iterable<Menu> menu = menuRepository.findAll();

        Iterable<Menu> menuZavtraki = menuRepository.findByType(1);
        Iterable<Menu> menuTemp = menuRepository.findByType(2);

        Map<String, Object> model = new HashMap<>();
        model.put("menu", menu);
        model.put("logedin", session.getAttribute("user_id") != null);
        model.put("menu", menuZavtraki);
        model.put("temp", menuTemp);


        return new ModelAndView("menu", model);
    }

    @RequestMapping(value = "/menu/{type}", method = RequestMethod.GET)
    public ModelAndView getTemp(
            @PathVariable("type") String type
    ) {
        Map<String, Object> model = new HashMap<>();
        if (type.equals("breakfast")) {
            Iterable<Menu> menu = menuRepository.findByType(1);
            model.put("menu", menu);
            model.put("menuTitle", "Завтраки");
        } else if (type.equals("salads")) {
            Iterable<Menu> menu = menuRepository.findByType(2);
            model.put("menu", menu);
            model.put("menuTitle", "Салаты");
        } else if (type.equals("all dishes")) {
            Iterable<Menu> menu = menuRepository.findAll();
            model.put("menu", menu);
            model.put("menuTitle", "Все блюда");
        } else if (type.equals("soups")) {
            Iterable<Menu> menu = menuRepository.findByType(3);
            model.put("menu", menu);
            model.put("menuTitle", "Супы");
        } else if (type.equals("pizza")) {
            Iterable<Menu> menu = menuRepository.findByType(5);
            model.put("menu", menu);
            model.put("menuTitle", "Пицца");
        } else if (type.equals("meat dishes")) {
            Iterable<Menu> menu = menuRepository.findByType(6);
            model.put("menu", menu);
            model.put("menuTitle", "Блюды из мяса");
        } else if (type.equals("seafood dishes")) {
            Iterable<Menu> menu = menuRepository.findByType(4);
            model.put("menu", menu);
            model.put("menuTitle", "Блюда из морепродуктов");
        } else if (type.equals("fast food")) {
            Iterable<Menu> menu = menuRepository.findByType(7);
            model.put("menu", menu);
            model.put("menuTitle", "Фасфуд");
        } else if (type.equals("dessert")) {
            Iterable<Menu> menu = menuRepository.findByType(8);
            model.put("menu", menu);
            model.put("menuTitle", "Десерт");
        } else if (type.equals("National cuisine")) {
            Iterable<Menu> menu = menuRepository.findByType(9);
            model.put("menu", menu);
            model.put("menuTitle", "Национальная кухня");
        } else if (type.equals("the drinks")) {
            Iterable<Menu> menu = menuRepository.findByType(10);
            model.put("menu", menu);
            model.put("menuTitle", "Напитки");
        }
        return new ModelAndView("menu", model);
    }

    @RequestMapping(value = "/add-menu", method = RequestMethod.GET)
    public ModelAndView addMenu() {
        return new ModelAndView("addMenu");
    }

    @RequestMapping(value = "/add-menu", method = RequestMethod.POST)
    public ModelAndView addMenu(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("type") int type,
            @RequestParam("amount") String amount,
            @RequestParam("description") String description,
            @RequestParam("image") String image


    ) {
        Menu menu = new Menu();
        menu.name = name;
        menu.price = price;
        menu.type = type;
        menu.amount = amount;
        menu.description = description;
        menu.image = image;
        menuRepository.save(menu);
        return new ModelAndView("addMenu");
    }

    @RequestMapping(value = "/add-to-cart", method = RequestMethod.POST)
    public String addToCart(
            @RequestParam("p_id") int menuId,
            Map<String, Object> model,
            HttpSession session
    ) {
        if (session.getAttribute("user_id") != null) {
            long userId = (long) session.getAttribute("user_id");
            Menu menu = menuRepository.findById(menuId).get();
            Order order = new Order();
            order.menuId = menuId;
            order.userId = userId;
            order.status = 0;
            order.orderDate = LocalDateTime.now();
//          order.comment=comment;
//           order.time=time;
//           order.count=count;
            orderRepo.save(order);
            model.put("message", menu.name + " added");


        } else {
            model.put("message", "Войдите на сайт");

        }
        return "ok";


    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public RedirectView logout(HttpSession session) {
        session.removeAttribute("user_id");
        return new RedirectView("/");
    }

    //    Orders список заказов
    @RequestMapping(value = "/list-orders", method = RequestMethod.GET)
    public ModelAndView listOrders(Map<String, Object> model) {
        Iterable<Order> orders = orderRepo.findAll();
        model.put("listOrders", orders);

        return new ModelAndView("listOrders", model);
    }
}

