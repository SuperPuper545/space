package shooter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class EnemyBossFinal{
    public double x4; public double y4;
    private double h; private double w;
    private Color color; private int type;
    private int rank; private double speed;
    private double dx; private double dy;
    public double xd; public double yd;
    public double speedx; public double speedy;
    private double len; private double m1; private double m2;
    public double health;
    public static boolean isFiring1;
    Image img = new ImageIcon("res/boss2.gif").getImage();
    Image img1 = new ImageIcon("res/boss2.gif").getImage();
    public Rectangle getRect() { // получение прямоугоьников
        return new Rectangle((int) x4, (int) y4, 200, 200);
    }
    public EnemyBossFinal(int type, int rank) {
        this.type = type;
        this.rank = rank;
        switch (type) {
            case (1):
                color = Color.GREEN;
                switch (rank) {
                    case (1):
                        x4 = Math.random() * GamePanel.HEIGHT;
                        y4 = 600;
                        w = 100;
                        h = 100;
                        speed = 6;
                        health = 150;
                }
        }
    }
    public double getX() { return x4;}
    public double getY() { return y4;}
    public double getW() { return w;}
    public double getH(){ return h;}
    public boolean remove_f() {
        if (health <= 0) {
            return true;
        }
        return false;
    }
    public void hit() {
        health--;
    }
    public void update(){
        xd = GamePanel.player.getX() - getX();
        yd = GamePanel.player.getY() - getY();
        len =  Math.sqrt(xd * xd + yd * yd);
        speedx = xd / len;
        speedy = yd / len;
        m1 = speedx * speed;
        m2 = speedy * speed;
        x4 += m1;
        y4 += m2;
        if (health >= 149) {
            GamePanel.bullets1.add(new BulletBoss());
        }
    }
    public void draw(Graphics2D g){
        Color bacColor1 = new Color(23, 0, 193, 255);
        g.setColor(bacColor1);
        g.fillRect(250,30,600,5);
        Color bacColor = new Color(255, 0, 0, 255);
        g.setColor(bacColor);// передаём цвет граф объекту
        g.fillRect(250,30,(int)health*4,5);
        if(x4<0) g.drawImage(img,(int)x4,(int)y4,null);
        if(x4>0) g.drawImage(img1,(int)x4,(int)y4,null);
    }
}