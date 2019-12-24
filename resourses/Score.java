import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.fxml.FXML;

public class Score {

    private static int score;

    // initial score
    Score(int n){
      score = n;
    }

    // minusScore(int 引数)
    // スコアを引数分マイナスする.

    // plusScore(int 引数)
    // スコアを引数分プラスする.

    // getScore()
    // スコアの値を返す.

    // setScore()
    // スコアの値をセットする.


    // printScore()
    // スコアの値をGUIに出力する.

    public static void minusScore(int n){
      score = score - n;
    }

    public static void plusScore(int n){
      score = score + n;
    }

    public static void setScore(int n){
      score = n;
      System.out.println("set score: " + score);
    }

    public static int getScore(){
      System.out.println("get score: " + score);
      return score;
    }
}
