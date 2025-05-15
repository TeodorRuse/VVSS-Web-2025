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
//@DefaultUrl("https://en.wiktionary.org/wiki/Wiktionary:Main_Page")

public class DictionaryPage extends PageObject {

    // Login
    @FindBy(id = "wpName1")
    WebElementFacade usernameField;

    @FindBy(id = "wpPassword1")
    WebElementFacade passwordField;

    @FindBy(id = "wpLoginAttempt")
    WebElementFacade loginButton;

    public void goToLoginPage() {
        getDriver().get("https://en.wiktionary.org/w/index.php?title=Special:UserLogin");
    }

    public void loginAs(String username, String password) {
        usernameField.waitUntilVisible().type(username);
        passwordField.waitUntilVisible().type(password);
        loginButton.click();
    }

    // Search (body form)
    @FindBy(css = "form[name^='bodySearch'] input[name='search']")
    private WebElementFacade searchTerms;

    @FindBy(css = "form[name^='bodySearch'] input[type='submit']")
    private WebElementFacade lookupButton;

    public void enter_keywords(String keyword) {
        searchTerms.waitUntilVisible().clear();
        searchTerms.type(keyword);
    }

    public void lookup_terms() {
        lookupButton.waitUntilClickable().click();
    }

    // Add to Watchlist
    @FindBy(id = "ca-watch")
    WebElementFacade watchButtonContainer;

    public void addCurrentPageToWatchlist() {
        WebElementFacade watchLink = watchButtonContainer.find(By.tagName("a"));
        watchLink.waitUntilClickable().click();
    }

    // Profile link
    @FindBy(css = "#pt-userpage-2 a")
    private WebElementFacade userProfileLink;

    public void openProfilePage() {
        userProfileLink.waitUntilClickable().click();
    }

    // Try exact match
    @FindBy(css = "input[type='submit'][value='Try exact match']")
    private WebElementFacade exactMatchButton;

    public void clickExactMatch() {
        exactMatchButton.waitUntilClickable().click();
    }

    public boolean isPageWatched() {
        return getDriver().getPageSource().contains("Unwatch") || getDriver().getCurrentUrl().contains("action=unwatch");
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public String getPageSource() {
        return getDriver().getPageSource(); // ✔ CORECT
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