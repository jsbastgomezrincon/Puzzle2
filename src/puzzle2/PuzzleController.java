/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

/*Los botones están etiquetados según su posición en una matriz de 3x3. Cada botón 
se declara con la anotación @FXML, que indica que estos elementos son controlados 
por el archivo FXML y están vinculados a elementos en la interfaz gráfica.
Cada botón esta en la posición (x, y) de la matriz
 */
public class PuzzleController {
    private ObservableList<String> lstNiveles = FXCollections.observableArrayList("x", "+", "-");
    @FXML
    public Button button00;
    @FXML
    public Button button01;
    @FXML
    public Button button02;
    @FXML
    public Button button10;
    @FXML
    public Button button11;
    @FXML
    public Button button12;
    @FXML
    public Button button20;
    @FXML
    public Button button21;
    @FXML
    public Button button22;
    @FXML
    public Button btnIniciar;
    @FXML
    public Button btnSalir;
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public Label label4;
    @FXML
    public ChoiceBox<String> niveles;
    private static final int EMPTY_CELL = 0;
    private static final String MOVING_BUTTON_STYLE = "-fx-background-color: #FF0000;";
    private static final int SIZE = 3;
    private int[][] board = new int[SIZE][SIZE];
    private Button[][] buttons = new Button[SIZE][SIZE];

    public void initialize() {
        initializeBoard();
        btnIniciar.setOnAction(e -> generarOperacionesYPuzzle());
        btnSalir.setOnAction(e -> salirDelJuego());
        niveles.setItems(lstNiveles);
        niveles.setValue("x"); // Valor predeterminado
        niveles.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            generarOperacionesYPuzzle();
        });
    }

    public void generarOperacionesYPuzzle() {
        String seleccion = niveles.getValue();

        List<Integer> multiplicandos = new ArrayList<>();
        List<Integer> multiplicadores = new ArrayList<>();
        List<Integer> resultados = new ArrayList<>();

        // Generar operaciones y resultados según la selección
        if (seleccion.equals("x")) {
            for (int i = 0; i < 4; i++) {
                int multiplicando = (int) (Math.random() * 9) + 1;
                int multiplicador = (int) (Math.random() * 9) + 1;
                int resultado = multiplicando * multiplicador;

                multiplicandos.add(multiplicando);
                multiplicadores.add(multiplicador);
                resultados.add(resultado);
            }
        } else if (seleccion.equals("+")) {
            for (int i = 0; i < 4; i++) {
                int sumando1 = (int) (Math.random() * 9) + 1;
                int sumando2 = (int) (Math.random() * 9) + 1;
                int resultado = sumando1 + sumando2;

                multiplicandos.add(sumando1);
                multiplicadores.add(sumando2);
                resultados.add(resultado);
            }
        } else if (seleccion.equals("-")) {
            for (int i = 0; i < 4; i++) {
                int minuendo = (int) (Math.random() * 9) + 1;
                int sustraendo = (int) (Math.random() * 9) + 1;

                // Asegurarse de que el resultado de la resta sea positivo
                int resultado = Math.max(minuendo, sustraendo) - Math.min(minuendo, sustraendo);

                multiplicandos.add(minuendo);
                multiplicadores.add(sustraendo);
                resultados.add(resultado);
            }
        }

        // Asignar resultados a los labels
        label1.setText(String.valueOf(resultados.get(0)));
        label2.setText(String.valueOf(resultados.get(1)));
        label3.setText(String.valueOf(resultados.get(2)));
        label4.setText(String.valueOf(resultados.get(3)));

        // Asignar números al puzzle
        List<Integer> numerosPuzzle = new ArrayList<>(multiplicandos);
        numerosPuzzle.addAll(multiplicadores);
        
        Collections.shuffle(numerosPuzzle);

        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = buttons[i][j];
                if (index < numerosPuzzle.size()) {
                    int number = numerosPuzzle.get(index++);
                    button.setText(String.valueOf(number));
                } else {
                    button.setText(""); // Dejar el último botón en blanco
                }
            }
        }
    }
    
    public void initializeBoard() {
        
        int count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = count++;
            }
        }
        board[SIZE - 1][SIZE - 1] = EMPTY_CELL; // Espacio en blanco
        buttons = new Button[][] {
            { this.button00, this.button01, this.button02 },
            { this.button10, this.button11, this.button12 },
            { this.button20, this.button21, this.button22 }
        };
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = buttons[i][j];
                // Aumentar el tamaño de fuente
                button.setStyle("-fx-font-size: 24px;");
                // Centrar el texto horizontalmente y verticalmente
                button.setStyle("-fx-alignment: center;");
                button.setOnAction(e -> moveNumber(button));
            }
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
                    Thread.sleep(200); // Retrasar el cambio de estilo durante 0.5 segundos
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

    private void salirDelJuego() {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?","Salir",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
        switch(respuesta){
            case 0:
            Platform.exit();
            break;
        case 1:
            break;
        case 2:
            break;
        default:
            break;
        }
    }
}
