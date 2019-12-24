import javafx.scene.image.*;

public class Selector {

    private SelectorDelegate delegate = null;

    public int currentNum = 0;
    public String dir;

    public Image[] images;

    Selector(String dir) {
        this.dir = dir;
    }

    public void setDelegate(SelectorDelegate delegate) {
        this.delegate = delegate;
        this.delegate.setData(this);
    }

    //現在の画像
    public Image currentImage() {
        return images[currentNum];
    }

    //次の画像
    public Image nextImage() {
        if (currentNum == images.length - 1) {
            currentNum = 0;
        } else {
            currentNum++;
        }

        return images[currentNum];
    }

    //前の画像
    public Image prevImage() {
        if (currentNum == 0) {
            currentNum = images.length - 1;
        } else {
            currentNum--;
        }

        return images[currentNum];
    }
}
