package shooter;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import static shooter.GamePanel.*;
/**
 главный герой игры
 */
public class Player {
    //  нач координаты и размер объекта
    public double x5;//координа х героя
    public double y5;//координа y героя
    private double w ; //ширина обьекта
    private double h ; // высота объекта
    public int speed;// скорость
    private Color color1;// цвет
    private double angl; // угол поворота
    private double distX;// разница по х от мышки
    private double distY;//
    private double dist;// расстояние от мышки
    // стаич прем - кавиши перемещения
    public static boolean up; public static boolean down;
    public static boolean left; public static boolean right;
    private double dx;// смещение
    private double dy;
    public static boolean isFiring;//стрельба
    public double health;// здоровье
    Image img = new ImageIcon("res/player.png").getImage();//загрузка
    public Rectangle getRect() { // получение прямоугоьников
        return new Rectangle((int) x5, (int) y5, 58, 74); //возвращаем конструктор с размером объекта
    }
    // Constructor
    public Player(){
        x5 = GamePanel.WIDTH / 2;// нач координаты героя
        y5 = 500;
        w = 58;
        h = 74;
        speed = 8;
        color1 = Color.WHITE;
        health = 6111;
        //  расстояние от мышки до пули
        angl= 0;
        // смещение
         dx = 0;
         dy = 0;
        // нач знач клавиш
        up = false;
        down = false;
        left = false;
        right = false;
        isFiring = false;
    }
    //  гетеры
    public double getX(){
        return  x5;
    }
    public double getY(){
        return  y5;
    }
    public double getW(){
        return  w;
    }
    public double getH(){
        return  h;
    }
    // - здоровья
    public void hit() {
        health--;
    }
    // обновления
    public void update(){
        distX =GamePanel.mouseX -x5 ;// разница по х от мышки
        distY =y5 - GamePanel.mouseY ;
        dist = (Math.sqrt(distX*distX + distY*distY));
        if (distX>0)  angl = Math.acos(distY/(Math.sqrt(distX*distX + distY*distY)));
        if (distX<0)  angl =- Math.acos(distY/(Math.sqrt(distX*distX + distY*distY)));
        if (health <=0){
            //killed = true;
            health = 0;}
        // смещение героя по игровому полю
        if (up && y5 >20){
            GamePanel.a_fly.play(); GamePanel.a_fly.setVolume();
            GamePanel.a_fly.timer_play ();
             y5 -=  speed;
         }
        if (down && y5 <GamePanel.HEIGHT -h){
            GamePanel.a_fly.play(); GamePanel.a_fly.setVolume();
            GamePanel.a_fly.timer_play ();
            y5 += speed;
        }
        if (left && x5 >0){
            GamePanel.a_fly.play(); GamePanel.a_fly.setVolume();
            GamePanel.a_fly.timer_play ();
            x5 -= speed;
        }
        if (right && x5 <GamePanel.WIDTH -w){
            GamePanel.a_fly.play(); GamePanel.a_fly.setVolume();
            GamePanel.a_fly.timer_play ();
            x5 += speed;
        }
        if(up && left || up && right || down && left || down && right){
            dy = dy * Math.sin(Math.toRadians(45));
            dx = dx * Math.cos(Math.toRadians(45));
        }
        y5 += dy;
        x5 += dx;
        dy = 0;
        dx = 0;
        if (isFiring == true && GamePanel.wave.magazine >0) { // если стрельба true
            GamePanel.bullets.add(new Bullet());
            //звук выстрела
            GamePanel.a_bul.sound();// играть звук
            GamePanel.a_bul.setVolume();//громкость
            isFiring = false;//запрет для стрельбы
            GamePanel.wave.magazine--; // опустошаем магазин при выстреле
            if (GamePanel.wave.magazine <= 0) {
                GamePanel.wave.magazine = 0;
            }
        }
    }
    public void Play(){
        if(GamePanel.state.equals(GamePanel.STATES.PLAY)){
            a_play.play();
            a_play.setVolume();
            a_play.repeat();
        }
    }
    // отрисовка героя
    public void draw(Graphics2D g){
        Color bacColor = new Color(142, 0, 170, 97);//созд обьект клсса цвет
        g.setColor(bacColor);// передаём цвет граф объекту
        g.fillRect(45, 0, 110, 20);//рисуем прямоугольную область
        g.fillRect(45, 25, 110, 20);
        g.fillRect(45, 653, 100, 20);
         g.setColor(Color.GRAY);
         Font font = new Font("Courier",Font.BOLD,20);//Создём объект класса фонт (передаем в конструктор параметры)
         g.setFont(font);//устанвливаем наш шрифт
         ((Graphics2D) g).drawString("Health   "+(int)health ,50,16);//отрисовываем строкi
         ((Graphics2D) g).drawString("Rocket  "+ GamePanel.wave.magazine,50,42);
         ((Graphics2D) g).drawString("Enemy "+ GamePanel.enemies.size(),50,670);
        // ВРАЩЕНИЕ
        AffineTransform origXform; // создаем объект класса AffineTransform
        origXform = g.getTransform(); // получаем текущее значение
        AffineTransform newXform = (AffineTransform)(origXform.clone()); // клонируем это значение
        newXform.rotate(angl,x5+29,y5+25) ; // крутица вертица изображ
        g.setTransform(newXform); // ставим трансформированное изображение
        g.drawImage(img,(int)x5,(int)y5,null); // здесь рисуем картинку
        g.setTransform(origXform); // возвращаем старое значение
    }
}