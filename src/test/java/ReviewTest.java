import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ReviewTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void save_savesReviewToDatabase_void() {
    Review testReview = new Review(1, "this is a review");
    testReview.save();
    assertTrue(testReview.getId() > 0);
  }

  @Test
  public void getAll_returnsListOfAllReviews_ArrayList() {
    Review testReview = new Review(1, "this is a review");
    Review testReview2 = new Review(1, "this is a review");
    testReview.save();
    testReview2.save();
    List<Review> reviewList = Review.getAll();
    assertTrue(reviewList.get(0).equals(testReview));
    assertTrue(reviewList.get(1).equals(testReview2));
  }

  @Test
  public void findById_returnsInstanceofReviewCorrespondingToArgument_Review() {
    Review testReview = new Review(1, "this is a review");
    testReview.save();
    assertTrue(Review.findById(testReview.getId()).equals(testReview));
  }

  @Test
  public void delete_deletesInformationInDatabase_void() {
    Review testReview = new Review(1, "this is a review");
    testReview.save();
    testReview.delete();
    assertEquals(null, Review.findById(testReview.getId()));
  }
}
