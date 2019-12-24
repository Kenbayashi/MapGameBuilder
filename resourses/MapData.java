import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class MapData {
    public static final int TYPE_NONE   = 0;
    public static final int TYPE_WALL   = 1;
    public static final int TYPE_ITEM   = 2;
    public static final int TYPE_GOAL   = 3;
    public static final int TYPE_OTHERS = 4;
    private static final String mapImageFiles[] = {
        "png/SPACE.png",
        "png/WALL.png",
        "png/item.png",
        "png/goal.png",
        "png/SPACE.png"  // not used
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width;
    private int height;

    Random random = new Random(); // random.nextInt(n) Return Random Number in n

    /* Map Generate */

    MapData(int x, int y){
        mapImages     = new Image[mapImageFiles.length - 1];
        mapImageViews = new ImageView[y][x];
        for (int i=0; i < mapImageFiles.length - 1; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width  = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL); //Fill Map TYPE_WALL
        digMap(1, 3);
        putSomething(MapData.TYPE_ITEM, 2);
        putSomething(MapData.TYPE_GOAL);
        setImageViews();
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    public void setMap(int x, int y, int type){
        if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
            return;
        }
        maps[y][x] = type;
    }

    // Views
    public void setImageViews() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }

    // Fill Map
    public void fillMap(int type){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                maps[y][x] = type;
            }
        }
    }

    // Dig
    public void digMap(int x, int y){
        setMap(x, y, MapData.TYPE_NONE);
        int[][] dl = {{0,1},{0,-1},{-1,0},{1,0}};
        int[] tmp;

        for (int i=0; i<dl.length; i++) {
            int r = (int)(Math.random()*dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i=0; i<dl.length; i++){
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
                setMap(x+dx, y+dy, MapData.TYPE_NONE);
                digMap(x+dx*2, y+dy*2);

            }
        }
    }

    // Put Something in SPACE (MapData.TYPE_SOMETHING, Number) by KUBOTA HARUKI //
    public void putSomething(int type, int n){
      int number = 0;
      while(number < n){
        int X = random.nextInt(width);
        int Y = random.nextInt(height);
        if(getMap(X, Y) == MapData.TYPE_NONE){
          setMap(X, Y, type);
          number ++;
        }
      }
    }

    // Put One Something(MaPData.TYPE)
    public void putSomething(int type){
      int number = 0;
      while(number < 1){
        int X = random.nextInt(width);
        int Y = random.nextInt(height);
        if(getMap(X, Y) == MapData.TYPE_NONE){
          setMap(X, Y, type);
          number ++;
        }
      }
    }

    // 座標の画像をNONEにする. by KUBOTA
    public void removeSomething(int x, int y){
      setMap(x, y, MapData.TYPE_NONE);
      System.out.println("remove " + getSomethingNumber(MapData.TYPE_ITEM) + getImageView(x,y));
      setImageViews();
    }

    // Return Something Number
    public int getSomethingNumber(int type){
      int number = 0;
      for (int y=0; y<height; y++){
          for (int x=0; x<width; x++){
            if(getMap(x, y) == type){
              number ++;
            }
          }
      }
      return number;
    }
    // by KUBOTA HARUKI //

    public void printMap(){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                if (getMap(x,y) == MapData.TYPE_WALL){
                    System.out.print("++");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }
}
