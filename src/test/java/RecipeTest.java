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

  @Test
  public void find_findsRecipeInDatabase_true() {
    Recipe myRecipe = new Recipe("Pineapple Juice", "Squeesh dose pineapples into a teacup");
    myRecipe.save();
    Recipe savedRecipe = Recipe.find(myRecipe.getId());
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void addIngredient_addIngredientToRecipe() {
    Ingredient myIngredient = new Ingredient("Orca Meat");
    myIngredient.save();
    Recipe myRecipe = new Recipe("Succulent Seafood Extravaganza", "Butcher an orca");
    myRecipe.save();
    myRecipe.addIngredient(myIngredient);
    Ingredient savedIngredient = myRecipe.getIngredients().get(0);
    assertTrue(myIngredient.equals(savedIngredient));
  }

  @Test
  public void getIngredients_returnsAllIngredients_List() {
    Recipe myRecipe = new Recipe("Chocolate Chip Cookie", "Put sum cheepz in dere");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("Choco Choco Chips");
    myIngredient.save();
    myRecipe.addIngredient(myIngredient);
    List savedIngredients = myRecipe.getIngredients();
    assertEquals(1, savedIngredients.size());
  }

  @Test
  public void updateName_updatesRecipeName_true() {
    Recipe myRecipe= new Recipe("Liver and Onions", "Hate your life, make this recipe, eat it, still hate your life");
    myRecipe.save();
    myRecipe.updateName("Bacon and Eggs");
    assertEquals("Bacon and Eggs", Recipe.find(myRecipe.getId()).getName());
  }

  @Test
  public void updateInstructions_updatesRecipeInstructions_true() {
    Recipe myRecipe= new Recipe("Monkey Bread", "Put monkeys in the bread");
    myRecipe.save();
    myRecipe.updateInstructions("Put bread in the monkeys");
    assertEquals("Put bread in the monkeys", Recipe.find(myRecipe.getId()).getInstructions());
  }

}
