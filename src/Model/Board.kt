package Model

import kotlin.random.Random

enum class BoardEvent{VITORY, LOSER}

class Board(val numRows:Int, val numColumn: Int, private val getMine: Int) {
    private val fields = ArrayList<ArrayList<Field>>()
    private val callbacks = ArrayList<(BoardEvent)->Unit>()
    var endGame: Boolean = false

    init{
        initFields()
        myNeighbors()
        miningField()
    }

    private fun initFields(){
        for(row in 0 until numRows){
            fields.add(ArrayList())
            for(column in 0 until numColumn){
                val newField = Field(row,column)
                newField.onEvent(this::play)
                fields[row].add(newField)
            }
        }
    }

    private fun myNeighbors(){
        forEachField { myNeighbors(it) }
    }

    private fun myNeighbors(field:Field){
        val (row, column) = field
        val rows = arrayOf( row -1, row , row +1)
        val columns = arrayOf( column - 1, column, column +1)
        rows.forEach { r ->
            columns.forEach { c->
                val current = fields.getOrNull(r)?.getOrNull(c)
                current?.takeIf { field != it }?.let{field.addNeighbor(it) }
            }
        }
    }

    private fun miningField(){
        val draw = Random
        var drawRow = -1
        var drawColumn = -1
        var numMine = 0

        while(numMine < this.getMine){
            drawRow = draw.nextInt(numRows)
            drawColumn = draw.nextInt(numColumn)
            val drawField = fields[drawRow][drawColumn]

            if(drawField.safe){
                drawField.mine()
                numMine ++
            }
        }
    }

    private fun goal():Boolean{
        var playerWin = true
        forEachField { if(!it.target)  playerWin = false}
        return playerWin
    }

    private fun play(field: Field, eventField: EventField){
        if(eventField == EventField.EXPLOSION){
            forEachField { f ->
                if(f.mined){
                    f.openField()
                }
            }
            if(!endGame) {
                callbacks.forEach { it(BoardEvent.LOSER) }
                endGame = true
            }
        }else if(goal()){
            callbacks.forEach { it(BoardEvent.VITORY) }
        }
    }
    fun forEachField(callback:(Field)->Unit){
        fields.forEach { row -> row.forEach(callback) }
    }

    fun onEvent(callback: (BoardEvent) -> Unit){
        callbacks.add(callback)
    }

    fun reset(){
        forEachField { it.reset() }
        miningField()
        endGame = false
    }
}