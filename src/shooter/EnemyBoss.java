package shooter;
import javax.swing.*;
import java.awt.*;
public class EnemyBoss {
    /*JPanel healthBarPanel;
    Container con;
    JProgressBar healthBar = new JProgressBar();*/
    //  нач координаты и размер объекта
    private double x; private double y;
    private double h; private double w;

    private Color color; private int type;
    private int rank; public static double speed;
    private double dx; private double dy;
    private double health;
    Image img = new ImageIcon("res/boss.gif").getImage();//загрузка картинки
    Image img1 = new ImageIcon("res/boss.gif").getImage();//загрузка картинки
    public Rectangle getRect() { // получение прямоугоьников
        return new Rectangle((int) x, (int) y, 100, 100); //возвращаем конструктор с размером объекта

    }
    public EnemyBoss(int type, int rank) {
        this.type = type;
        this.rank = rank;
        switch (type) {
            case (1):
                color = Color.GREEN;
                switch (rank) {
                    case (1):
                        x = Math.random() * GamePanel.WIDTH;
                        y = 20;
                        w = 100;
                        h = 100;
                        speed = 25;
                        health = 50;
                        if(health==40){
                            speed += 1;
                        }
                        if(health==20){
                            speed += 1;
                        }
                        double angle = Math.toRadians(Math.random() * 360);
                        dx = Math.sin(angle) * speed;
                        dy = Math.cos(angle) * speed;
                }
        }
    }
    // геттеры
    public double getX() { return x;}
    public double getY() { return y;}
    public double getW() { return w;}
    public double getH(){ return h;}
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
        /*con = Graphics2D.getContentPane();
        healthBarPanel = new JPanel();
        healthBarPanel.setBounds(100,15,200,30);
        healthBarPanel.setBackground(Color.GREEN);
        con.add(healthBarPanel);
        healthBar = new JProgressBar(0,50);
        healthBar.setPreferredSize(new Dimension(200,30));
        healthBarPanel.add(healthBar);*/
        //hpbar ma?
        Color bacColor1 = new Color(12, 0, 137, 173);//созд обьект клсса цвет
        g.setColor(bacColor1);// передаём цвет граф объекту
        g.fillRect(300,30,500,5);

        Color bacColor = new Color(255, 0, 0, 193);//созд обьект клсса цвет
        g.setColor(bacColor);// передаём цвет граф объекту
        g.fillRect(300,30,(int)health*10,5);
        if(dx<0) g.drawImage(img,(int)x,(int)y,null);//отрисовываем элемент в координатах
        if(dx>0) g.drawImage(img1,(int)x,(int)y,null);//отрисовываем элемент в координатах
    }
}