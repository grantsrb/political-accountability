import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class GoalTest {

  @Before
  public void establishConnection() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/political_accountability_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM goals").executeUpdate();
      con.createQuery("DELETE FROM politicians").executeUpdate();
      con.createQuery("DELETE FROM reviews").executeUpdate();
    }
  }

  @Test
  public void save_savesGoalToDatabase_void() {
    Goal testGoal = new Goal("this is a goal", 1);
    testGoal.save();
    assertTrue(testGoal.getId() > 0);
  }

  @Test
  public void getAll_returnsListOfAllGoals_ArrayList() {
    Goal testGoal = new Goal("this is a goal", 1);
    Goal testGoal2 = new Goal("this is a goal", 1);
    testGoal.save();
    testGoal2.save();
    List<Goal> goalList = Goal.getAll();
    assertTrue(goalList.get(0).equals(testGoal));
    assertTrue(goalList.get(1).equals(testGoal2));
  }

  @Test
  public void findById_returnsInstanceofGoalCorrespondingToArgument_Goal() {
    Goal testGoal = new Goal("this is a goal", 1);
    testGoal.save();
    assertTrue(Goal.findById(testGoal.getId()).equals(testGoal));
  }

  @Test
  public void delete_deletesInformationInDatabase_void() {
    Goal testGoal = new Goal("this is a goal", 1);
    testGoal.save();
    testGoal.delete();
    assertEquals(null, Goal.findById(testGoal.getId()));
  }
}
