package Screen

import Model.Board
import Model.BoardEvent
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    ScreenBoard()
}

class ScreenBoard : JFrame(){
    private val  board = Board(16,3,89)
    private val panelBoard = PanelBoard(board)

    init{
        board.onEvent ( this::showResult )
        add(panelBoard)
        setSize(690,438)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Mine Field"
        isVisible = true
    }

    private fun showResult(event:BoardEvent){
        SwingUtilities.invokeLater {
            val msg = when(event){
                BoardEvent.VITORY -> "Win"
                BoardEvent.LOSER -> "Loser.. :P"
            }
            JOptionPane.showMessageDialog(this, msg)
            board.reset()
            panelBoard.repaint()
            panelBoard.validate()
        }
    }
}