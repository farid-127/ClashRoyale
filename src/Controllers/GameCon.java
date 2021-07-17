package Controllers;

import Main.Config;
import Models.Cards.CardImage;
import Models.Graphic.FXManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class GameCon implements Controller{
    @FXML
    private GridPane deck;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;
    @FXML
    private Label opponentName;

    @FXML
    private Label opponentLevel;

    @FXML
    private Label opponentHp;

    @FXML
    private Label name;

    @FXML
    private Label level;

    @FXML
    private Label hp;

    @FXML
    private HBox mainBorder;

    @FXML
    private ImageView next;

    @FXML
    private Pane boardPane;


    @FXML
    public void initialize() {
        FXManager.setBackground(FXManager.getImage("/Game/jungle.jpg"), mainBorder);
        setCardsImages();
        name.setText(Config.client.getName());
        level.setText("" + Config.client.getLevel());
        hp.setText("MAX");
        Platform.runLater(() -> {
            FXManager.setStageReadyForGame(Config.primaryStage);
        });
    }

    private void setCardsImages() {
        card1.setImage(CardImage.find(Config.client.getDeckCards().get(0)).getImage());
        card2.setImage(CardImage.find(Config.client.getDeckCards().get(0)).getImage());
        card3.setImage(CardImage.find(Config.client.getDeckCards().get(0)).getImage());
        card4.setImage(CardImage.find(Config.client.getDeckCards().get(0)).getImage());
        next.setImage(CardImage.find(Config.client.getDeckCards().get(4)).getImage());
    }
    @Override
    public void actionHandler(ActionEvent ae) throws Exception {

    }
    @FXML
    private void dragHandler(DragEvent de) {

    }
//    @FXML
//    private void dragDetectionHandler(MouseEvent me) {
//        if ()
//    }
}
