package de.dirty.server;

import de.dirty.server.netty.ServerBootstrap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {
  public static Main main;
  public static ServerBootstrap serverBootstrap;

  public Scene scene;
  private Stage primaryStage;
  private double positionX;
  private double positionY;

  private Thread serverThread =
      new Thread(
          () -> {
            System.out.println("Starting Server");
            serverBootstrap = new ServerBootstrap(1337);
          });

  public Main() {
    main = this;
    System.out.println("Abstimmung by DasDirt");
    serverThread.setName("ServerThread");
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(scene = new Scene(root, 800, 600));

    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setResizable(false);

    // close Event
    root.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::close);

    // Window movement
    root.setOnMousePressed(
        event -> {
          positionX = event.getSceneX();
          positionY = event.getSceneY();
        });
    root.setOnMouseDragged(
        event -> {
          primaryStage.setX(event.getScreenX() - positionX);
          primaryStage.setY(event.getScreenY() - positionY);
        });

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  /** This method will be executed if the programm closes */
  public void close(WindowEvent event) {
    System.out.println("Closing!\nShutdown Server.");
    if (serverThread.isAlive()) {
      serverBootstrap.disconnect();
      try {
        System.out.println("Wait for the server thread too close.");
        serverThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("Exit...");
    System.exit(0);
  }

  /** Returns the instance of the main class */
  public static Main getMain() {
    return main;
  }

  /** Returns the server bootstrap. */
  public static ServerBootstrap getServerBootstrap() {
    return serverBootstrap;
  }

  /** Returns the primary stage. */
  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public Scene getScene() {
    return scene;
  }
}
