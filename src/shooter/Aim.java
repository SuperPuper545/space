package shooter;
import javax.swing.*;
import java.awt.*;
/**
 * Прицел
 */
public class Aim {
    //  нач координаты и размер объекта
    private double x; private double y;
    private double w; //ширина обьекта
    private double h; // высота объекта
    private double dx;// смещение относительно начала картинки
    private double dy;
    private String img; private double dist;//дистанция
    public Aim( int x, int y,int w,int h,String img,int dx,int dy){
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.img = img; this.dx = dx; this.dy = dy;
    }
    public Rectangle getRect() { // получение прямоугоьников
        return new Rectangle((int) x, (int) y, (int) w, (int) h); //возвращаем конструктор с размером объекта
    }
    // геттеры
    public double getX() {return x;} public double getY() {return y;}
    public double getW() {return w;} public double getH() {return h;}
    // смещение
    public void update() {
        if(Listeners.is_nip){
            for (int k = 0; k < GamePanel.enemies.size(); k++) {// каждого из списка
                Enemy e = GamePanel.enemies.get(k); // выделяем элемент списка
                double ex = e.getX();// получаем коорд элемента
                double ey = e.getY();
                double ew = e.getW();
                double eh = e.getH();
                // отслеживаем центральный прицел
                double ax = GamePanel.aim.getX();// получаем коорд элемента центрального прицела
                double ay = GamePanel.aim.getY();
                double aw = GamePanel.aim.getW();
                double ah = GamePanel.aim.getH();
                if ((ax > ex - aw) && (ax < ex + ew) && (ay > ey - ah) && (ay < ey + eh)) {
                    this.x = ex;// всем прицелам присваиваем координаты врага
                    this.y = ey;
                    GamePanel.aim.x = ex+ew/2; //переопределяем положения центрального маркера
                    GamePanel.aim.y = ey+eh/2;
                }
            }
            // дальномер привязан к конкретным объектам
            dist = (Math.sqrt((GamePanel.player.getX()-GamePanel.aim.x)*(GamePanel.player.getX()-GamePanel.aim.x)
                    +(GamePanel.aim.y-GamePanel.player.getY())*(GamePanel.aim.y-GamePanel.player.getY())));
        }
         else {
            this.x = GamePanel.mouseX + dx;
            this.y = GamePanel.mouseY + dy;
        }
    }
    // отрисовка
    public void draw(Graphics2D g) {
        //загрузка картинки//
        if(Listeners.is_nip) GamePanel.aim1.img = "res/aim2.gif";
        else GamePanel.aim1.img = "res/aim1.png";
        Image im = new ImageIcon(img).getImage();
        g.drawImage(im,(int)this.x,(int)this.y,null);//отрисовываем элемент в координатах
    }
}