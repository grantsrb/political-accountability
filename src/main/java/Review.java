import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Review {
  private int id;
  private int goalId;
  private String description;
  private static int selectedId = 0;

  public Review(int _goalId, String _description) {
    goalId = _goalId;
    description = _description;
  }

  public int getId() {
    return id;
  }

  public int getGoalId() {
    return goalId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String _update) {
    this.description = _update;
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE reviews SET description = :description WHERE id = :id")
        .addParameter("description", this.description)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static int getSelectedId() {
    return selectedId;
  }

  public static void setSelectedId(int _selectedId) {
    selectedId = _selectedId;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO reviews (description, goalId) VALUES (:description, :goalId)", true)
        .addParameter("description", this.description)
        .addParameter("goalId", this.goalId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Review> getAll() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM reviews")
        .executeAndFetch(Review.class);
    }
  }

  public static Review findById(int _id) {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM reviews WHERE id=:id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Review.class);
    }
  }

  @Override
  public boolean equals(Object _otherReview) {
    if (!(_otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) _otherReview;
      return (this.id == newReview.getId()) &&
             (this.goalId == newReview.getGoalId()) &&
             (this.description.equals(newReview.getDescription()));
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM reviews WHERE id = :id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
