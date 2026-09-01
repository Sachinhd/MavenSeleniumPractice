# Maven Selenium Practice

A Selenium WebDriver test automation framework built in Java, using the **Page Object Model (POM)**, integrated with **TestNG**, **Maven**, and a **Jenkins CI/CD pipeline**.

This project automates login functionality testing for [saucedemo.com](https://www.saucedemo.com/), with automatic reporting, screenshot capture on failure, and a working continuous integration pipeline.

---

## Tech Stack

| Tool | Purpose |
|---|---|
| **Java** | Programming language |
| **Selenium WebDriver** | Browser automation |
| **TestNG** | Test execution framework, assertions, data providers |
| **Maven** | Dependency management and build tool |
| **WebDriverManager** | Automatically downloads/matches the correct ChromeDriver version |
| **ExtentReports** | HTML test reporting with pass/fail status and embedded screenshots |
| **Jenkins** | CI/CD — automated build, test execution, and report publishing |
| **Git / GitHub** | Version control and source code hosting |

---

## Project Structure

```
MavenSeleniumPractice/
├── src/test/java/com/sachin/
│   ├── base/
│   │   └── BaseClass.java        # WebDriver setup/teardown (shared by all tests)
│   ├── pages/
│   │   ├── LoginPage.java        # Page Object for the login page
│   │   └── HomePage.java         # Page Object for the home/inventory page
│   ├── tests/
│   │   └── LoginTest.java        # Test cases (data-driven login scenarios)
│   └── utilities/
│       ├── ConfigReader.java     # Reads config.properties
│       ├── ExcelReader.java      # Reads test data from Excel
│       ├── ExtentReportManager.java  # Initializes the Extent report
│       └── TestListener.java     # TestNG listener — logs results + captures screenshots on failure
├── src/test/resources/
│   ├── config.properties
│   ├── testdata/                 # Excel-based test data
│   └── testng.xml                # TestNG suite configuration
├── test-output/
│   └── ExtentReport.html         # Generated after every run
└── pom.xml
```

---

## Design Pattern: Page Object Model (POM)

Each web page is represented as its own Java class, containing:
- **Locators** for that page's elements
- **Methods** that perform actions on that page (e.g., `enterUsername()`, `clickLogin()`)

Test classes call these page methods instead of interacting with locators directly. This keeps tests readable and means UI changes only require updating one page class, not every test.

---

## How to Run Locally

**Prerequisites:** Java JDK 17+, Maven, Google Chrome installed.

```bash
git clone https://github.com/Sachinhd/MavenSeleniumPractice.git
cd MavenSeleniumPractice
mvn clean test
```

ChromeDriver is downloaded and matched automatically at runtime via **WebDriverManager** — no manual driver setup needed.

### View the report
After the run completes, open:
```
test-output/ExtentReport.html
```

---

## CI/CD Pipeline (Jenkins)

This project is connected to a Jenkins pipeline that automates the full test lifecycle:

```
GitHub push
    │
    ▼
Jenkins clones the latest code
    │
    ▼
Maven builds the project (mvn clean test)
    │
    ▼
Selenium + TestNG execute the test suite
    │
    ├── ✅ All tests pass  → Extent Report generated
    │                        → Report published inside Jenkins (HTML Publisher)
    │                        → Report copied to a shared location (simple CD step)
    │
    └── ❌ Any test fails  → Screenshot automatically captured and embedded
                              in the report → deployment step skipped
```

### What Jenkins does automatically on every build:
1. Pulls the latest code from GitHub
2. Runs the full regression suite via Maven/TestNG
3. Generates a detailed HTML report (ExtentReports) with pass/fail breakdown
4. Attaches a screenshot to any failed test step
5. Publishes the report as a clickable link on the Jenkins build page
6. Only "deploys" (copies/publishes) the report when the full suite passes — failing builds are never shipped

---

## Key Engineering Challenges Solved

| Issue | Root Cause | Fix |
|---|---|---|
| Jenkins couldn't clone from GitHub | DNS/network resolution failing under the SYSTEM account | Configured DNS/proxy at the system level, not just the user account |
| `NoSuchDriverException` for ChromeDriver | Selenium Manager's driver auto-download was failing (network + very new Chrome version) | Integrated **WebDriverManager** to reliably auto-match ChromeDriver to the installed Chrome version |
| `NullPointerException` on `driver` inside Page Objects | Variable shadowing — `WebDriver driver = new ChromeDriver();` inside `setUp()` created a new local variable instead of assigning the class field | Removed the type declaration so the assignment correctly targets the class-level `driver` field |
| No visibility into *why* a test failed | Plain TestNG XML output only | Added ExtentReports + automatic screenshot capture on failure via a custom `TestListener` |

---

## Future Improvements

- [ ] Automatic build triggering via GitHub Webhook (currently manual/Poll SCM)
- [ ] Parallel test execution across browsers/threads
- [ ] Email/Slack notification on build failure
- [ ] Cross-browser test matrix (Chrome, Firefox, Edge)
- [ ] Dockerized Selenium Grid for consistent CI environments

---

## Author

**Sachin Dawkar**
📧 sachinhd.sachin.dawkar@gmail.com
