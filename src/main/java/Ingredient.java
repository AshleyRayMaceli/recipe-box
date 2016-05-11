import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Ingredient {
  private int id;
  private String reagent;

  public Ingredient(String reagent) {
    this.reagent = reagent;
  }

  public int getId() {
    return id;
  }

  public String getReagent() {
    return reagent;
  }

  public static List<Ingredient> all() {
    String sql = "SELECT * FROM ingredients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }

  @Override
  public boolean equals(Object otherIngredient) {
    if (!(otherIngredient instanceof Ingredient)) {
      return false;
    } else {
      Ingredient newIngredient = (Ingredient) otherIngredient;
      return this.getReagent().equals(newIngredient.getReagent()) &&
             this.getId() == (newIngredient.getId());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients (reagent) VALUES (:reagent)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("reagent", this.reagent)
        .executeUpdate()
        .getKey();
    }
  }

  public static Ingredient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM ingredients WHERE id=:id";
      Ingredient ingredient = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Ingredient.class);
    return ingredient;
    }
  }

  public void addRecipe(Recipe recipe) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients_recipes (ingredient_id, recipe_id) VALUES (:ingredient_id, :recipe_id)";
        con.createQuery(sql)
          .addParameter("ingredient_id", this.getId())
          .addParameter("recipe_id", recipe.getId())
          .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT recipe_id FROM ingredients_recipes WHERE ingredient_id = :ingredient_id";

      List<Integer> recipeIds = con.createQuery(joinQuery)
        .addParameter("ingredient_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Recipe> recipes = new ArrayList<Recipe>();

      for (Integer recipeId : recipeIds) {
        String recipeQuery = "SELECT * FROM recipes WHERE id = :recipeId";
        Recipe recipe = con.createQuery(recipeQuery)
          .addParameter("recipeId", recipeId)
          .executeAndFetchFirst(Recipe.class);
        recipes.add(recipe);
      }
      return recipes;
    }
  }

}
