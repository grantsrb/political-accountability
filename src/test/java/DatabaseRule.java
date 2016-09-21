import org.junit.*;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {
  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/political_accountability_test", null, null);
    Map<String,Object> model = new HashMap<>();
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM goals").executeUpdate();
      con.createQuery("DELETE FROM politicians").executeUpdate();
      con.createQuery("DELETE FROM reviews").executeUpdate();
    }
  }
}
