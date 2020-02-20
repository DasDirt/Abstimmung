package de.dirty.server.controller;

import de.dirty.server.Component;
import de.dirty.server.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {

  public static Controller controller;

  private Scene addScene;

  private HashMap<String, ComponentController> componentHashMap = new HashMap<>();
  private List<Component> components = new ArrayList<>();

  @FXML private VBox list;

  @FXML private Button exit;
  @FXML private Button minimize;
  @FXML private Button addBtn;

  @FXML private Button nextBtn; //todo add next page

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    controller = this;
    try {
      addScene =
          new Scene(
              new FXMLLoader(getClass().getResource("../fxml/AddComponent.fxml")).load(), 800, 600);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Add button events.");
    exit.setOnAction(event -> Main.getMain().close(null));
    minimize.setOnAction(event -> Main.getMain().getPrimaryStage().setIconified(true));

    addBtn.setOnAction(event -> Main.getMain().getPrimaryStage().setScene(addScene));

    // add some test fake Themes
    for (int i = 0; i < 15; i++) {
      System.out.println(i);
      components.add(new Component("Test" + i));
    }

    for (Component component : components) {
      ComponentController.tmpName = component.getName();
      Node node = null;
      try {
        node = FXMLLoader.load(getClass().getResource("../fxml/Component.fxml"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (node != null) {
        list.getChildren().add(node);
      }
      if (ComponentController.componentController != null) {
        ComponentController.componentController.setNode(node);
        componentHashMap.put(component.getName(), ComponentController.componentController);
        ComponentController.componentController = null;
      }
    }
  }

  public void addComponent(String name) {
    ComponentController.tmpName = name;
    Node node = null;
    try {
      node = FXMLLoader.load(getClass().getResource("../fxml/Component.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (node != null) {
      list.getChildren().add(node);
    }
    if (ComponentController.componentController != null) {
      ComponentController.componentController.setNode(node);
      componentHashMap.put(name, ComponentController.componentController);
      ComponentController.componentController = null;
    }
  }

  public void removeComponent(String name, Node node) {
    componentHashMap.remove(name);
    list.getChildren().remove(node);
  }

  public HashMap<String, ComponentController> getComponentHashMap() {
    return componentHashMap;
  }
}
