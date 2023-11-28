package com.example.sacms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    final String connectionUrl;

    public Database() {
        connectionUrl = "jdbc:mysql://localhost/sacms";
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionUrl, "root", "");
        } catch (SQLException sqlException) {
            System.out.println("Could not get SQL Connection");
            System.out.println(sqlException.getMessage());
            return null;
        }
    }

    // Login method for both members and advisors
    public boolean validateUserLogin(String username, String password, String userType) {
        String query = "";
        if ("member".equals(userType)) {
            query = "SELECT * FROM member WHERE username = ? AND password = ?";
        } else if ("advisor".equals(userType)) {
            query = "SELECT * FROM advisor WHERE username = ? AND password = ?";
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Error validating login");
            System.out.println(e.getMessage());
            return false;
        }
    }


    // Registration method for both members and advisors
    public boolean registerUser(User user, String userType) {
        String query = "";
        if ("member".equals(userType)) {
            query = "INSERT INTO member (`Username`, `FirstName`, `LastName`, `PhoneNumber`, `Email`, `Password`, `DateOfBirth`, `Gender`, `Grade`,`StudentID`)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
        } else if ("advisor".equals(userType)) {
            query = "INSERT INTO advisor (`Username`, `FirstName`, `LastName`, `PhoneNumber`, `Email`, `Password`, `TeacherID`)" +
                    "VALUES (?,?,?,?,?,?,?)";
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //Set common fields
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());

            //Set User-specific fields
            if ("member".equals(userType)) {

                Member member = (Member) user;
                preparedStatement.setDate(7, member.getDateOfBirth());
                preparedStatement.setString(8, member.getGender());
                preparedStatement.setString(9, member.getGrade());
                preparedStatement.setString(10, member.getStudentId());

            } else if ("advisor".equals(userType)) {

                Advisor advisor = (Advisor) user;
                preparedStatement.setString(7, advisor.getTeacherId());

            }

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if registration is successful (rowsAffected > 0)
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error registering user");
            System.out.println(e.getMessage());
            return false;
        }

    }

    //checking the already exists students
    public boolean isUserIdExists(String UserID, String UserType) {
        String query = "";
        if ("member".equals(UserType)) {
            query = "SELECT COUNT(*) FROM member WHERE StudentID = ?";
        } else if ("advisor".equals(UserType)) {
            query = "SELECT COUNT(*) FROM advisor WHERE TeacherID = ?";
        }
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, UserID);  // Fix: Use the provided UserID parameter

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, user ID exists
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if user ID exists");
            System.out.println(e.getMessage());
        }
        return false;
    }

    //Get user data to the member profile view

    public Member getMemberData(String username) {
        String query = "SELECT * FROM member WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Extract data from the result set
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String phoneNumber = resultSet.getString("PhoneNumber");
                    String email = resultSet.getString("Email");
                    Date dateOfBirth = resultSet.getDate("DateOfBirth");
                    String gender = resultSet.getString("Gender");
                    String grade = resultSet.getString("Grade");
                    String studentId = resultSet.getString("StudentID");

                    // Create a Member object with the retrieved data
                    Member member = new Member(firstName, lastName, phoneNumber, username, email, null, dateOfBirth, grade, gender, studentId);
                    return member;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting member data");
            System.out.println(e.getMessage());
        }
        return null;
    }


    // Get advisor details to the advisor controllers.
    public Advisor getAdvisorData(String username) {
        String query = "SELECT * FROM advisor WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    // Extract data from the result set
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String teacherId = resultSet.getString("teacherID");

                    // Creating object to store data

                    Advisor advisor = new Advisor(firstName, lastName, null, username, null, null, teacherId);
                    return advisor;
                }

            }
        } catch (SQLException e) {
            System.out.println("Error getting advisor data");
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Get event data and advisor data from the database

    public List<Event> getAdvisorEvents(String username) throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event WHERE Username = ? ";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int eventID = resultSet.getInt("EventID");
                    String eventName = resultSet.getString("EventName");
                    String dateOfEvent = resultSet.getString("DateOfEvent");
                    String venue = resultSet.getString("Venue");
                    String clubName = resultSet.getString("ClubName");


                    Event event = new Event(eventID, eventName, "dateOfEvent", "venue", "", clubName);
                    events.add(event);

                    getAdvisorEventIDs(username);
                }
            }
        }

        return events;
    }

    // Get all the event ids to a one list
    public List<Integer> getAdvisorEventIDs(String username) throws SQLException {
        List<Integer> eventIdList = new ArrayList<>();
        String query = "SELECT EventID FROM event WHERE Username = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int eventID = resultSet.getInt("EventID");
                    Integer eventIDAsString = Integer.valueOf(String.valueOf(eventID));
                    eventIdList.add(eventIDAsString);
                }
            }

        }

        return eventIdList;
    }



}
