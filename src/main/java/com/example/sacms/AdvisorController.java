package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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


    private Club selectedClubRecord;


    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }

    public void onAttendance(ActionEvent actionEvent)throws Exception {

        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdvisorClub.fxml")));
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
