package de.dirty.server.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ComponentController implements Initializable {

  public static ComponentController componentController;

  public static String tmpName;

  @FXML private HBox box;

  @FXML private Label name;

  @FXML private Button removeBtn;

  private Node node;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    componentController = this;
    name.setText(tmpName);
    removeBtn.setOnAction(event -> Controller.controller.removeComponent(name.getText(), node));
  }

  /** This method is used for an hover effect */
  public void onEnter(MouseEvent mouseEvent) {
    box.setStyle("-fx-background-color: F2F3F5;");
  }

  /** This method is used for an hover effect */
  public void onExit(MouseEvent mouseEvent) {
    box.setStyle("-fx-background-color: FAFAFA;");
  }

  public void setNode(Node node) {
    this.node = node;
  }
}
