package com.chat;

import com.screenHandler.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage ps;
    private static Client client;

    private static String screen0ID = "welcome";
    private static String screen0File = "/views/welcomeScreen.fxml";
    private static String screen1ID = "chat";
    private static String screen1File = "/views/chatScreen.fxml";
    private static String screen2ID = "edit";
    private static String screen2File = "editScreen.fxml";
    private static String screen3ID = "help";
    private static String screen3File = "helpScreen.fxml";

    @Override
    public void start(Stage primaryStage) {
        client = new Client();
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(screen0ID, screen0File);
        mainContainer.loadScreen(screen1ID, screen1File);
//        mainContainer.loadScreen(screen2ID, screen2File);
//        mainContainer.loadScreen(screen3ID, screen3File);

        mainContainer.setScreen(screen0ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        primaryStage.setTitle("Chatting app");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ps = primaryStage;
    }

    public static Client getClient() { return client; }
    public static Stage getPrimaryStage() { return ps; }


    public static void main(String[] args) {
        launch(args);
    }
}
