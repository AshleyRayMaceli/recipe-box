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
      String name = request.queryParams("name");
      Recipe newRecipe = new Recipe(name);
      newRecipe.save();
      response.redirect("/recipes/" + newRecipe.getId());
      return null;
    });

    post("/recipes/:id/ingredient/new", (request, response) -> {
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      String reagent = request.queryParams("reagent");
      Ingredient newIngredient = new Ingredient(reagent);
      newIngredient.save();
      recipe.addIngredient(newIngredient);
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    post("/recipes/:id/tag/new", (request, response) -> {
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      String nameTag = request.queryParams("name-tag");
      Tag newTag = new Tag(nameTag);
      newTag.save();
      recipe.addTag(newTag);
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

    post("/recipes/:id/update", (request, response) -> {
      int recipeId = Integer.parseInt(request.params("id"));
      Recipe recipe = Recipe.find(recipeId);
      String newName = request.queryParams("update");
      recipe.updateName(newName);
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    post("/recipes/:id/update-instructions", (request, response) -> {
      int recipeId = Integer.parseInt(request.params("id"));
      Recipe recipe = Recipe.find(recipeId);
      String newInstructions = request.queryParams("update-instructions");
      recipe.updateInstructions(newInstructions);
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    post("/recipes/:id/ingredient/:ingredient_id/delete", (request, response) -> {
      int recipeId = Integer.parseInt(request.params("id"));
      int ingredientId = Integer.parseInt(request.params("ingredient_id"));
      Recipe recipe = Recipe.find(recipeId);
      Ingredient ingredient = Ingredient.find(ingredientId);
      ingredient.delete();
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    post("/recipes/:id/tag/:tag_id/delete", (request, response) -> {
      int recipeId = Integer.parseInt(request.params("id"));
      int tagId = Integer.parseInt(request.params("tag_id"));
      Recipe recipe = Recipe.find(recipeId);
      Tag tag = Tag.find(tagId);
      tag.delete();
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

  }
}
