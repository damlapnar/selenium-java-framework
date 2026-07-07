package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    @FindBy(css = "[data-test='firstName']")
    private WebElement firstNameInput;

    @FindBy(css = "[data-test='lastName']")
    private WebElement lastNameInput;

    @FindBy(css = "[data-test='postalCode']")
    private WebElement postalCodeInput;

    @FindBy(css = "[data-test='continue']")
    private WebElement continueButton;

    @FindBy(css = "[data-test='finish']")
    private WebElement finishButton;

    @FindBy(css = "[data-test='cancel']")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = ".complete-header")
    private WebElement orderCompleteHeader;

    @FindBy(css = ".summary_total_label")
    private WebElement totalLabel;

    public CheckoutPage() { super(); }

    public void fillShippingInfo(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
    }

    public void clickContinue() { click(continueButton); }
    public void clickFinish()   { click(finishButton); }
    public void clickCancel()   { click(cancelButton); }

    public String getErrorMessage() { return getText(errorMessage); }

    public boolean isErrorDisplayed() { return isDisplayed(errorMessage); }

    public String getOrderCompleteText() { return getText(orderCompleteHeader); }

    public String getTotalLabel() { return getText(totalLabel); }
}
