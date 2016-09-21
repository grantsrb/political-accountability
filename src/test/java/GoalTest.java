import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class GoalTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

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
  public void getReviews_returnsAllReviewsFromOneGoal_ListReview() {
    Goal testGoal = new Goal("this is a goal", 1);
    Review testReview = new Review(testGoal.getId(), "Where's the birth certificate???");
    testGoal.save();
    testReview.save();
    assertTrue(testGoal.getReviews().get(0).equals(testReview));
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
