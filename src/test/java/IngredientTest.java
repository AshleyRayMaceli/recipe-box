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

  @Test
  public void find_findsIngredientInDatabase_true() {
    Ingredient myIngredient = new Ingredient("Pineapples");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.find(myIngredient.getId());
    assertTrue(myIngredient.equals(savedIngredient));
  }

  @Test
  public void addRecipe_addRecipeToIngredient() {
    Recipe myRecipe = new Recipe("Buffalo Chicken", "Add chicken to a buffalo");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("Chicken");
    myIngredient.save();
    myIngredient.addRecipe(myRecipe);
    Recipe savedRecipe = myIngredient.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void getRecipes_returnsAllRecipes_List() {
    Recipe myRecipe = new Recipe("Wrap", "Roll a wrap with stuff in it");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("Stuff");
    myIngredient.save();
    myRecipe.addIngredient(myIngredient);
    List savedRecipes = myIngredient.getRecipes();
    assertEquals(1, savedRecipes.size());
  }

  @Test
  public void update_updatesIngredientReagent_true() {
    Ingredient myIngredient= new Ingredient("Strawberry");
    myIngredient.save();
    myIngredient.update("Marionberry");
    assertEquals("Marionberry", Ingredient.find(myIngredient.getId()).getReagent());
  }

  @Test
  public void delete_deleteAllIngredientAndRecipeAssociations() {
    Recipe myRecipe = new Recipe("Wrap", "Roll a wrap with stuff in it");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("Stuff");
    myIngredient.save();
    myRecipe.addIngredient(myIngredient);
    myIngredient.delete();
    assertEquals(0, Ingredient.all().size());
    assertEquals(0, myRecipe.getIngredients().size());
  }

}
