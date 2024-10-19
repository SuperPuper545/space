package shooter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * меню игры
 */
public class Menue {
    private int buttonWidth, buttonWidth1;//шширина кнопки
     private int buttonHeight, buttonHeight1;//высота кнопки
     private Color color1;//цвет
     private String s,s1;//надпись
     private int transp = 0;// прозрачность

    //
    public Menue(){
       buttonWidth = 120;
       buttonWidth1 = 50;
       buttonHeight =60;
       buttonHeight1 =50;
       color1 = Color.GRAY ;
       s = "Play";
       s1 = "K";
    }
    public void update(){
        if (GamePanel.mouseX >GamePanel.WIDTH/2 - buttonWidth/2 &&
                GamePanel.mouseX < GamePanel.WIDTH/2 + buttonWidth/2 &&
                GamePanel.mouseY > GamePanel.HEIGHT/2 - buttonHeight/2 &&
                GamePanel.mouseY < GamePanel.HEIGHT/2 + buttonHeight/2){// если курсор попал в кнопку
                transp = 60;
                if (GamePanel.leftMouse){
                    GamePanel.state = GamePanel.STATES.PLAY;
                }
        }else {
            transp = 0;
        }
    }

    public void draw(Graphics2D g){
        g.setColor(color1);//передаём цвет
        g.setStroke(new BasicStroke(3));//обводка
        g.drawRect(GamePanel.WIDTH/2-buttonWidth/2,GamePanel.HEIGHT/2-buttonHeight/2,buttonWidth,buttonHeight);//квадрат по центру
        g.drawRect(GamePanel.WIDTH/17-buttonWidth1/5,GamePanel.HEIGHT/18-buttonHeight1/4,buttonWidth1,buttonHeight1);
        g.setColor(new Color(255,255,255, transp));//передаём цвет c прозрачностью
        g.fillRect(GamePanel.WIDTH/2-buttonWidth/2,GamePanel.HEIGHT/2-buttonHeight/2,buttonWidth,buttonHeight);
        g.setStroke(new BasicStroke(1));//обводка
        g.setColor(color1);//передаем цвет
        g.setFont(new Font("Algerian",Font.BOLD,40));//передаем шрифт

        long length = (int) g.getFontMetrics().getStringBounds(s,g).getWidth();//ширина строки
        g.drawString(s,(int)(GamePanel.WIDTH/2-length/2),(int)(GamePanel.HEIGHT/2+buttonHeight/4));//рисуем надпись

        long video = (int) g.getFontMetrics().getStringBounds(s1,g).getWidth();
        g.drawString(s1,(int)(GamePanel.WIDTH/15-video/5),(int)(GamePanel.HEIGHT/14+buttonHeight1/4));
    }
}
