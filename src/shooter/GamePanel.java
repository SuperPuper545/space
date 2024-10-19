package shooter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
public class GamePanel extends JPanel implements Runnable {
    public static int WIDTH = 1024;
    public static int HEIGHT = 680;
    private Thread thread; // Создаем поток- ссылка на обьект класса Thread
    private BufferedImage image; // ссылка на обьект класса
    private Graphics2D g; // ссылка на обьект класса
    public static GameBack background;// ссылка на обьект класса
    public static Player player;// ссылка на обьект класса
    public static ArrayList<Bullet> bullets;
    public static ArrayList<BulletBoss> bullets1;
    public static ArrayList<shooter.Enemy> enemies;
    public static ArrayList<shooter.EnemyBoss> enemies1;
    public static ArrayList<shooter.EnemyBossFinal> enemies2;
    public static Wave wave;
    public static Menue menue;
    public static GameOver over;
    public static Aim aim1;
    public static Aim aim;
    public static Audio a_wave;
    public static Audio a_menu;
    public static Audio a_play;//звук игры
    public static Audio a_playfast;//звук игры
    public static Audio a_playBoss;//звук боссфайта
    public static Audio a_playBoss1;//звук боссфайта
    public static Audio a_bul;// звук выстрела
    public static Audio a_enem;// звук взрыва врага
    public static Audio a_enem1;// звук взрыва врага при столкновении с героем
    public static Audio a_fly;
    public static Audio a_dead;
    public static int mouseX;// координаты мышки
    public static int mouseY;
    public static boolean leftMouse;
    private int FPS;//
    private double millisToFPS;// fps в миллсек
    private long timerFPS;// таймер fps
    private int sleepTime; //сколько он будет спать
    public static   enum STATES{MENUE,PLAY,OVER} //обьявляем перечсления
    public static STATES state = STATES.MENUE;// переменная меню
    // конструктор
    public GamePanel() {
        super(); // активируем консруктор родителя
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // размер передаем в обьект класса Измерения
        setFocusable(true); //передаем фокус
        requestFocus(); // акивируем
        addMouseListener( new Listeners());// добавляем обработчик событий клик мышь
        addKeyListener( new Listeners());// добавляем обработчик событий клаава
         addMouseMotionListener(new Listeners());//добавляем обработчик событий перем мышь
    }
    // запуск потока
    public void start() {
        thread = new Thread((Runnable) this);
        thread.start();// запускаем поток
    }
    //метод от интерфейса Runnable (потока)
    @Override
    public void run() {
        FPS = 30; // задаем желаемый FPS
        millisToFPS = 1000/FPS; //пересчет в миллисек
        sleepTime = 0;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//сглаживание соседних пиксеей
        leftMouse = false;
        background = new GameBack();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        bullets1 = new ArrayList<BulletBoss>();
        enemies = new ArrayList<Enemy>();
        enemies1 = new ArrayList<EnemyBoss>();
        enemies2 = new ArrayList<EnemyBossFinal>();
        wave = new Wave();
        menue = new Menue();//
        over = new GameOver();
        aim1 = new Aim(GamePanel.mouseX,GamePanel.mouseY,72,76,"res/aim1.png",0,0);
        aim = new Aim(GamePanel.mouseX,GamePanel.mouseY,4,4,"res/aim.png",27,12);
        Toolkit kit = Toolkit.getDefaultToolkit();// переназначение
        Cursor myCursor =  kit.getDefaultToolkit().createCustomCursor(kit.getDefaultToolkit().getImage("")
                ,new Point(0,0),"myCursor");
        Cursor Default = kit.getDefaultToolkit().createCustomCursor(kit.getDefaultToolkit().getImage("res/aim.png")
                ,new Point(0,0),"Default");
        a_menu = new Audio("res/menu.wav",0.88);// инициализируем муз-фон с громкостью=0.88
       //вступительная музыка
        a_menu.play();// играть
        a_menu.setVolume();// громкость воспроизведения
        a_menu.repeat();// повторять
        a_play = new Audio("res/MAIN.wav",1.2);
        a_playfast = new Audio("res/MAINFAST.wav",1.2);
        a_playBoss = new Audio("res/boos.wav",0.88);
        a_playBoss1 = new Audio("res/bd.wav",0.88);
        a_bul = new Audio("res/laz.wav",0.68);
        a_enem = new Audio("res/boom.wav",0.88);
        a_enem1 = new Audio("res/auch.wav",0.88);
        a_wave = new Audio("res/siren.wav",0.78);
        a_fly = new Audio("res/da.wav",0.47,300);
        a_dead = new Audio("res/dead.wav",0.88);
        while (true) { // игровой цикл
            if (state.equals(STATES.MENUE)){// если пер state == MENUE
                this.setCursor(Default);// активируемсвой курсор
                background.update();// фон обновляем
                background.draw(g);// рисуем фон
                menue.update();//меню обновляем
                menue.draw(g);// рисуем меню
                gameDraw();// перерсовываем на панель
            }
            if (state.equals(STATES.PLAY)){// ира
                this.setCursor(myCursor);// активируемсвой курсор(невидимый)
                gameUpdate(); //обновление
                gameRender(); //перерисовка
                gameDraw(); ////перенос изображения на панель
            }
            if (state.equals(STATES.OVER)){
                this.setCursor(Default);// активируемсвой курсор
                background.update();// фон обновляем
                background.draw(g);// рисуем фон
                over.update();//меню обновляем
                over.draw(g);// рисуем меню
                gameDraw();// перерсовываем на панель
                if(state.equals(STATES.OVER)){//рестарт
                    enemies2.clear();//удаляем финального босса
                    enemies1.clear();//удаляем боссов
                    enemies.clear();//удаляем обычных врагов
                    player.health = 6;//возвращаем здоровье
                    wave.waveNumber = 1;//откатываем волну на начало
                }
            }
            timerFPS = System.nanoTime();// присвоим текущ время
            timerFPS = (System.nanoTime()-timerFPS)/1000000;//сколько прошло миллсек на операции выше
            if(millisToFPS > timerFPS){
                sleepTime = (int)(millisToFPS - timerFPS);
            } else sleepTime = 1;
            try {
                thread.sleep(sleepTime); //засыпаем на ... мс
            } catch (InterruptedException ex) { //если не удается заснуть- исключение
                ex.printStackTrace();
            }
            timerFPS = 0;// обнуляем таймер
            sleepTime = 1;// обновляем время сна
        }
    }
    //обновление
    public void gameUpdate() { //обновление
        background.update();// вызов метода для заднего плана
        player.update();// обновление
        aim1.update();
        aim.update();
        if (player.health<=0) { // если
            if (wave.waveNumber == 2 || player.health<=0){
                a_playBoss.stop();
                a_playBoss1.stop();
            }
            state = STATES.OVER;
            a_play.stop();
            a_dead.play();
            a_dead.setVolume();
            a_dead.repeat();
            //System.exit(1); //выход в систему ;// удалить элемент из списка врагов
        } else if (wave.waveNumber == 2 || player.health<=0){
            a_playBoss.stop();
            a_playBoss1.stop();
        }

            //bullets.update
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();// обновлям текущую пулю
            boolean remove_p = bullets.get(i).remove_f();//текущую пулю проверяем где она
            if (remove_p) { // если правдиво(улетела)
                bullets.remove(i);//удаляем пулю которая вылетела
                i--;//
            }
        }
        for (int i = 0; i < bullets1.size(); i++) {
            bullets1.get(i).update();// обновлям текущую пулю
            boolean remove_p = bullets1.get(i).remove_f();//текущую пулю проверяем где она
            if (remove_p) { // если правдиво(улетела)
                bullets1.remove(i);//удаляем пулю которая вылетела
                i--;//
            }
        }
        //enemies123.update
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();// обновлям текущего врага
        }
        for (int i = 0; i < enemies1.size(); i++) {
            enemies1.get(i).update();// обновлям текущего врага
        }
        for (int i = 0; i < enemies2.size(); i++) {
            enemies2.get(i).update();// обновлям текущего врага
        }
        //столкновение пули с врагом
        for (int i = 0; i < enemies.size(); i++) {// каждого врвгв из списка
            shooter.Enemy e = enemies.get(i); // выделяем элемент списка
            double ex = e.getX();// получаем коорд элемента
            double ey = e.getY();
            double ew = e.getW();
            double eh = e.getH();
            //по списку пуль
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b = bullets.get(j);// выделяем элемент списка
                double bx = b.getX();// получаем коорд элемента
                double by = b.getY();
                double bw = b.getW();
                double bh = b.getH();
                if ((bx>ex-bw) &&(bx<ex+ew) && (by>ey-bh) &&(by<ey+eh) ) { // если пересеклись
                    e.hit();// метод уменьшения здоровья врага
                    bullets.remove(j); // удаляем пулю из списка
                    //Проверка здоровья врага
                    boolean remove_p = e.remove_f(); // пер присваив значение метода пров здоров врага
                    if (remove_p) { // если правдиво
                        enemies.remove(i);// удалить элемент из списка врагов
                        a_enem.sound();//взрыв врага
                        a_enem.setVolume();
                        //i--;
                        break;
                         }
                }
                double ax = aim.getX();// получаем коорд элемента
                double ay = aim.getY();
                double aw = aim.getW();
                double ah = aim.getH();
                if ((bx > ax - bw) && (bx < ax + aw) && (by > ay - bh) && (by < ay + ah)&&(Listeners.is_nip))  // условие столкновения
                    bullets.remove(j); // удаляем пулю из списка
            }
        }

        for (int i1 = 0; i1 < enemies1.size(); i1++) {// каждого врвгв из списка
            shooter.EnemyBoss e1 = enemies1.get(i1); // выделяем элемент списка
            double e1x = e1.getX();// получаем коорд элемента
            double e1y = e1.getY();
            double e1w = e1.getW();
            double e1h = e1.getH();
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b = bullets.get(j);// выделяем элемент списка
                double bx = b.getX();// получаем коорд элемента
                double by = b.getY();
                double bw = b.getW();
                double bh = b.getH();
                if ((bx>e1x-bw) &&(bx<e1x+e1w) && (by>e1y-bh) &&(by<e1y+e1h) ) { // если пересеклись
                    e1.hit();// метод уменьшения здоровья врага
                    bullets.remove(j); // удаляем пулю из списка
                    //Проверка здоровья врага
                    boolean remove_p = e1.remove_f(); // пер присваив значение метода пров здоров врага
                    if (remove_p) { // если правдиво
                        enemies1.remove(e1);// удалить элемент из списка врагов
                        a_enem.sound();//взрыв врага
                        a_enem.setVolume();
                    }else {
                        System.out.println(" ");
                    }
                }
                double ax = aim.getX();// получаем коорд элемента
                double ay = aim.getY();
                double aw = aim.getW();
                double ah = aim.getH();
                if ((bx > ax - bw) && (bx < ax + aw) && (by > ay - bh) && (by < ay + ah)&&(Listeners.is_nip))  // условие столкновения
                    bullets.remove(j); // удаляем пулю из списка
            }
        }
        for (int i2 = 0; i2 < enemies2.size(); i2++) {
            shooter.EnemyBossFinal e2 = enemies2.get(i2); // выделяем элемент списка
            double e2x = e2.getX();// получаем коорд элемента
            double e2y = e2.getY();
            double e2w = e2.getW();
            double e2h = e2.getH();
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b = bullets.get(j);// выделяем элемент списка
                double bx = b.getX();// получаем коорд элемента
                double by = b.getY();
                double bw = b.getW();
                double bh = b.getH();
                if ((bx>e2x-bw) &&(bx<e2x+e2w) && (by>e2y-bh) &&(by<e2y+e2h) ) {
                    e2.hit();
                    bullets.remove(j);
                    boolean remove_p = e2.remove_f();
                    if (remove_p) {
                        enemies2.remove(i2);
                        a_enem.sound();
                        a_enem.setVolume();
                    }else {
                        System.out.println(" ");
                    }
                }
                double ax = aim.getX();
                double ay = aim.getY();
                double aw = aim.getW();
                double ah = aim.getH();
                if ((bx > ax - bw) && (bx < ax + aw) && (by > ay - bh) && (by < ay + ah)&&(Listeners.is_nip))
                    bullets.remove(j);
            }
        }
        // Столкновение героя с врагом
        Iterator<shooter.Enemy> i = enemies.iterator();// итератор по списку соперников
            while (i.hasNext()){
                shooter.Enemy e = i.next(); //
                if (player.getRect().intersects(e.getRect())){ // если плеер столкнулся с элементом списка соперников
                    e.hit();
                player.hit();
                //Проверка здоровья врага
                boolean remove_p = e.remove_f(); // пер присваив значение метода пров здоров врага
                if (remove_p) { // если правдиво
                    enemies.remove(e);// удалить элемент из списка врагов
                    a_enem1.sound();//взрыв врага
                    a_enem1.setVolume();
                    break;
                }
            }
        }
        wave.update();
    Iterator<shooter.EnemyBoss> i1 = enemies1.iterator();// итератор по списку соперников
            while (i1.hasNext()){
        shooter.EnemyBoss e1 = i1.next(); //
        if (player.getRect().intersects(e1.getRect())){ // если плеер столкнулся с элементом списка соперников
            e1.hit();
            player.hit();
            //Проверка здоровья врага
            boolean remove_p = e1.remove_f(); // пер присваив значение метода пров здоров врага
            if (remove_p) { // если правдиво
                enemies1.remove(e1);// удалить элемент из списка врагов
                a_enem1.sound();//взрыв врага
                a_enem1.setVolume();
                break;
            }
        }
    }
        Iterator<shooter.EnemyBossFinal> i2 = enemies2.iterator();
        while (i2.hasNext()){
            shooter.EnemyBossFinal e2 = i2.next(); //
            if (player.getRect().intersects(e2.getRect())){
                e2.hit();
                player.hit();
                boolean remove_p = e2.remove_f();
                if (remove_p) {
                    enemies2.remove(e2);
                    a_enem1.sound();
                    a_enem1.setVolume();
                    break;
                }
            }
        }
        Iterator<shooter.BulletBoss> i3 = bullets1.iterator();
        while (i2.hasNext()){
            shooter.EnemyBossFinal e2 = i2.next(); //
            if (player.getRect().intersects(e2.getRect())){
                e2.hit();
                player.hit();
                boolean remove_p = e2.remove_f();
                if (remove_p) {
                    enemies2.remove(e2);
                    a_enem1.sound();
                    a_enem1.setVolume();
                    break;
                }
            }
        }
        wave.update();
}
    //рисуем в виртуальном окне
    public void gameRender() { //перерисовка
        background.draw(g);// вызов метода для заднего плана

        //перерисовка - вызов метода для bullet
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
        //перерисовка - вызов метода для enemies123
        for(int i = 0; i<enemies.size(); i++) {
            enemies.get(i).draw(g);// рисуем текущего врага
        }
        for(int i = 0; i<enemies1.size(); i++) {
            enemies1.get(i).draw(g);// рисуем текущего врага
        }
        for(int i = 0; i<enemies2.size(); i++) {
            enemies2.get(i).draw(g);// рисуем текущего врага
        }
        aim1.draw(g);// рисуем большой прицел
        aim.draw(g); //рисуем центральный маркер
        player.draw(g);
        //перерисовка - вызов метода для волны
        //wave.draw(g);// вызов метода перерисовки для волны
        if (wave.showWave()){
            wave.draw(g);// вызов метода перерисовки для волны
        }
    }
    //перенос изображения на панель
    private void gameDraw() {
        Graphics g2 = this.getGraphics();// переоппред Graphics2d на Graphics
        g2.drawImage(image, 0, 0, null);// рисуем
        g2.dispose();// команда для уборщщика мусора
    }
}