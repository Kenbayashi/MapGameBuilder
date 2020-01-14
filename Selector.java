public class Selector {

    private SelectorDelegate delegate = null;

    public int currentNum = 0;
    public AnimationItem[] items;

    public Selector() {
    }

    public void setDelegate(SelectorDelegate delegate) {
        this.delegate = delegate;
        this.delegate.setData(this);
        items[0].start();
    }

    //現在のアイテム
    public AnimationItem getItem() {
        return items[currentNum];
    }

    //次のアイテム
    public void nextItem() {
        items[currentNum].stop();

        if (currentNum == items.length - 1) {
            currentNum = 0;
        } else {
            currentNum++;
        }

        items[currentNum].start();
    }

    //前のアイテム
    public void prevItem() {
        items[currentNum].stop();

        if (currentNum == 0) {
            currentNum = items.length - 1;
        } else {
            currentNum--;
        }

        items[currentNum].start();
    }
}
