import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/",(request,response) -> {
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    post("/politicians/new",(request,response) -> {
      String nameInput = request.queryParams("politicianNameInput");
      Politician newPolitician = new Politician(nameInput);
      newPolitician.save();
      Politician.setSelectedId(newPolitician.getId());
      Goal.setSelectedId(0);
      Review.setSelectedId(0);
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    post("/goals/new",(request,response) -> {
      int selectedPoliticianId = Politician.getSelectedId();
      String descriptionInput = request.queryParams("goalDescriptionInput");
      Goal newGoal = new Goal(descriptionInput, selectedPoliticianId);
      newGoal.save();
      Goal.setSelectedId(newGoal.getId());
      Review.setSelectedId(0);
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    post("/reviews/new",(request,response) -> {
      int selectedGoalId = Goal.getSelectedId();
      String descriptionInput = request.queryParams("reviewDescriptionInput");
      Review newReview = new Review(selectedGoalId, descriptionInput);
      newReview.save();
      Review.setSelectedId(newReview.getId());
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    get("/sign-in",(request,response) -> { // Directs to sign in page
      Map<String,Object> model = new HashMap<>();
      return new ModelAndView(model, "templates/sign-in.vtl");
    },new VelocityTemplateEngine());

    post("/sign-in", (request,response) -> { // Directs to home page if successful login
      String userName = request.queryParams("user-name");
      String password = request.queryParams("password");
      if (User.validUser(userName,password)) {
        User.setLogInStatus(true);
        User.setLoggedInUser(userName);
        return new ModelAndView(createModel_Homepage(), layout);
      } else {
        Map<String,Object> model = new HashMap<>();
        model.put("invalidLogin", true);
        return new ModelAndView(model, "templates/sign-in.vtl");
      }
    },new VelocityTemplateEngine());

    post("/new-account", (request,response) -> {
      String userName = request.queryParams("create-user-name");
      String password = request.queryParams("create-password");
      if (!User.userAlreadyExists(userName)) { // If user name is valid, directs to home page
        User.setLogInStatus(true);
        User newUser = new User(userName, password);
        newUser.save();
        User.setLoggedInUser(userName);
        return new ModelAndView(createModel_Homepage(), layout);
      } else {
        Map<String,Object> model = new HashMap<>();
        model.put("invalidUserName", true);
        return new ModelAndView(model, "templates/sign-in.vtl");
      }
    },new VelocityTemplateEngine());

    post("/sign-out", (request, response) -> {
      User.setLogInStatus(false);
      User.setLoggedInUser(null);
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    get("/politicians/:id", (request, response) -> {
      int politicianId = Integer.parseInt(request.params(":id"));
      Politician.setSelectedId(politicianId);
      List<Goal> goals = Politician.findById(politicianId).getGoals();
      List<Review> reviews = new ArrayList<Review>();

      if (goals.size() > 0) {
        Goal.setSelectedId(goals.get(0).getId());
        reviews = Goal.findById(Goal.getSelectedId()).getReviews();
      }
      else
        Goal.setSelectedId(0);

      if (reviews.size() > 0)
        Review.setSelectedId(reviews.get(0).getId());
      else
        Review.setSelectedId(0);

      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    get("/goals/:id", (request, response) -> {
      int goalId = Integer.parseInt(request.params(":id"));
      Goal.setSelectedId(goalId);
      List<Review> reviews = Goal.findById(goalId).getReviews();

      if (reviews.size() > 0)
        Review.setSelectedId(reviews.get(0).getId());
      else
        Review.setSelectedId(0);

      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    get("/reviews/:id", (request, response) -> {
      Review.setSelectedId(Integer.parseInt(request.params(":id")));
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    post("/politicians/delete", (request, response) -> {
      Politician.findById(Politician.getSelectedId()).delete();
      Politician.setSelectedId(0);
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    post("/goals/delete", (request, response) -> {
      Goal.findById(Goal.getSelectedId()).delete();
      Goal.setSelectedId(0);
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    post("/reviews/delete", (request, response) -> {
      Review.findById(Review.getSelectedId()).delete();
      Review.setSelectedId(0);
      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());
  }

  private static Map<String,Object> createModel_Homepage(){
    Map<String,Object> model = new HashMap<>();
    model.put("selectedPoliticianId", Politician.getSelectedId());
    model.put("selectedGoalId", Goal.getSelectedId());
    model.put("selectedReviewId", Review.getSelectedId());
    model.put("politicians", Politician.getAll());

    if (Politician.getSelectedId() > 0) {
      model.put("goals", Politician.findById(Politician.getSelectedId()).getGoals());
      if(Goal.getSelectedId() > 0)
        model.put("reviews", Goal.findById(Goal.getSelectedId()).getReviews());
      else
        model.put("reviews", new ArrayList<Review>());
    } else {
      model.put("goals", new ArrayList<Goal>());
    }

    model.put("signedIn", User.getLogInStatus());
    model.put("loggedInUser", User.getLoggedInUser());
    model.put("template", "templates/index.vtl");
    return model;
  }
}
