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
    public boolean registerUser(User user,String userType){
        String query = "";
        if ("member".equals(userType)){
            query = "INSERT INTO member (`Username`, `FirstName`, `LastName`, `PhoneNumber`, `Email`, `Password`, `DateOfBirth`, `Gender`, `Grade`,`StudentID`)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
        } else if ("advisor".equals(userType)) {
            query = "INSERT INTO advisor (`Username`, `FirstName`, `LastName`, `PhoneNumber`, `Email`, `Password`, `TeacherID`)" +
                    "VALUES (?,?,?,?,?,?,?)";
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            //Set common fields
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getFirstName());
            preparedStatement.setString(3,user.getLastName());
            preparedStatement.setString(4,user.getPhoneNumber());
            preparedStatement.setString(5,user.getEmail());
            preparedStatement.setString(6,user.getPassword());

            //Set User-specific fields
            if("member".equals(userType)){

                Member member = (Member) user;
                preparedStatement.setDate(7,member.getDateOfBirth());
                preparedStatement.setString(8,member.getGender());
                preparedStatement.setString(9, member.getGrade());
                preparedStatement.setString(10, member.getStudentId());

            } else if ("advisor".equals(userType)) {

                Advisor advisor = (Advisor) user;
                preparedStatement.setString(7,advisor.getTeacherId());

            }

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if registration is successful (rowsAffected > 0)
            return rowsAffected > 0;

        }catch (SQLException e){
            System.out.println("Error registering user");
            System.out.println(e.getMessage());
            return false;
        }

    }

    //checking the already exists students
    public boolean isUserIdExists(String UserID,String UserType) {
        String query = "";
        if ("member".equals(UserType)){
            query = "SELECT COUNT(*) FROM member WHERE StudentID = ?";
        } else if ("advisor".equals(UserType)) {
            query = "SELECT COUNT(*) FROM advisor WHERE TeacherID = ?";
        }
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, UserID);  //Use the provided UserID parameter

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

    //get member data and club data from the database
    public List<Club> getMemberClubs(String username) {
        List<Club> clubs = new ArrayList<>();
        String query = "SELECT c.ClubName, a.FirstName AS AdvisorFirstName, a.LastName AS AdvisorLastName, a.PhoneNumber AS AdvisorPhoneNumber, cm.JoinDate " +
                "FROM club c " +
                "JOIN clubmember cm ON c.ClubID = cm.ClubID " +
                "JOIN advisor a ON c.AdvisorID = a.Username " +
                "WHERE cm.Username = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String clubName = resultSet.getString("ClubName");
                    String advisorFirstName = resultSet.getString("AdvisorFirstName");
                    String advisorLastName = resultSet.getString("AdvisorLastName");
                    String advisorPhoneNumber = resultSet.getString("AdvisorPhoneNumber");
                    Date joinDate = resultSet.getDate("JoinDate");

                    String advisorName = advisorFirstName + " " + advisorLastName;

                    Club club = new Club(clubName, "", advisorName);
                    club.setAdvisorPhoneNumber(advisorPhoneNumber);
                    club.setJoinDate(joinDate);
                    clubs.add(club);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting member clubs");
            System.out.println(e.getMessage());
        }
        return clubs;
    }

    //getting not join club data from the clubmember table
    public List<String> getAvailableClubs(String username) {
        List<String> availableClubs = new ArrayList<>();
        String query = "SELECT ClubName FROM club WHERE ClubID NOT IN (SELECT ClubID FROM clubmember WHERE Username = ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String clubName = resultSet.getString("ClubName");
                    availableClubs.add(clubName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting available clubs");
            System.out.println(e.getMessage());
        }
        return availableClubs;
    }

    //method for the join clubs
    public void joinClub(String username, String clubName) {
        String query = "INSERT INTO clubmember (ClubID, Username, JoinDate) " +
                "VALUES ((SELECT ClubID FROM club WHERE ClubName = ?), ?, CURDATE())";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, clubName);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error joining club");
            System.out.println(e.getMessage());
        }
    }


    //method for the quit from a club
    public void quitClub(String username, String clubName) {
        String query = "DELETE FROM clubmember WHERE ClubID = (SELECT ClubID FROM club WHERE ClubName = ?) AND Username = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, clubName);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error quitting club");
            System.out.println(e.getMessage());
        }
    }

   //get club data for the club table in the advisor controller
   // Get club data for the advisor
   public List<Club> getClubDataForAdvisor() {
       List<Club> clubs = new ArrayList<>();
       String query = "SELECT c.ClubName, c.ClubDescription, a.Username AS AdvisorID " +
               "FROM club c " +
               "JOIN advisor a ON c.AdvisorID = a.Username";

       try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

           while (resultSet.next()) {
               String clubName = resultSet.getString("ClubName");
               String clubDescription = resultSet.getString("ClubDescription");
               String advisorID = resultSet.getString("AdvisorID");

               Club club = new Club(clubName, clubDescription, advisorID);
               clubs.add(club);
           }

       } catch (SQLException e) {
           System.out.println("Error getting club data for advisor");
           System.out.println(e.getMessage());
       }

       return clubs;
   }

}

