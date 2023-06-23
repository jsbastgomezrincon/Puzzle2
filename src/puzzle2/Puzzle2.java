package puzzle2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Puzzle2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("puzzle.fxml"));
        Parent root = fxmlLoader.load();
        PuzzleController puzzleController = fxmlLoader.getController();
        
        puzzleController.initialize();

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Puzzle Game");
        primaryStage.resizableProperty().setValue(Boolean.FALSE);

        // Establecer color de fondo
        root.setStyle("-fx-background-color: #FFEFC2;");

        primaryStage.show();
    }
}
