import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;
public class SnakeGame extends JPanel implements ActionListener , KeyListener{
    private class Tile {
        int x;
        int y;
        Tile(int x , int y){
            this.x=x;
            this.y=y;
        }

    
        
    }

    int boradWidht;
    int boradHeight;
    int tileSize = 25;
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    //food
    Tile food;
    Random random;

    // game logic 
    Timer gameloop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;


    SnakeGame(int boradWidht, int boradHeight){
        this.boradWidht=boradWidht;
        this.boradHeight=boradHeight;
        setPreferredSize(new Dimension(this.boradWidht, this.boradHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        snakeHead = new Tile(5,5 );
        snakeBody = new ArrayList<Tile>();
        food = new Tile(10, 10);
        random = new Random();
        palceFood();
        velocityX= 0;
        velocityY= 0;
        gameloop = new Timer(100, this);
        gameloop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        //grid
        for(int i=0; i< boradWidht/tileSize ;i++){
            //(x1,y1,x2,y2)
            g.drawLine(i*tileSize, 0, i*tileSize, boradHeight);
            g.drawLine(0, i*tileSize, boradWidht, i*tileSize);


        }
        //food 
        g.setColor(Color.RED);
        g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);

        //snake head
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        // snake body 
        for(int i = 0 ; i< snakeBody.size();i++){
            Tile snakepart = snakeBody.get(i);
            g.fillRect(snakepart.x*tileSize, snakepart.y*tileSize, tileSize, tileSize);
        }
        //Score 
        g.setFont(new Font("Arial",Font.PLAIN, 16));
        if ( gameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);

        }else {
            g.drawString("score :"+ String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        }
    }
    public void palceFood(){
        food.x=random.nextInt(boradWidht/tileSize);
        food.y=random.nextInt(boradHeight/tileSize);
    }
    public boolean collision (Tile tile1, Tile tile2){
        return tile1.x==tile2.x && tile1.y == tile2.y;
    }
    public void move(){
        // eat food 
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            palceFood();
        }
        // snake body 
        for (int i = snakeBody.size() - 1; i >=0 ; i--){
            Tile snakepart = snakeBody.get(i);
            if(i == 0){
                snakepart.x=snakeHead.x;
                snakepart.y= snakeHead.y;
            }else{
                Tile snakePrevPart = snakeBody.get(i-1);
                snakepart.x=snakePrevPart.x;
                snakepart.y= snakePrevPart.y;
            }
        }
        // snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
        // game over condition 
        for (int i = 0 ; i< snakeBody.size();i ++){
            Tile snakepart = snakeBody.get(i);
            if( collision(snakepart, snakeHead)){
                gameOver= true;
            }
        }
        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boradWidht
        || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boradHeight){
            gameOver=true;
        }
        
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameloop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX=0;
            velocityY=-1;
        }else if (e.getKeyCode()== KeyEvent.VK_DOWN && velocityY != -1){
            velocityX=0;
            velocityY=1;
        }else if (e.getKeyCode()==KeyEvent.VK_LEFT && velocityX != 1){
            velocityX=-1;
            velocityY=0;

        }else if (e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX=1;
            velocityY=0;
        }

        }
    

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
