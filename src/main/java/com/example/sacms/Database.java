package com.example.sacms;

import java.sql.*;

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


    // Stores advisor event data in the database
    public boolean storeAdvisorEventData(event e1) throws SQLException {
        String query = "INSERT INTO advisorevent (`ClubName`, `eventName`, `advisorName`, `eventVenue`, `eventDate`, `eventTime`)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set common fields
            preparedStatement.setString(1, e1.getcName());
            preparedStatement.setString(2, e1.geteName());
            preparedStatement.setString(3, e1.getAdvName());
            preparedStatement.setString(4, e1.getVenue());
            preparedStatement.setString(5, e1.geteDate());
            preparedStatement.setString(6, e1.geteTime());


            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if all fields are filled (rowsAffected > 0)
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error storing event data");
            System.out.println(e.getMessage());
            return false;
        }
    }


    // Stores advisor meeting data in the database
    public boolean storeAdvisorMeetingData(Meetings m1) throws SQLException{
        // SQL query to insert data into the advisorevent table
        String query = "INSERT INTO advisormeeting (`MeetingTopic`, `ClubName`, `MeetingAdvisor`, `MeetingDuration`, `MeetingDate`)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set common fields
            preparedStatement.setString(1, m1.getMeetingTopic());
            preparedStatement.setString(2, m1.getMeetingClubName());
            preparedStatement.setString(3, m1.getMeetingAdvisorName());
            preparedStatement.setString(4, m1.getMeetingDuration());
            preparedStatement.setString(5, m1.getMeetingDate());

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if all fields are filled (rowsAffected > 0)
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error Storing Meeting Details");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Stores advisor activity data in the database
    public boolean storeAdvisorActivityData(Activity a1) throws SQLException{
        String query = "INSERT INTO advisoractivity (`ActivityName`, `Date`, `Venue`, `ClubName`, `ActivityDescription`)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set common fields
            preparedStatement.setString(1, a1.getActName());
            preparedStatement.setString(2, a1.getActDate());
            preparedStatement.setString(3, a1.getActVenue());
            preparedStatement.setString(4, a1.getActClubName());
            preparedStatement.setString(5, a1.getActDescription());

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if all fields are filled (rowsAffected > 0)
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error Storing Activity Details");
            System.out.println(e.getMessage());
            return false;
        }

    }

    public boolean isClubNameExists(String clubName) {
        String query = "SELECT COUNT(*) FROM club WHERE ClubName = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clubName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, the club name exists
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if club name exists");
            System.out.println(e.getMessage());
        }
        return false;
    }

}



