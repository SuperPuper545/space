package shooter;
import javax.swing.*;
public class GameStart {
    public static void main(String[] args) {
        GamePanel panel = new GamePanel();// создаём объект панель
        JFrame startFrame = new JFrame("Space");// создаем окно с названием
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// закрытие окна при клике крестика
        startFrame.setLocation(30, 0);// имзменяем местоположение фрейма.
        //startFrame.setLocationRelativeTo(null);//положение фрейма по центру
        startFrame.setContentPane(panel); // перенос в фрейм панели с GamePanel
        //startFrame.add(panel);
        //Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();// получим размер экрана
        //startFrame.setSize(screenSize);// установим его
        startFrame.pack();//размер фрейма как и размер его компонентов
        panel.start();// заускаем поток панели
        // окно видемо
        startFrame.setVisible(true);
        startFrame.setResizable(false);
    }
}