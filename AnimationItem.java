import javafx.animation.AnimationTimer;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;

public class AnimationItem extends AnimationTimer {

    private ImageView imageView;
    private Image[]   images;

    //variables for animation
    private int       index     = 0;
    private long      duration  = 500 * 1000000L;
    private long      startTime = 0;
    private long      count     = 0L;
    private long      preCount  = 0L;
    private boolean   isPlus    = true;

    //コンストラクタ
    public AnimationItem(ImageView imageView, Image[] images, boolean autoStart) {
        this.imageView = imageView;
        this.images    = images;

        if (autoStart) {
            this.start();
        }
    }

    //imagesのゲッタ
    public Image[] getImages() {
        return images;
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

            if (index < 0 || images.length <= index) {
                index  = 1;
                isPlus = !isPlus;
            }

            imageView.setImage(images[index]);
        }
    }
}
