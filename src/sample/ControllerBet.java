package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerBet implements Initializable {

    @FXML TextField textField;
    @FXML Text currentMoney;
    @FXML AnchorPane pane;
    @FXML ImageView startButton;

    private double sum;
    private Money money;

    public void bet() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        money.loadMoney();
        this.sum = 0;
        String text = textField.getText().replaceAll(" ","").replaceAll(",",".");
        if (check(text) && !text.isEmpty()){
            this.sum = Double.parseDouble(text);
        }
        if (getSum()!=null&& getSum()>0){
            money.setSum(money.getSum() + getSum());
            write();
            BufferedWriter bw = new BufferedWriter(new FileWriter("money.dat"));
            bw.write(String.valueOf(money.getSum()));
            Controller.controller.setRun(true);
            bw.close();
        }
        if (money.getSum()>=1){
            Controller.controller.setRun(true);
            stage.close();
        }
    }

    private boolean check(String text){
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))){
                if (!(text.charAt(i)=='.')){
                    return false;
                }
            }
        }
        return true;
    }

    public Double getSum() {
        return sum;
    }

    private void write(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("database.dat",true));
            bw.write(money.getDate() + "-Vklad-"+ getSum()+"\n");
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textField.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.ENTER){
                try {
                    bet();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        money = new Money();
        money.loadMoney();
        currentMoney.setText("Aktuálne máš " + money.getSum() + "€");
        Main.handleOpacity(startButton);
    }
}
