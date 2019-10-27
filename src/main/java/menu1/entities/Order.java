package menu1.entities;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    public int id;

    @Column(name = "order_date", columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime orderDate;

    @Column(name = "menu_id")
    public int menuId;
    @Column(name = "user_id")
    public long userId;
    @Column(name = "comment")
    public int comment;
    @Column(name = "time")
    public int time;
    @Column(name = "count")
    public int count;
    @Column(name = "status")
    //0-новый
    //1-готовится
    //2-готово
    public int status;
    @ManyToOne
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    public Menu menu;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    public User user;

    public String getTime() {
        return orderDate.toString().substring(11, 16);
    }

    public String getOrderStatus() {
        switch (status) {
            case 0:
                return "New";
            case 1:
                return "In process";
            case 2:
                return "Done";
        }
        return "New";
    }
}
