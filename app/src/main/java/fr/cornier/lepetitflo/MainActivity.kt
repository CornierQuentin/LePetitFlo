package fr.cornier.lepetitflo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView


    var testMalusCase = 0

    private var diceResult = 0

    val set = ConstraintSet()

    lateinit var caseList: Array<View>
    private lateinit var playerList: Array<View>

    var playerScoreList = mutableListOf(0, 0, 0, 0)

    var playerFinishTest = arrayOf(0, 0, 0, 0)

    var textList = arrayOf("Case\nDépart", "40\nFentes\nSautées", "30\nBurpees", "2 min\nMountain\nClimber", "40\nSquats\nSautés", "2 min\nGainage\nPendule", "30\nFrog\nJumps", "100\nCrunchs", "3 min\nPapillion", "Reculer\nDe\n2 Cases", "2 min\nGainage", "BSU\nSquat", "30\nPompes", "2 min\nChaise", "Repos", "40\nDeeps", "BSU\nAbdos", "30\nFrog\nJumps", "40\nFentes\nSautées", "Retour à \nla Case\nDépart", "2 min\nCrunch\nBicylcle", "50\nSquats", "100\nCrunchs", "Reculer\nDe\n5 Cases", "4 min\nGainage", "Fin")

    var buttonList = arrayListOf(R.drawable.blue_button, R.drawable.green_button, R.drawable.yellow_button, R.drawable.red_button)

    private var playerTurn = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        caseList = arrayOf(caseD, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15, case16, case17, case18, case19, case20, case21, case22, case23, case24, caseF)
        playerList = arrayOf(player1, player2, player3, player4)

        blurLayout.visibility = View.INVISIBLE

        getScore()

        for (i in 0..3) {
            movePlayer(i, i + 1)
            if (playerScoreList[i] != 0 && playerScoreList[i] != 25) {
                caseList[playerScoreList[i]].setBackgroundResource(R.drawable.player_case)
            }
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener(this)
    }

    fun onDrawerButtonClick(button: View) {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_restart -> {
                val sharedPreferences = getSharedPreferences("fr.cornier.lepetitflo", Context.MODE_PRIVATE).edit()
                for (i in 0..3) {
                    sharedPreferences.putInt("score$i", 0)
                }
                sharedPreferences.putInt("playerTurn", 0)
                sharedPreferences.apply()

                recreate()
            }
            R.id.nav_player -> {
                val intent = Intent(this, PlayerActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_index -> {
                Toast.makeText(this, "Index clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about -> {
                Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_quit -> {
                Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show()
                finish()
                exitProcess(0)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    fun onDiceButtonClick(button: View) {
        /*
        args : Prend en argument le bouton cliquer

        Choisi un nombre aléatoire entre 1 et 6. pour ensuite l'envoyer dans la fonction displayDiceResult().
        Et après 1000 ms passe à la fonction gameTurn()
         */

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
        /*
        No arguments*

        Effectue l'entièreté du tour d'un joueur.
        Il vérifie que le joueur n'a pas déjà fini, si c'est le cas il passe au joueur d'après.
        Il remet la couleur de bas de la case ou le joueur se trouve
        Il augmente le score du joueur.
        Il déplace le joueur jusqu'à sa case ne vérifiant si il n'est pas sur une case malus,
        si c'est le cas il applique le malus et le déplace sur sa case final.
        Il Affiche la case sur laquelle le joueur est tombé, si il n'était pas tomber sur une case malus.
        Il change la couleur de la case sur laquelle vient d'arriver le joueur.
        Il vérifie que le joueur n'est pas fini, si c'est le cas il passe sa valeur à 1 dans la variable playerFinishTest.
        Il passe augmente le playerTurn.
        Il vérifie que les 4 joueurs n'ont pas fini.
        Il change la couleur du bouton par rapport au prochain joueur devant jouer.
         */

        var testCaseClear: Int

        testMalusCase = 0

        isPlayerAlreadyFinished()

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

                testCaseClear = isCaseClear(playerScoreList[playerTurn])

                movePlayer(playerTurn, testCaseClear)
            }
            playerScoreList[playerTurn] == 19 -> {
                testMalusCase = 1

                afficherCaseMalus(playerScoreList[playerTurn])

                playerScoreList[playerTurn] = 0

                testCaseClear = isCaseClear(playerScoreList[playerTurn])

                movePlayer(playerTurn, testCaseClear)
            }
            playerScoreList[playerTurn] == 23 -> {
                testMalusCase = 1

                afficherCaseMalus(playerScoreList[playerTurn])

                playerScoreList[playerTurn] = playerScoreList[playerTurn] - 5

                testCaseClear = isCaseClear(playerScoreList[playerTurn])

                movePlayer(playerTurn, testCaseClear)
            }
            else -> {
                testCaseClear = isCaseClear(playerScoreList[playerTurn])

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
        }

        nextPlayerTurn()

        isFinish()

        changeButtonColor()

        saveScore()
    }

    private fun movePlayer(playerTurn:Int, testCaseClear:Int) {
        /*
        args : prend en arguments le playerTurn et le compteur du nombre de joueur sur la case

        Déplace le joueur en modifiant ses parents pour changer ses contraintes, tout en changeant son écart avec le haut
        suivant le nombre de joueur déjà présent sur la case.
         */

        if (playerScoreList[playerTurn] != 0 && playerScoreList[playerTurn] != 25) {
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
        } else {
            when (testCaseClear) {
                1 -> {
                    set.clone(constraintLayout)
                    set.connect(
                        playerList[playerTurn].id,
                        ConstraintSet.TOP,
                        caseList[playerScoreList[playerTurn]].id,
                        ConstraintSet.TOP,
                        25
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
                        110
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
                        25
                    )
                    set.connect(
                        playerList[playerTurn].id,
                        ConstraintSet.START,
                        caseList[playerScoreList[playerTurn]].id,
                        ConstraintSet.START,
                        110
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
                        110
                    )
                    set.connect(
                        playerList[playerTurn].id,
                        ConstraintSet.START,
                        caseList[playerScoreList[playerTurn]].id,
                        ConstraintSet.START,
                        110
                    )
                    set.applyTo(constraintLayout)
                }
            }
        }
    }

    private fun afficherCase(playerScore:Int) {
        /*
        args : prend en argument le score du joueur

        Affiche la case sur correspondant au score du joueur.
         */

        dé.visibility = View.GONE

        blurLayout.visibility = View.VISIBLE
        continuer.visibility = View.VISIBLE

        textAffichage.text = textList[playerScore]
        textAffichage.visibility = View.VISIBLE

        caseAffichage.setBackgroundResource(R.drawable.affichage_case)
        caseAffichage.visibility = View.VISIBLE
    }

    private fun afficherCaseMalus(playerScore:Int) {
        /*
        args : prend en arguments le score du joueur

        Affiche la case malus, correspondant au score du joueur, pendant 2000 ms pour ensuite utiliser
        la fonction displayCaseAfterMalus().
         */

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
        /*
        args : prend en argument le bouton cliquer

        Redémarre l'activité
         */

        recreate()
    }

    fun onContinueButtonClick(button: View) {
        /*
        args : prend en argument le bouton cliquer

        Cache l'affichage de la case sur laquelle est tombé le joueur
         */

        dé.visibility = View.VISIBLE

        blurLayout.visibility = View.INVISIBLE
        continuer.visibility = View.INVISIBLE

        textAffichage.visibility = View.INVISIBLE
        caseAffichage.visibility = View.INVISIBLE
    }

    private fun displayCaseAfterMalus() {
        /*
        No arguments

        Affiche la case sur laquelle tombe un joueur après avoir reçu un malus
         */

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
        /*
        args : prend en arguments le résultat du dé

        Affiche le résultat du dé
         */

        dé.visibility = View.GONE

        diceResultText.text = "$diceResult"
        diceResultText.visibility = View.VISIBLE
        diceAffichage.visibility = View.VISIBLE
        blurLayout.visibility = View.VISIBLE
    }

    private fun hideDiceResult () {
        /*
        No arguments

        Cache l'affichage du résultat du dé
         */

        dé.visibility = View.VISIBLE

        diceResultText.visibility = View.INVISIBLE
        diceAffichage.visibility = View.INVISIBLE
        blurLayout.visibility = View.INVISIBLE
    }

    private fun changeButtonColor() {
        /*
        No arguments

        Change la couleur du bouton pour celle du prochain joueur devant jouer
         */

        isPlayerAlreadyFinished()
        dé.setBackgroundResource(buttonList[playerTurn])
    }

    private fun isCaseClear(case: Int) : Int {
        /*
        args : prend en argument la case sur laquelle il faut faire le test.

        La variable tesCaseClear est un compteur permettant dee savoir le nombre de personne sur une case.
        Elle augmente de 1 chaque fois que le score d'un joueur est égal au numéro de la case.
         */

        var testCaseClear = 0

        if (case == playerScoreList[0]) {
            testCaseClear += 1
        }
        if (case == playerScoreList[1]) {
            testCaseClear += 1
        }
        if (case == playerScoreList[2]) {
            testCaseClear += 1
        }
        if (case == playerScoreList[3]) {
            testCaseClear += 1
        }

        return testCaseClear
    }

    private fun isFinish() {
        /*
        No arguments

        Quand les 4 joueurs sont arrivés à la fin, un message s'affiche pour les féliciter et un bouton apparait pour recommencer.
         */

        if (playerFinishTest[0] == 1 && playerFinishTest[1] == 1 && playerFinishTest[2] == 1 && playerFinishTest[3] == 1) {
            dé.visibility = View.INVISIBLE
            continuer.visibility = View.GONE

            blurLayout.visibility = View.VISIBLE
            restart.visibility = View.VISIBLE

            textAffichage.text = "Fini, Bravo !!"

            textAffichage.visibility = View.VISIBLE
            caseAffichage.visibility = View.VISIBLE
        }
    }

    private fun nextPlayerTurn () {
        /*
        No arguments

        Permet de passer au playerTurn suivant. Si le playerTurn est entre 0 et 2 alors il est augmenté de 1,
        sinon s'il est égal à 3 il est redescendu à 0.
         */

        if (playerTurn in 0..2) {
            playerTurn += 1
        }else {
            playerTurn = 0
        }
    }

    private fun isPlayerAlreadyFinished() {
        /*
        No arguments

        Si le joueur du playerTurn a déjà fini alors le playerTurn est augmenté ou remis à 0 jusqu'à ce que le joueur du playerTurn ai fini.
         */

        if (playerFinishTest[playerTurn] == 1) {
            while (playerFinishTest[playerTurn] == 1) {
                nextPlayerTurn()
            }
        }
    }

    private fun saveScore() {
        val sharedPreferences = getSharedPreferences("fr.cornier.lepetitflo", Context.MODE_PRIVATE).edit()
        for (i in 0..3) {
            sharedPreferences.putInt("score$i", playerScoreList[i])
        }
        sharedPreferences.putInt("playerTurn", playerTurn)
        sharedPreferences.apply()
    }

    private fun getScore() {
        val sharedPreferences = getSharedPreferences("fr.cornier.lepetitflo", Context.MODE_PRIVATE)
        for (i in 0..3) {
            playerScoreList[i] = sharedPreferences.getInt("score$i", 0)
        }
        playerTurn = sharedPreferences.getInt("playerTurn", 0)
    }

    fun onRestartIconClick(button: View) {
        val sharedPreferences = getSharedPreferences("fr.cornier.lepetitflo", Context.MODE_PRIVATE).edit()
        for (i in 0..3) {
            sharedPreferences.putInt("score$i", 0)
        }
        sharedPreferences.putInt("playerTurn", 0)
        sharedPreferences.apply()

        recreate()
    }
}