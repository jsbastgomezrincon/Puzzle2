package puzzle2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Puzzle2 extends Application {    
    private Button btnIniciar;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("puzzle.fxml"));
        Parent root = fxmlLoader.load();
        PuzzleController puzzleController = fxmlLoader.getController();
        
        puzzleController.initialize();

        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.setTitle("Puzzle Game");

        // Establecer color de fondo
        root.setStyle("-fx-background-color: #FFEFC2;");

        primaryStage.show();
    }
}
