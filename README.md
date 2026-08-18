# Online Quiz Application

A robust web-based **Online Quiz Application** developed using **Java, Spring Boot, Spring Security, Spring Data JPA, MySQL, HTML, CSS, JavaScript, and Thymeleaf**.

The application allows users to register, securely log in, participate in online quizzes, submit answers, receive instant scores, and track their previous assessment results.

---

## 🚀 Features

### 🔐 User Authentication

* User registration and login.
* Secure authentication using Spring Security.
* Session-based user management.
* Secure logout functionality.
* Protected application pages.

### 📝 Interactive Quiz

* Dynamic question loading from MySQL.
* Multiple-choice questions.
* User-friendly quiz interface.
* Answer selection and submission.
* Dynamic rendering using Thymeleaf.

### ⚡ Instant Scoring

* Automatic answer evaluation.
* Immediate score calculation.
* Displays obtained marks and total questions.
* No manual evaluation required.

### 📊 Result Tracking

* Stores quiz results in MySQL.
* Users can view previous scores.
* Tracks assessment performance.
* Displays result information after submission.

### 🗄️ Database Management

* Relational database design using MySQL.
* JPA/Hibernate for database operations.
* Entity-based data management.
* Persistent storage for users, questions, and results.

---

# 🛠️ Technology Stack

| Layer           | Technologies                      |
| --------------- | --------------------------------- |
| Frontend        | HTML5, CSS3, JavaScript           |
| Template Engine | Thymeleaf                         |
| Backend         | Java, Spring Boot                 |
| Security        | Spring Security                   |
| ORM             | Spring Data JPA, Hibernate        |
| Database        | MySQL                             |
| Build Tool      | Maven                             |
| Version Control | Git, GitHub                       |
| IDE             | IntelliJ IDEA / Eclipse / VS Code |

---

# 🏗️ Project Architecture

The application follows a layered **Spring Boot MVC architecture**.

```text
                    Online Quiz Application
                              |
                +-------------+-------------+
                |                           |
             Frontend                    Backend
                |                           |
        HTML / CSS / JS               Spring Boot
        Thymeleaf                         |
                                    +------+------+
                                    |             |
                               Controller      Security
                                    |
                                 Service
                                    |
                               Repository
                                    |
                              Spring Data JPA
                                    |
                                 MySQL
```

---

# 🔄 Application Workflow

```text
User Registration
       ↓
User Login
       ↓
Authentication
       ↓
Quiz Page
       ↓
Load Questions
       ↓
Select Answers
       ↓
Submit Quiz
       ↓
Evaluate Answers
       ↓
Calculate Score
       ↓
Store Result
       ↓
Display Result
       ↓
View Previous Results
```

---

# 📂 Project Structure

```text
Online-Quiz-Application/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── quiz/
│   │   │           ├── controller/
│   │   │           ├── service/
│   │   │           ├── repository/
│   │   │           ├── entity/
│   │   │           ├── config/
│   │   │           └── OnlineQuizApplication.java
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── quiz.html
│   │       │   └── result.html
│   │       │
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       │
│   │       └── application.properties
│   │
├── pom.xml
└── README.md
```

---

# 🗃️ Database Design

The application uses **MySQL** as the relational database.

### User Table

Stores registered user information.

```text
User
-------------------------
id
name
email
password
role
```

### Question Table

Stores quiz questions and their options.

```text
Question
-------------------------
id
question
option_a
option_b
option_c
option_d
correct_answer
```

### Result Table

Stores quiz performance.

```text
Result
-------------------------
id
user_id
score
total_questions
attempt_date
```

### Relationship

```text
User
 |
 | 1
 |
 | *
Result
```

One user can have multiple quiz results.

---

# 🔒 Security

Spring Security is implemented to protect application resources and manage authentication.

Security features include:

* Login authentication.
* Password validation.
* Session management.
* Logout functionality.
* Protected routes.
* Role-based access support.
* Unauthorized access prevention.

---

# ⚙️ How the Quiz Works

