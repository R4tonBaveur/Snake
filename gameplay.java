package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class gameplay extends JPanel implements KeyListener,ActionListener{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static int size = 15;
public static int[] newapple() {
	
	int[][]pp= {{}};
	for (int y=0;y<size;y++) {
		for (int x=0;x<size;x++) {
			boolean found=false;
			for (int i=0;i<snakepositions.length;i++) {
				if (x==snakepositions[i][0]&&y==snakepositions[i][1]) {
					found=true;
				}}
			if (!found) {
					int[]np={x,y};
					pp=push(pp,np);
			}

		}
	}
	
	int[]apple= pp[randInt(0,pp.length-1)];
	System.out.println("apple done");
	return apple;
}
public static boolean alive=true;
public static int[][] push(int array[][],int x[]){
	int[][] newarray = new int[array.length+1][2];
	for (int i=0;i<array.length;i++) {
		newarray[i]=array[i];
	}
	newarray[array.length]=x;
	return newarray;
}
public static int[][] shift(int array[][]){
	int newarray[][]=new int[array.length-1][2];
	for (int i=0;i<array.length-1;i++) {
		newarray[i]=array[i+1];
	}
	return newarray;
}
public static int[][]reset(int array[][]){
	int[][] snakepositions= {{0,0},{1,0},{2,0}};
	return snakepositions;
}

public static int[][] snakeupdate(int[][] snakepositions,boolean moveup,boolean moveleft,boolean movedown,boolean moveright){
int[]head= {snakepositions[snakepositions.length-1][0],snakepositions[snakepositions.length-1][1]};
if (apple.length!=0) {
if (head[0]==apple[0]&&head[1]==apple[1]) {
	apple=newapple();
	score=snakepositions.length-2;
} else {
	snakepositions=shift(snakepositions);
}}else {apple=newapple();};
	if(moveup&&!movedown) {
		head[1]=head[1]-1;
	}else if(moveleft&&!moveright) {
		head[0]=head[0]-1;
	}else if(movedown&&!moveup) {
		head[1]=head[1]+1;
	}else if(moveright&&!moveleft) {
		head[0]=head[0]+1;
	}
	if(head[0]<0||head[1]<0||head[0]>size-1||head[1]>size-1) {
		System.out.println("wall colistion");
		alive=false;
	}
	for (int i=0;i<snakepositions.length;i++) {
		if (snakepositions[i][0]==head[0]&&snakepositions[i][1]==head[1]) {
			System.out.println("self colision");
			alive=false;
		}	
	}
snakepositions=push(snakepositions,head);
return snakepositions;
}

public static boolean moveup=false;
public static boolean moveleft=false;
public static boolean movedown=false;
public static boolean moveright=true;
public static int[][] snakepositions= {{0,0},{0,1},{0,2}};



public static int randInt(int min, int max) {
	Random rand=new Random();
	int randomNum = rand.nextInt((max - min) + 1) + min;
    return randomNum;
}
public static int[]apple = newapple();
public static int score;
public static Timer t = new Timer(0, null);
public gameplay() {
	addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);

	t.setDelay(250);
	t.start();
	t.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			snakepositions = snakeupdate(snakepositions,moveup,moveleft,movedown,moveright);
			repaint();
		}
		
	});
};

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
}
@Override
public void keyPressed(KeyEvent e) {
	if(e.getKeyCode()== KeyEvent.VK_RIGHT&&!moveleft) {
		moveup = false;
		moveleft = false;
		moveright = true;
		movedown = false;
	}
	if(e.getKeyCode()== KeyEvent.VK_LEFT&&!moveright) {
		moveup = false;
		moveleft = true;
		moveright = false;
		movedown = false;
	}
	if(e.getKeyCode()== KeyEvent.VK_UP&&!movedown) {
		moveup = true;
		moveleft = false;
		moveright = false;
		movedown = false;
	}
	if(e.getKeyCode()== KeyEvent.VK_DOWN&&!moveup) {
		moveup = false;
		moveleft = false;
		moveright = false;
		movedown = true;
	}
	if(e.getKeyCode()== KeyEvent.VK_SPACE) {
		if(t.isRunning()) {
			t.stop();
		} else {
			t.restart();
		}
		if (!alive) {
			snakepositions=reset(snakepositions);
			t.restart();
			moveright=true;
			moveleft=false;
			moveup=false;
			movedown=false;
			alive=true;
			score=0;
		}
		System.out.println("space pressed");
	}
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
public void paint (Graphics g) {
	Rectangle bound = g.getClipBounds();
	g.setColor(Color.black);
	g.fillRect(bound.x, bound.y, bound.width, bound.height);
	g.setColor(Color.red);
	try {
	g.fillRect(apple[0]*bound.width/size+2, apple[1]*bound.height/size+2, bound.width/size-4, bound.height/size-4);
	}catch(Exception e) {};
	g.setColor(Color.yellow);
	g.fillRect(snakepositions[snakepositions.length-1][0]*bound.width/size+2, snakepositions[snakepositions.length-1][1]*bound.height/size+2, bound.width/size-4, bound.height/size-4);
	g.setColor(Color.green);
	for (int i=0;i<snakepositions.length-1;i++) {
		g.fillRect(snakepositions[i][0]*bound.width/size+2, snakepositions[i][1]*bound.height/size+2, bound.width/size-4, bound.height/size-4);
	}
	if (!alive) {
		t.stop();
		g.setColor(Color.blue);
		g.drawString("You died with a score of "+score, 230, 250);
		g.drawString("Press space to restart", 230, 260);
	}
	repaint();
}
}
