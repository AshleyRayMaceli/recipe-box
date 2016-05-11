import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.sql2o.*;
import org.junit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Recipe Box");
  }

  @Test
  public void allRecipesPageIsDisplayed() {
    goTo("http://localhost:4567/");
    click("a", withText("View All Recipes"));
    assertThat(pageSource()).contains("All Recipes");
  }

  @Test
  public void recipeFormisDisplayed() {
    goTo("http://localhost:4567/");
    click("a", withText("Add Recipe"));
    assertThat(pageSource()).contains("Add New Recipe");
  }


  // @Test
  // public void recipeIsCreatedTest() {
  //   Recipe testRecipe = new Recipe("Cucumber Salad");
  //   testRecipe.save();
  //   String url = String.format("http://localhost:4567/recipes/%d", testRecipe.getId());
  //   goTo(url);
  //   assertThat(pageSource()).contains("Cucumber Salad");
  // }

}
