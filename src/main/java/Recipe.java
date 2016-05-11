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
      String joinQuery = "SELECT ingredient_id FROM ingredients_recipes WHERE recipe_id = :recipe_id";

      List<Integer> ingredientIds = con.createQuery(joinQuery)
        .addParameter("recipe_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Ingredient> ingredients = new ArrayList<Ingredient>();

      for (Integer ingredientId : ingredientIds) {
        String ingredientQuery = "SELECT * FROM ingredients WHERE id = :ingredientId";
        Ingredient ingredient = con.createQuery(ingredientQuery)
          .addParameter("ingredientId", ingredientId)
          .executeAndFetchFirst(Ingredient.class);
        ingredients.add(ingredient);
      }
      return ingredients;
    }
  }
}
