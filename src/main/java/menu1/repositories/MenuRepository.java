package menu1.repositories;

import menu1.entities.Menu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
    List<Menu> findByType(int type);

}
