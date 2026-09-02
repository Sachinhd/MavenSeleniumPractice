# Maven Selenium Practice

A Selenium WebDriver test automation framework built in Java, using the **Page Object Model (POM)**, integrated with **TestNG**, **Maven**, and a **Jenkins CI/CD pipeline**.

This project automates login functionality testing for [saucedemo.com](https://www.saucedemo.com/), with dual test reporting (Extent Reports + Allure), automatic screenshot capture on failure, and a working continuous integration pipeline.

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
| **Allure Report** | Rich, interactive test reporting integrated directly into the Jenkins UI, with history/trend graphs across builds |
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
│   ├── ExtentReport.html         # Generated after every run
│   └── allure-results/           # Raw result files consumed by the Allure Jenkins plugin
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

### View the reports
After the run completes:
- **Extent Report:** `test-output/ExtentReport.html`
- **Allure Report** (requires the Allure commandline tool locally):
  ```bash
  allure serve test-output/allure-results
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
    ├── ✅ All tests pass  → Extent Report generated (HTML Publisher plugin)
    │                        → Allure Report generated (Allure Jenkins plugin)
    │                        → Report copied to a shared location (simple CD step)
    │
    └── ❌ Any test fails  → Screenshot automatically captured and embedded
                              in the Extent report → deployment step skipped
                              → Failure also visible in Allure's trend/history view
```

### What Jenkins does automatically on every build:
1. Pulls the latest code from GitHub
2. Runs the full regression suite via Maven/TestNG
3. Generates **two** reports:
   - **Extent Report** — detailed HTML report with pass/fail breakdown and embedded failure screenshots
   - **Allure Report** — interactive report with historical trend graphs across builds, step-by-step execution timelines, and categorized failures
4. Publishes both reports as clickable links on the Jenkins build page
5. Only "deploys" (copies/publishes) the report when the full suite passes — failing builds are never shipped

### Why both Extent and Allure?
- **Extent Reports** — lightweight, self-contained single HTML file with embedded screenshots; easy to share as a standalone file
- **Allure Report** — better for tracking trends across multiple builds over time (pass/fail history, flaky test detection, execution duration graphs) directly inside Jenkins

---

## Key Engineering Challenges Solved

| Issue | Root Cause | Fix |
|---|---|---|
| Jenkins couldn't clone from GitHub | DNS/network resolution failing under the SYSTEM account | Configured DNS/proxy at the system level, not just the user account |
| `NoSuchDriverException` for ChromeDriver | Selenium Manager's driver auto-download was failing (network + very new Chrome version) | Integrated **WebDriverManager** to reliably auto-match ChromeDriver to the installed Chrome version |
| `NullPointerException` on `driver` inside Page Objects | Variable shadowing — `WebDriver driver = new ChromeDriver();` inside `setUp()` created a new local variable instead of assigning the class field | Removed the type declaration so the assignment correctly targets the class-level `driver` field |
| No visibility into *why* a test failed | Plain TestNG XML output only | Added ExtentReports + automatic screenshot capture on failure via a custom `TestListener`, plus Allure for build-over-build trend visibility |

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
🔗 [GitHub](https://github.com/Sachinhd)
