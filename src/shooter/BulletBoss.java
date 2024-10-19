package shooter;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
/**
 * пуля
 */
public class BulletBoss {
    //  нач координаты и размер объекта
    private double x;
    private double y;
    private double w ; //ширина обьекта
    private double h ; // высота объекта
    private Color color;
    private int speed;
    //public int magazine;
    private double angl; // угол поворота
    private double distX;// разница по х от мышки до пули
    private double distY;//
    private double dist;// расстояние от мышки до пули
    Image img = new ImageIcon("res/bullb.gif").getImage();//загрузка картинки

    public Rectangle getRect() { // получение прямоугоьников
        return new Rectangle((int) x, (int) y, 100, 22); //возвращаем конструктор с размером объекта
    }
    public BulletBoss(){
// присвиваем нач положение пули = коодинатам героя
        x = 29+GamePanel.player.getX();
        y = 25+GamePanel.player.getY();
        w = 100;
        h = 22;
        speed = 10; // скорость
        distX =GamePanel.player.getX() -x ;// разница по х от прицела до пули
        distY =y - GamePanel.player.getY() ; //разница по y от прицела до пули
        dist = (Math.sqrt(distX*distX + distY*distY));//  расстояние
        color = Color.WHITE;// цвет */
    }
    // геттеры
    public double getX() { return  x; }
    public double getY(){
        return  y;
    }
    public double getW(){
        return  w;
    }
    public double getH(){
        return  h;
    }
    // смещение пули
    public void update(){
        this.x = x+speed*distX/dist;
        this.y = y-speed*distY/dist;

        if(Listeners.is_nip) {// если работает захват
            distX = GamePanel.player.getX() - this.x;// разница по х
            distY = this.y - GamePanel.player.getY();
            dist = (Math.sqrt(distX * distX + distY * distY));
            if (distX > 0) angl = Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
            if (distX < 0) angl = -Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
        }
    }

    // отрисовка
    public void draw(Graphics2D g){
        //g.drawImage(img,(int)x,(int)y,null);//отрисовываем элемент в координатах
        AffineTransform origXform;
        origXform = g.getTransform();// получаем текущее значение
        AffineTransform newXform = (AffineTransform)(origXform.clone()); // клонируем текущее значение
        if( !Listeners.is_nip) {

            if (distX > 0) angl = Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
            if (distX < 0) angl = -Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
        }
        newXform.rotate(angl, x, y); // вертим полученное
        g.setTransform(newXform); // ставим
        g.drawImage(img,(int)x,(int)y,null); // здесь рисуем картинку
        g.setTransform(origXform); // возвращаем старое значение

    }
    // проверка где пуля
    public boolean remove_f(){
        if(y < 20 || y>GamePanel.HEIGHT || x<0 || x > GamePanel.WIDTH) { //если вылетела
            return true;
        }
        return false;
    }
}