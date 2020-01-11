package menu1.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
@Id
public long id;
public int role;
public String name;
public String login;
public String password;

}
