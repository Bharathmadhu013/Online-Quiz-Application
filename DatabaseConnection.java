package com.quiz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:quiz_app.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Create Users Architecture
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "role TEXT DEFAULT 'USER')");

            // Create Quizzes Architecture
            stmt.execute("CREATE TABLE IF NOT EXISTS quizzes (" +
                    "quiz_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT NOT NULL)");

            // Create Questions Architecture
            stmt.execute("CREATE TABLE IF NOT EXISTS questions (" +
                    "question_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "quiz_id INTEGER, " +
                    "question_text TEXT NOT NULL, " +
                    "option_a TEXT, option_b TEXT, option_c TEXT, option_d TEXT, " +
                    "correct_option TEXT, " +
                    "FOREIGN KEY(quiz_id) REFERENCES quizzes(quiz_id))");

            // Create Progress Attempts Architecture
            stmt.execute("CREATE TABLE IF NOT EXISTS quiz_attempts (" +
                    "attempt_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id INTEGER, quiz_id INTEGER, score INTEGER, total INTEGER)");

            // Seed Mock Core Java Data if empty
            stmt.execute("INSERT OR IGNORE INTO users (user_id, username, password, role) VALUES (1, 'bharath', 'java123', 'USER')");
            stmt.execute("INSERT OR IGNORE INTO quizzes (quiz_id, title) VALUES (1, 'Core Java Concepts Mini-Exam')");
            
            stmt.execute("INSERT OR IGNORE INTO questions (question_id, quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option) " +
                    "VALUES (1, 1, 'Which memory component handles storage execution of local variables?', 'Heap Memory', 'Stack Memory', 'Garbage Collector', 'Method Area', 'B')");
            
            stmt.execute("INSERT OR IGNORE INTO questions (question_id, quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option) " +
                    "VALUES (2, 1, 'What is the default initial value of an unassigned object reference variable in a class definition?', '0', 'false', 'null', 'Compilation Error', 'C')");

        } catch (SQLException e) {
            System.err.println("Database setup initialization failed: " + e.getMessage());
        }
    }
}