package puzzle2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Puzzle2 extends Application {
    
    public static void ejecutarJuego(String[] args) {
        launch(args); // Ejecuta la aplicación JavaFX
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //representa la ventana principal de la aplicación
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("puzzle.fxml"));
        Parent root = fxmlLoader.load(); // Carga el archivo FXML y crea el árbol de nodos JavaFX
        PuzzleController puzzleController = fxmlLoader.getController(); // Obtiene el controlador asociado al archivo FXML
        
        puzzleController.initialize();  // Inicializa el controlador del puzzle, el que permite acceder y controlar los 
                                        //elementos y comportamientos definidos en el archivo FXML

        primaryStage.setScene(new Scene(root, 400, 300)); // Crea una nueva escena con el nodo raíz y establece sus dimensiones
        primaryStage.setTitle("Puzzle Game"); // Establece el título de la ventana principal
        primaryStage.resizableProperty().setValue(Boolean.FALSE); // Hace que la ventana no se pueda maximizar

        
        root.setStyle("-fx-background-color: #FFEFC2;"); // Establecer color de fondo de la escena a un tono de amarillo claro
        primaryStage.show(); // Muestra la ventana principal
    }
}
