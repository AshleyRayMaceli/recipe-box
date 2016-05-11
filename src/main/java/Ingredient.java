import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Ingredient {
  private int id;
  private String reagent;

  public Ingredient(String reagent) {
    this.reagent = reagent;
  }

  public String getReagent() {
    return reagent;
  }

  public static List<Ingredient> all() {
    String sql = "SELECT * FROM ingredients;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }
}
