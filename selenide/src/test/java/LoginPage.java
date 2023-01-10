import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class LoginPage {

  private LoginPage() {
  }

  public static LoginPage getInstance() {
    return new LoginPage();
  }

  public static final SelenideElement USERNAME = $(By.cssSelector("input.username.input-md"));
  public static final SelenideElement PASSWORD = $(By.cssSelector("input.password.input-md"));

}
