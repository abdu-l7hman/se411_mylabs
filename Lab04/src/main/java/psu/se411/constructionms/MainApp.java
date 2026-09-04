package psu.se411.constructionms;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("SE411 - Construction Management System");

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
