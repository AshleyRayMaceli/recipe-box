import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Ingredient_instantiatesCorrectly_true() {
    Ingredient myIngredient = new Ingredient("Avocados");
    assertEquals(true, myIngredient instanceof Ingredient);
  }

  @Test
  public void getReagent_recipeInstantiatesWithReagent_String() {
    Ingredient myIngredient = new Ingredient("Avocados");
    assertEquals("Avocados", myIngredient.getReagent());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Ingredient.all().size(), 0);
  }
}
