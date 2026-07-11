package com.automation.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        jsClick(continueButton);
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        jsClick(finishButton);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        jsClick(cancelButton);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        waitForVisible(errorMessage);
        return errorMessage.getText();
    }

    public String getOrderCompleteText() {
        waitForVisible(orderCompleteHeader);
        return orderCompleteHeader.getText();
    }

    public String getTotalLabel() {
        waitForVisible(totalLabel);
        return totalLabel.getText();
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
