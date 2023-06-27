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
    // Declaración de variables y elementos de la interfaz
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
    public Label clicCounter;
    @FXML
    public ChoiceBox<String> niveles;
    private final ObservableList<String> lstNiveles = FXCollections.observableArrayList("x", "+", "-");
    private static final int EMPTY_CELL = 0;
    private static final String MOVING_BUTTON_STYLE = "-fx-background-color: #FF0000;";
    private List<Integer> resultados;
    List<Integer> multiplicandos;
    List<Integer> multiplicadores;
    private static final int SIZE = 3;
    private final int[][] board = new int[SIZE][SIZE];
    private Button[][] buttons = new Button[SIZE][SIZE];
    private Label[] labels = new Label[4];

    public void initialize() { // Este método se ejecuta cuando se inicializa el controlador
        //Configura los componentes de la interfaz de usuario y agrega los manejadores de eventos a los botones
        clicCounter.setText("0"); // Establece el contador de clics en 0
        initializeBoard(); // Inicializa el tablero
        btnIniciar.setOnAction(e -> generarOperacionesYPuzzle()); // Configura el evento de clic para el botón "Iniciar"
        btnSalir.setOnAction(e -> salirDelJuego()); // Configura el evento de clic para el botón "Salir"
        niveles.setItems(lstNiveles); // Establece las opciones del ChoiceBox (lista desplegable)
        niveles.setValue("x"); // Establece el valor predeterminado del ChoiceBox
        niveles.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            generarOperacionesYPuzzle(); // Configura el evento de cambio de selección en el ChoiceBox
        });
    }

    public void generarOperacionesYPuzzle() { // Método para generar las operaciones y el puzzle según la selección del ChoiceBox ("x", "+", "-")
        String seleccion = niveles.getValue(); // Obtiene la selección actual del ChoiceBox

        multiplicandos = new ArrayList<>(); // Lista de multiplicandos
        multiplicadores = new ArrayList<>(); // Lista de multiplicadores
        resultados = new ArrayList<>(); // Lista de resultados
        /*  Se generan 3 operaciones y se calculan los resultados correspondientes
        Los números utilizados en las operaciones se almacenan en las listas `multiplicandos` y `multiplicadores`
        Los resultados se almacenan en la lista `resultados`
        Luego, los resultados se asignan a los labels en la interfaz de usuario, y los números se distribuyen aleatoriamente
        En el puzzle. Por último, se invoca este método al seleccionar un nuevo nivel.
         */
        switch (seleccion) {
            case "x" -> {
                // Generar operaciones de multiplicación
                for (int i = 0; i < 4; i++) {
                    int multiplicando = (int) (Math.random() * 9) + 1;
                    int multiplicador = (int) (Math.random() * 9) + 1;
                    int resultado = multiplicando * multiplicador;
                    
                    multiplicandos.add(multiplicando);
                    multiplicadores.add(multiplicador);
                    resultados.add(resultado);
                }
            }
            case "+" -> {
                // Generar operaciones de suma
                for (int i = 0; i < 4; i++) {
                    int sumando1 = (int) (Math.random() * 9) + 1;
                    int sumando2 = (int) (Math.random() * 9) + 1;
                    int resultado = sumando1 + sumando2;
                    
                    multiplicandos.add(sumando1);
                    multiplicadores.add(sumando2);
                    resultados.add(resultado);
                }
            }
            case "-" -> {
                // Generar operaciones de resta
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
            default -> {
            }
        }

        // Asignar resultados a los labels
        label1.setText(String.valueOf(resultados.get(0)));
        label2.setText(String.valueOf(resultados.get(1)));
        label3.setText(String.valueOf(resultados.get(2)));
        label4.setText(String.valueOf(resultados.get(3)));
        labels = new Label[]{label1, label2, label3, label4};

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
        /* Este método inicializa el tablero del puzzle. Asigna números consecutivos a cada celda del tablero, 
        dejando la última celda vacía. Los botones de la interfaz de usuario se asignan a la matriz `buttons` 
        para acceder a ellos más fácilmente. Además, se configuran los estilos y los manejadores de eventos para los botones.
        */
        int count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = count++;
            }
        }
        board[SIZE - 1][SIZE - 1] = EMPTY_CELL; // Espacio en blanco
        buttons = new Button[][]{
            {this.button00, this.button01, this.button02},
            {this.button10, this.button11, this.button12},
            {this.button20, this.button21, this.button22}
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
        /* Este método busca y devuelve la posición de la celda vacía en el tablero. 
        Recorre todas las celdas del tablero y verifica si su valor es `EMPTY_CELL` (0). 
        Devuelve un arreglo de dos elementos con las coordenadas (fila y columna) de la celda vacía.
        */
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
        /* Este método maneja el movimiento de los números en el puzzle. 
        Se activa cuando se hace clic en un botón. Primero, verifica si el botón seleccionado no está vacío. 
        Luego, obtiene el número del botón y la posición de la celda vacía. 
        Si el botón seleccionado está adyacente a la celda vacía (horizontal o verticalmente), 
        intercambia el número del botón con la celda vacía. Actualiza los textos de los botones correspondientes y 
        realiza el efecto visual de movimiento. Después de eso, verifica si el puzzle está resuelto llamando 
        al método `puzzleSolved()`. Si es así, muestra un mensaje de ganador y reinicia el juego invocando `initialize()`. 
        También incrementa el contador de clics en la etiqueta `clicCounter`. */
        if (selectedButton.getText().equals("")) {
            return; // Ignorar el botón en blanco
        }
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
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Puzzle", JOptionPane.ERROR_MESSAGE);
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
        // Mostrar un mensaje de victoria si se resuelve el rompecabezas
        if (puzzleSolved()) {
            JOptionPane.showMessageDialog(null, "Has ganado con "+clicCounter.getText()+" clics, comenzarás un nuevo juego, escoge tu nivel","Puzzle",JOptionPane.INFORMATION_MESSAGE);
            initialize();
            return;
        }
        String clicCounterValue = clicCounter.getText();
        clicCounter.setText(String.valueOf(Integer.parseInt(clicCounterValue)+1));
        /* Este método actualiza el contador de clics en la etiqueta `clicCounter` de la interfaz de usuario. 
        Se llama después de cada movimiento válido para reflejar el número de movimientos realizados por el jugador. */
    }

    private void salirDelJuego() {
        /*Este método muestra un cuadro de diálogo para confirmar si el jugador desea salir del juego. 
        Dependiendo de la respuesta del jugador, se ejecutan diferentes acciones. 
        Si el jugador confirma la salida, se llama a `Platform.exit()` para cerrar la aplicación. */
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        switch (respuesta) {
            case 0 ->
                Platform.exit();
            case 1 -> {
            }
            case 2 -> {
            }
            default -> {
            }
        }
    }

    private boolean puzzleSolved() {
         /*Este método verifica si el puzzle está resuelto. Dependiendo del nivel seleccionado, 
        compara los números en las celdas del puzzle con los resultados correspondientes almacenados en las etiquetas. 
        Si todos los cálculos son correctos, devuelve `true`; de lo contrario, devuelve `false`.*/
        int valor1, valor2, labelValue;
        switch (niveles.getValue()) {
            case "x" -> {
                if (!(buttons[0][0].getText().equals("") || buttons[0][1].getText().equals(""))) {
                    valor1 = Integer.parseInt(buttons[0][0].getText());
                    valor2 = Integer.parseInt(buttons[0][1].getText());
                    labelValue = Integer.parseInt(labels[0].getText());
                    if (valor1 * valor2 != labelValue) {
                        return false;
                    }
                    if (!(buttons[0][2].getText().equals("") || buttons[1][0].getText().equals(""))) {
                        valor1 = Integer.parseInt(buttons[0][2].getText());
                        valor2 = Integer.parseInt(buttons[1][0].getText());
                        labelValue = Integer.parseInt(labels[1].getText());
                        if (valor1 * valor2 != labelValue) {
                            return false;
                        }
                        if (!(buttons[1][1].getText().equals("") || buttons[1][2].getText().equals(""))) {
                            valor1 = Integer.parseInt(buttons[1][1].getText());
                            valor2 = Integer.parseInt(buttons[1][2].getText());
                            labelValue = Integer.parseInt(labels[2].getText());

                            if (valor1 * valor2 != labelValue) {
                                return false;
                            }
                            if (!(buttons[2][0].getText().equals("") || buttons[2][1].getText().equals(""))) {
                                valor1 = Integer.parseInt(buttons[2][0].getText());
                                valor2 = Integer.parseInt(buttons[2][1].getText());
                                labelValue = Integer.parseInt(labels[3].getText());
                                return valor1 * valor2 == labelValue;
                            }
                        }
                    }

                }
                return false;
            }
            case "+" -> {
                if (!(buttons[0][0].getText().equals("") || buttons[0][1].getText().equals(""))) {
                    valor1 = Integer.parseInt(buttons[0][0].getText());
                    valor2 = Integer.parseInt(buttons[0][1].getText());
                    labelValue = Integer.parseInt(labels[0].getText());
                    if (valor1 + valor2 != labelValue) {
                        return false;
                    }
                    if (!(buttons[0][2].getText().equals("") || buttons[1][0].getText().equals(""))) {
                        valor1 = Integer.parseInt(buttons[0][2].getText());
                        valor2 = Integer.parseInt(buttons[1][0].getText());
                        labelValue = Integer.parseInt(labels[1].getText());
                        if (valor1 + valor2 != labelValue) {
                            return false;
                        }
                        if (!(buttons[1][1].getText().equals("") || buttons[1][2].getText().equals(""))) {
                            valor1 = Integer.parseInt(buttons[1][1].getText());
                            valor2 = Integer.parseInt(buttons[1][2].getText());
                            labelValue = Integer.parseInt(labels[2].getText());

                            if (valor1 + valor2 != labelValue) {
                                return false;
                            }
                            if (!(buttons[2][0].getText().equals("") || buttons[2][1].getText().equals(""))) {
                                valor1 = Integer.parseInt(buttons[2][0].getText());
                                valor2 = Integer.parseInt(buttons[2][1].getText());
                                labelValue = Integer.parseInt(labels[3].getText());
                                return valor1 + valor2 == labelValue;
                            }
                        }
                    }

                }
                return false;
            }
            case "-" -> {
                if (!(buttons[0][0].getText().equals("") || buttons[0][1].getText().equals(""))) {
                    valor1 = Integer.parseInt(buttons[0][0].getText());
                    valor2 = Integer.parseInt(buttons[0][1].getText());
                    labelValue = Integer.parseInt(labels[0].getText());
                    if (Math.abs(valor1 - valor2) != labelValue) {
                        return false;
                    }
                    if (!(buttons[0][2].getText().equals("") || buttons[1][0].getText().equals(""))) {
                        valor1 = Integer.parseInt(buttons[0][2].getText());
                        valor2 = Integer.parseInt(buttons[1][0].getText());
                        labelValue = Integer.parseInt(labels[1].getText());
                        if (Math.abs(valor1 - valor2) != labelValue) {
                            return false;
                        }
                        if (!(buttons[1][1].getText().equals("") || buttons[1][2].getText().equals(""))) {
                            valor1 = Integer.parseInt(buttons[1][1].getText());
                            valor2 = Integer.parseInt(buttons[1][2].getText());
                            labelValue = Integer.parseInt(labels[2].getText());

                            if (Math.abs(valor1 - valor2) != labelValue) {
                                return false;
                            }
                            if (!(buttons[2][0].getText().equals("") || buttons[2][1].getText().equals(""))) {
                                valor1 = Integer.parseInt(buttons[2][0].getText());
                                valor2 = Integer.parseInt(buttons[2][1].getText());
                                labelValue = Integer.parseInt(labels[3].getText());
                                return Math.abs(valor1 - valor2) == labelValue;
                            }
                        }
                    }

                }
                return false;
            }
        }
        return false;
    }
}
