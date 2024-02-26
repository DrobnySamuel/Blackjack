package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root, 850,500);
        stage.setTitle("BlackJack");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ControllerGame.URL+"icon.png")));
        stage.show();
    }

    public static void handleOpacity(ImageView image){

        image.setOpacity(0.92);

        image.setOnMouseMoved(e -> image.setOpacity(1.0));
        image.setOnMouseExited(e -> image.setOpacity(0.92));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
