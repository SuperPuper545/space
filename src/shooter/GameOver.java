package shooter;

import javax.swing.*;
import java.awt.*;

import static shooter.GamePanel.a_dead;
import static shooter.GamePanel.a_playBoss;

public class GameOver {
    private int buttonWidth, b1;//шширина кнопки
    private int buttonHeight, b2;//высота кнопки
    private Color color1;//цвет
    private String d,e,s,a,b;
    private int transp = 0;// прозрачность
    Image img = new ImageIcon("res/fonn2.gif").getImage();//загрузка картинки

    public GameOver(){
        buttonWidth = 150;
        b1 = 50;
        buttonHeight =70;
        b2 = 50;
        color1 = Color.WHITE ;
        b = "- Press on keyboard ";
        d = "Restart";
        e = "Captain, this is the end of our journey.";
        s = "  Communication with the ship was lost, the crew died ...";
        a = "L";
    }
    public void update(){
        if (GamePanel.mouseX >GamePanel.WIDTH/2 - buttonWidth/2 &&
                GamePanel.mouseX < GamePanel.WIDTH/2 + buttonWidth/2 &&
                GamePanel.mouseY > GamePanel.HEIGHT/2 - buttonHeight/2 &&
                GamePanel.mouseY < GamePanel.HEIGHT/2 + buttonHeight/2){// если курсор попал в кнопку
            transp = 60;
            if (GamePanel.leftMouse){
                a_dead.stop();
                a_playBoss.stop();
                GamePanel.state = GamePanel.STATES.PLAY;
            }
        }else {
            transp = 0;
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(img,(int)0,(int)0,1024,700,null);//отрисовываем элемент в координатах
        g.setColor(color1);//передаём цвет
        g.setStroke(new BasicStroke(2));//обводка
        g.drawRect(GamePanel.WIDTH/2-buttonWidth/2,GamePanel.HEIGHT/2-buttonHeight/2,buttonWidth,buttonHeight);//квадрат по центру
        g.drawRect(GamePanel.WIDTH/17-b1/5,GamePanel.HEIGHT/18-b2/4,b1,b2);
        g.setColor(new Color(100,250,225, transp));//передаём цвет c прозрачностью
        g.fillRect(GamePanel.WIDTH/2-buttonWidth/2,GamePanel.HEIGHT/2-buttonHeight/2,buttonWidth,buttonHeight);//квадрат по центру
        g.setStroke(new BasicStroke(1));//обводка
        g.setColor(color1);//передаем цвет
        g.setFont(new Font("Algerian",Font.BOLD,30));//передаем шрифт
        long length = (int) g.getFontMetrics().getStringBounds(d,g).getWidth();//ширина строки
        g.drawString(d,(int)(GamePanel.WIDTH/2-length/2),(int)(GamePanel.HEIGHT/2+buttonHeight/4));//рисуем надпись
        g.drawString(e,(int)(GamePanel.WIDTH/18-length/4),(int)(GamePanel.HEIGHT/7+buttonHeight/4));
        g.drawString(s,(int)(GamePanel.WIDTH/25-length/4),(int)(GamePanel.HEIGHT/5+buttonHeight/4));
        g.drawString(b,(int)(GamePanel.WIDTH/7-length/4),(int)(GamePanel.HEIGHT/15+buttonHeight/4));
        long video = (int) g.getFontMetrics().getStringBounds(a,g).getWidth();
        g.drawString(a,(int)(GamePanel.WIDTH/15-video/5),(int)(GamePanel.HEIGHT/14+buttonHeight/4));
    }
}
