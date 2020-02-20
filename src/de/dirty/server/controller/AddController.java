package de.dirty.server.controller;

import de.dirty.server.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddController implements Initializable {

  @FXML private Button exit;
  @FXML private Button minimize;

  @FXML private TextField nameField;
  @FXML private Button okBtn;
  @FXML private Button cancelBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("Add button events.");
    exit.setOnAction(event -> Main.getMain().close(null));
    minimize.setOnAction(event -> Main.getMain().getPrimaryStage().setIconified(true));

    cancelBtn.setOnAction(
        event -> {
          nameField.clear();
          Main.getMain().getPrimaryStage().setScene(Main.getMain().getScene());
        });
    okBtn.setOnAction(
        event -> {
          Main.getMain().getPrimaryStage().setScene(Main.getMain().getScene());
          Controller.controller.addComponent(nameField.getText());
          nameField.clear();
        });
  }
}
