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

               Club club = new Club.ManageClub(clubName, clubDescription, "", advisorID);
               clubs.add(club);
           }

       } catch (SQLException e) {
           System.out.println("Error getting club data for advisor");
           System.out.println(e.getMessage());
       }

       return clubs;
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

   


    public boolean createClub(Club club) {
        String query = "INSERT INTO club (ClubName, ClubDescription, AdvisorID) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
          preparedStatement.setString(1, club.getClubName());
            preparedStatement.setString(2, club.getClubDescription());

            if (club instanceof Club.ManageClub) {
                Club.ManageClub manageClub = (Club.ManageClub) club;

                // Check if the advisorID is not null or empty
                if (manageClub.getAdvisorID() != null && !manageClub.getAdvisorID().isEmpty()) {
                    // Check if the club already exists in the database
                    if (clubExists(connection, club)) {
                        System.out.println("Club already exists");
                        return false;
                    }

                    // Check if the advisor exists in the advisor table
                    if (!advisorExists(connection, manageClub.getAdvisorID())) {
                        // Insert the advisor if not exists
                        insertAdvisor(connection, manageClub.getAdvisorID());
                    }

                    preparedStatement.setString(3, manageClub.getAdvisorID());
                } else {
                    preparedStatement.setNull(3, Types.VARCHAR); // or set an appropriate default value
                }
            } else {
                preparedStatement.setNull(3, Types.VARCHAR); // or set an appropriate default value
            }

            // Start a transaction
            connection.setAutoCommit(false);

            try {
                // Insert the club
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Commit the transaction if the insertion is successful
                    connection.commit();
                    return true;
                } else {
                    // Rollback the transaction if the insertion fails
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                // Rollback the transaction in case of an exception
                connection.rollback();
                System.out.println("Error creating club");
                e.printStackTrace();
                return false;
            } finally {
                // Restore the default auto-commit behavior
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Error creating club");
            e.printStackTrace();
            return false;
        }
    }
       

    // Helper method to check if a club already exists
    private boolean clubExists(Connection connection, Club club) throws SQLException {
        String query = "SELECT 1 FROM club WHERE ClubName = ? AND ClubDescription = ? AND AdvisorID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, club.getClubName());
            preparedStatement.setString(2, club.getClubDescription());
            preparedStatement.setString(3, club.getAdvisorName()); // Assuming AdvisorName is used as AdvisorID
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


    // Helper method to check if an advisor already exists
    private boolean advisorExists(Connection connection, String advisorID) throws SQLException {
        String query = "SELECT 1 FROM advisor WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, advisorID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
    public boolean isAdvisorIdValid(String advisorID) throws SQLException {
        String query = "SELECT COUNT(*) FROM advisor WHERE Username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, advisorID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }

    // Helper method to insert an advisor if not exists
    private void insertAdvisor(Connection connection, String advisorID) throws SQLException {
        String query = "INSERT INTO advisor (Username) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, advisorID);
            preparedStatement.executeUpdate();
        }
    }

    // Update an existing club

    public boolean updateClub(Club club) {
        String query = "UPDATE club SET ClubDescription = ?, AdvisorID = ? WHERE ClubName = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, club.getClubDescription());

            if (club instanceof Club.ManageClub) {
                Club.ManageClub manageClub = (Club.ManageClub) club;

                // Check if the advisorID is not null or empty
                if (manageClub.getAdvisorID() != null && !manageClub.getAdvisorID().isEmpty()) {
                    // Check if the advisor exists in the advisor table
                    if (!advisorExists(connection, manageClub.getAdvisorID())) {
                        // Insert the advisor if not exists
                        insertAdvisor(connection, manageClub.getAdvisorID());
                    }

                    preparedStatement.setString(2, manageClub.getAdvisorID());
                } else {
                    preparedStatement.setNull(2, Types.VARCHAR); // or set an appropriate default value
                }
            } else {
                preparedStatement.setNull(2, Types.VARCHAR); // or set an appropriate default value
            }

            preparedStatement.setString(3, club.getClubName());

            // Start a transaction
            connection.setAutoCommit(false);

            try {
                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Commit the transaction if the update is successful
                    connection.commit();
                    return true;
                } else {
                    // Rollback the transaction if the update fails
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                // Rollback the transaction in case of an exception
                connection.rollback();
                System.out.println("Error updating club");
                e.printStackTrace();
                return false;
            } finally {
                // Restore the default auto-commit behavior
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Error updating club");
            e.printStackTrace();
            return false;
        }
    }

    // Remove an existing club
    public boolean deleteClub(String clubName) {
        String query = "DELETE FROM club WHERE ClubName = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, clubName);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting club");
            e.printStackTrace();
            return false;
        }
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

    public String getAdvisorName(int eventID, String username) {
        String query = "SELECT EventName FROM event WHERE Username = ? AND EventID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, eventID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("EventName");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if the event ID isn't found for that username
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

