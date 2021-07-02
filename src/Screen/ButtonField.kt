package Screen

import Model.Board
import Model.EventField
import Model.Field
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.SwingUtilities

private val Color_BG_NORMAL = Color(184,184,184)
private val Color_BG_CHECK = Color(8,179,247)
private val Color_BG_EXPLOSION = Color(189,66,68)
private val Color_TXT_GREEN = Color(0,100,0)



class ButtonField(private val field: Field):JButton() {

    init{
        font = font.deriveFont((Font.BOLD))
        background = Color_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(MouseClickListener(field, {it.openField()}, {it.changeCheck()}))
        field.onEvent( this::applyStyle )
    }

    private fun applyStyle(field:Field, eventField: EventField){
        when(eventField){
            EventField.EXPLOSION -> applyExplosion()
            EventField.SHOWMINE -> applyShowMine()
            EventField.SHOWMINECHECK -> applyShowMineChecked()
            EventField.OPENING -> applyOpen()
            EventField.CHECKING -> applyCheck()
            else -> applyNormal()
        }

        SwingUtilities.invokeLater{
            repaint()
            validate()
        }
    }

    private fun applyExplosion(){
        background = Color_BG_EXPLOSION
        text = "X"
    }
    private fun applyShowMine(){
        border = BorderFactory.createLineBorder(Color.GRAY)
        text = "X"
        foreground = Color.RED
    }
    private fun applyShowMineChecked(){
        background = Color_BG_CHECK
        border = BorderFactory.createLineBorder(Color.GRAY)
        text = "X"
    }

    private fun applyOpen(){
        background = Color_BG_NORMAL
        border = BorderFactory.createLineBorder(Color.GRAY)
        foreground = when (field.numNeighborsMined){
            1 -> Color_TXT_GREEN
            2->Color.BLUE
            3->Color.YELLOW
            4,5,6->Color.RED
            else-> Color.PINK
        }
        text = if (field.numNeighborsMined>0) field.numNeighborsMined.toString() else ""
    }

    private fun applyCheck(){
        background = Color_BG_CHECK
        foreground = Color.BLACK
        text = "M"
    }
    private fun applyNormal(){
        background = Color_BG_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
    }
}