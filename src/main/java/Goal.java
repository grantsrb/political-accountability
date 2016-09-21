import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Goal {
  private String description;
  private int id;
  private int politicianId;
  private static int selectedId = 0;

  public Goal(String _description, int _politicianId) {
    this.description = _description;
    this.politicianId = _politicianId;
  }

  public String getDescription() {
    return this.description;
  }

  public int getPoliticianId() {
    return this.politicianId;
  }

  public int getId() {
    return this.id;
  }

  public static int getSelectedId() {
    return selectedId;
  }

  public static void setSelectedId(int _selectedId) {
    selectedId = _selectedId;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO goals (description, politicianId) VALUES (:description, :politicianId)", true)
        .addParameter("description", this.description)
        .addParameter("politicianId", this.politicianId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Goal> getAll() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM goals")
        .executeAndFetch(Goal.class);
    }
  }

  public static Goal findById(int _id) {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM goals WHERE id=:id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Goal.class);
    }
  }

  @Override
  public boolean equals(Object _otherGoal) {
    if (!(_otherGoal instanceof Goal)) {
      return false;
    } else {
      Goal newGoal = (Goal) _otherGoal;
      return (this.id == newGoal.id) &&
        (this.politicianId == newGoal.getPoliticianId());
    }
  }

  public List<Review> getReviews() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM reviews")
        .executeAndFetch(Review.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM goals WHERE id = :id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

/////////////////////////////////////////////////////////

  public static void deleteById(int _id) {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM goals WHERE id = :id")
        .addParameter("id", _id)
        .executeUpdate();
    }
  }

  public static void deleteAll() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM goals")
        .executeUpdate();
    }
  }
}
