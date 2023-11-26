package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class AdvisorController {

    @FXML
    private TableView<Club> tableView;

    //Columns
    @FXML
    private TableColumn<Club, String> clubNameColumn;

    @FXML
    private TableColumn<Club, String> clubDescriptionColumn;

    @FXML
    private TableColumn<Club, String> advisorNameColumn;

    @FXML
    private TableColumn<Club, String> presidentNameColumn;


    //Text input
    @FXML
    private TextField clubNameInput;

    @FXML
    private TextField clubDescriptionInput;

    @FXML
    private TextField advisorNameInput;

    @FXML
    private TextField presidentNameInput;


    @FXML
    private AnchorPane advisorANC;

    private Club selectedClubRecord;

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


    private final Database database = new Database();
    private String username;


    public void switchingPages(ActionEvent event){

        if(event.getSource() == btnManageClubs ){
            manageClub.setVisible(true);
            clubAttendance.setVisible(false);
            attendanceTracking.setVisible(false);


        }else if(event.getSource() == btnAttendance){
            manageClub.setVisible(false);
            clubAttendance.setVisible(true);
            attendanceTracking.setVisible(false);

            Advisor advisor = database.getAdvisorData(username);

            if(advisor != null){
                setAdvisorData(advisor);
            }

        }else if(event.getSource() == btnSearch){
            manageClub.setVisible(false);
            clubAttendance.setVisible(false);
            attendanceTracking.setVisible(true);
        }
    }


    public void setUsername(String username){
        this.username=username;
    }

    public void setAdvisorData(Advisor advisor){
        advisorName.setText(advisor.getFirstName()+" "+advisor.getLastName());
    }

    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }

    public void createClub(ActionEvent actionEvent) {
    }

    public void initialize(MouseEvent mouseEvent) {
    }

    public void updateClub(ActionEvent actionEvent) {
    }

    public void removeClub(ActionEvent actionEvent) {
    }
}
