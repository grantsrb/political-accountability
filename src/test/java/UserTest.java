import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class UserTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void save_savesUserToDatabase_void() {
    User testUser = new User("qwerty", "qwerty");
    testUser.save();
    assertTrue(testUser.getId() > 0);
  }

  @Test
  public void getAll_returnsListOfAllUsers_ArrayList() {
    User testUser = new User("qwerty", "qwerty");
    User testUser2 = new User("qwerty", "qwerty");
    testUser.save();
    testUser2.save();
    List<User> userList = User.getAll();
    assertTrue(userList.get(0).equals(testUser));
    assertTrue(userList.get(1).equals(testUser2));
  }

  @Test
  public void findById_returnsInstanceofUserCorrespondingToArgument_User() {
    User testUser = new User("qwerty", "qwerty");
    testUser.save();
    assertTrue(User.findById(testUser.getId()).equals(testUser));
  }

  @Test
  public void delete_deletesInformationInDatabase_void() {
    User testUser = new User("qwerty", "qwerty");
    testUser.save();
    testUser.delete();
    assertEquals(null, User.findById(testUser.getId()));
  }
}
