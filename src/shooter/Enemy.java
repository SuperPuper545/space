package shooter;
import javax.swing.*;
import java.awt.*;
/**
враги
 */
public class Enemy {
    //  нач координаты и размер объекта
    private double x;
    private double y;
    private double h;
    private double w;

    private Color color;
    private int type;
    private int rank;
    public double speed;
    private double dx;
    private double dy;
    private double health;
    Image img = new ImageIcon("res/glazza.gif").getImage();//загрузка картинки
    Image img1= new ImageIcon("res/glazzaa.gif").getImage();//загрузка картинки

    public Rectangle getRect() { // получение прямоугоьников
        return new Rectangle((int) x, (int) y, 72, 76); //возвращаем конструктор с размером объекта
    }
    // Constructor

    public Enemy(int type,int rank){

       this.type = type;
       this.rank = rank;
       switch (type){
           case (1):color = Color.GREEN;
               switch (rank){
               case (1):

                   x = Math.random() * GamePanel.WIDTH;
                   y = 20;
                   w = 72;
                   h = 76;
                   speed = 10;
                   health = 1;
                   double angle = Math.toRadians(Math.random()*360);
                   dx = Math.sin(angle)*speed;
                   dy = Math.cos(angle)*speed;
           }    }

    }
    // геттеры
    public double getX() { return x;}
    public double getY() { return y;}
    public double getW() { return w;}
    public double getH(){
        return h;
    }
        // проверка здоровья

   public boolean remove_f() {
       if (health <= 0) {
           return true;
       }
       return false;
   }

    //уменьшение  здоровья
        public void hit() {
            health--;

        }

    // смещение
    public void update(){
    x += dx;
    y += dy;
        if(x <-100&& dx < 0) dx =- dx;
        if(x>GamePanel.WIDTH +100 && dx>0) dx = -dx;
        if(y<-150 && dy < 0) dy =- dy;
        if(y>GamePanel.HEIGHT+150&& dy>0) dy = -dy;
    }
    // отрисовка
    public void draw(Graphics2D g){
        if(dx<0) g.drawImage(img,(int)x,(int)y,null);//отрисовываем элемент в координатах
        if(dx>0) g.drawImage(img1,(int)x,(int)y,null);//отрисовываем элемент в координатах
    }
}
