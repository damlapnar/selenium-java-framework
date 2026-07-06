# Contributing to selenium-java-framework

Thank you for your interest in contributing!

## Prerequisites

- Java 17+
- Maven 3.8+

## Getting Started

```bash
git clone https://github.com/damlapinar/selenium-java-framework.git
cd selenium-java-framework
mvn test -Dheadless=true
```

## Running Tests

```bash
mvn test
mvn test -Dheadless=true
```

## Guidelines

- Follow the Page Object Model pattern for all new page interactions
- Keep step definitions thin — business logic belongs in page objects
- Add `@smoke` tag to critical path tests
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
