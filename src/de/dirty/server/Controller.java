package de.dirty.server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {

  @FXML private VBox list;

  @FXML private Button exit;
  @FXML private Button minimize;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("Add button events.");
    exit.setOnAction(event -> Main.getMain().close(null));
    minimize.setOnAction(event -> Main.getMain().getPrimaryStage().setIconified(true));
  }
}
