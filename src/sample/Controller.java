package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML AnchorPane rootPane;
    @FXML Label currentMoney;
    @FXML ImageView startButton, scoresButton, endButton;

    private Money money;
    private boolean run = false;
    private boolean isShowing = false;

    public static Controller controller;

    public void loadGame() throws IOException {
        if (!isShowing){
            if (registration()) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("sampleGame.fxml"));
                rootPane.getChildren().setAll(pane);
            }
        }
    }

    public void exit(){
        Platform.exit();
    }

    public boolean registration() throws IOException {
        Parent rooter = FXMLLoader.load(getClass().getResource("sampleBet.fxml"));
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setTitle("Zadaj vklad");
        stage.setScene(new Scene(rooter, 250, 250));
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ControllerGame.URL+"icon.png")));
        isShowing = true;
        stage.showAndWait();
        isShowing = false;
        money.loadMoney();
        if (money.getSum() >= 1 && run) {
            setRun(false);
            return true;
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = this;
        money = new Money();
        money.loadMoney();
        currentMoney.setText("Aktuálne máš " + money.getSum() + " €");
        Main.handleOpacity(startButton);
        Main.handleOpacity(scoresButton);
        Main.handleOpacity(endButton);
    }

    public void loadScores() throws IOException {
        Parent rooter = FXMLLoader.load(getClass().getResource("sampleScore.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Scores");
        stage.setScene(new Scene(rooter,350,350));
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ControllerGame.URL+"icon.png")));
        stage.setResizable(false);
        stage.show();
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
