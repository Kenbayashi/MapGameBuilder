import java.io.File;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.MalformedURLException;

import javafx.scene.media.AudioClip;

public class Audio {
  // used by MapGame.java
  // set BGM
  public static Media bgm = new Media(new File("bgm.wav").toURI().toString());

  public static Media item = new Media(new File("item.wav").toURI().toString());

  public static Media goal = new Media(new File("goal.wav").toURI().toString());

  public static void playBgm(){
    MediaPlayer bgm_play = new MediaPlayer(bgm);
    // 無限ループ
    bgm_play.setCycleCount(MediaPlayer.INDEFINITE);
    bgm_play.setAutoPlay(true);
  }

  public static void playItem(){
    MediaPlayer item_play = new MediaPlayer(item);
    item_play.play();
  }

  public static void playGoal(){
    MediaPlayer goal_play = new MediaPlayer(goal);
    goal_play.play();
  }
}
