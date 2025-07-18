# 📦 AEO Automated Testing Project

[![CI Status](https://img.shields.io/badge/CI-pending-lightgrey?style=flat-square&logo=github)](https://github.com/Alex-V0l/AEOTests/actions)
[![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=java)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-7.x-02303A?style=flat-square&logo=gradle)](https://gradle.org/)
[![Allure](https://img.shields.io/badge/Allure%20Report-Enabled-purple?style=flat-square&logo=allure)](https://docs.qameta.io/allure/)

This is a **graduation project** completed as part of the advanced Java automation testing course by Gennady Chursov.  
The project focuses on testing the e-commerce website [ae.com](https://www.ae.com/us/en/) which offers a wide range of clothing and accessories.

The test suite is structured and developed in stages:
- ✅ API test development (RESTful endpoints)
- ✅ UI test automation using Selenium
- ✅ ✅ CI/CD integration via GitHub Actions and Allure Reports

---

## 📚 Contents

+ [💻 Tech Stack](#-tech-stack)
+ [🧪 Covered Test Scenarios](#-covered-test-scenarios)
+ [🚀 How to Run the Project](#-how-to-run-the-project)
   - [🔸 Run Locally (via IntelliJ or Terminal)](#-run-locally-via-intellij-or-terminal)
   - [🔹 Run via GitHub Actions](#-run-via-github-actions)
+ [📊 View Allure Report](#-view-allure-report)
   - [📍 Locally](#-locally)
   - [📈 View Allure Report in GitHub Actions](#-view-allure-report-in-github-actions)

## 💻 Tech Stack

<p align="center"> 
<a href="https://www.oracle.com/java/" target="_blank"> <img src="images/logo/Java.svg" alt="Java" width="60" height="60"/> </a> 
<a href="https://junit.org/junit5/" target="_blank"> <img src="images/logo/JUnit5.svg" alt="JUnit 5" width="60" height="60"/> </a> 
<a href="https://rest-assured.io/" target="_blank"> <img src="images/logo/RestAssured.svg" alt="RestAssured" width="60" height="60"/> </a> 
<a href="https://www.selenium.dev/" target="_blank"> <img src="images/logo/Selenium.png" alt="Selenium" width="60" height="60"/> </a> 
<a href="https://gradle.org/" target="_blank"> <img src="images/logo/Gradle.svg" alt="Gradle" width="60" height="60"/> </a> 
<a href="https://www.jetbrains.com/idea/" target="_blank"> <img src="images/logo/IntelliJ_IDEA.svg" alt="IntelliJ IDEA" width="60" height="60"/> </a> 
<a href="https://github.com/" target="_blank"> <img src="images/logo/GitHub.svg" alt="GitHub" width="60" height="60"/> </a> 
<a href="https://docs.github.com/en/actions" target="_blank"> <img src="images/logo/GithubActions.svg" alt="GitHub Actions" width="60" height="60"/> </a> 
<a href="https://docs.qameta.io/allure/" target="_blank"> <img src="images/logo/Allure_Report.svg" alt="Allure Report" width="60" height="60"/> </a> 
</p>

---

## 🧪 Covered Test Scenarios

### 🔹 Test Types:
- **Smoke tests**
- **Negative scenarios**
- **Extended (end-to-end) coverage**

---

### 🔧 API Tests:

| Area         | Description |
|--------------|-------------|
| **Authorization** | Guest token retrieval using `POST` request |
| **Search**        | Product search with `GET` |
| **Cart**          | Full cart functionality using `POST`, `GET`, `PATCH`, and `DELETE`: add, view, update, and remove products |

---

### 🖥️ UI Tests:

| Module | Description |
|--------|-------------|
| **Account Creation** | Navigate to sign-up page, fill out forms, interact with dropdowns and checkboxes |
| **Login** | Open modal, fill login fields |
| **Search** | Interact with search modal, validate suggestions dropdown, and check navigation to results page |
| **Category Page** | Test filters (checkboxes, radio buttons), navigate to product page |
| **Product Page** | Change color via radio buttons, choose size from dropdown, adjust quantity, add to cart |
| **Cart Functionality** | Add via search/category, validate product info, update quantity, remove product, proceed to checkout, update shipping method |

---

## 🚀 How to Run the Project

### ⚠️ Note: Anti-bot protection on the tested website

Since this project tests a **real website**, it uses **anti-bot protection (e.g., Akamai)**, which can block automated test traffic. As a result, **some tests consistently fail**, even when they are implemented correctly. These tests are explicitly marked with the tag `@Tag("defect")`.

Therefore, there are **two execution modes**:

### 🔸 Run Locally (via IntelliJ or Terminal)

1. Open the **Gradle panel** in IntelliJ
2. Execute the following task:

#### 💥 Run all tests:

   ```bash
      gradle clean test -Denv=prod
   ```

#### ✅ Run only stable tests (excluding @Tag("defect")):

   ```bash
      gradle clean testWithoutDefects -Denv=prod
   ```

---

### 🔹 Run via GitHub Actions

To run 💥 all tests using GitHub Actions:

1. Go to the **[Actions](https://github.com/Alex-V0l/AEOTests/actions)** tab of the repository.
2. Select the workflow named: `AEOTests CI`
3. Click **Run workflow** (top-right), then confirm.
4. Wait for the job to complete and check the status.

To run ✅ only stable tests using GitHub Actions:

1. Go to the **[Actions](https://github.com/Alex-V0l/AEOTests/actions)** tab of the repository.
2. Select the workflow named: `AEOTests CI (no defects)`
3. Click **Run workflow** (top-right), then confirm.
4. Wait for the job to complete and check the status.
---

## 📊 View Allure Report

### 📍 Locally

To view the report locally, after the execution of tests locally has been completed:

```bash
  cd ./build/
  allure serve
```

---

## 📈 View Allure Report in GitHub Actions

After the CI build completes:

1. Go to the **[Actions](https://github.com/Alex-V0l/AEOTests/actions)** tab.
2. Open the workflow run for `AEOTests CI`.
3. Scroll to the `pages-build-deployment` job.
4. Click on the generated **Deploy link** (you’ll find it in the summary of the job).
5. The Allure Report will open in a new browser tab.