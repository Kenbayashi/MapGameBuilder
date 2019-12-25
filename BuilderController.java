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
    //Selector charaSelector = new Selector("./chara/");

    AnimationItem item;

    //イニシャライザ
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blockSelector.setDelegate(this);
        //charaSelector.setDelegate(this);

        titleField.setPromptText("入力してください");

        Image[] images = getImages("./chara/");
        item = new AnimationItem(charaView, images, true);
    }

    //アイテムをセット
    @Override
    public void setData(Selector selector) {
        if (selector == blockSelector) {
            selector.items = toAnimamtionItems(blockView, selector.dir);
            selector.currentItem().start();
        } 
        /*  selectorを追加したら解除
        if (selector == itemSelector) {
            selector.items = toAnimationItems(itemView, selector.dir);
            selector.currentItem().start();
        }

        if (selector == charaSelector) {
            selector.items = toAnimationItems(charaView, selector.dir);
            selector.currentItem().start();
        }
        */
    }

    //右ボタン
    public void blockNextAction(ActionEvent event) {
        blockSelector.nextItem();
    }

    //左ボタン
    public void blockPrevAction(ActionEvent event) {
        blockSelector.prevItem();
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

        //ディレクトリを作成
        final File gamesDir = new File("games");
        final File titleDir = new File("games/" + title);
        final File pngDir   = new File("games/" + title + "/png");
        final File wallDir  = new File("games/" + title + "/png/wall");

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
                System.out.println("games/" + title + "/png/wall を作成");
                wallDir.mkdir();
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        //もろもろのコピー
        Image[]  blockImages = blockSelector.currentItem().getImages();
        String blockPath = "games/" + title + "/png/wall/";
        copyImages(blockImages, blockPath);

        copyDir("resourses/", "games/" + title + "/");

        //メッセージ
        System.out.println("\n\n" + "Use following commands to play new MapGame!!" + "\n");
        System.out.println("cd games/" + title + "/");
        System.out.println("javac *.java");
        System.out.println("java MapGame" + "\n");

        //アプリケーションを終了
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

    //ディレクトリからAnimationItem[]を作成
    private AnimationItem[] toAnimamtionItems(ImageView imageView, String dir) {
        try {
            File[] files = new File(dir).listFiles();
            AnimationItem[] items = new AnimationItem[files.length];

            for (int i = 0; i < files.length; i++) {
                Image[] images = getImages(dir + files[i].getName());
                items[i] = new AnimationItem(imageView, images, false);
            }

            return items;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    //指定された画像ファイルをコピー
    private void copyImages(Image[] images, String target) {
        for (int i = 0; i < images.length; i++) {
            final File imgFile = new File(target + i + ".png");
            try {
                imgFile.createNewFile();
                ImageIO.write(SwingFXUtils.fromFXImage(images[i], null), "png", imgFile);
                System.out.println("画像ファイルをコピー");
            } catch (IOException e) {
                System.err.println(e);
            }
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

                Path sourcePath = Paths.get(pathIn);
                Path targetPath = Paths.get(pathOut);
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
