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

  @Test
  public void equals_returnsTrueIfIngredientsAreTheSame_true() {
    Ingredient firstIngredient = new Ingredient("Jalapenos");
    Ingredient secondIngredient = new Ingredient("Jalapenos");
    assertTrue(firstIngredient.equals(secondIngredient));
  }

  @Test
  public void save_savesIngredientCorrectly_1() {
    Ingredient newIngredient = new Ingredient("Cheese");
    newIngredient.save();
    assertEquals(1, Ingredient.all().size());
  }

  @Test
  public void save_assignsIdToObject() {
    Ingredient myIngredient = new Ingredient("Tomatoes");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.all().get(0);
    assertEquals(myIngredient.getId(), savedIngredient.getId());
  }

}
