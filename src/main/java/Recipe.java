import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Recipe {
  private int id;
  private String name;
  private String instructions;

  public Recipe(String name, String instructions) {
    this.name = name;
    this.instructions = instructions;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getInstructions() {
    return instructions;
  }

  public static List<Recipe> all() {
    String sql = "SELECT * FROM recipes";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getName().equals(newRecipe.getName()) &&
             this.getId() == (newRecipe.getId());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Recipe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes WHERE id=:id";
      Recipe recipe = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Recipe.class);
    return recipe;
    }
  }

  public void addIngredient(Ingredient ingredient) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients_recipes (ingredient_id, recipe_id) VALUES (:ingredient_id, :recipe_id)";
        con.createQuery(sql)
          .addParameter("ingredient_id", ingredient.getId())
          .addParameter("recipe_id", this.getId())
          .executeUpdate();
    }
  }

  public List<Ingredient> getIngredients() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT ingredients.* FROM recipes " +
      "JOIN ingredients_recipes ON (recipes.id = ingredients_recipes.recipe_id) " +
      "JOIN ingredients ON (ingredients_recipes.ingredient_id = ingredients.id) " +
      "WHERE recipes.id = :recipe_id";

      List<Ingredient> ingredients = con.createQuery(joinQuery)
        .addParameter("recipe_id", this.getId())
        .executeAndFetch(Ingredient.class);

      return ingredients;
    }
  }

  public void updateName(String newName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public void updateInstructions(String newInstructions) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET instructions = :instructions WHERE id = :id";
      con.createQuery(sql)
        .addParameter("instructions", newInstructions)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM recipes WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", this.id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM ingredients_recipes WHERE recipe_id = :recipeId";
        con.createQuery(joinDeleteQuery)
          .addParameter("recipeId", this.id)
          .executeUpdate();
    }
  }

}
