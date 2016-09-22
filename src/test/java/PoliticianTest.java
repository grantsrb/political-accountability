import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PoliticianTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void save_savesPoliticianToDatabase_void() {
    Politician testPolitician = new Politician("Dick Trump");
    testPolitician.save();
    assertTrue(testPolitician.getId() > 0);
  }

  @Test
  public void getAll_returnsListOfAllPoliticians_ArrayList() {
    Politician testPolitician = new Politician("Dick Trump");
    testPolitician.save();
    assertTrue(Politician.getAll().get(0).equals(testPolitician));
  }

  @Test
  public void getGoals_returnsAllGoalsFromOneGoal_ListGoal() {
    Politician testPolitician = new Politician("Dick Trump");
    testPolitician.save();
    Goal testGoal = new Goal("Build a wall", testPolitician.getId());
    Goal testGoal2 = new Goal("Shoot a friend", testPolitician.getId());
    testGoal.save();
    testGoal2.save();
    assertTrue(testPolitician.getGoals().get(0).equals(testGoal));
    assertTrue(testPolitician.getGoals().get(1).equals(testGoal2));
  }

  @Test
  public void findById_returnsInstanceofPoliticianCorrespondingToArgument_Politician() {
    Politician testPolitician = new Politician("Dick Trump");
    testPolitician.save();
    assertTrue(Politician.findById(testPolitician.getId()).equals(testPolitician));
  }

  @Test
  public void delete_deletesInformationInDatabase_void() {
    Politician testPolitician = new Politician("Dick Trump");
    testPolitician.save();
    testPolitician.delete();
    assertEquals(null, Politician.findById(testPolitician.getId()));
  }

  @Test
  public void setName_changesName_void() {
    Politician testPolitician = new Politician("Dick Trump");
    testPolitician.save();
    testPolitician.setName("Hilary Dickton");
    assertEquals("getName test", "Hilary Dickton", testPolitician.getName());
    assertEquals("database Test", "Hilary Dickton", Politician.findById(testPolitician.getId()).getName());
  }
}
