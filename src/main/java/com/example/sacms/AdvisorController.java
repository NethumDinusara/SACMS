package com.example.sacms;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdvisorController {

    //Table
    @FXML
    private TableColumn<Club, String> advisorNameColumn;

    @FXML
    private TableColumn<Club, String> clubDescriptionColumn;

    @FXML
    private TableColumn<Club, String> clubNameColumn;

    @FXML
    private TableView<Club> clubTable;

    //Text input
    @FXML
    private TextField clubNameInput;

    @FXML
    private TextField clubDescriptionInput;

    @FXML
    private TextField advisorIDInput;

    private Club selectedClubRecord;


    private final Database database = new Database();
    private String username;
  
    @FXML
    private AnchorPane advisorANC;


    @FXML
    private Button btnManageClubs;

    @FXML
    private Button btnAttendance;

    @FXML
    private Button btnSearch;

    @FXML
    private SplitPane manageClub;

    @FXML
    private AnchorPane clubAttendance;

    @FXML
    private AnchorPane attendanceTracking;

    @FXML
    private Label advisorName;

    @FXML
    private TableView<Event> eventsTable;

    @FXML
    private TableColumn<Event, Integer> eventIdColumn;

    @FXML
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, String> clubNameColumn1;

    @FXML
    private TextField eventIdTextField;

    @FXML
    private Label eventNameTF;

    // FxIDs for member table in attendance tracking
    @FXML
    private TableView<Member> ParticipantTable;
    @FXML
    private  TableColumn<Member, Integer> StudentIdColumn;
    @FXML
    private TableColumn<Member, String> StudentFirstNameColumn;
    @FXML
    private TableColumn<Member, String> StudentLastNameColumn;
    @FXML
    private TableColumn<Member, String> GradeColumn;
    @FXML
    private TableColumn<Member, Boolean> StatusColumn;




    @FXML
    private void initialize() throws SQLException {

        // Initialize the TableView columns
        clubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        clubDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("clubDescription"));
        advisorNameColumn.setCellValueFactory(new PropertyValueFactory<>("advisorName"));

        // Load and set data to the TableView
        loadClubData();

        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        clubNameColumn1.setCellValueFactory(new PropertyValueFactory<>("clubName"));

        // Replace this with actual logic to get the username

        Advisor advisor = database.getAdvisorData(username);
        if(advisor != null){
            setAdvisorData(advisor);
            loadEventsData();
        }

        // populate participant table

        StudentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        StudentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        StudentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        GradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // Create the StatusColumn with checkboxes
        StatusColumn.setCellValueFactory(cellData -> cellData.getValue().attendanceProperty());
        StatusColumn.setCellFactory(CheckBoxTableCell.forTableColumn(StatusColumn));
        StatusColumn.setEditable(true);

        try {
            loadEventData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // load participant data
    public void loadEventData() throws SQLException {
        // Fetch event data and display in the table
        List<Member> members = database.getMembers();
        for(Member member : members){
            member.setAttendanceStatus(false);
        }

        ObservableList<Member> memberData = FXCollections.observableArrayList(members);
        ParticipantTable.setItems(memberData);
    }



    private void loadClubData() {
        // Retrieve club data from the database
        List<Club> clubs = database.getClubDataForAdvisor();

        // Create an ObservableList from the retrieved data
        ObservableList<Club> clubList = FXCollections.observableArrayList(clubs);

        // Clear existing items before setting the new ones
        clubTable.getItems().clear();

        // Set the items of the TableView
        clubTable.setItems(clubList);

    }
  
  @FXML
    private void onRowClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            Club clickedClub = clubTable.getSelectionModel().getSelectedItem();
            if (clickedClub != null) {
                // Update the selectedClubRecord when a row is clicked
                selectedClubRecord = clickedClub;
                // Populate the input fields with selected club's data
                populateInputFields(selectedClubRecord);
            }
        }
    }
    // Helper method to populate input fields with club data
    private void populateInputFields(Club club) {
        if (club != null) {
            clubNameInput.setText(club.getClubName());
            clubDescriptionInput.setText(club.getClubDescription());
            advisorIDInput.setText(club.getAdvisorName()); // Assuming AdvisorName is used as AdvisorID
        }
    }

    @FXML
    public Button advScheduleEvent;


    public void loadEventsData() throws SQLException {
        List<Event> events = database.getAdvisorEvents(username);
        ObservableList<Event> eventData = FXCollections.observableArrayList(events);
        eventsTable.setItems(eventData);

    }


    int currentEventID;
    public void switchingPages(ActionEvent event) throws SQLException {

        if(event.getSource() == btnManageClubs ){
            manageClub.setVisible(true);
            clubAttendance.setVisible(false);
            attendanceTracking.setVisible(false);



        }else if(event.getSource() == btnAttendance){
            manageClub.setVisible(false);
            clubAttendance.setVisible(true);
            attendanceTracking.setVisible(false);
            loadEventsData();


        }  else if (event.getSource() == btnSearch) {
            int eventID = Integer.parseInt(eventIdTextField.getText());
            List<Integer> IdList = database.getAdvisorEventIDs(username);
            boolean found = false;

            for (Integer evenIDinList : IdList) {
                if (evenIDinList.equals(eventID)) {

                    manageClub.setVisible(false);
                    clubAttendance.setVisible(false);
                    attendanceTracking.setVisible(true);
                    currentEventID = eventID; // Store the current event ID
                    eventNameTF.setText(database.getAdvisorName(eventID, username));
                    loadEventData();

                    found = true;
                    break;
                }
            }

            // If Event id is not in the list it will print this error
            if (!found) {
                showAlert("Please Enter An Event In The Table.");
            }
            // Clear text field after an execution
            eventIdTextField.setText("");
        }

    }

    public void setUsername(String username){
        this.username=username;
    }

    public void setAdvisorData(Advisor advisor){
        advisorName.setText(advisor.getFirstName()+" "+advisor.getLastName());
    }


    // Get the event details from the table and pass to the table

    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }

    public void onSubmitButtonClick(ActionEvent actionEvent) throws SQLException {
        ObservableList<Member> participants = ParticipantTable.getItems();
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();

        for (Member member : participants) {
            BooleanProperty attendanceProperty = member.attendanceProperty();

            if (attendanceProperty.get()) {
                // If the checkbox is selected, store the attendance record
                String status = "Present";

                AttendanceRecord record = new AttendanceRecord(
                        member.getStudentId(),
                        member.getFirstName(), // Use the checked value of FirstName
                        member.getLastName(),
                        member.getGrade(),
                        currentEventID,
                        status
                );
                attendanceRecords.add(record);
            }
        }

        manageClub.setVisible(false);
        clubAttendance.setVisible(true);
        attendanceTracking.setVisible(false);

        submitAttendance(attendanceRecords);
    }

    private void submitAttendance(List<AttendanceRecord> attendanceRecords) throws SQLException {
        // Insert attendance records into the memberattendance table
        try {
            database.insertAttendanceRecords(attendanceRecords);
        } catch (SQLException e) {
            showAlert("Failed to submit attendance. Please try again.");
        }
    }


    @FXML
    public void onScheduleBottonClick(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Schedule_event.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("Advisor");
    }

    
    public void createClub(ActionEvent actionEvent) throws SQLException {
        // Get data from input fields
        String clubName = clubNameInput.getText();
        String clubDescription = clubDescriptionInput.getText();
        String advisorID = advisorIDInput.getText();

        // Validate input
        if (clubName.isEmpty() || clubDescription.isEmpty() || advisorID.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }
        if (!database.isAdvisorIdValid(advisorID)) {
            showAlert("Advisor does not exist. Please enter a valid advisor ID.");
            return;
        }

        // Create an instance of ManageClub
        Club.ManageClub newClub = new Club.ManageClub(clubName, clubDescription,"",advisorID);

        // Add the club to the database
        if (database.createClub(newClub)) {
            showAlert("Club created successfully.");
            clearInputFields();
            // Load data after a successful creation if needed
            loadClubData();
        } else {
            showAlert("Error creating club. Please try again.");
        }
    }


    // Helper method to show an alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Club Management");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void updateClub(ActionEvent actionEvent) {
        if (selectedClubRecord == null) {
            // No club selected, show an error message or handle accordingly
            System.out.println("Please select a club to update.");
            return;
        }

        // Get updated values from input fields
        String updatedClubName = clubNameInput.getText();
        String updatedClubDescription = clubDescriptionInput.getText();
        String updatedAdvisorName = advisorIDInput.getText();

        // Validation (You might want to add more sophisticated validation)
        if (updatedClubName.isEmpty() || updatedClubDescription.isEmpty() || updatedAdvisorName.isEmpty()) {
            // Show an error message or handle validation failure
            System.out.println("Please fill in all fields.");
            return;
        }

        // Update the selected club's properties
        selectedClubRecord.setClubName(updatedClubName);
        selectedClubRecord.setClubDescription(updatedClubDescription);
        selectedClubRecord.setAdvisorName(updatedAdvisorName);

        // Update the club in the database
        boolean success = database.updateClub(selectedClubRecord);

        if (success) {
            // Refresh the club data in the TableView
            loadClubData();
            showAlert("Club updated successfully.");
        } else {
            // Show an error message or handle the failure
            showAlert("Error updating club. Please try again.");
        }

        // Clear input fields and reset selectedClubRecord
        clearInputFields();
        selectedClubRecord = null;
    }


    @FXML
    public void removeClub(ActionEvent actionEvent) {
        if (selectedClubRecord == null) {
            // No club selected, show an error message or handle accordingly
            System.out.println("Please select a club to remove.");
            return;
        }

        // Remove the selected club from the database
        boolean success = database.deleteClub(selectedClubRecord.getClubName());

        if (success) {
            // Refresh the club data in the TableView
            loadClubData();
        } else {
            // Show an error message or handle the failure
            System.out.println("Error removing club.");
        }

        // Clear input fields and reset selectedClubRecord
        clearInputFields();
        selectedClubRecord = null;
    }


    private void clearInputFields() {
        clubNameInput.clear();
        clubDescriptionInput.clear();
        advisorIDInput.clear();
    }


}
