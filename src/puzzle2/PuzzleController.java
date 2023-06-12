/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/*Los botones están etiquetados según su posición en una matriz de 3x3. Cada botón 
se declara con la anotación @FXML, que indica que estos elementos son controlados 
por el archivo FXML y están vinculados a elementos en la interfaz gráfica.
Cada botón esta en la posición (x, y) de la matriz
*/
public class PuzzleController {
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
}