1. The user creates an account.
2. The user logs into the application.
3. Spring Security authenticates the user.
4. The application loads quiz questions from MySQL.
5. The user selects answers.
6. The user submits the quiz.
7. The backend compares submitted answers with correct answers.
8. The application calculates the score.
9. The result is stored in MySQL.
10. The final score is displayed immediately.
11. The user can access previous results.

---

# 🚀 Getting Started

## Prerequisites

Make sure the following software is installed:

* Java 17 or later
* Maven
* MySQL
* Git
* IntelliJ IDEA / Eclipse / VS Code

---

## 1. Clone the Repository

```bash
git clone https://github.com/Bharathmadhu013/Online-Quiz-Application.git
```

Navigate to the project directory:

```bash
cd Online-Quiz-Application
```

---

## 2. Create MySQL Database

Open MySQL and create the database:

```sql
CREATE DATABASE online_quiz;
```

Verify the database:

```sql
SHOW DATABASES;
```

---

## 3. Configure Database

Open:

```text
src/main/resources/application.properties
```

Configure your MySQL connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/online_quiz
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.thymeleaf.cache=false
```

Replace:

```text
YOUR_PASSWORD
```

with your local MySQL password.

---

## 4. Build the Application

Using Maven:

```bash
mvn clean install
```

---

## 5. Run the Application

```bash
mvn spring-boot:run
```

Or run the main Spring Boot class:

```text
OnlineQuizApplication.java
```

---

## 🌐 Access the Application

After starting the application, open:

```text
http://localhost:8080
```

---

# 📌 Main Application Modules

| Module       | Description                          |
| ------------ | ------------------------------------ |
| Registration | Creates new user accounts            |
| Login        | Authenticates users                  |
| Quiz         | Displays questions and options       |
| Evaluation   | Checks submitted answers             |
| Scoring      | Calculates final score               |
| Results      | Displays assessment performance      |
| Database     | Stores users, questions, and results |
| Security     | Protects authenticated resources     |

---

# 💻 Core Backend Components

### Controller

Handles incoming HTTP requests and returns appropriate views.

```text
UserController
QuizController
ResultController
```

### Service

Contains application business logic.

```text
UserService
QuizService
ResultService
```

### Repository

Handles database communication through Spring Data JPA.

```text
UserRepository
QuestionRepository
ResultRepository
```

### Entity

Represents database tables as Java objects.

```text
User
Question
Result
```

---

# 📊 Example Scoring Logic

If a quiz contains 10 questions and the user answers 8 correctly:

```text
Total Questions : 10
Correct Answers : 8
Incorrect Answers: 2
Score            : 8/10
Percentage       : 80%
```

The result is automatically calculated when the user submits the quiz.

---

# 🧪 Testing

The application can be tested by verifying:

* User registration.
* Valid login.
* Invalid login.
* Logout.
* Question loading.
* Answer selection.
* Quiz submission.
* Score calculation.
* Result storage.
* Previous result retrieval.
* Unauthorized page access.

---

# 🔮 Future Enhancements

The application can be extended with:

* [ ] Admin dashboard.
* [ ] Admin question management.
* [ ] Quiz categories.
* [ ] Difficulty levels.
* [ ] Timer-based quizzes.
* [ ] Randomized questions.
* [ ] Question pagination.
* [ ] Leaderboard.
* [ ] Performance analytics.
* [ ] JWT authentication.
* [ ] REST API integration.
* [ ] Email notifications.
* [ ] Responsive mobile design.
* [ ] Docker deployment.
* [ ] AWS deployment.

---

# 📈 Learning Outcomes

This project demonstrates practical knowledge of:

* Core Java
* Object-Oriented Programming
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* HTML
* CSS
* JavaScript
* Thymeleaf
* REST concepts
* MVC architecture
* Authentication
* Database relationships
* Git and GitHub

---

# 👨‍💻 Author

**Boya Bharath Kumar**

Computer Science & Engineering | Artificial Intelligence

### GitHub

[Bharathmadhu013 GitHub](https://github.com/Bharathmadhu013?utm_source=chatgpt.com)

---

# 📜 License

This project is created for **educational and portfolio purposes**.


