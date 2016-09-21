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

      return new ModelAndView(createModel_Homepage(), layout);
    },new VelocityTemplateEngine());

    post("/goals/new",(request,response) -> {
      int selectedPoliticianId = Politician.getSelectedId();

      String descriptionInput = request.queryParams("goalDescriptionInput");
      Goal newGoal = new Goal(descriptionInput, selectedPoliticianId);
      newGoal.save();
      Goal.setSelectedId(newGoal.getId());

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
    model.put("template", "templates/index.vtl");
    return model;
  }
}
