import java.util.Scanner

fun main(){

    val game = Game()
    game.player.createShips()
    game.computer.createShips()
    var isGameFinished= game.player.isLose() || game.computer.isLose()

    while(!isGameFinished){
        game.player.shoot(game.computer.shipBoard)
        game.computer.shoot(game.player.shipBoard)
        game.player.shotBoard.printBoard()
       isGameFinished= game.player.isLose() || game.computer.isLose()
    }
    if (!game.player.isLose()) println("Kazandın")else println("Kaybettin")
}

class Game {
    val player = Player(true)
    val computer = Player(false)
}

class Player(val isReal: Boolean) {
    val shipBoard = Board()
    val shotBoard = Board()

    fun createShips(){

        insertShip(Ship(2,"Ship1"))
        //insertShip(Ship(3,"Ship2"))
        //insertShip(Ship(4,"Ship3"))
        //insertShip(Ship(5,"Ship4"))

        arrayOf(1,2,3,4,5,6,7,8).forEach { print(it) }
        println()
        shipBoard.noktaList.forEach {
            it.forEach {
                print(it)
            }
            println()
        }
    }

    fun insertShip(ship: Ship){
        val yty:Int
        val dky:Int
        val dir: String
        val xScanner = Scanner(System.`in`)
        if (isReal) {
            println("${ship.name} ${ship.uzunluk} birim uzunlukta: ")
            println("${ship.name} için x koordinatı girin (1-8 arası): ")
            yty = xScanner.nextLine().trim().toInt() - 1
            println("${ship.name} için y koordinatı girin (1-8 arası): ")
            dky = xScanner.nextLine().trim().toInt() - 1
            println("${ship.name} için yön girin (x-y arası): ")
            dir = xScanner.nextLine().trim()
        }else{
            yty = (Math.random() * shipBoard.noktaList.size).toInt()
            dky = (Math.random() * shipBoard.noktaList.size).toInt()
            dir = arrayOf("x","y")[(Math.random()*2).toInt()]
            println("x:${yty} y:${dky} uzunluk:${ship.uzunluk} yön:${dir}" )
        }
        if (insertable(yty,dky,ship.uzunluk,dir)){
            for (i in 0 until ship.uzunluk){
                when(dir.toLowerCase()){
                    "y" -> shipBoard.noktaList[dky+i][yty] = 1
                    "x" -> shipBoard.noktaList[dky][yty+i] = 1
                }
            }
            println("${ship.name} yerleştirildi.")
        }else{
            println("Hata ${ship.name} yerleştirilemedi. Tekrar deneyin")
            insertShip(ship)
        }
    }


    fun insertable(yty: Int,dky: Int,lng:Int,dir:String):Boolean{
        if ((yty+lng-1) < shipBoard.noktaList.size && (dky+lng-1)< shipBoard.noktaList.size){
            when(dir){
                "x"-> for (i in yty until yty + lng) {
                    return shipBoard.noktaList[dky][i] != 1
                }
                "y"-> for (i in dky until dky + lng) {
                    return shipBoard.noktaList[i][yty] != 1
                }
            }
        }else{
            return false
        }
        return true
    }

    fun shoot(board: Board){
        val yty:Int
        val dky:Int
        val xScanner = Scanner(System.`in`)
        if (isReal) {
            println("Atış için x koordinatı girin (1-8 arası): ")
            yty = xScanner.nextLine().trim().toInt() - 1
            println("Atış için y koordinatı girin (1-8 arası): ")
            dky = xScanner.nextLine().trim().toInt() - 1
        }else{
            yty = (Math.random() * shipBoard.noktaList.size).toInt()
            dky = (Math.random() * shipBoard.noktaList.size).toInt()
            //println("x:${yty} y:${dky} uzunluk:${ship.uzunluk} yön:${dir}" )
        }
        if (board.noktaList[dky][yty] == 1){
            println("Düşman gemisi isabet aldı ")
            board.noktaList[dky][yty] = 0
            shotBoard.noktaList[dky][yty] =1
        }
    }

    fun isLose():Boolean{
        var finish = true
        shipBoard.noktaList.forEach {
            it.forEach {
                    if (it == 1){ finish = false }
                }
            }
        return finish
    }
}

class Board {
    val noktaList = arrayOf(
        arrayOf(0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0)
    )

    fun printBoard(){
        noktaList.forEach {
            it.forEach {
                print(it)
            }
            println()
        }
    }

}
class Ship(val uzunluk:Int,val name:String){

}
