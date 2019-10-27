package menu1.repositories;

import menu1.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Order,Integer> {
    @Query("SELECT o FROM Order o WHERE o.userId = ?1")
    List<Order> findByUserId(long userId);
}
