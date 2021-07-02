package Model

import kotlin.reflect.jvm.internal.impl.utils.DFS

enum class EventField {OPENING, CHECKING, UNCHECKING, EXPLOSION, RESET}

data class Field(val row:Int, val column:Int){
    private val neighbors = ArrayList<Field>()
    private val callbacks = ArrayList<(Field, EventField) -> Unit>()
    var checked: Boolean = false
    var open : Boolean = false
    var mined : Boolean = false

    //read
    val unchecked: Boolean get() = !checked
    val close: Boolean get() = !open
    val safe: Boolean get() = !mined
    val target :Boolean get() = safe && open || mined && checked
    val numNeighborsMined : Int get() = neighbors.filter{it.mined}.size
    val neighborhoodSafe:Boolean get() = neighbors.map { it.safe }.reduce{result, safe -> result && safe}

    fun addNeighbor(neighbor: Field){
        neighbors.add(neighbor)
    }

    fun onEvent(callback: (Field, EventField) ->Unit){
            callbacks.add(callback)
    }

    fun openField(){
        if(close){
            open = true
            if(mined){
                callbacks.forEach{ it(this,EventField.EXPLOSION)}
            }else{
                callbacks.forEach{ it(this,EventField.OPENING)}
                neighbors.filter{it.close && it.safe && neighborhoodSafe}.forEach { it.openField() }
            }
        }
    }

    fun changeCheck(){
        if(close){
            checked =!checked
            val event = if(checked) EventField.CHECKING else EventField.UNCHECKING
            callbacks.forEach { it(this,event) }
        }
    }

    fun mine(){
        mined = true
    }

    fun reset(){
        checked = false
        open = false
        mined = false
        callbacks.forEach { it(this, EventField.RESET) }
    }
}