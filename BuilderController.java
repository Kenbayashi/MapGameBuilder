import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BuilderController implements SelectorDelegate, Initializable {

    @FXML public TextField titleField;
    @FXML public ImageView selectorView;
    @FXML public GridPane  gridPane;

    private Selector selector = new Selector();

    //マップデータ
    private AnimationItem[][] data = new AnimationItem[21][15];

    //イニシャライザ
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selector.setDelegate(this);

        gridPane.getChildren().clear();
        initMap();
        printMap();
    }

    //セレクタにデータを入れるメソッド
    @Override
    public void setData(Selector sender) {
        final int allCount = Space.length() + Wall.length();
        sender.items = new AnimationItem[allCount];
        int count = 0;

        //空白を追加してるとこ
        for (Space spaceItem : Space.values()) {
            sender.items[count] = new AnimationItem(selectorView, AnimationItem.Attribute.Space, spaceItem.getValue(), false);
            count++;
        }

        //壁を追加してるとこ
        for (Wall wallItem : Wall.values()) {
            sender.items[count] = new AnimationItem(selectorView, AnimationItem.Attribute.Wall, wallItem.getValue(), false);
            count++;
        }
/*
        //敵キャラを追加してるとこ
        for (Enemy enemyItem : Enemy.values()) {
            sender.items[count] = new AnimationItem(selectorView, AnimationItem.Attribute.Enemy, enemyItem.getValue(), false);
            count++;
        }
*/
    }

    //壁を増やすならココへ
    protected enum Wall {
        brown("brown"),
        blue("blue"),
        human("human"),
        metal("metal");

        private String name;

        private Wall(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }

        public static int length() {
            return Wall.values().length;
        }
    }

    //空白を増やすならココへ
    protected enum Space {
        white("SPACE");

        private String name;

        private Space(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }

        public static int length() {
            return Space.values().length;
        }
    }

    //敵キャラを増やすならココへ
    protected enum Enemy {
        def("def");

        private String name;

        private Enemy(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }

        public static int length() {
            return Enemy.values().length;
        }
    }

    //右ボタン
    public void nextAction(ActionEvent event) {
        selector.nextItem();
    }

    //左ボタン
    public void prevAction(ActionEvent event) {
        selector.prevItem();
    }

    //作成ボタン
    public void decideAction(ActionEvent event) {
    }

    //キャンセルボタン
    public void exitAction(ActionEvent event) {
        Platform.exit();
    }

    //マップを描画
    private void printMap() {
        gridPane.getChildren().clear();

        for (int x = 0; x < 21; x++) {
            for (int y = 0; y < 15; y++) {
                //gridPane.add(data[x][y].getImageView(), x, y);
                ImageView view  = data[x][y].getImageView();
                GridPane.setConstraints(view, x, y);
                gridPane.getChildren().addAll(view);
            }
        }
    }

    //クリックしたときの動作
    public void clickGrid(MouseEvent event) {
        final int x = (int)(event.getX() / 32);
        final int y = (int)(event.getY() / 32);
        System.out.println("clicked at:" + x + " " + y);

        final AnimationItem oldItem = data[x][y];
        final ImageView     view    = oldItem.getImageView();
        final AnimationItem newItem = new AnimationItem(view, selector.getItem().attribute, selector.getItem().identifier, false);

        oldItem.stop();
        newItem.start();
        data[x][y] = newItem;
    }

    //空マップデータを作成
    private void initMap() {
        for (int x = 0; x < 21; x++) {
            for (int y = 0; y < 15; y++) {
                System.out.println("x: " + x + " y: " + y);
                if (shouldWall(x, y)) {
                    //壁にする
                    ImageView view = new ImageView();
                    data[x][y]     = new AnimationItem(view, AnimationItem.Attribute.Wall, Wall.brown.getValue(), true);
                } else {
                    //空白にする
                    ImageView view = new ImageView();
                    data[x][y]     = new AnimationItem(view, AnimationItem.Attribute.Space, Space.white.getValue(), true);
                }
            }
        }
    }

    //座標の地点が壁であるべきかどうかの判定
    private boolean shouldWall(int x, int y) {
        if (x == 0 || x == 20 || y == 0 || y == 14) {
            return true;
        } else {
            return false;
        }
    }
}
