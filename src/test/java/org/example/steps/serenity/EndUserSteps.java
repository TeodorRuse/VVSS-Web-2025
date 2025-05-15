package org.example.steps.serenity;

import com.google.inject.Inject;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;
import org.example.pages.DictionaryPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps extends ScenarioSteps {

    DictionaryPage dictionaryPage;

    @Step
    public void is_the_home_page() {
        dictionaryPage.open();  // se duce la pagina de bază
    }

    @Step
    public void enters(String keyword) {
        dictionaryPage.enter_keywords(keyword);
    }

    @Step
    public void starts_search() {
        dictionaryPage.lookup_terms();
    }

    @Step
    public void should_see_definition(String definition) {
        assertThat(dictionaryPage.getDefinitions(), hasItem(containsString(definition)));
    }

    @Step
    public void looks_for(String term) {
        enters(term);
        starts_search();
    }

    // ✅ Login steps
    @Step
    public void is_the_login_page() {
        dictionaryPage.open_login_page();
    }

    @Step
    public void enter_login_credentials(String username, String password) {
        dictionaryPage.type_username(username);
        dictionaryPage.type_password(password);
    }

    @Step
    public void submit_login() {
        dictionaryPage.click_login_button();
    }

    @Step
    public void should_see_logged_in_user(String username) {
        dictionaryPage.verify_user_logged_in(username);
    }

    @Step
    public void should_see_login_error() {
        dictionaryPage.verify_login_error_displayed();
    }
}
