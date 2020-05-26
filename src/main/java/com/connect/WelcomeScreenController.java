package com.connect;

import com.chat.*;
import com.screenHandler.ControlledScreen;
import com.screenHandler.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeScreenController implements Initializable, ControlledScreen {

    private static Server server;
    ScreensController myController;
    Client client;

    @FXML
    Label title_label;
    @FXML
    Label name_label;
    @FXML
    Label ip_label;
    @FXML
    Label port_label;
    @FXML
    Label status_label;
    @FXML
    Button connectBtn;
    @FXML
    TextField username;
    @FXML
    TextField serverIP;
    @FXML
    TextField portNumber;
    @FXML
    Label status;

    public void create(ActionEvent event) {
        status.setText("");
        status.setTextFill(Color.RED);

        try {
            if (username.getText().trim().length() == 0) { // parsing username
                username.setText("");
                status.setText("username can not be empty");
                return;
            }
            if (!serverIP.getText().matches("\\d{3}\\.\\d{3}\\.\\d{1,3}\\.\\d{1,3}")) { // parsing serverIP
                serverIP.setText("192.168.");
                status.setText("wrong serverIP format");
                return;
            }
            int port = Integer.parseInt(portNumber.getText()); // parsing port
            if (port < 4000) {
                status.setText("use ports 4000 and higher");
                return;
            }
            client.setUsername(username.getText());
            client.setHost(serverIP.getText());
            client.setPortNumber(port);
            client.startClient();
            status.setTextFill(Color.GREEN);
            status.setText("server active");
            myController.setScreen("chat");
            System.out.println("success, server run already");
        } catch (NumberFormatException e) {
            e.getStackTrace();
            status.setText("wrong port format");
            portNumber.setText("");
        } catch (IOException e) {
            server = new Server(Integer.parseInt(portNumber.getText()));
            try {
                client.startClient();
                status.setTextFill(Color.GREEN);
                status.setText("server created");
                myController.setScreen("chat");
                System.out.println("success, created new server");
            } catch (IOException ex) {
                status.setText("connection failure");
                System.out.println("failed when creating server");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = Main.getClient();

        username.requestFocus();

        connectBtn.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                create(new ActionEvent());
            }
        });
        username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                create(new ActionEvent());
            }
        });
        serverIP.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                create(new ActionEvent());
            }
        });
        portNumber.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                create(new ActionEvent());
            }
        });
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
