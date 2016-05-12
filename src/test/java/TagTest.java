import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Tag_instantiatesCorrectly_true() {
    Tag myTag = new Tag("Mexican");
    assertEquals(true, myTag instanceof Tag);
  }

  @Test
  public void getName_tagInstantiatesWithName_String() {
    Tag myTag = new Tag("Mexican");
    assertEquals("Mexican", myTag.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Tag.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfTagsAreTheSame_true() {
    Tag firstTag = new Tag("Breakfast");
    Tag secondTag = new Tag("Breakfast");
    assertTrue(firstTag.equals(secondTag));
  }

  @Test
  public void save_savesTagCorrectly_1() {
    Tag newTag = new Tag("Italian");
    newTag.save();
    assertEquals(1, Tag.all().size());
  }

  @Test
  public void save_assignsIdToObject() {
    Tag myTag = new Tag("Greek");
    myTag.save();
    Tag savedTag = Tag.all().get(0);
    assertEquals(myTag.getId(), savedTag.getId());
  }

  @Test
  public void find_findsTagInDatabase_true() {
    Tag myTag = new Tag("Hawaiian");
    myTag.save();
    Tag savedTag = Tag.find(myTag.getId());
    assertTrue(myTag.equals(savedTag));
  }

  @Test
  public void addRecipe_addRecipeToTag() {
    Recipe myRecipe = new Recipe("Buffalo Chicken", "Add chicken to a buffalo");
    myRecipe.save();
    Tag myTag = new Tag("Indian");
    myTag.save();
    myTag.addRecipe(myRecipe);
    Recipe savedRecipe = myTag.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));
  }

  // @Test
  // public void getRecipes_returnsAllRecipes_List() {
  //   Recipe myRecipe = new Recipe("Wrap", "Roll a wrap with stuff in it");
  //   myRecipe.save();
  //   Tag myTag = new Tag("Stuff");
  //   myTag.save();
  //   myRecipe.addTag(myTag);
  //   List savedRecipes = myTag.getRecipes();
  //   assertEquals(1, savedRecipes.size());
  // }

  @Test
  public void update_updatesTagName_true() {
    Tag myTag= new Tag("Japanese");
    myTag.save();
    myTag.update("Korean");
    assertEquals("Korean", Tag.find(myTag.getId()).getName());
  }

  // @Test
  // public void delete_deleteAllTagAndRecipeAssociations() {
  //   Recipe myRecipe = new Recipe("Wrap", "Roll a wrap with stuff in it");
  //   myRecipe.save();
  //   Tag myTag = new Tag("Stuff");
  //   myTag.save();
  //   myRecipe.addTag(myTag);
  //   myTag.delete();
  //   assertEquals(0, Tag.all().size());
  //   assertEquals(0, myRecipe.getTags().size());
  // }

}
