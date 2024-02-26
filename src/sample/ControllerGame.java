package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class ControllerGame implements Initializable {

    public ControllerGame(){
    }

    @FXML AnchorPane pane;
    @FXML ImageView playerCard1, playerCard2, croupierCard1, croupierCard2;
    @FXML ImageView animatedCard1, animatedCard2, animatedCard3, animatedCard4;
    @FXML ImageView backButton, addButton, decrementButton, giveButton, moveButton, doubleButton, resultButton, playAgainButton;
    @FXML HBox hboxPlayer;
    @FXML HBox hboxCroupier;
    @FXML TextArea playerValue, croupierValue;
    @FXML TextArea gameResult;
    @FXML Label sumLabel;

    private ArrayList<Card> pack = new ArrayList<>();
    private Money money;
    private Image image;
    private Double strike, ratio;
    private int gameStarted;
    private boolean start;
    private boolean r;
    private int intAnimationCroupier;
    private int intAnimationPlayer;
    private Player player;
    private Player croupier;
    private Image sampleImage = new Image(getClass().getResourceAsStream(URL+"cardSample.jpg"));
    private ImageView imageView = new ImageView();

    public static final String URL = "/resources/";

    public void loadHome() throws IOException {
        if (gameStarted == 1){
            money.giveUp(strike);
        }
        if (gameStarted == 2){
            money.loss(strike);
        }
        AnchorPane pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.pane.getChildren().setAll(pane);
    }

    public void loadCards(){
        for (int i = 2; i < 15; i++) {
            for (int j = 0; j < 4; j++) {
                pack.add(newCard(i, j));
            }
        }
        Collections.shuffle(pack);
    }

    public Image dealCards(String who) {
        if (who.equals("player")) {
            player.setValue(player.getValue()+Integer.parseInt(parseValue(pack.get(0).getValue())));
            if (Integer.parseInt(parseValue(pack.get(0).getValue()))==11){
                player.setEso(player.getEso()+1);
            }
            player.addCard(pack.get(0));
            player.testEso();
        }
        if (who.equals("croupier")){
            croupier.setValue(croupier.getValue()+Integer.parseInt(parseValue(pack.get(0).getValue())));
            if (Integer.parseInt(parseValue(pack.get(0).getValue()))==11){
                croupier.setEso(croupier.getEso()+1);
            }
            croupier.addCard(pack.get(0));
            croupier.testEso();
        }
        Image image = new Image(getClass().getResourceAsStream(URL+ pack.get(0).getValue()+ pack.get(0).getType()+".png"));
        Card card = pack.get(0);
        pack.remove(0);
        pack.add(card);
        return image;
    }

    private Card newCard(int i, int j){
        return new Card(Integer.toString(i), j);
    }

    static String parseValue(String value){
        if (value.equals("A")){
            value = "11";
        }
        else if (value.equals("J") || value.equals("Q") || value.equals("K")){
            value = "10";
        }
        return value;
    }

    private void end(){
        gameStarted = 0;
        doubleButton.setVisible(false);
        moveButton.setVisible(false);
        resultButton.setVisible(false);
        playAgainButton.setVisible(true);
    }

    public void test() {
        if (croupier.getValue()>21){
            gameResult.setVisible(true);
            gameResult.setText("Vyhral si " + strike.toString()+"€\nHodnota tvojich kariet: "+ player.getValue()+"\nHodnota krupierových kariet: "+ croupier.getValue());
            money.win(strike);
            sumLabel.setText("Teraz máš\n" + money.getSum() + "€");
        }
        else {
            if (player.getValue()>21){
                gameResult.setVisible(true);
                gameResult.setText("Prehral si " + strike.toString()+"€\nHodnota tvojich kariet: "+ player.getValue()+"\nHodnota krupierových kariet: "+ croupier.getValue());
                money.loss(strike);
                sumLabel.setText("Teraz máš\n" + money.getSum() + "€");
            }
            else {
                if (croupier.getValue() > player.getValue()) {
                    gameResult.setVisible(true);
                    gameResult.setText("Prehral si " + strike.toString()+"€\nHodnota tvojich kariet: "+ player.getValue()+"\nHodnota krupierových kariet: "+ croupier.getValue());
                    money.loss(strike);
                    sumLabel.setText("Teraz máš\n" + money.getSum() + "€");
                } else if (player.getValue() > croupier.getValue()) {
                    gameResult.setVisible(true);
                    gameResult.setText("Vyhral si " + strike.toString()+"€\nHodnota tvojich kariet: "+ player.getValue()+"\nHodnota krupierových kariet: "+ croupier.getValue());
                    money.win(strike);
                    sumLabel.setText("Teraz máš\n" + money.getSum() + "€");
                } else {
                    gameResult.setVisible(true);
                    gameResult.setText("Remíza\nHodnota tvojich kariet: "+ player.getValue()+"\nHodnota krupierových kariet: "+ croupier.getValue());
                    sumLabel.setText("Teraz máš\n" + money.getSum() + "€");
                }
            }
        }
        end();
    }

    public boolean testBlackJack(int hodnota,int eso, String who) {
        if (hodnota==21&&eso==1){
            croupierCard2.setImage(image);
            if (who.equals("player")){
                strike = strike * 1.5;
            }
            test();
            return true;
        }
        return false;
    }

    private void playerMove() {
        gameStarted = 2;
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(100);
        hboxPlayer.getChildren().add(imageView);
        imageView.setImage(dealCards("player"));
        playerValue.setText(String.valueOf(player.getValue()));
        if (player.getValue()>21) {
            croupierCard2.setImage(image);
            test();
        }
    }

    public void playerMoveAnimation() {
        cardMoveAnimation("player");
    }

    private void croupierMove() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(100);
        hboxCroupier.getChildren().add(imageView);
        imageView.setImage(dealCards("croupier"));
        croupier.testEso();
        croupierValue.setText(String.valueOf(croupier.getValue()));
        if (croupier.getValue()>16){
            test();
        }
        else cardMoveAnimation("croupier");
    }

    public void cardMoveAnimation(String who) {
        r = false;

        ratio = (playerCard1.getParent().getLayoutY() - imageView.getLayoutY())/(playerCard1.getParent().getLayoutX() + intAnimationPlayer - imageView.getLayoutX());

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (who.equals("player")) {

                    if (imageView.getLayoutX() <= playerCard1.getParent().getLayoutX() + intAnimationPlayer) {
                        imageView.setLayoutX(imageView.getLayoutX() + 4);
                    }

                    if (imageView.getLayoutY() <= playerCard1.getParent().getLayoutY()) {
                        imageView.setLayoutY(imageView.getLayoutY() + 4* ratio);
                    }

                    if (imageView.getLayoutX() >= playerCard1.getParent().getLayoutX() + intAnimationPlayer) {
                        r = true;
                    }
                }
                if (who.equals("croupier")){

                    if (imageView.getLayoutX() <= croupierCard1.getParent().getLayoutX() + intAnimationCroupier) {
                        imageView.setLayoutX(imageView.getLayoutX() + 4);
                    }

                    if (imageView.getLayoutX() >= croupierCard1.getParent().getLayoutX() + intAnimationCroupier) {
                        r = true;
                    }
                }
                if (r){
                    this.stop();
                    imageView.setLayoutY(26.0);
                    imageView.setLayoutX(28.0);
                    if (who.equals("player")) {
                        playerMove();
                        intAnimationPlayer += 100;
                    }
                    if (who.equals("croupier")){
                        croupierMove();
                        intAnimationCroupier +=100;
                    }

                }
            }
        };
        animationTimer.start();
    }

    public void animation(){
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (animatedCard1.getLayoutX()<= playerCard1.getParent().getLayoutX()) {
                    animatedCard1.setLayoutX(animatedCard1.getLayoutX() + 3);
                }
                if (animatedCard1.getLayoutY()<= playerCard1.getParent().getLayoutY()) {
                    animatedCard1.setLayoutY(animatedCard1.getLayoutY() + 3);
                }
                if (animatedCard2.getLayoutX()-100<= playerCard2.getParent().getLayoutX()) {
                    animatedCard2.setLayoutX(animatedCard2.getLayoutX() + 3);
                }
                if (animatedCard2.getLayoutY()>= playerCard2.getParent().getLayoutY()) {
                    start =true;
                }
                if (animatedCard2.getLayoutY()<= playerCard2.getParent().getLayoutY()) {
                    animatedCard2.setLayoutY(animatedCard2.getLayoutY() + 3);
                }
                if (animatedCard3.getLayoutX()<= croupierCard1.getParent().getLayoutX()) {
                    animatedCard3.setLayoutX(animatedCard3.getLayoutX() + 3);
                }
                if (animatedCard3.getLayoutY()<= croupierCard1.getParent().getLayoutY()) {
                    animatedCard3.setLayoutY(animatedCard3.getLayoutY()+3);
                }
                if (animatedCard4.getLayoutX()-100<= croupierCard2.getParent().getLayoutX()) {
                    animatedCard4.setLayoutX(animatedCard4.getLayoutX() + 3);
                }
                if (animatedCard4.getLayoutY()<= croupierCard2.getParent().getLayoutY()) {
                    animatedCard4.setLayoutY(animatedCard4.getLayoutY() + 3);
                }
                if(start){
                    this.stop();
                    startRound();
                }
            }
        };
        animationTimer.start();
    }

    public void makeResult() {
        boolean ok = false;
        resultButton.setVisible(false);
        doubleButton.setVisible(false);
        moveButton.setVisible(false);
        croupierCard2.setImage(image);
        croupier.testEso();
        croupierValue.setText(String.valueOf(croupier.getValue()));
        if (player.getCards().size()==2){
            ok = testBlackJack(player.getValue(), player.getEso(),"player");
        }
        if (!ok){
            if (croupier.getValue()>16){
                test();
            }
            else {
                cardMoveAnimation("croupier");
            }
        }
    }

    public void doubleStrike() {
        if (strike *2 <= money.getSum()){
            strike *= 2;
            sumLabel.setText(strike+"€");
            doubleButton.setVisible(false);
            gameStarted = 2;
            playerMoveAnimation();
        }
    }

    public void addStrike() {
        if (strike +1<= money.getSum()){
            strike += 1;
            sumLabel.setText(strike+"€");
        }
    }

    public void reduceStrike() {
        if (strike -1>=1){
            strike -= 1;
            sumLabel.setText(strike+"€");
        }
    }

    public void startGame() {
        addButton.setVisible(false);
        decrementButton.setVisible(false);
        giveButton.setVisible(false);
        animation();
    }

    private void startRound(){
        boolean ok = false;
        try {
            animatedCard1.setVisible(false);
            animatedCard2.setVisible(false);
            animatedCard3.setVisible(false);
            animatedCard4.setVisible(false);
            playerCard1.setImage(dealCards("player"));
            croupierCard1.setImage(dealCards("croupier"));
            playerCard2.setImage(dealCards("player"));
            croupierValue.setText(String.valueOf(croupier.getValue()));
            image = dealCards("croupier");
            ok = testBlackJack(croupier.getValue(), croupier.getEso(),"croupier");
        } catch (Exception e) {
            e.printStackTrace();
        }
        croupierCard2.setVisible(true);
        if (!ok){
            gameStarted = 1;
            playerValue.setText(String.valueOf(player.getValue()));
            doubleButton.setVisible(true);
            croupierValue.setVisible(true);
            playerValue.setVisible(true);
            moveButton.setVisible(true);
            resultButton.setVisible(true);
        }
    }

    private void hide(){
        gameResult.setVisible(false);
        doubleButton.setVisible(false);
        playerValue.setVisible(false);
        croupierValue.setVisible(false);
        croupierCard2.setVisible(false);
        moveButton.setVisible(false);
        resultButton.setVisible(false);
        playAgainButton.setVisible(false);
    }

    private void buttonEvents(){
        Main.handleOpacity(backButton);
        Main.handleOpacity(addButton);
        Main.handleOpacity(decrementButton);
        Main.handleOpacity(giveButton);
        Main.handleOpacity(moveButton);
        Main.handleOpacity(resultButton);
        Main.handleOpacity(doubleButton);
        Main.handleOpacity(playAgainButton);
    }

    public void playAgain(){
        if (money.getSum()>=1){
            gameStarted = 0;
            start = false;
            r = false;
            strike = 1.0;
            intAnimationCroupier = 200;
            intAnimationPlayer = 200;
            hide();
            player.clearCards();
            croupier.clearCards();
            addButton.setVisible(true);
            decrementButton.setVisible(true);
            giveButton.setVisible(true);
            setPosition(animatedCard1);
            setPosition(animatedCard2);
            setPosition(animatedCard3);
            setPosition(animatedCard4);
            playerCard1.setImage(null);
            playerCard2.setImage(null);
            croupierCard1.setImage(null);

            image = new Image(getClass().getResourceAsStream(URL+"cardSample.jpg"));
            croupierCard2.setImage(image);

            hboxCroupier.getChildren().clear();
            hboxPlayer.getChildren().clear();
            hboxPlayer.getChildren().add(playerCard1);
            hboxPlayer.getChildren().add(playerCard2);
            hboxCroupier.getChildren().add(croupierCard1);
            hboxCroupier.getChildren().add(croupierCard2);
            sumLabel.setText(strike.toString()+"€");
        }
        else {
            gameResult.setText("\nNo money, no funny");
        }
    }

    private void setPosition(ImageView img){
        img.setLayoutX(28);
        img.setLayoutY(26);
        img.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonEvents();
        strike = 1.0;
        player = new Player(0, 0);
        croupier = new Player(0, 0);
        money = new Money();
        money.loadMoney();
        playAgain();
        imageView.setImage(sampleImage);
        imageView.setLayoutY(26.0);
        imageView.setLayoutX(28.0);
        imageView.setFitHeight(150);
        imageView.setFitWidth(100);
        pane.getChildren().add(imageView);
        loadCards();
    }
}