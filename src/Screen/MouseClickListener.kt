package Screen
import Model.Field
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseClickListener (
    private val field: Field,
    private val onButtonLeft: (Field)->Unit,
    private val onButtonRigth: (Field)->Unit
) : MouseListener{
    override fun mousePressed(e: MouseEvent?) {
        when(e?.button){
            MouseEvent.BUTTON1->onButtonLeft(field)
            MouseEvent.BUTTON3-> onButtonRigth(field) //or BUTTON2
        }
    }
    override fun mouseClicked(e: MouseEvent?) {}
    override fun mouseExited(e: MouseEvent?) {}
    override fun mouseEntered(e: MouseEvent?) {}
    override fun mouseReleased(e: MouseEvent?) {}
}