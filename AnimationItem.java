import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;

public class AnimationItem extends AnimationTimer {

    private ImageView imageView;
    private Image[]   images;

    public Attribute attribute;
    public String    identifier;

    //variables for animation
    private int       index     = 0;
    private long      duration  = 500 * 1000000L;
    private long      startTime = 0;
    private long      count     = 0L;
    private long      preCount  = 0L;
    private boolean   isPlus    = true;

    //コンストラクタ
    public AnimationItem(ImageView imageView, Attribute attribute, String id, boolean autoStart) {
        this.imageView  = imageView;
        this.identifier = id;

        this.attribute = attribute;

        //imagesを設定する
        if (attribute != null) {
            this.images = loadImages();
            System.out.println("complete");
        } else {
            System.err.println("不正なタイプです");
            System.exit(0);
        }

        if (autoStart) {
            this.start();
        }
    }

    public String getID() {
        return attribute.getValue() + ":" + identifier;
    }

    public ImageView getImageView() {
        return imageView;
    }

    //ディレクトリから画像をロードする
    private Image[] loadImages() {
        try {
            String  dir   = "png/" + attribute.getValue() + "/" + identifier; 
            File[]  files = new File(dir).listFiles();

            if (files[0].isDirectory()) {
                File[]  downFiles = new File(dir + "/down").listFiles();
                Image[] imgs      = new Image[downFiles.length];
                index             = imgs.length;

                for (int i = 0; i < files.length; i++) {
                    System.out.println("load: " + downFiles[i].getName());
                    imgs[i] = new Image(dir + "/" + downFiles[i].getName());
                }

                return imgs;
            } else {
                Image[] imgs  = new Image[files.length];
                index         = imgs.length;

                for (int i = 0; i < files.length; i++) {
                    System.out.println("load: " + files[i].getName());
                    imgs[i] = new Image(dir + "/" + files[i].getName());
                }

                return imgs;
            }

        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    //タイプを定義
    public enum Attribute {
        Start("Start"),
        Goal("Goal"),
        Wall("Wall"),
        Space("Space"),
        Enemy("Enemy");

        private String name;

        private Attribute(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }
    }

    //アニメーション本体
    @Override
    public void handle(long now) {
        if (startTime == 0) {
            startTime = now;
        }

        preCount = count;
        count    = (now - startTime) / duration;

        if (preCount != count) {
            if (isPlus) {
                index++;
            } else {
                index--;
            }

            if (index < 0) {
                if (1 < images.length) {
                    index = 1;
                } else {
                    index = 0;
                }
                
                isPlus = true;
            }

            if (images.length <= index) {
                if (1 < images.length) {
                    index = images.length - 2;
                } else {
                    index = 0;
                }

                isPlus = false;
            }

            imageView.setImage(images[index]);
        }
    }
}
