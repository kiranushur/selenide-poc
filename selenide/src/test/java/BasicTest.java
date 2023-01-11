import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.SelenideElement;
import com.google.common.util.concurrent.Uninterruptibles;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BasicTest {

  public static final String url = "https://qa1.ushur.in/mob3.0/auth/";
  SelenideElement userName;
  SelenideElement password;
  SelenideElement loginButton;
  SelenideElement dashboardUshurLogo;
  @BeforeClass
  public void beforeClass(){
    open("https://qa1.ushur.in/mob3.0/auth/");
  }

  @BeforeMethod(onlyForGroups = "Login")
  public void beforeMethod(){
    LoginPage.getInstance().USERNAME.setValue("test_admin@ushurdummy.me");
    LoginPage.getInstance().PASSWORD.setValue("Ushur@123");
    $(By.linkText("Log In")).click();
    $(By.cssSelector(".navbar-brand .nav-ushur-logo")).shouldBe(Condition.visible, Duration.ofSeconds(30));
  }

  @AfterMethod(onlyForGroups = "Login")
  public void afterMethod(){
    $(By.linkText("Logout")).click();
  }

  @BeforeMethod(onlyForGroups = "basic")
  public void setupForBasic(){
    userName = $(By.cssSelector("input.username.input-md"));
    password = $(By.cssSelector("input.password.input-md"));
    loginButton = $(By.linkText("Log In"));
    dashboardUshurLogo = $(By.cssSelector(".navbar-brand .nav-ushur-logo"));
  }

  @Test (groups = "basic", priority = 3)
  public void loginTest() {
    $(By.linkText("Logout")).click();
    open(url);
    userName.setValue("test_admin@ushurdummy.me");
    password.setValue("Ushur@123");
    loginButton.click();
    dashboardUshurLogo.shouldBe(Condition.visible,
        Duration.ofSeconds(30));
  }

  @Test (priority = 3)
  public void exampleLoginTest() {
    $(By.cssSelector("input.username.input-md")).setValue("test_admin@ushurdummy.me");
    $(By.cssSelector("input.password.input-md")).setValue("Ushur@123");
    $(By.linkText("Log In")).click();
    $(By.cssSelector(".navbar-brand .nav-ushur-logo")).shouldBe(Condition.visible, Duration.ofSeconds(30));
  }

  @Test (priority = 3)
  public void validationTest() {
    $(By.linkText("Logout")).click();
    $(By.cssSelector("div.subtitle")).shouldHave(Condition.text("Automation That Understands"));
    $(By.cssSelector("div.subtitle")).shouldNotHave(Condition.text("Ushur"));
    $(By.cssSelector("input.username.input-md")).shouldBe(Condition.exist);
    $(By.cssSelector("input.username.input-mX")).shouldNotBe(Condition.exist);
    $(By.linkText("Log In")).shouldBe(Condition.visible, Condition.enabled);
  }

  @Test (priority = 0, groups = "Login")
  public void tableOperationsTest() {
    $(By.cssSelector(".form-control.app-context")).shouldBe(Condition.visible, Duration.ofSeconds(30));
    $(By.cssSelector(".form-control.app-context")).selectOption("TestAutomation");
    $(By.id("l-meta-data")).click();
    ElementsCollection tr = $$(By.tagName("tr"));
    System.out.println("Table row size -> " + tr.size());
    tr.shouldHave(CollectionCondition.sizeGreaterThan(1));
  }

  @Test (priority = 0, groups = "Login")
  public void fileUploadOperationsTest() {
    $(By.cssSelector(".btn-create-campaign-box .btn-ushur-filled")).shouldBe(Condition.visible, Duration.ofSeconds(30)).click();
    $(By.id("file-import-ushur")).uploadFile(new File("src/test/resources/dataex.ufo"));
    String text = $(By.cssSelector(".sweet-alert.showSweetAlert")).shouldBe(
        Condition.visible, Duration.ofSeconds(30)).getText();
    if(text.contains("Import Failed")){
      $(By.cssSelector(".ushur-import-error-btn")).pressEnter();
    }
    System.out.println(text);
    Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
  }

  @Test (priority = 0, groups = "Login")
  public void fileDownloadOperationsTest() {
//    Configuration.downloadsFolder = "src/test/resources/dynamicdata/";
//    Configuration.proxyHost = "32.453.324.435";
//    Configuration.proxyPort = 8080;
//    Configuration.proxyEnabled = true;
//    Configuration.fileDownload = FileDownloadMode.PROXY;
//
    $(By.cssSelector(".form-control.app-context")).shouldBe(Condition.visible, Duration.ofSeconds(30));
    $(By.cssSelector(".form-control.app-context")).selectOption("TestAutomation");
    $(By.id("l-meta-data")).click();

//    $(By.cssSelector(".meta-data-export-data")).download();   //  "Cannot download file: proxy server is not started"
    $(By.cssSelector(".meta-data-export-data")).click();
    Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
  }

}
