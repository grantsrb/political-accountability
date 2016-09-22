import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Politician {
  private static int selectedId = 0;
  private String name;
  private int id;


  public Politician(String _name) {
    this.name = _name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String _update) {
    this.name = _update;
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE politicians SET name = :update WHERE id = :id")
        .addParameter("update", this.name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
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
      this.id = (int) con.createQuery("INSERT INTO politicians (name) VALUES (:name)", true)
                .addParameter("name", this.name)
                .executeUpdate()
                .getKey();
    }
  }

  public static List<Politician> getAll() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM politicians")
        .executeAndFetch(Politician.class);
    }
  }

  public static Politician findById(int _id) {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM politicians WHERE id=:id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Politician.class);
    }
  }

  @Override
  public boolean equals(Object _otherPolitician) {
    if (!(_otherPolitician instanceof Politician)) {
      return false;
    } else {
      Politician newPolitician = (Politician) _otherPolitician;
      return (this.id == newPolitician.id) &&
        (this.name.equals(newPolitician.getName()));
    }
  }

  public List<Goal> getGoals() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM goals WHERE politicianId = :id")
        .addParameter("id", this.id)
        .executeAndFetch(Goal.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      List<Goal> thisPoliticainsGoals = con.createQuery("SELECT * FROM goals WHERE politicianId = :id")
        .addParameter("id", this.id)
        .executeAndFetch(Goal.class);
      for(Goal goal:thisPoliticainsGoals){
        goal.delete();
      }
      con.createQuery("DELETE FROM politicians WHERE id = :id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
