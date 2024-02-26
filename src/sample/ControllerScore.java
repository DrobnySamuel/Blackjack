package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ControllerScore implements Initializable {
    @FXML AnchorPane pane;
    @FXML private TableView<Row> tableView;
    @FXML private TableColumn<Row, String> date, event, sum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setCellValueFactory(new PropertyValueFactory<Row, String>("date"));
        event.setCellValueFactory(new PropertyValueFactory<Row, String>("event"));
        sum.setCellValueFactory(new PropertyValueFactory<Row, String>("sum"));
        setRows();
        date.setStyle( "-fx-alignment: CENTER;");
        event.setStyle( "-fx-alignment: CENTER;");
        sum.setStyle( "-fx-alignment: CENTER;");
    }

    public void setRows(){
        try {
            StringBuilder v = new StringBuilder();
            String [] results;
            Scanner sc = new Scanner(new File("database.dat"));
            while (sc.hasNext()){
                v.append(sc.nextLine()).append(";");
            }
            results = v.toString().split(";");
            sc.close();
            String[] a;
            for (int j = results.length-1; j >= 0; j--) {
                a = results[j].split("-");
                if (results[j].contains("Výhra")){
                    tableView.getItems().add(new Row(a[0], a[1], a[2]+"€"));
                }
                else if (results[j].contains("Prehra")){
                    tableView.getItems().add(new Row(a[0], a[1], a[2]+"€"));
                }
                else if (results[j].contains("Vklad")){
                    tableView.getItems().add(new Row(a[0], a[1], a[2]+"€"));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("file database.dat does not exist yet");
        }
    }
}