package com.quiz;

import com.quiz.model.Question;
import com.quiz.model.User;
import com.quiz.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("====== SYSTEM: BOOTING QUIZ CONSOLE ENGINE ======");
        DatabaseConnection.initializeDatabase();
        
        while (true) {
            if (currentUser == null) {
                showAuthMenu();
            } else {
                showDashboardMenu();
            }
        }
    }

    private static void showAuthMenu() {
        System.out.println("\n*** WELCOME TO ONLINE QUIZ HUB (ID: 65HIBKJS) ***");
        System.out.println("1. Login into Profile");
        System.out.println("2. Exit System");
        System.out.print("Enter command selection choice: ");
        
        String option = scanner.nextLine();
        if (option.equals("1")) {
            handleLogin();
        } else if (option.equals("2")) {
            System.out.println("Closing session. Keep up the learning journey!");
            System.exit(0);
        } else {
            System.out.println("[!] Invalid input. Provide valid option numeric values.");
        }
    }

    private static void handleLogin() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password credential string: ");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                currentUser = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("role"));
                System.out.println("\n[✓] Access granted. Welcome back, " + currentUser.getUsername() + "!");
            } else {
                System.out.println("\n[X] Authentication rejection: Incorrect username or password mismatch.");
            }
        } catch (SQLException e) {
            System.err.println("JDBC Login transaction dropped: " + e.getMessage());
        }
    }

    private static void showDashboardMenu() {
        System.out.println("\n====== DASHBOARD HUB CONTROL ======");
        System.out.println("1. View Available Assessments & Start Quiz");
        System.out.println("2. View Past Academic Score Progress Records");
        System.out.println("3. Logout Session Profile");
        System.out.print("Select execution path routing: ");

        String option = scanner.nextLine();
        switch (option) {
            case "1" -> launchQuizSequence();
            case "2" -> viewPerformanceHistory();
            case "3" -> {
                currentUser = null;
                System.out.println("[✓] Session terminated successfully.");
            }
            default -> System.out.println("[!] Option command values unmapped.");
        }
    }

    private static void launchQuizSequence() {
        List<Question> questions = new ArrayList<>();
        String quizTitle = "";
        int quizId = 1; // Default mapped seed index 

        // 1. Fetch data from DB
        try (Connection conn = DatabaseConnection.getConnection()) {
            String titleQuery = "SELECT title FROM quizzes WHERE quiz_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(titleQuery)) {
                pstmt.setInt(1, quizId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) quizTitle = rs.getString("title");
            }

            String questionsQuery = "SELECT * FROM questions WHERE quiz_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(questionsQuery)) {
                pstmt.setInt(1, quizId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    questions.add(new Question(
                            rs.getInt("question_id"),
                            rs.getString("question_text"),
                            rs.getString("option_a"),
                            rs.getString("option_b"),
                            rs.getString("option_c"),
                            rs.getString("option_d"),
                            rs.getString("correct_option")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Quiz structural retrieval failure: " + e.getMessage());
            return;
        }

        if (questions.isEmpty()) {
            System.out.println("[!] No questions found for this quiz.");
            return;
        }

        // 2. Run the dynamic quiz loop (One question at a time)
        System.out.println("\n==============================================");
        System.out.println("STARTING EXAM SESSION: " + quizTitle);
        System.out.println("==============================================");
        
        int runningScore = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + " of " + questions.size());
            System.out.println(">> " + q.getQuestionText());
            System.out.println("A) " + q.getOptionA());
            System.out.println("B) " + q.getOptionB());
            System.out.println("C) " + q.getOptionC());
            System.out.println("D) " + q.getOptionD());
            
            System.out.print("Your selected answer input (A, B, C, or D): ");
            String answerInput = scanner.nextLine().trim().toUpperCase();

            // Requirement 3: Immediate correct/incorrect evaluation tracking feedback loop
            if (answerInput.equals(q.getCorrectOption())) {
                System.out.println("[✓] System Correction Result: Correct Answer chosen!");
                runningScore++;
            } else {
                System.out.println("[X] System Correction Result: Incorrect! Valid answer path option was: " + q.getCorrectOption());
            }
        }

        // 3. Finalization, Score presentation, and Database persistence log write
        System.out.println("\n====== EXAMINATION WRAP SUMMARY ======");
        System.out.println("Completed performance score total: " + runningScore + " / " + questions.size());
        double percentage = ((double) runningScore / questions.size()) * 100;
        System.out.printf("Total calculated execution percentage accuracy: %.2f%%\n", percentage);

        String saveAttemptSql = "INSERT INTO quiz_attempts (user_id, quiz_id, score, total) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(saveAttemptSql)) {
            pstmt.setInt(1, currentUser.getId());
            pstmt.setInt(2, quizId);
            pstmt.setInt(3, runningScore);
            pstmt.setInt(4, questions.size());
            pstmt.executeUpdate();
            System.out.println("[✓] Results tracked and securely synced with personal storage archives.");
        } catch (SQLException e) {
            System.err.println("Failed syncing completion parameters metric score: " + e.getMessage());
        }
    }

    private static void viewPerformanceHistory() {
        System.out.println("\n====== HISTORIC SCORE PROGRESS TRACKER ======");
        String sql = "SELECT qa.score, qa.total, q.title, qa.attempt_id " +
                     "FROM quiz_attempts qa JOIN quizzes q ON qa.quiz_id = q.quiz_id " +
                     "WHERE qa.user_id = ? ORDER BY qa.attempt_id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, currentUser.getId());
            ResultSet rs = pstmt.executeQuery();

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                System.out.println("Attempt #" + rs.getInt("attempt_id") + " | Quiz: " + rs.getString("title") +
                        " | Score: " + rs.getInt("score") + "/" + rs.getInt("total"));
            }
            if (!hasRecords) {
                System.out.println("[-] No recorded data metrics found. Try taking an initial evaluation test module first.");
            }
        } catch (SQLException e) {
            System.err.println("History mapping trace execution thrown: " + e.getMessage());
        }
    }
}