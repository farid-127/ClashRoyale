package Controllers;

import Main.Config;
import Models.Cards.Card;
import Models.Cards.CardImage;
import Models.Cards.troops.Troop;
import Models.GameManager.Game;
import Models.Graphic.FXManager;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class GameCon implements Controller {
    private static Pane staticBoardPane;
    private Card chosenCard;
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
    private ImageView blueKingTower;

    @FXML
    private ImageView redKingTower;

    @FXML
    private ImageView blueLeftPrincessTower;

    @FXML
    private ImageView redLeftPrincessTower;

    @FXML
    private ImageView redRightPrincessTower;

    @FXML
    private ImageView blueRightPrincessTower;

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
    private Label timerLabel;

    @FXML
    private ProgressBar elixirBar;

    @FXML
    private ImageView leftKingImageView;

    @FXML
    private ImageView rightKingImageView;

    private Timer timer;

    private LocalTime localTime;

    @FXML
    public void initialize() {
//        leftKingImageView.setImage(FXManager.getImage("/Game/leftKings.png"));
//        rightKingImageView.setImage(FXManager.getImage("/Game/rightKings.png"));
        FXManager.setBackground(FXManager.getImage("/Game/jungle2.jpg"), mainBorder);
        setCardsImages();
        name.setText(Config.client.getName());
        level.setText("" + Config.client.getLevel());
        hp.setText("MAX");
        Platform.runLater(() -> {
            FXManager.setStageReadyForGame(Config.primaryStage);
        });
        staticBoardPane = boardPane;
        startTimer();
        Runnable runnable = (Runnable) Game.getInstance().getManager();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void startTimer() {
        timer = new Timer();
        localTime = LocalTime.of(0, 3, 0);
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        timerAdvance();
                        elixirAdvance();
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(1000.0);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void timerAdvance() {
        localTime = localTime.minusSeconds(1);
        timerLabel.setText(localTime.format(DateTimeFormatter.ofPattern("mm:ss")));
        if (localTime.isBefore(LocalTime.of(0, 0, 1))) {
            timer.cancel();
            Game.getInstance().finish();
        }
    }

    private void elixirAdvance() {
        if (elixirBar.getProgress() < 1)
            elixirBar.setProgress(elixirBar.getProgress() + 0.1);
        Game.getInstance().getPlayer1().setElixirs((int) elixirBar.getProgress() * 10);
    }

    private void setCardsImages() {
        card1.setImage(CardImage.find(Config.client.getDeckCards().get(0)).getImage());
        card2.setImage(CardImage.find(Config.client.getDeckCards().get(1)).getImage());
        card3.setImage(CardImage.find(Config.client.getDeckCards().get(2)).getImage());
        card4.setImage(CardImage.find(Config.client.getDeckCards().get(3)).getImage());
        next.setImage(CardImage.find(Config.client.getDeckCards().get(4)).getImage());
    }
    @Override
    public void actionHandler(ActionEvent ae) throws Exception {

    }
    @FXML
    private void dragHandler(DragEvent de) {

    }
    @FXML
    private void dragDetectionHandler(MouseEvent me) {
        if (!(me.getSource() instanceof ImageView)) {
            return;
        }
        Dragboard db = ((ImageView) me.getSource()).startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        chosenCard = CardImage.find(((ImageView) me.getSource()).getImage()).getCard();
        cb.putImage(((ImageView) me.getSource()).getImage());
        db.setContent(cb);
        me.consume();
    }
    @FXML
    private void dragOverHandler(DragEvent de) {
        if (de.getDragboard().hasImage()) {
            de.acceptTransferModes(TransferMode.ANY);
        }
    }
    @FXML
    private void dragDroppedHandler(DragEvent de) {
        try {
            Game.getInstance().bornCard(chosenCard, new Point2D(de.getX(), de.getY())
                    , new Point2D(redRightPrincessTower.getLayoutX(), redRightPrincessTower.getLayoutY()), boardPane);
            Game.getInstance().playCardPlayer1(chosenCard);
            setCardsImages();
            chosenCard = null;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Pane getStaticBoardPane() {
        return staticBoardPane;
    }
}
