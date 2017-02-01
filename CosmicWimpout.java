//Xia Lin(110732381)
package cosmic.wimpout;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CosmicWimpout extends Application {

    static DiceSet play = new DiceSet();
    static Person p = new Person();
    static Computer cp = new Computer();
    static int rollNum1 = 0, rollNum2 = 0, row = 2, n=0,turn=0,winScore;
    static String cName = "Computer";
    static Cube cube = new Cube();
    static TextField total = new TextField();
    static TextField name = new TextField();
    static Button enter = new Button("Start");
    static Button roll = new Button("Roll");
    static ButtonType continueGame = new ButtonType("Continue");
    static ButtonType score = new ButtonType("Score");
    static GridPane pane = new GridPane();
    static VBox vb = new VBox();
    static BorderPane bp = new BorderPane();
    static ScrollPane sp = new ScrollPane();

    public void start(Stage primaryStage) {
        sp.setContent(vb);
        bp.setLeft(pane);
        bp.setCenter(sp);
        pane.add(new Label("       "), 1, 1);
        pane.add(new Label("Set the winning total:"), 2, row++);
        pane.add(total, 2, row++);
        pane.add(new Label("Enter a player name:"), 2, row++);
        pane.add(name, 2, row++);
        pane.add(enter, 2, row++);
        pane.add(roll, 2, row++);
        enter.setOnAction(
                new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                enter.setDisable(true);
                total.setDisable(true);
                name.setDisable(true);
                winScore = Integer.parseInt(total.getText());
                vb.getChildren().add(new Label("Rolling to see who goes first:"));
                while (rollNum1 == rollNum2) {
                    cube.roll();
                    rollNum1 = cube.value();
                    cube.roll();
                    rollNum2 = cube.value();
                    vb.getChildren().add(new Label(name.getText() + " rolled " + rollNum1));
                    vb.getChildren().add(new Label(cName + " rolled " + rollNum2));
                    if (rollNum1 > rollNum2) {
                        vb.getChildren().add(new Label(name.getText() + " goes first!"));
                    } else if (rollNum1 < rollNum2) {
                        vb.getChildren().add(new Label(cName + " goes first!"));
                    } else {
                        vb.getChildren().add(new Label("The number are equal roll again!"));
                    }
                }
                vb.getChildren().add(new Label("You must get at least 35 points to enter the game!"));
                roll.setOnAction(
                        new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        vb.getChildren().clear();
                        if (p.getFinalScore() < winScore && cp.getFinalScore() < winScore) {
                            boolean isEnterGame;
                            if (rollNum1 > rollNum2) {
                                turn=1;
                                if (n++ % 2 == 0) {
                                    vb.getChildren().add(new Label(name.getText() + "'s turn (" + p.getFinalScore() + " pts so far):"));
                                    play.reset();
                                    Roll();
                                    isEnterGame = p.setFinalScore(play.getCurrentScore());
                                    play.setCurrentScore(0);
                                    if (!isEnterGame) {
                                        vb.getChildren().add(new Label("You must get at least 35 points to enter the game!"));
                                    }
                                    vb.getChildren().add(new Label("Final Points: " + p.getFinalScore()));
                                    if (p.getFinalScore() >= winScore) {
                                        vb.getChildren().add(new Label(name.getText() + " Win!"));
                                        roll.setDisable(true);
                                    }

                                } else {
                                    vb.getChildren().add(new Label(cName + "'s turn (" + cp.getFinalScore() + " pts so far):"));
                                    play.reset();
                                    Roll();
                                    isEnterGame = cp.setFinalScore(play.getCurrentScore());
                                    play.setCurrentScore(0);
                                    if (!isEnterGame) {
                                        vb.getChildren().add(new Label("You must get at least 35 points to enter the game!"));
                                    }
                                    vb.getChildren().add(new Label("Final Points: " + cp.getFinalScore()));
                                    if (cp.getFinalScore() >= winScore) {
                                        vb.getChildren().add(new Label(cName + " Win!"));
                                        roll.setDisable(true);
                                    }
                                }
                            } else {
                                turn=2;
                                if (n++ % 2 == 0) {
                                vb.getChildren().add(new Label(cName + "'s turn (" + cp.getFinalScore() + " pts so far):"));
                                play.reset();
                                Roll();
                                isEnterGame = cp.setFinalScore(play.getCurrentScore());
                                play.setCurrentScore(0);
                                if (!isEnterGame) {
                                    vb.getChildren().add(new Label("You must get at least 35 points to enter the game!"));
                                }
                                vb.getChildren().add(new Label("Final Points: " + cp.getFinalScore()));
                                if (cp.getFinalScore() >= winScore) {
                                    vb.getChildren().add(new Label(cName + " Win!"));
                                    roll.setDisable(true);
                                }
                                }else {
                                vb.getChildren().add(new Label(name.getText() + "'s turn (" + p.getFinalScore() + " pts so far):"));
                                play.reset();
                                Roll();
                                isEnterGame = p.setFinalScore(play.getCurrentScore());
                                play.setCurrentScore(0);
                                if (!isEnterGame) {
                                    vb.getChildren().add(new Label("You must get at least 35 points to enter the game!"));
                                }
                                vb.getChildren().add(new Label("Final Points: " + p.getFinalScore()));
                                if (p.getFinalScore() >= winScore) {
                                    vb.getChildren().add(new Label(name.getText() + " Win!"));
                                    roll.setDisable(true);
                                }
                                }
                        }

                        }
                    }
                });
            }
        });
        Scene scene = new Scene(bp, 1000, 1000);
        primaryStage.setTitle("Cosmic Wimpout");
        primaryStage.setScene(scene);
        primaryStage.setX(0);
        primaryStage.show();
    }

    public void Roll() {
        play.roll();
        vb.getChildren().add(new Label(play.getString()));
        if (play.getFreightTrain()) {
            FreightTrain();
        } else if (play.getMustRollAgain()) {
            MustRollAgain();
        } else if (play.getFlash()) {
            Flash();
        } else if (play.getFiveOrTen()) {
            FiveOrTen();
        } else if (play.getWimpedOut()) {
            WimpedOut();
        }
    }

    public void FreightTrain() {
        vb.getChildren().add(new Label("Freight Train!"));
        vb.getChildren().add(new Label("Current Points: " + play.getCurrentScore()));
        if (play.getCurrentScore() == 600) {
            vb.getChildren().add(new Label(name.getText() + " Wins!"));
        } else if (play.getCurrentScore() == 1000) {
            vb.getChildren().add(new Label("Too many point!" + name.getText() + " lose!"));
        }
        MustRollAgain();
    }

    public void Flash() {
        boolean isClear = false;
        vb.getChildren().add(new Label("Flash!" + "\n" + "Current Points: " + play.getCurrentScore()
                + "\n" + "Remaining dice(): " + play.toString() + "\n" + "Clear the flash as per the Futtless Rule!"));
        while (!isClear) {
            isClear = true;
            for (int i = 0; i < play.getCube().size(); i++) {
                play.getCube().get(i).roll();
            }
            vb.getChildren().add(new Label(play.toString()));
            for (int i = 0; i < play.getCube().size(); i++) {
                if (play.getValueOfRepeat() == play.getCube().get(i).value()) {
                    isClear = false;
                    vb.getChildren().add(new Label("The value can not be the same as flash value! Roll again!"));
                    break;
                }
            }
        }
        play.setWimpedOut(true);
        play.setFiveOrTen(false);
        play.deterAndEvalu();
        if (play.getMustRollAgain()) {
            MustRollAgain();
        } else if (play.getWimpedOut()) {
            WimpedOut();
        } else if (play.getFiveOrTen()) {
            FiveOrTen();
        }
    }

    public void FiveOrTen() {
        if((turn==1&&n%2==1)||(turn==2&&n%2==0)){
        vb.getChildren().add(new Label("You Current Points is " + play.getCurrentScore() + "! If you chose Continue you may "
                + "lose your all points"));
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Make a Decition");
        alert.setHeaderText("You Current Points is " + play.getCurrentScore());
        alert.setContentText("If you chose Continue you may lose your all points!");
        alert.getButtonTypes().setAll(continueGame, score);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == continueGame)
            Roll();
        }
        else{
            if(play.getCurrentScore()<35){
                vb.getChildren().add(new Label(cName+" chose to continue!"));
                Roll();
            }
            else{
            int i=(int)Math.random();
            if(i>0.7){
                vb.getChildren().add(new Label(cName+" chose to continue!"));
                Roll();
            }
            }
        }
    }

    public void MustRollAgain() {
        vb.getChildren().add(new Label("Current Points: " + play.getCurrentScore()));
        vb.getChildren().add(new Label("You May Not Want But Must roll again!"));
        play.reset();
        Roll();
    }

    public void WimpedOut() {
        vb.getChildren().add(new Label("Wimped Out!"));
        play.getCube().clear();
        play.reset();
        play.setCurrentScore(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
