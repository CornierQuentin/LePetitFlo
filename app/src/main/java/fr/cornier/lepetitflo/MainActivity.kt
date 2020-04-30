package fr.cornier.lepetitflo

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var testMalusCase = 0

    var playerNumber = 4

    private var diceResult = 0

    val set = ConstraintSet()

    lateinit var caseList: Array<View>
    private lateinit var playerList: Array<View>

    var playerScoreList = mutableListOf(0, 0, 0, 0)

    var playerFinishTest = arrayOf(0, 0, 0, 0)

    var textList = arrayOf("Case\nDépart", "40\nFentes\nSautées", "30\nBurpees", "2 min\nMountain\nClimber", "40\nSquats Sautés", "2 min\nGainage\nPendule", "30\nFrog\nJumps", "100\nCrunchs", "3 min\nPapillion", "Reculer\nDe\n2 Cases", "2 min\nGainage", "BSU\nSquat", "30\nPompes", "2 min\nChaise", "Repos", "40\nDeeps", "BSU\nAbdos", "30\nFrog\nJumps", "40\nFentes\nSautées", "Retour à \nla Case\nDépart", "2 min\nCrunch\nBicylcle", "50\nSquats", "100\nCrunchs", "Reculer\nDe\n5 Cases", "4 min\nGainage", "Fin")

    var buttonList = arrayListOf(R.drawable.blue_button, R.drawable.green_button, R.drawable.yellow_button, R.drawable.red_button)

    private var playerTurn = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        caseList = arrayOf(caseD, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15, case16, case17, case18, case19, case20, case21, case22, case23, case24, caseF)
        playerList = arrayOf(player1, player2, player3, player4)

        blurLayout.visibility = View.INVISIBLE
    }


    fun onDiceButtonClick(button: View) {
        diceResult = (1..6).random()

        displayDiceResult(diceResult)

        Handler().postDelayed(
            {
                hideDiceResult()
                gameTurn()
            },
            1000
        )
    }

    private fun gameTurn() {
        var testCaseClear: Int

        testMalusCase = 0

        if (playerFinishTest[playerTurn] == 1) {
            while (playerFinishTest[playerTurn] == 1) {
                if (playerTurn in 0..2) {
                    playerTurn += 1
                } else {
                    playerTurn = 0
                }
            }
        }
        if (playerScoreList[playerTurn] != 0 && playerScoreList[playerTurn] != 25) {
            caseList[playerScoreList[playerTurn]].setBackgroundResource(R.drawable.normal_case)
        }

        when {
            playerScoreList[playerTurn] + diceResult <= 25 -> {
                playerScoreList[playerTurn] += diceResult
            }
            playerScoreList[playerTurn] + diceResult > 25 -> {
                playerScoreList[playerTurn] = 25
            }
        }

        when {
            playerScoreList[playerTurn] == 9 -> {
                testMalusCase = 1

                afficherCaseMalus(playerScoreList[playerTurn])

                playerScoreList[playerTurn] = playerScoreList[playerTurn] - 2

                testCaseClear = isCaseClear()

                movePlayer(playerTurn, testCaseClear)
            }
            playerScoreList[playerTurn] == 19 -> {
                testMalusCase = 1

                afficherCaseMalus(playerScoreList[playerTurn])

                playerScoreList[playerTurn] = 0

                testCaseClear = isCaseClear()

                movePlayer(playerTurn, testCaseClear)
            }
            playerScoreList[playerTurn] == 23 -> {
                testMalusCase = 1

                afficherCaseMalus(playerScoreList[playerTurn])

                playerScoreList[playerTurn] = playerScoreList[playerTurn] - 5

                testCaseClear = isCaseClear()

                movePlayer(playerTurn, testCaseClear)
            }
            else -> {
                testCaseClear = isCaseClear()
                
                movePlayer(playerTurn, testCaseClear)
            }
        }

        if (testMalusCase == 0) {
            afficherCase(playerScoreList[playerTurn])
        }

        if (playerScoreList[playerTurn] != 0 && playerScoreList[playerTurn] != 25) {
            caseList[playerScoreList[playerTurn]].setBackgroundResource(R.drawable.player_case)
        }

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
            dé.visibility = View.INVISIBLE
            continuer.visibility = View.GONE

            blurLayout.visibility = View.VISIBLE
            restart.visibility = View.VISIBLE

            textAffichage.text = "Fini, Bravo !!"

            textAffichage.visibility = View.VISIBLE
            caseAffichage.visibility = View.VISIBLE
        }

        changeButtonColor(playerTurn)
    }

    private fun movePlayer(playerTurn:Int, testCaseClear:Int) {
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
    }

    private fun afficherCase(playerScore:Int) {
        dé.visibility = View.GONE

        blurLayout.visibility = View.VISIBLE
        continuer.visibility = View.VISIBLE

        textAffichage.text = textList[playerScore]
        textAffichage.visibility = View.VISIBLE

        caseAffichage.setBackgroundResource(R.drawable.affichage_case)
        caseAffichage.visibility = View.VISIBLE
    }

    private fun afficherCaseMalus(playerScore:Int) {
        dé.visibility = View.GONE

        blurLayout.visibility = View.VISIBLE

        textAffichage.text = textList[playerScore]
        textAffichage.visibility = View.VISIBLE

        caseAffichage.setBackgroundResource(R.drawable.malus_affichage_case)
        caseAffichage.visibility = View.VISIBLE

        Handler().postDelayed(
            {
                displayCaseAfterMalus()
            },
            2000
        )
    }

    fun onRestartButtonClick(button: View) {
        recreate()
    }

    fun onContinueButtonClick(button: View) {
        dé.visibility = View.VISIBLE

        blurLayout.visibility = View.INVISIBLE
        continuer.visibility = View.INVISIBLE

        textAffichage.visibility = View.INVISIBLE
        caseAffichage.visibility = View.INVISIBLE
    }

    private fun displayCaseAfterMalus() {
        var playerPlay = playerTurn

        continuer.visibility = View.VISIBLE

        blurLayout.visibility = View.VISIBLE
        continuer.visibility = View.VISIBLE

        do {
            if (playerPlay in 1..3) {
                playerPlay -= 1
            } else {
                playerPlay = 3
            }
        } while (playerFinishTest[playerPlay] == 1)

        textAffichage.text = textList[playerScoreList[playerPlay]]
        textAffichage.visibility = View.VISIBLE

        caseAffichage.setBackgroundResource(R.drawable.affichage_case)
        caseAffichage.visibility = View.VISIBLE
    }

    private fun displayDiceResult (diceResult:Int) {
        dé.visibility = View.GONE

        diceResultText.text = "$diceResult"
        diceResultText.visibility = View.VISIBLE
        diceAffichage.visibility = View.VISIBLE
        blurLayout.visibility = View.VISIBLE
    }

    private fun hideDiceResult () {
        dé.visibility = View.VISIBLE

        diceResultText.visibility = View.INVISIBLE
        diceAffichage.visibility = View.INVISIBLE
        blurLayout.visibility = View.INVISIBLE
    }

    private fun changeButtonColor(playerTurn:Int) {
        dé.setBackgroundResource(buttonList[playerTurn])
    }

    private fun isCaseClear() : Int {
        var testCaseClear = 0

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

        return testCaseClear
    }
}