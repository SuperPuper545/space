package shooter;
import java.awt.*;
import static shooter.GamePanel.*;
/**
 * Created by nikelen on 2017-02-20.
 */
public class Wave {
    public int waveNumber;//номер волны
    private int waveMultiplier;//кол врагов в волне
    private long waveTimer;// таймер
    private long waveDelay;// время между волнами
    private long waveTimerDiff;// разница врем
    private String waveText;// сообщ волны
    public int magazine;// количество патронов
    // Constructor
    public Wave (){
        waveNumber = 1;
        waveMultiplier = 7;
        waveTimer = 0;
        waveDelay = 5000;
        waveTimerDiff = 0;
        waveText = "WAVE";
    }
    //Создание врагов
    public void createEnemies(){
       int enemyCount = waveNumber * waveMultiplier;// колич врагов
        int enemyBossFinal = 1;
        int enemyBoss = 2;
        if (waveNumber == 1){
            a_menu.stop();// стоп музыка меню
            a_playBoss.play();
            a_playBoss.stop();
            a_playBoss1.play();
            a_playBoss1.stop();
            a_play.play();//играть зюмику
            a_play.setVolume();
            a_play.repeat();
        }
        if (waveNumber == 5){
            a_play.stop();
            a_playBoss.play();
            a_playBoss.setVolume();
            a_playBoss.repeat();
            while (enemyBoss  > 0){
                int type = 1;
                int rank = 1;
                GamePanel.enemies1.add(new EnemyBoss(type,rank));
                enemyBoss -= type * rank;
            }
            while (enemyCount > 0){//Не спавнить врагов во время первого боссфайта
                int type = 1;
                int rank = 1;
                enemyCount = 0;
            }
        }
        if (waveNumber == 6){
            player.health += 2;
            player.speed += 2;
            a_playBoss.stop();
            a_playfast.play();
            a_playfast.setVolume();
            a_playfast.repeat();
        }
        if (waveNumber == 10){
            player.health += 13;
            a_play.stop();
            a_playBoss1.play();
            a_playBoss1.setVolume();
            a_playBoss1.repeat();
            while (enemyBossFinal  > 0){
                int type = 1;
                int rank = 1;
                GamePanel.enemies2.add(new EnemyBossFinal(type,rank));
                enemyBossFinal -= type * rank;
                enemyCount = 15;
            }
        }
        if (waveNumber < 11){
            while (enemyCount > 0){// пока
                int type = 1;//
                int rank = 1;//
                GamePanel.enemies.add(new Enemy(type,rank));//
                enemyCount -= type * rank;// конец цикла создания врагов
            }
        }
        waveNumber++;
        magazine = 25*waveNumber;// количество патронов
    }
    public void update(){
      //обновление таймера и провнрка присутствия враговъ
        if(GamePanel.enemies.size() == 0 && waveTimer == 0 && GamePanel.enemies1.size() == 0 && GamePanel.enemies2.size() == 0){//если нет врагов и таймер=0
          waveTimer = System.nanoTime();//= текущее время
      }
        // проверка на создание врагов
        if (waveTimer>0){//если таймер больше времени задержки
            waveTimerDiff+= (System.nanoTime()- waveTimer)/1000000;// разница времени
            waveTimer = System.nanoTime();// время
        }
       if(waveTimerDiff > waveDelay){//если пауза закончилась
            createEnemies(); // создаём врагов
           waveTimer=0;// таймер
           waveTimerDiff=0;
        }
    }
       //показ надписи
    public boolean showWave(){
        if (waveTimer !=0){// если таймер не равен нулю
            return true; //  возвращ - правда
        }else{
            return false;} // возвращ - jopi
    }
    // отрисовка
    public void draw(Graphics2D g){
                 // закон изменения прозрачности
        double divider = waveDelay / 180; //сколько вемени прийдется на 1 градус изменения цвеtа
        double alpha = waveTimerDiff/divider;// показатель прозрачности в опреденный момент паузы
        alpha = 255*Math.sin(Math.toRadians(alpha)); //переводим прозрачн в радианы и определ синус и умнож на уровень прозррачности
        if (alpha < 0 ) alpha = 0;
        if (alpha > 255) alpha = 255;
        g.setFont(new Font("Courier",Font.BOLD,20));//передаём шрифт
        g.setColor(new Color(255,255,255,(int)alpha ));//полная строка
        String s = waveNumber +"th "+ waveText; //полная строка
        long length = (int) g.getFontMetrics().getStringBounds(s,g).getWidth();// длина надписи в пиксилях
        g.drawString(s,GamePanel.WIDTH/2 - (int)(length / 2),GamePanel.HEIGHT/2);// рисуем строчку в центре панели
    }
}
