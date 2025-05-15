package org.example.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@DefaultUrl("http://en.wiktionary.org/wiki/Wiktionary")
public class DictionaryPage extends PageObject {

    @FindBy(name="search")
    private WebElementFacade searchTerms;

    @FindBy(name="go")
    private WebElementFacade lookupButton;

    public void enter_keywords(String keyword) {
        searchTerms.waitUntilVisible().type(keyword);
    }

    public void lookup_terms() {
        lookupButton.click();
    }

    public List<String> getDefinitions() {
        WebElementFacade definitionList = find(By.tagName("ol"));
        return definitionList.findElements(By.tagName("li")).stream()
                .map( element -> element.getText() )
                .collect(Collectors.toList());
    }


    //Login testing valid-nonvalid
    // ✅ corect pentru Wikipedia
    public void type_username(String username) {
        $("#wpName1").type(username);
    }

    public void type_password(String password) {
        $("#wpPassword1").type(password);
    }

    public void click_login_button() {
        $("#wpLoginAttempt").click();
    }

    public void verify_user_logged_in(String username) {
        try {
            WebElementFacade userSpan = withTimeoutOf(15, TimeUnit.SECONDS)
                    .find(By.cssSelector("li[id^='pt-userpage'] a span"));

            userSpan.waitUntilVisible();
            String actualUsername = userSpan.getText().trim();

            if (!actualUsername.equalsIgnoreCase(username)) {
                throw new AssertionError("Expected username '" + username + "', but found '" + actualUsername + "'");
            }
        } catch (Exception e) {
            throw new AssertionError("User not logged in or username not found. Actual error: " + e.getMessage());
        }
    }

    public void verify_login_error_displayed() {
        // Selectorul actual corect conform mesajului tău
        WebElementFacade errorMessage = $(".cdx-message__content");
        errorMessage.waitUntilVisible(); // așteaptă să apară
        errorMessage.shouldContainText("Incorrect username or password"); // verifică conținutul
    }

    public void open_login_page() {
        getDriver().get("https://en.wiktionary.org/w/index.php?title=Special:UserLogin");
    }
}