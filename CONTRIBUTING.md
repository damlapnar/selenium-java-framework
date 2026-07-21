# Contributing to selenium-java-framework

Thank you for your interest in contributing!

## Prerequisites

- Java 17+
- Maven 3.8+

## Getting Started

```bash
git clone https://github.com/damlapnar/selenium-java-framework.git
cd selenium-java-framework
mvn test -Dheadless=true
```

## Running Tests

```bash
mvn test
mvn test -Dheadless=true
```

## Guidelines

- Follow the Page Object Model pattern for all new page interactions: locators and clicks belong in a `pages/` class, assertions belong in the test
- Use `jsClick()` (from `BasePage`) instead of a native `WebElement.click()` for saucedemo's React-controlled buttons — native clicks don't reliably register in headless Chrome
- Extend `AuthenticatedBaseTest` or `CartBaseTest` instead of duplicating login/cart-seeding `@BeforeMethod`s
- Put shared users, product names, and shipping data in `TestData` rather than inline string literals
- Register every new test class in `src/test/resources/testng.xml`
- Ensure tests are independent and idempotent

## Pull Request Process

1. Fork the repository
2. Create a feature branch (`git checkout -b feat/your-feature`)
3. Commit with a descriptive message
4. Open a Pull Request against `main`
5. Ensure CI passes before requesting review

## Reporting Bugs

Open a GitHub Issue with:
- Steps to reproduce
- Expected vs actual behavior
- Maven/Java version and OS
