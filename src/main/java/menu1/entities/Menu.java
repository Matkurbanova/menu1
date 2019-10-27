package menu1.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    public int id;
    public String name;
    public String description;
    public String amount;
    public double price;
    public String image;
    public int type;



}
