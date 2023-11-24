package com.example.sacms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class MemberController{


    @FXML
    private Button btnClubs;

    @FXML
    private Button btnEvents;

    @FXML
    private Button btnProfile;

    @FXML
    private AnchorPane Events;

    @FXML
    private AnchorPane yourClubs;

    @FXML
    private AnchorPane yourProfile;

    @FXML
    private Label userFName;

    @FXML
    private Label userLName;

    @FXML
    private Label email;

    @FXML
    private Label grade;

    @FXML
    private Label phoneNumber;

    @FXML
    private ListView<String> clubsList;

    @FXML
    private TableView<Club> clubsTable;

    @FXML
    private TableColumn<Club, String> clubNameColumn;

    @FXML
    private TableColumn<Club, String> advisorColumn;

    @FXML
    private TableColumn<Club, String> advisorContactNumberColumn;

    @FXML
    private TableColumn<Club, Date> joinDateColumn;

    @FXML
    private TableColumn<Club, Button> quitButtonColumn;

    @FXML
    private Button joinClub;


    private final Database database = new Database();
    private String username;

    public void initialize() {
        // Set up columns for the clubsTable
        clubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        advisorColumn.setCellValueFactory(new PropertyValueFactory<>("advisorName"));
        advisorContactNumberColumn.setCellValueFactory(new PropertyValueFactory<>("advisorPhoneNumber"));
        joinDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        quitButtonColumn.setCellValueFactory(new PropertyValueFactory<>("quitButton"));
        quitButtonColumn.setCellFactory(createQuitButtonCellFactory());

        // Set member data when switching to the profile view
        Member member = database.getMemberData(username);
        if (member != null) {
            setMemberData(member);
            loadClubsData();
        }
    }

    private Callback<TableColumn<Club, Button>, TableCell<Club, Button>> createQuitButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Club, Button> call(TableColumn<Club, Button> param) {
                return new TableCell<>() {
                    final Button quitButton = new Button("Quit");

                    {
                        quitButton.setOnAction(event -> {
                            Club club = getTableView().getItems().get(getIndex());
                            handleQuitButton(club);
                        });
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(quitButton);
                        }
                    }
                };
            }
        };
    }

    private void handleQuitButton(Club club) {
        // Handle quit button action here
        // You can call the database.quitClub method and refresh the table
        database.quitClub(username, club.getClubName());
        loadClubsData();
    }

    public void loadClubsData() {
        // Load clubs data from the database and populate the table
        List<Club> clubs = database.getMemberClubs(username);
        ObservableList<Club> clubData = FXCollections.observableArrayList(clubs);
        clubsTable.setItems(clubData);

        // Load available clubs for the member in the clubsList
        List<String> availableClubs = database.getAvailableClubs(username);
        ObservableList<String> availableClubsData = FXCollections.observableArrayList(availableClubs);
        clubsList.setItems(availableClubsData);
    }

    public void SwitchForm(ActionEvent event) {
        if (event.getSource() == btnProfile) {
            yourProfile.setVisible(true);
            yourClubs.setVisible(false);
            Events.setVisible(false);
        } else if (event.getSource() == btnClubs) {
            yourProfile.setVisible(false);
            yourClubs.setVisible(true);
            Events.setVisible(false);
            loadClubsData(); // Load clubs data when switching to the clubs view
        } else if (event.getSource() == btnEvents) {
            yourProfile.setVisible(false);
            yourClubs.setVisible(false);
            Events.setVisible(true);
        }
    }

    // Set Data to the text filed
    public void setUsername(String username) {
        this.username = username;
    }

    public void setMemberData(Member member) {
        userFName.setText(member.getFirstName());
        userLName.setText(member.getLastName());
        email.setText(member.getEmail());
        grade.setText("grade " + member.getGrade());
        phoneNumber.setText(member.getPhoneNumber());
    }

    @FXML
    public void goBack(ActionEvent actionEvent) throws Exception {
        Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("SACMS");
    }

    @FXML
    public void joinClub(ActionEvent actionEvent) {
        // Get the selected club from the list
        String selectedClub = clubsList.getSelectionModel().getSelectedItem();
        if (selectedClub != null) {
            // Join the selected club
            database.joinClub(username, selectedClub);
            // Refresh the clubs data
            loadClubsData();
        }
    }

    @FXML
    public void quitClub(ActionEvent actionEvent) {
        // Get the selected club from the table
        Club selectedClub = clubsTable.getSelectionModel().getSelectedItem();
        if (selectedClub != null) {
            // Quit the selected club
            database.quitClub(username, selectedClub.getClubName());
            // Refresh the clubs data
            loadClubsData();
        }
    }
}
