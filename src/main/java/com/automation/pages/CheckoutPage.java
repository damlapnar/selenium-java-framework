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

    @FindBy(className = "complete-header")
    private WebElement orderConfirmation;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    public void fillShippingInfo(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
    }

    // "continue" is a reserved word in Java, hence the longer method name.
    public void continueToOverview() {
        click(continueButton);
    }

    public void finish() {
        click(finishButton);
    }

    public void cancel() {
        click(cancelButton);
    }

    public String getOrderConfirmationText() {
        return getText(orderConfirmation);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    private double parseCurrency(WebElement element) {
        return Double.parseDouble(getText(element).replaceAll("[^0-9.]", ""));
    }

    public double getSubtotal() {
        return parseCurrency(subtotalLabel);
    }

    public double getTax() {
        return parseCurrency(taxLabel);
    }

    public double getTotal() {
        return parseCurrency(totalLabel);
    }
}
