

/*
*
* // move sıcsa da calısıup
*
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Paint {
    public static void main(String[]a) {
        new Window();
    }
}
class Shape{
    public Color shapeColor;
    public int x;
    public int y;
    public int width;
    public int height;
    public Shape(int x, int y, int width, int height, Color shapeColor) {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.shapeColor=shapeColor;
    }
    public Shape() {}
}
class Rectangle extends Shape{
    public Rectangle() {}
    public Rectangle(int dragStartX, int dragStartY, int newX, int newY, Color current) {
        super(dragStartX, dragStartY, newX, newY,current);
    }
    public String toString(){
        return "rectangle..";
    }
}
class Oval extends Shape{
    public Oval(){}
    public Oval(int dragStartX, int dragStartY, int newX, int newY, Color current) {
        super(dragStartX, dragStartY, newX, newY,current);
    }
    public String toString(){
        return "oval..";
    }
}
class Line extends Shape{
    ArrayList<Point>points=new ArrayList<>();
    public String toString(){
        return "line!!!!!..";
    }
}
class Window extends JFrame {
    //rect 1 oval 2 pen 3 move 0
    // black purple orange yellow green red blue 1-7
    int PenButton = 89; // by default it is nothing
    int ColorButton = 1;
    boolean moveButton=false; //for move button pressing
    boolean moveable = false; //for availability of moving chosen obj
    Color currentColor=Color.BLACK;

    class DrawerMenu extends JPanel implements MouseListener, MouseMotionListener{
        public int dragStartX, dragStartY, newX, newY, prevX, prevY, newx, newy;
        public boolean addOval=false, addRect=false, addLine=false;
        public ArrayList<Shape>shapeList = new ArrayList<>();
        public Line tempLine=new Line();
        Rectangle toMoveRect=null;
        Oval toMoveOval=null;
        public DrawerMenu() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Color chosen = currentColor;
            g.setColor(currentColor);
            //dert oldu todo
            for (Shape s : shapeList) {
                boolean worked = false;
                //System.out.println(s.x+" "+toMoveOval.x+" "+s.y==toMoveOval.y
                //        +" "+ s.width+" "+toMoveOval.width +" "+ s.height+" "+toMoveOval.height);
                //System.out.println("***"+s.x+" "+toMoveRect.x+" "+s.y+" "+toMoveRect.y
                //        +" "+ s.width+" "+toMoveRect.width +" "+ s.height+" "+toMoveRect.height);

                if((toMoveRect!=null || toMoveOval!=null) && s instanceof Oval && s.x==toMoveOval.x && s.y==toMoveOval.y
                && s.width==toMoveOval.width && s.height==toMoveOval.height){
                    System.out.println("oval chosen");
                    g.setColor(s.shapeColor);
                    g.fillOval(newX-dragStartX+s.x , newY-dragStartX+s.y,
                            s.width,s.height);
                    g.setColor(currentColor);
                }

                else if((toMoveRect!=null || toMoveOval!=null) && s instanceof Rectangle && s.x==toMoveRect.x && s.y==toMoveRect.y
                        && s.width==toMoveRect.width && s.height==toMoveRect.height){
                    System.out.println("rect chosen");
                    g.setColor(s.shapeColor);
                    g.fillRect(newX-dragStartX+s.x , newY-dragStartY+s.y,
                            s.width,s.height);
                    g.setColor(currentColor);
                }
                else{
                    if(s instanceof Rectangle) {
                        g.setColor(s.shapeColor);
                        g.fillRect(s.x, s.y, s.width, s.height);
                        g.setColor(currentColor);
                    }
                    else if(s instanceof Oval) {
                        g.setColor(s.shapeColor);
                        g.fillOval(s.x, s.y, s.width, s.height);
                        g.setColor(currentColor);
                    }
                    else if(s instanceof Line){
                        g.setColor(s.shapeColor);
                        Line beren = (Line)s;
                        for(int i=0; i<beren.points.size()-1; i++){
                            Point tempPoint1=beren.points.get(i);
                            Point tempPoint2=beren.points.get(i+1);
                            g.drawLine(tempPoint1.x,tempPoint1.y,tempPoint2.x,tempPoint2.y);
                        }
                        g.setColor(currentColor);
                    }
                }
            }
            if(PenButton!=0){
                if(PenButton==1) {
                    g.fillRect(Math.min(dragStartX, newX),
                            Math.min(dragStartY, newY),
                            Math.abs(dragStartX - newX),
                            Math.abs(dragStartY - newY));
                    addRect=true;
                }
                else if(PenButton==2) {
                    g.fillOval(Math.min(dragStartX, newX),
                            Math.min(dragStartY, newY),
                            Math.abs(dragStartX - newX),
                            Math.abs(dragStartY - newY));
                    addOval=true;
                }
                else if(PenButton==3) {
                    for(int i=0; i<tempLine.points.size()-1; i++){
                        Point tempPoint1=tempLine.points.get(i);
                        Point tempPoint2=tempLine.points.get(i+1);
                        g.drawLine(tempPoint1.x,tempPoint1.y,tempPoint2.x,tempPoint2.y);
                    }
                    addLine=true;
                }
            }
        }
        public void mousePressed(MouseEvent e) {
            dragStartX = e.getX(); dragStartY = e.getY();
            if(PenButton==3){
                tempLine.shapeColor=currentColor;
                prevX=e.getX();
                prevY=e.getY();
                tempLine.points.add(new Point(prevX,prevY));
            }
            if(PenButton==0){
                //todo: silinecek mi secilen sekil
                //todo: overlapping
                int countShapes=0;
                for(Shape ww : shapeList){
                    if(ww instanceof Rectangle) {
                        int topLeft_x = ww.x;
                        int topLeft_y = ww.y;
                        int bottomRight_x = ww.x + ww.width - 1;
                        int bottomRight_y = ww.y + ww.height - 1;
                        //System.out.println(e.getX()+" "+e.getY());
                        if (e.getX() >= ww.x && e.getX() <= ww.x + ww.width - 1 && e.getY() >= ww.y && e.getY() <= ww.y + ww.height - 1) {
                            //System.out.println("yes rect");
                            toMoveRect = new Rectangle(ww.x, ww.y, ww.width, ww.height, ww.shapeColor);
                            countShapes++;
                        }
                    }
                    else if(ww instanceof Oval){
                        int centerX = ww.x + ww.width / 2;
                        int centerY = ww.y + ww.height / 2;
                        int radiusX = ww.width / 2;
                        int radiusY = ww.height / 2;
                        if (((e.getX() - centerX) * (e.getX() - centerX)) / (radiusX * radiusX) +
                                ((e.getY() - centerY) * (e.getY() - centerY)) / (radiusY * radiusY) <= 1) {
                            //System.out.println("yes oval");
                            toMoveOval = new Oval(ww.x,ww.y,ww.width,ww.height,ww.shapeColor);
                            countShapes++;
                        }
                        /* TODO:read

						    ((moveCursorX - centerX) * (moveCursorX - centerX)) / (radiusX * radiusX) computes the square of the horizontal distance between the point (moveCursorX, moveCursorY) and the center of the oval, divided by the square of the horizontal radius of the oval. This is equivalent to ((x - h) / a)^2 in the equation of the oval.

							((moveCursorY - centerY) * (moveCursorY - centerY)) / (radiusY * radiusY) computes the square of the vertical distance between the point (moveCursorX, moveCursorY) and the center of the oval, divided by the square of the vertical radius of the oval. This is equivalent to ((y - k) / b)^2 in the equation of the oval.

							The two distances are added together, giving ((x - h) / a)^2 + ((y - k) / b)^2.

							This value is compared to 1, the right-hand side of the equation of the oval. If it is less than or equal to 1, then the point is inside the oval.

							If the point is inside the oval, the code inside the if statement is executed. tempoval = toJudge_oval; assigns the current oval being checked to the tempoval variable, and countShapes++; increments the countShapes variable to keep track of the number of shapes the point is inside.
						*/
                    }
                }
                if(countShapes==1){
                    moveable=true;
                    repaint();
                }
            }
        }
        public void mouseDragged(MouseEvent e) {
            newX=e.getX();
            newY=e.getY();
            //todo: bu ne amk
            if(PenButton==3) {
                newx=e.getX();
                newy=e.getY();
                prevX = newx; // Update prevX with the previous value of newx
                prevY = newy; // Update prevY with the previous value of newy
                tempLine.points.add(new Point(prevX,prevY));
            }
            repaint();
        }
        public void mouseReleased(MouseEvent e) {
            int x = Math.min(dragStartX, newX);
            int y = Math.min(dragStartY, newY);
            int width = Math.abs(dragStartX - newX);
            int height = Math.abs(dragStartY - newY);
//            System.out.println(x+" "+y+" "+width+" "+height);

            if (x != 0 && y != 0 && width != 0 && height != 0) {
                if(addRect) shapeList.add(new Rectangle(x, y, width, height,currentColor));
                else if(addOval) shapeList.add(new Oval(x, y, width, height,currentColor));
                else if(addLine) {
                    tempLine.shapeColor=currentColor;
                    shapeList.add(tempLine);
                    tempLine=new Line();
                }
                addRect=false;
                addOval=false;
                addLine=false;
            }
            if(PenButton == 3) {

            }
            if(PenButton == 0) moveable=false;
        }
        public void mouseClicked(MouseEvent e) {}
        public void mouseMoved(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }
    Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(150,400));
        this.getContentPane().setBackground(Color.BLUE);
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout(0,2));
        this.setVisible(true);
        JPanel ust = new JPanel();
        JPanel ust_ust =  new JPanel();
        JPanel ust_alt = new JPanel();
        DrawerMenu alt = new DrawerMenu();
        ust.setBackground(Color.white);
        ust.setPreferredSize(new Dimension(700,100));
        ust_ust.setBackground(Color.white);
        ust_ust.setPreferredSize(new Dimension(700,50));
        ust_alt.setBackground(Color.white);
        ust_alt.setPreferredSize(new Dimension(700,50));
        alt.setBackground(Color.white);
        alt.setSize(new Dimension(700,500));
        ust.setLayout(new GridLayout(2,1));
        ust.add(ust_ust);
        ust.add(ust_alt);
        this.add(ust,BorderLayout.NORTH);
        this.add(alt,BorderLayout.CENTER);
        ust_ust.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel blue= new JPanel(); blue.setBackground(Color.blue);
        JPanel red= new JPanel(); red.setBackground(Color.red);
        JPanel green= new JPanel(); green.setBackground(Color.green);
        JPanel yellow= new JPanel(); yellow.setBackground(Color.yellow);
        JPanel orange= new JPanel(); orange.setBackground(Color.orange);
        JPanel purple= new JPanel(); purple.setBackground(new Color(123,50,250));
        JPanel black= new JPanel(); black.setBackground(Color.black);
        blue.setPreferredSize(new Dimension(60,75));
        red.setPreferredSize(new Dimension(60,75));
        green.setPreferredSize(new Dimension(60,75));
        yellow.setPreferredSize(new Dimension(60,75));
        orange.setPreferredSize(new Dimension(60,75));
        purple.setPreferredSize(new Dimension(60,75));
        black.setPreferredSize(new Dimension(60,75));
        blue.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                currentColor = Color.BLUE;
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        red.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e)  {
                currentColor = Color.RED;
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        green.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                currentColor = Color.GREEN;
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        yellow.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                currentColor = Color.YELLOW;
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        orange.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                currentColor = Color.ORANGE;
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        purple.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                currentColor = new Color(123,50,250);
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        black.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                currentColor = Color.BLACK;
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        ust_ust.add(blue);
        ust_ust.add(red);
        ust_ust.add(green);
        ust_ust.add(yellow);
        ust_ust.add(orange);
        ust_ust.add(purple);
        ust_ust.add(black);
        JButton rect = new JButton();
        rect.setText("Dikdörtgen Çiz");
        JButton oval = new JButton();
        oval.setText("Oval Çiz");
        JButton pen = new JButton();
        pen.setText("Kalemle Çiz");
        JButton move = new JButton();
        move.setText("Taşı");
        ust_alt.setLayout(new FlowLayout(FlowLayout.CENTER));
        rect.addActionListener(e->{
            PenButton = 1;
            moveButton = false;
        });
        oval.addActionListener(e->{
            PenButton = 2;
            moveButton = false;
        });
        pen.addActionListener(e->{
            PenButton = 3;
            moveButton = false;
        });
        move.addActionListener(e->{
            PenButton = 0;
            moveButton = true;
        });
        ust_alt.add(rect);
        ust_alt.add(oval);
        ust_alt.add(pen);
        ust_alt.add(move);
        pack();
    }
}