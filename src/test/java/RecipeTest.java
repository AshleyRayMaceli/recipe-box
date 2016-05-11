import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly_true() {
    Recipe myRecipe = new Recipe("Guacamole", "Destroy avocados");
    assertEquals(true, myRecipe instanceof Recipe);
  }

  @Test
  public void getName_recipeInstantiatesWithName_String() {
    Recipe myRecipe = new Recipe("Guacamole", "Destroy avocados");
    assertEquals("Guacamole", myRecipe.getName());
  }

  @Test
  public void getInstructions_recipeInstantiatesWithInstructions_String() {
    Recipe myRecipe = new Recipe("Guacamole", "Destroy avocados");
    assertEquals("Destroy avocados", myRecipe.getInstructions());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void save_savesRecipeCorrectly_1() {
    Recipe newRecipe = new Recipe("Pizza", "Spin the pizza in the air");
    newRecipe.save();
    assertEquals(1, Recipe.all().size());
  }

  @Test
  public void save_assignsIdToObject() {
    Recipe myRecipe = new Recipe("Glazed Yams", "Smear cinnamon all over dem yams");
    myRecipe.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(myRecipe.getId(), savedRecipe.getId());
  }
}
