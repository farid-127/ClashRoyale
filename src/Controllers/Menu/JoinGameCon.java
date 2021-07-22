package Controllers.Menu;

import Main.Config;
import Models.GameManager.*;
import Models.Graphic.FXManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class JoinGameCon {

    @FXML
    private VBox mainPane;

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private Label warningLabel;

    private Socket socket;

    public void initialize() {
        FXManager.setBackground(FXManager.getImage("/BackGrounds/PlayGame.jpg"), mainPane);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        FXManager.goTo("1v1.fxml", Config.primaryStage);
    }

    @FXML
    void connectButtonOnAction(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String ip = ipTextField.getText();
                    int port = Integer.parseInt(portTextField.getText());
                    socket = new Socket(ip, port);
                    HumanManager humanManager = new HumanManager(socket, NetworkClient.JOIN);
                    new Game(new Player(Config.client.getDeckCards()), humanManager.getPlayer(), GameMode.MULTI, humanManager);
                    Config.mediaPlayer.stop();
                    FXManager.goTo("game.fxml", Config.primaryStage);
                } catch (NumberFormatException e) {
                    warningLabel.setText("Port is a NUMBER ://");
                    warningLabel.setVisible(true);
                } catch (UnknownHostException e) {
                    warningLabel.setText("The Host is Unknown.");
                    warningLabel.setVisible(true);
                } catch (IOException e) {
                    warningLabel.setText("The IP or Port may be incorrect.");
                    warningLabel.setVisible(true);
                }
            }
        });

    }

}
