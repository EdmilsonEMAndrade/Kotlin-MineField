package Screen

import Model.Board
import java.awt.GridLayout
import javax.swing.JPanel

class PanelBoard (board:Board):JPanel(){
    init{
        layout = GridLayout(board.numRows, board.numColumn)
        board.forEachField { field ->
            val button = ButtonField(field)
            add(button)
        }
    }
}