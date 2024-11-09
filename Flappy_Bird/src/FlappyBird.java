import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;




public class FlappyBird extends JPanel implements ActionListener,KeyListener {
    int boardWidth=360;
    int boardHeight=640;

  //Images
    Image flappybirdbg;
    Image flappybird;
    Image bottompipe;
    Image toppipe;
  //Bird
    int birdx=boardWidth/8;
    int birdy=boardHeight/2;
    int birdWidth=34;
    int birdHeight=24;

    class Bird{
      int x=birdx;
      int y=birdy;
      int width =birdWidth;
      int height = birdHeight;
      Image img;

      Bird(Image img){
        this.img=img;
      }
    }
   //pipes
     int pipex=boardWidth;
     int pipey=0;
     int pipeWidth = 64;
     int pipeHeight = 512;

     class Pipe{
      int x=pipex;
      int y=pipey;
      int width=pipeWidth;
      int height=pipeHeight;
      Image img;
      boolean passed= false;

      Pipe(Image img){
        this.img=img;
      }
     }

   //game logic
    Bird bird;
    int velocityX=-4;
    Timer gameLoop;
    Timer placePipesTimer;
    int velocityY =0;
    int gravity =1;

    ArrayList<Pipe> pipes;
    Random random=new Random();

    boolean gameOver = false;
    double score =0;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
      //setBackground(Color.blue);

      setFocusable(true);
      addKeyListener(this);
      //load images
      flappybirdbg = new ImageIcon(getClass().getResource("./flappybirdbg.jpg")).getImage();
      flappybird = new ImageIcon(getClass().getResource("./flappybird.jpg")).getImage();
      bottompipe = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
      toppipe = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();

      bird = new Bird(flappybird);
      pipes=new ArrayList<Pipe>();
    //gameLoop
      gameLoop =new Timer(1000/60,this);
      gameLoop.start();
    //place pipes timer
      placePipesTimer=new Timer(1500,new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
          placePipes();
        }
      });
      placePipesTimer.start();

          

    }

    public void placePipes(){

      int randomPipeY = (int)(pipey - pipeHeight/4 - Math.random()*(pipeHeight/2));
      int openingSpace = boardHeight/4;
      Pipe topPipe=new Pipe(toppipe);
      topPipe.y=randomPipeY;
      pipes.add(topPipe);
      
      Pipe bottomPipe = new Pipe(bottompipe);
      bottomPipe.y =topPipe.y + pipeHeight + openingSpace;
      pipes.add(bottomPipe);


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //background
        g.drawImage(flappybirdbg,0, 0, boardWidth,boardHeight,null);

        g.drawImage(bird.img, bird.x, bird.y, bird.width , bird.height, null);

        //pipes
        for(int i=0;i<pipes.size();i++){
          Pipe pipe= pipes.get(i);
          g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        //score 
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameOver){
          g.drawString("Game Over:" + String.valueOf((int)score),10,35);

        }
        else 
        {
          g.drawString(String.valueOf((int)score), 10, 35);
        }

    }

    public void move(){
      velocityY+=gravity;
      bird.y+=velocityY;
      bird.y=Math.max(bird.y,0);
      for(int i=0;i<pipes.size();i++){
        Pipe pipe = pipes.get(i);
        pipe.x +=velocityX;

        if(!pipe.passed && bird.x > pipe.x+pipe.width){
          pipe.passed= true;
          score +=0.5;
          
        }

        if(collision(bird, pipe)){
          gameOver =true;
        }
      }
      if(bird.y > boardHeight){
        gameOver = true;
      }
    }

    public boolean collision(Bird a, Pipe b){
      return a.x < b.x +b.width && a.x+ a.width > b.x &&
             a.y < b.y + b.height && a.y + a.height> b.y;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      move();
      repaint();
      if(gameOver==true){
        placePipesTimer.stop();
        gameLoop.stop();
      }
    }
    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==KeyEvent.VK_SPACE){
        velocityY = -9;
        if(gameOver){
          //restart 
          bird.y = birdy;
          velocityY=0;
          pipes.clear();
          score =0;
          gameOver= false;
          gameLoop.start();
          placePipesTimer.start();
        }
      }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    
   
    @Override
    public void keyReleased(KeyEvent e) {}
    
}