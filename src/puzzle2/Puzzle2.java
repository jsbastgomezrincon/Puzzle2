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
    private static final int SIZE = 3;
    private static final int EMPTY_CELL = 0;
    private static final String MOVING_BUTTON_STYLE = "-fx-background-color: #FF0000;";

    private int[][] board = new int[SIZE][SIZE];
    private Button[][] buttons = new Button[SIZE][SIZE];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("puzzle.fxml"));
        Parent root = fxmlLoader.load();
        PuzzleController puzzleController = fxmlLoader.getController();
        
        initializeBoard();
        initializeButtons(puzzleController);

        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.setTitle("Puzzle Game");

        // Establecer color de fondo
        root.setStyle("-fx-background-color: #FFEFC2;");

        primaryStage.show();
    }

    private void initializeBoard() {
        int count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = count++;
            }
        }
        board[SIZE - 1][SIZE - 1] = EMPTY_CELL; // Espacio en blanco
    }

    private void initializeButtons(PuzzleController puzzleController) {
        Button[][] buttons = {
            { puzzleController.button00, puzzleController.button01, puzzleController.button02 },
            { puzzleController.button10, puzzleController.button11, puzzleController.button12 },
            { puzzleController.button20, puzzleController.button21, puzzleController.button22 }
        };

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= SIZE * SIZE - 1; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = buttons[i][j];
                if (index < numbers.size()) {
                    int number = numbers.get(index++);
                    button.setText(String.valueOf(number));
                } else {
                    button.setText(""); // Dejar el último botón en blanco
                }
                
                // Aumentar el tamaño de fuente
                button.setStyle("-fx-font-size: 24px;");
                
                // Centrar el texto horizontalmente y verticalmente
                button.setStyle("-fx-alignment: center;");
                
                button.setOnAction(e -> moveNumber(button));
            }
        }
        this.buttons = buttons;
    }

    private void moveNumber(Button selectedButton) {
        if(selectedButton.getText().equals("")) return; // Ignorar el botón en blanco
        int number = Integer.parseInt(selectedButton.getText());

        int[] emptyCellPosition = findEmptyCell();
        int row = GridPane.getRowIndex(selectedButton);
        int col = GridPane.getColumnIndex(selectedButton);
        if (Math.abs(row - emptyCellPosition[0]) + Math.abs(col - emptyCellPosition[1]) == 1) {
            board[emptyCellPosition[0]][emptyCellPosition[1]] = number;
            board[row][col] = EMPTY_CELL;
            buttons[emptyCellPosition[0]][emptyCellPosition[1]].setText(String.valueOf(number));
            selectedButton.setText("");
            
            selectedButton.setStyle(MOVING_BUTTON_STYLE);
            buttons[emptyCellPosition[0]][emptyCellPosition[1]].setStyle(MOVING_BUTTON_STYLE);
            
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    buttons[i][j].setDisable(true); // Desactivar botones mientras se realiza el movimiento
                }
            }
            
            // Lógica para el retraso del cambio de estilo y reactivación de botones
            new Thread(() -> {
                try {
                    Thread.sleep(500); // Retrasar el cambio de estilo durante 0.5 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        buttons[i][j].setDisable(false); // Reactivar botones
                    }
                }
                
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        buttons[i][j].setStyle(""); // Restablecer el estilo por defecto de los botones
                    }
                }
            }).start();
        }
    }

    private int[] findEmptyCell() {
        int[] emptyCell = new int[2];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    emptyCell[0] = i;
                    emptyCell[1] = j;
                    break;
                }
            }
        }

        return emptyCell;
    }
}
