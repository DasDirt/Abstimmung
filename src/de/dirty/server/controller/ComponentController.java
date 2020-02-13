package de.dirty.server.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ComponentController implements Initializable {

  public static String tmpName;

  @FXML
  private HBox box;

  @FXML
  private Label name;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    name.setText(tmpName);
  }

  /** This method is used for an hover effect */
  public void onEnter(MouseEvent mouseEvent){
    box.setStyle("-fx-background-color: F2F3F5;");
  }

  /** This method is used for an hover effect */
  public void onExit(MouseEvent mouseEvent){
    box.setStyle("-fx-background-color: FAFAFA;");
  }
}
