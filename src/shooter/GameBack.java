package shooter;
/**
 Задний фон игровой панели
 */
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
public class GameBack {
    Image img = new ImageIcon("res/fonn.gif").getImage();//загрузка картинки
    public void update() {  //обновление
    }
    public void draw(Graphics2D g) {  //прорисовка в Graphics2D
        g.drawImage(img,(int)0,(int)0,1024,700,null);//отрисовываем элемент в координатах
    }
}


