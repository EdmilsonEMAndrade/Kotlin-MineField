package Screen


import Model.Board
import Model.BoardEvent
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    ScreenBoard()
}

class ScreenBoard : JFrame() {

    private val board = Board(16, 30, 45)
    private val panelBoard = PanelBoard(board)

    init {
        board.onEvent(this::showResult)
        add(panelBoard)

        setSize(690, 438)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Mine Field"
        isVisible = true
    }

    private fun showResult(evento: BoardEvent) {
        SwingUtilities.invokeLater {
            val msg = when(evento) {
                BoardEvent.VITORY -> "Winner!"
                BoardEvent.LOSER -> {"Loser... :P"}
            }
            JOptionPane.showMessageDialog(this, msg)
            board.reset()
            panelBoard.repaint()
            panelBoard.validate()
        }
    }
}