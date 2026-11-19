
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicGameApp implements Runnable {

    public int randDX;
    public int randDY;
    public int randDX2;
    public int randDY2;

    public int randxpos;
    public int randypos;


    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image astroPic;
	public Image galaxypic;

    public Astronaut astro;

    public Astronaut astro2;

    public Astronaut astro3;


    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();
        new Thread(ex).start();
    }

    public BasicGameApp() {

        setUpGraphics();


        astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png");
		galaxypic = Toolkit.getDefaultToolkit().getImage("galaxy.png");

        astro = new Astronaut(10, 100);
        astro2 = new Astronaut(20, 200);
        astro3 = new Astronaut(20, 200);

        randDX = ((int) (Math.random() * 10) + 1);
        randDY = ((int) (Math.random() * 10) + 1);

        randDX2 = ((int) (Math.random() * 10) + 1);
        randDY2 = ((int) (Math.random() * 10) + 1);

        randxpos = ((int) (Math.random() * 50) + 1);
        randypos = ((int) (Math.random() * 10) + 1);

        astro2.dy = randDY;
        astro2.dx = randDX;
        astro3.dy = randDY2;
        astro3.dx = randDX2;
        astro3.xpos = randxpos;
        astro3.ypos = randypos;
    }

    public void run() {

        while (true) {

            moveThings();
            render();
            pause(10);
        }
    }


    public void moveThings() {

        astro.move();
        astro2.move();
        astro3.move();
        crashing();
    }

    public void crashing() {
        if (astro.hitBox.intersects(astro2.hitBox) && astro.iscrashing == false && astro2.isAlive == true) {

		}
            if (astro.hitBox.intersects(astro2.hitBox) && astro.iscrashing == false) {
                System.out.println("crash 1,2");
                astro.height = astro.height + 1;
                astro2.height = astro2.height + 1;
                astro2.dx = -astro2.dx;
                astro.dx = -astro.dx;
                astro2.dy = -astro2.dy;
                astro.dy = -astro.dy;
				astro.iscrashing = true;
				astro2.iscrashing = true;
            }
            if (astro3.hitBox.intersects(astro.hitBox)) {
                System.out.println("crash 1,3");
                astro.height = astro.height + 1;
                astro3.height = astro3.height + 1;
                astro.dx = -astro.dx;
                astro3.dx = -astro3.dx;
                astro.dy = -astro.dy;
                astro3.dy = -astro3.dy;
				astro.iscrashing = true;
				astro3.iscrashing = true;

            }

            if (astro2.hitBox.intersects(astro3.hitBox)) {
                System.out.println("crash 2,3");
                astro3.height = astro3.height + 1;
                astro2.height = astro2.height + 1;
                astro3.dx = -astro3.dx;
                astro2.dx = -astro2.dx;
                astro3.dy = -astro3.dy;
                astro2.dy = -astro2.dy;
				astro3.iscrashing = true;
				astro2.iscrashing = true;
            }

            if (!astro.hitBox.intersects(astro2.hitBox)) {
                astro.iscrashing = false;
            }
            if (!astro.hitBox.intersects(astro3.hitBox)) {
                astro.iscrashing = false;
            }
            if (!astro2.hitBox.intersects(astro3.hitBox)) {
                astro.iscrashing = false;
            }



    }


    public void pause(int time) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }


    private void setUpGraphics() {
        frame = new JFrame("Application Template");

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    private void render() {

        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(galaxypic, 0,0, WIDTH, HEIGHT, null);
        g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);

		if(astro2.isAlive == true){
			g.drawImage(astroPic, astro3.xpos, astro3.ypos, astro3.width, astro3.height, null);
		}
        g.drawImage(astroPic, astro2.xpos, astro2.ypos, astro2.width, astro2.height, null);
        g.drawImage(astroPic, astro3.xpos, astro3.ypos, astro3.width, astro3.height, null);

        g.drawRect(astro.hitBox.x, astro.hitBox.y, astro.hitBox.width, astro.hitBox.height);
        g.drawRect(astro2.hitBox.x, astro2.hitBox.y, astro2.hitBox.width, astro2.hitBox.height);
        g.drawRect(astro3.hitBox.x, astro3.hitBox.y, astro3.hitBox.width, astro3.hitBox.height);


        g.dispose();
        bufferStrategy.show();
    }
}