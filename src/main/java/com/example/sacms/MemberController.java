package com.example.sacms;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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


    public void SwitchForm(ActionEvent event){
        if(event.getSource() == btnProfile){
            yourProfile.setVisible(true);
            yourClubs.setVisible(false);
            Events.setVisible(false);
        } else if (event.getSource() == btnClubs){
            yourProfile.setVisible(false);
            yourClubs.setVisible(true);
            Events.setVisible(false);
        }else if (event.getSource() == btnEvents) {
            yourProfile.setVisible(false);
            yourClubs.setVisible(false);
            Events.setVisible(true);
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }



}
