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

  public String getName() {
    return name;
  }

  public String getInstructions() {
    return instructions;
  }

  public static List<Recipe> all() {
    String sql = "SELECT * FROM recipes;";
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
      return this.getName().equals(newRecipe.getName());
    }
  }
}
