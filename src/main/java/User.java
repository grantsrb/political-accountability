import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class User {
  private String userName;
  private String password;
  private int id;

  public User(String _userName, String _password) {
    this.userName = _userName;
    this.password = _password;
  }

  public String getUserName() {
    return this.userName;
  }

  public String getPassword() {
    return this.password;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO users (userName, password) VALUES (:userName, :password)", true)
                          .addParameter("userName", this.userName)
                          .addParameter("password", this.password)
                          .executeUpdate()
                          .getKey();
    }
  }

  public static List<User> getAll() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM users")
        .executeAndFetch(User.class);
    }
  }

  public static User findById(int _id) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM users WHERE id = :id")
        .addParameter("id", _id)
        .executeAndFetchFirst(User.class);
    }
  }

  @Override
  public boolean equals(Object _otherUser) {
    if (!(_otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) _otherUser;
      return (this.id == newUser.id) &&
             (this.userName.equals(newUser.getUserName()));
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM users WHERE id = :id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }


}
