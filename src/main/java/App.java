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
      Map<String,Object> model = new HashMap<>();
      model.put("selectedReviewId", Review.getSelectedId());
      model.put("selectedGoalId", Goal.getSelectedId());
      model.put("selectedPoliticianId", Politician.getSelectedId());
      model.put("politicians", Politician.getAll());
      model.put("goals", Goal.getAll());
      model.put("reviews", Review.getAll());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    post("/politicians/new",(request,response) -> {
      Map<String,Object> model = new HashMap<>();
      String nameInput = request.queryParams("politicianNameInput");
      Politician newPolitician = new Politician(nameInput);
      newPolitician.save();
      Politician.setSelectedId(newPolitician.getId());
      model.put("selectedReviewId", Review.getSelectedId());
      model.put("selectedGoalId", Goal.getSelectedId());
      model.put("selectedPoliticianId", Politician.getSelectedId());
      model.put("politicians", Politician.getAll());
      model.put("goals", Goal.getAll());
      model.put("reviews", Review.getAll());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    post("/goals/new",(request,response) -> {
      Map<String,Object> model = new HashMap<>();
      int selectedPoliticianId = Politician.getSelectedId();
      String descriptionInput = request.queryParams("goalDescriptionInput");
      Goal newGoal = new Goal(descriptionInput, selectedPoliticianId);
      newGoal.save();
      Goal.setSelectedId(newGoal.getId());
      model.put("selectedReviewId", Review.getSelectedId());
      model.put("selectedGoalId", Goal.getSelectedId());
      model.put("selectedPoliticianId", Politician.getSelectedId());
      model.put("politicians", Politician.getAll());
      model.put("goals", Goal.getAll());
      model.put("reviews", Review.getAll());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    post("/reviews/new",(request,response) -> {
      Map<String,Object> model = new HashMap<>();
      int selectedGoalId = Goal.getSelectedId();
      String descriptionInput = request.queryParams("reviewDescriptionInput");
      Review newReview = new Review(selectedGoalId, descriptionInput);
      newReview.save();
      Review.setSelectedId(newReview.getId());
      model.put("selectedReviewId", Review.getSelectedId());
      model.put("selectedGoalId", Goal.getSelectedId());
      model.put("selectedPoliticianId", Politician.getSelectedId());
      model.put("politicians", Politician.getAll());
      model.put("goals", Goal.getAll());
      model.put("reviews", Review.getAll());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());
  }
}
