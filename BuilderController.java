import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;

public class BuilderController implements SelectorDelegate, Initializable {

    @FXML public TextField titleField;

    @FXML public ImageView blockView;
    @FXML public ImageView charaView;

    Selector blockSelector = new Selector("./blocks/");
    //Selector charaSelector = new Selector("./charas/");

    //イニシャライザ
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blockSelector.setDelegate(this);
        //charaSelector.setDelegate(this);

        titleField.setPromptText("入力してください");
    }

    //画像をセット
    @Override
    public void setData(Selector selector) {
        selector.images = getImages(selector.dir);

        blockView.setImage(blockSelector.currentImage());
        //charaView.setImage(charaSelector.currentImage());
    }

    //右ボタン
    public void blockNextAction(ActionEvent event) {
        blockView.setImage(blockSelector.nextImage());
    }

    //左ボタン
    public void blockPrevAction(ActionEvent event) {
        blockView.setImage(blockSelector.prevImage());
    }

    //右ボタン
    public void charaNextAction(ActionEvent event) {
        //charaView.setImage(charaSelector.nextImage());
    }

    //左ボタン
    public void charaPrevAction(ActionEvent event) {
        //charaView.setImage(charaSelector.prevImage());
    }

    //キャンセルボタン
    public void cancelButtonAction(ActionEvent event) {
        //アプリケーションを終了
        System.exit(0);
    }

    //作成ボタン
    public void decideButtonAction(ActionEvent event) {
        //いろいろ取得
        final String title = titleField.getText();

        //タイトル関連
        if (title.isEmpty()) {
            System.out.println("タイトルが入力されていません");
            return;
        } else {
            System.out.println("タイトル: " + title);
        }

        //ここからファイル操作
        final File gamesDir = new File("games");
        final File titleDir = new File("games/" + title);
        final File pngDir   = new File("games/" + title + "/png");

        if (!gamesDir.exists()) {
            gamesDir.mkdir();
        }

        if (titleDir.exists()) {
            System.out.println("すでに同名のゲームが存在します");
            return;
        } else {
            try {
                System.out.println("games/" + title + " を作成");
                titleDir.mkdir();
                System.out.println("games/" + title + "/png を作成");
                pngDir.mkdir();
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        //もろもろのコピー
        Image  blockImg  = blockSelector.currentImage();
        String blockPath = "games/" + title + "/png/WALL.png";
        copyImage(blockImg, blockPath);

        copyDir("resourses/", "games/" + title + "/");

        System.out.println("\n\n" + "Use following commands to play new MapGame!!" + "\n");
        System.out.println("cd games/" + title + "/");
        System.out.println("javac *.java");
        System.out.println("java MapGame" + "\n");

        System.exit(0);
    }

    //画像の配列を指定したディレクトリから読み込む
    private Image[] getImages(String dir) {
        try {
            File[]  files  = new File(dir).listFiles();
            Image[] images = new Image[files.length];

            for (int i = 0; i < files.length; i++) {
                System.out.println("add: " + files[i].getName());
                images[i] = new Image(dir + files[i].getName());
            }

            return images;

        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    //指定された画像ファイルをコピー
    private void copyImage(Image img, String target) {
        final File imgFile = new File(target);
        try {
            imgFile.createNewFile();
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", imgFile);
            System.out.println("画像ファイルをコピー");
        } catch (IOException e) {
            System.err.println(e);
        }
  }

    //指定されたディレクトリをコピー
    private void copyDir(String dirIn, String dirOut) {
        final File[] files = new File(dirIn).listFiles();

        for (int i = 0; i < files.length; i++) {
            try {
                String pathIn  = dirIn  + files[i].getName();
                String pathOut = dirOut + files[i].getName();
                System.out.println(pathIn + " をコピー");

                //final File newFile = new File(pathOut);
                //newFile.createNewFile();

                Path sourcePath = Paths.get(pathIn);
                Path targetPath = Paths.get(pathOut);
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
