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

}
