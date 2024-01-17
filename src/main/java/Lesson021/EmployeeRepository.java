package Lesson021;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/alevel";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Dg18021996";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createEmployees(connection);
            displayEmployees(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createEmployees(Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO employees (name, position) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Добавляем 2 сотрудников
            addEmployee(preparedStatement, "John Doe", "Developer");
            addEmployee(preparedStatement, "Jane Doe", "Manager");
        }
    }

    private static void addEmployee(PreparedStatement preparedStatement, String name, String position) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, position);
        preparedStatement.executeUpdate();
    }

    private static void displayEmployees(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM employees";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String position = resultSet.getString("position");

                System.out.println("Employee ID: " + id + ", Name: " + name + ", Position: " + position);
            }
        }
    }
}
