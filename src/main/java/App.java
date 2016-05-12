import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/recipe-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      model.put("recipe", recipe);
      model.put("ingredients", recipe.getIngredients());
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Recipe newRecipe = new Recipe(name);
      newRecipe.save();
      response.redirect("/recipes/" + newRecipe.getId());
      return null;
    });

    post("/recipes/:id/ingredient/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      String reagent = request.queryParams("reagent");
      Ingredient newIngredient = new Ingredient(reagent);
      newIngredient.save();
      recipe.addIngredient(newIngredient);
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    post("/recipes/:id/delete", (request, response) -> {
      int recipeId = Integer.parseInt(request.params("id"));
      Recipe recipe = Recipe.find(recipeId);
      recipe.delete();
      response.redirect("/recipes");
      return null;
    });
  }
}
