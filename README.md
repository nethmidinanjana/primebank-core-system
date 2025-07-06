# PrimeBank Core System

A secure and scalable core banking system built with **Java EE**, **Enterprise JavaBeans (EJB)**, **Hibernate**, and **EJB Timer Services**, designed for automating critical banking operations such as scheduled fund transfers, interest calculations, and periodic reporting.

---

## 🚀 Features

- ✅ Customer and Account Management
- 🔁 Scheduled Fund Transfers via EJB Timer Services
- 💰 Automated Daily Interest Calculation
- 📊 Transaction Logging and Reporting
- 🔐 Role-based Security using Java EE annotations
- 🔄 Reliable Transaction Management with JTA
- 📅 Timer-based execution for daily operations

---

## 🧰 Tech Stack

| Layer       | Technology           |
|-------------|----------------------|
| Backend     | Java EE (Jakarta EE) |
| Business    | EJB (Stateless, Singleton, Timers) |
| Persistence | Hibernate + JPA      |
| Scheduler   | EJB Timer Services   |
| Database    | MySQL                |
| REST API    | JAX-RS (Jakarta RESTful WS) |
| Security    | @RolesAllowed, Java EE Security |
| Build Tool  | Maven                |
| Testing     | JUnit + Postman      |
| Server      | WildFly / GlassFish  |

---

## ⚙️ How to Run

1. Clone the Repository

```bash

git clone https://github.com/nethmidinanjana/primebank-core-system.git
cd primebank-core-system

```

2. Configure MySQL Database

- Create a database named primebank_db
- Update persistence.xml with your DB credentials
  

3. Build the Project

```bash

mvn clean install

```

4. Deploy on WildFly or GlassFish

- Deploy the .war file to your application server

- Ensure database and JDBC resources are configured

## 📄 License

This project is part of an academic assignment and is not intended for commercial use.

## 👤 Author

[Nethmi Dinanjana](https://github.com/nethmidinanjana) <br/>
Undergraduate Student – Software Engineering
