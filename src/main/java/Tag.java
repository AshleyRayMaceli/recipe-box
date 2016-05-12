import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Tag {
  private int id;
  private String name;

  public Tag(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static List<Tag> all() {
    String sql = "SELECT * FROM tags";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  @Override
  public boolean equals(Object otherTag) {
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getName().equals(newTag.getName()) &&
             this.getId() == (newTag.getId());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id=:id";
      Tag tag = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Tag.class);
    return tag;
    }
  }

  public void addRecipe(Recipe recipe) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_tags (recipe_id, tag_id) VALUES (:recipe_id, :tag_id)";
        con.createQuery(sql)
          .addParameter("tag_id", this.getId())
          .addParameter("recipe_id", recipe.getId())
          .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT recipes.* FROM tags " +
      "JOIN recipes_tags ON (tags.id = recipes_tags.tag_id) " +
      "JOIN recipes ON (recipes_tags.recipe_id = recipes.id) " +
      "WHERE tags.id = :tag_id";

      List<Recipe> recipes = con.createQuery(joinQuery)
        .addParameter("tag_id", this.getId())
        .executeAndFetch(Recipe.class);

      return recipes;
    }
  }

  public void update(String newName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM tags WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", this.id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM recipes_tags WHERE tag_id = :tagId";
        con.createQuery(joinDeleteQuery)
          .addParameter("tagId", this.id)
          .executeUpdate();
    }
  }

}
