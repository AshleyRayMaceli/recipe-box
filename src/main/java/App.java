import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("recipes", Recipe.all());
      model.put("tags", Tag.all());
      model.put("ingredients", Ingredient.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.all());
      model.put("tags", Tag.all());
      model.put("ingredients", Ingredient.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      Integer tagDropDownResult = Integer.parseInt(request.queryParams("tagId"));
      Tag tag = Tag.find(tagDropDownResult);
      if (tagDropDownResult == 0) {
        tag = new Tag("");
      }

      Integer ingredientDropDownResult = Integer.parseInt(request.queryParams("ingredientId"));
      Ingredient ingredient = Ingredient.find(ingredientDropDownResult);
      if (ingredientDropDownResult == 0) {
        ingredient = new Ingredient("");
      }

      model.put("actualTag", tag);
      model.put("actualIngredient", ingredient);
      model.put("tag", tag.getName());
      model.put("tagID", tag.getId());
      model.put("ingredient", ingredient.getReagent());
      model.put("ingredientID", ingredient.getId());
      model.put("recipes", Recipe.all());
      model.put("tags", Tag.all());
      model.put("ingredients", Ingredient.all());
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
      List<Ingredient> allIngredients = Ingredient.all();
      Ingredient newIngredient = new Ingredient(reagent);
      boolean matchFound = false;

      for(Ingredient oldIngredient : allIngredients) {
        if (newIngredient.getReagent().equals(oldIngredient.getReagent())) {
          recipe.addIngredient(oldIngredient);
          matchFound = true;
          break;
        }
      }

      if (matchFound == false) {
        newIngredient.save();
        recipe.addIngredient(newIngredient);
      }

      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    post("/recipes/:id/tag/new", (request, response) -> {
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      String nameTag = request.queryParams("name-tag");
      List<Tag> allTags = Tag.all();
      Tag newTag = new Tag(nameTag);
      boolean matchFound = false;

      for(Tag oldTag : allTags) {
        if(newTag.getName().equals(oldTag.getName())) {
          recipe.addTag(oldTag);
          matchFound = true;
          break;
        }
      }

      if (matchFound == false) {
        newTag.save();
        recipe.addTag(newTag);
      }

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
