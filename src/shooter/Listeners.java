package shooter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 обработчик событий
 */
public class Listeners implements MouseListener, KeyListener, MouseMotionListener {
    // проверка нажатой клавиши
    private boolean isFiring_on; // дослать патрон
    public static boolean is_nip;//захват
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();// получить код нажатой клавиши
        // проверка  клавиши
        if (key == KeyEvent.VK_W) {
            Player.up = true;
        }
        if (key == KeyEvent.VK_S) {
            Player.down = true;
        }
        if (key == KeyEvent.VK_A) {
            Player.left = true;
        }
        if (key == KeyEvent.VK_D) {
            Player.right = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            if (isFiring_on)// если патрон в патроннике
            Player.isFiring = true; //стрельба разрешена
            isFiring_on = false;  // нет патрона
        }
        if (key == KeyEvent.VK_ESCAPE) {
            GamePanel.state = GamePanel.STATES.MENUE;
        }
        if (key == KeyEvent.VK_K){
            File file = new File("C:/Users/lpn77/Desktop/Java/jh/res/vid/openv.exe");
            File file1 = new File("C:/Users/lpn77/Desktop/Java/jh/res/vid/openv.py");
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ioException) {
                try {
                    Desktop.getDesktop().open(file1);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
        if (key == KeyEvent.VK_L){
            File file = new File("C:/Users/lpn77/Desktop/Java/jh/res/vid/openvv.exe");
            File file1 = new File("C:/Users/lpn77/Desktop/Java/jh/res/vid/openvv.py");
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ioException) {
                try {
                    Desktop.getDesktop().open(file1);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
    // проверка отжатой клавиши
    public void keyReleased(KeyEvent e){
             int key = e.getKeyCode();
             if (key == KeyEvent.VK_W) {
                 Player.up = false;
             }
             if (key == KeyEvent.VK_S) {
                 Player.down = false;
             }
             if (key == KeyEvent.VK_A) {
                 Player.left = false;
             }
             if (key == KeyEvent.VK_D) {
                 Player.right = false;
             }
             if (key == KeyEvent.VK_SPACE) {
                 Player.isFiring = false;
                 isFiring_on = true; // патрон в патроннике
             }
    }
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //GamePanel.player.isFiring = true;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (GamePanel.state == GamePanel.STATES.MENUE) {
                GamePanel.leftMouse = true;
            }
            if (GamePanel.state == GamePanel.STATES.PLAY) {
                Player.isFiring = true;
            }
            if (GamePanel.state == GamePanel.STATES.OVER){
                GamePanel.leftMouse = true;
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            is_nip= true;
        }
    }
        public void mouseReleased(MouseEvent e){
            if (e.getButton() == MouseEvent.BUTTON1) {
                GamePanel.player.isFiring = false;
                //isFiring_on = true;
                GamePanel.leftMouse = false;
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
               is_nip= false;
            }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {// метод переноса мышкой
        GamePanel.mouseX = e.getX();// получаем коорд. мыши
        GamePanel.mouseY = e.getY();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        GamePanel.mouseX = e.getX();
        GamePanel.mouseY = e.getY();
    }
}