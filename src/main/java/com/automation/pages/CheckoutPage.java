package com.automation.pages;

import org.openqa.selenium.By;
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
        // Use native click on the submit input — triggers proper form submission
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='continue']")));
        btn.click();
        // Wait for either successful navigation OR a validation error to appear
        wait.until(d -> {
            String url = driver.getCurrentUrl();
            return url.contains("checkout-step-two") ||
                    !driver.findElements(By.cssSelector("[data-test='error']")).isEmpty();
        });
    }

    public void clickFinish() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='finish']")));
        jsClick(btn);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }

    public void clickCancel() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='cancel']")));
        jsClick(btn);
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(By.cssSelector("[data-test='error']")).isEmpty();
    }

    public String getErrorMessage() {
        WebElement err = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']")));
        return err.getText();
    }

    public String getOrderCompleteText() {
        waitForVisible(orderCompleteHeader);
        return orderCompleteHeader.getText();
    }

    public String getTotalLabel() {
        waitForVisible(totalLabel);
        return totalLabel.getText();
    }
}
