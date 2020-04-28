package fr.cornier.lepetitflo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var playerNumber = 4

    val set = ConstraintSet()

    lateinit var caseList: Array<View>
    lateinit var playerList: Array<View>

    var playerScoreList = mutableListOf(0, 0, 0, 0)

    var playerFinishTest = arrayOf(0, 0, 0, 0)

    var playerTurn = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        caseList = arrayOf(caseD, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15, case16, case17, case18, case19, case20, case21, case22, case23, case24, caseF)
        playerList = arrayOf(player1, player2, player3, player4)
    }
    fun test (button: View) {
        val result = (1..6).random()

        var testCaseClear = 0

        if (playerFinishTest[playerTurn] == 1) {
            while (playerFinishTest[playerTurn] == 1) {
                if (playerTurn in 0..2) {
                    playerTurn += 1
                } else {
                    playerTurn = 0
                }
            }
        }
        var test10 = playerScoreList[playerTurn]
        Log.i("Test", "playerScoreList[playerPlay] avant + : $test10")
        if (playerScoreList[playerTurn] + result <= 25) {
            playerScoreList[playerTurn] += result
            Log.i("Test", "IF")
        } else if (playerScoreList[playerTurn] + result > 25) {
            playerScoreList[playerTurn] = 25
            Log.i("Test", "ELSE IF")
        }else {
            Log.i("Test", "ELSE")
        }
        var test11 = playerScoreList[playerTurn]
        Log.i("Test", "playerScoreList[playerPlay] aprés + : $test11")

        if (playerScoreList[playerTurn] == playerScoreList[0]) {
            testCaseClear += 1
        }
        if (playerScoreList[playerTurn] == playerScoreList[1]) {
            testCaseClear += 1
        }
        if (playerScoreList[playerTurn] == playerScoreList[2]) {
            testCaseClear += 1
        }
        if (playerScoreList[playerTurn] == playerScoreList[3]) {
            testCaseClear += 1
        }

        when (testCaseClear) {
            1 -> {
                set.clone(constraintLayout)
                set.connect(
                    playerList[playerTurn].id,
                    ConstraintSet.TOP,
                    caseList[playerScoreList[playerTurn]].id,
                    ConstraintSet.TOP,
                    15
                )
                set.connect(
                    playerList[playerTurn].id,
                    ConstraintSet.START,
                    caseList[playerScoreList[playerTurn]].id,
                    ConstraintSet.START,
                    20
                )
                set.applyTo(constraintLayout)
            }
            2 -> {
                set.clone(constraintLayout)
                set.connect(
                    playerList[playerTurn].id,
                    ConstraintSet.TOP,
                    caseList[playerScoreList[playerTurn]].id,
                    ConstraintSet.TOP,
                    45
                )
                set.connect(
                    playerList[playerTurn].id,
                    ConstraintSet.START,
                    caseList[playerScoreList[playerTurn]].id,
                    ConstraintSet.START,
                    20
                )
                set.applyTo(constraintLayout)
            }
            3 -> {
                set.clone(constraintLayout)
                set.connect(
                    playerList[playerTurn].id,
                    ConstraintSet.TOP,
                    caseList[playerScoreList[playerTurn]].id,
                    ConstraintSet.TOP,
                    75
                )
                set.connect(
                    playerList[playerTurn].id,
                    ConstraintSet.START,
                    caseList[playerScoreList[playerTurn]].id,
                    ConstraintSet.START,
                    20
                )
                set.applyTo(constraintLayout)
            }
            4 -> {
                set.clone(constraintLayout)
                set.connect(
                    playerList[playerTurn].id,
                    ConstraintSet.TOP,
                    caseList[playerScoreList[playerTurn]].id,
                    ConstraintSet.TOP,
                    105
                )
                set.connect(
                    playerList[playerTurn].id,
                    ConstraintSet.START,
                    caseList[playerScoreList[playerTurn]].id,
                    ConstraintSet.START,
                    20
                )
                set.applyTo(constraintLayout)
            }
        }
        var test0 = playerTurn
        Log.i("Test", "playerTurn : $test0")

        if (playerScoreList[playerTurn] >= 25) {
            playerFinishTest[playerTurn] = 1
            playerNumber -= 1
        }

        if (playerTurn in 0..2) {
            playerTurn += 1
        }else {
            playerTurn = 0
        }

        if (playerFinishTest[0] == 1 && playerFinishTest[1] == 1 && playerFinishTest[2] == 1 && playerFinishTest[3] == 1) {
            recreate()
        }
    }

}
