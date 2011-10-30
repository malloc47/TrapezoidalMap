import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;

public class TrapezoidPanel extends JPanel implements MouseListener{
    // Stores the final set of shapes
    private Set<Shape> shapes = new HashSet<Shape>();
    // Stores the current shape being formed
    private List<Point> points = new ArrayList<Point>();

    private Set<TrapezoidLine> lines = new HashSet<TrapezoidLine>();

    private Set<TrapezoidFace> faces = new HashSet<TrapezoidFace>();

    // True if shape is being drawn, otherwise a new shape will be instantiated
    private boolean shapeMode = false;

    private int index = 0;
    
    public Point queryPoint = null; 

    // Default constructor
    public TrapezoidPanel() {
      super();
      // Allow mouse events to be intercepted
      this.addMouseListener(this);
    }

    public Set<Shape> getShapes() {
      return shapes; //(Shape[])shapes.toArray(new Shape[shapes.size()]);
    }

    public void setLines(Set<TrapezoidLine> l) {
      this.lines = l;
//      this.repaint();
    }

    public void setFaces(Set<TrapezoidFace> f) {
      this.faces = f;
//      this.lines.clear();
      this.repaint();
    }

  	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

      // Draw the shapes
      Iterator<Shape> s = shapes.iterator();
      while(s.hasNext()) {
        s.next().draw(g);
      }

      g.setColor(Color.red);

      // Draw the incomplete points (if any)
      Iterator<Point> p = points.iterator();
      while(p.hasNext()) {
        p.next().draw(g);
      }

      g.setColor(Color.blue);
      if(queryPoint != null) queryPoint.draw(g);

//      g.drawString("Lines: "+lines.size(),50,50);

      Iterator<TrapezoidLine> l = lines.iterator();
      while(l.hasNext()) {
        l.next().draw(g);
      }

/*      for(TrapezoidFace f : faces) {
        f.draw(g,this.getWidth(),this.getHeight());
      }
*/
    }


    public void clear() {
      points.clear();
      lines.clear();
      faces.clear();
      this.repaint();
    }

    // Clear the control 
    public void clearAll() {
      points.clear();
      shapes.clear();
      lines.clear();
      faces.clear();
      queryPoint = null;
      index=0;
      this.repaint();
    }

    public void mousePressed (MouseEvent e) {
    }
    public void mouseReleased (MouseEvent e) {
    }
    public void mouseEntered (MouseEvent e) {
    }
    public void mouseExited (MouseEvent e) {
    }

    // Handle standard mouse clicks
    public void mouseClicked (MouseEvent e) {
      // If no shape is currently being created, start a new shape
      if(!shapeMode && e.getButton() == MouseEvent.BUTTON1) {
        points.add(new Point(e.getX(),e.getY()));
        shapeMode = true;
      }
      else if(shapeMode && e.getButton() == MouseEvent.BUTTON1) {
      // If the user is finishing a shape (right mouse button), create the shape
//      else if(e.getButton() == MouseEvent.BUTTON3) {
        shapeMode = false;
        points.add(new Point(e.getX(),e.getY()));
        List<Point> listCopy = new ArrayList<Point>(points);
        Shape newShape = new Shape(listCopy);
        newShape.index = index++;
        shapes.add(newShape);
        points.clear();
      }
      else if(e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) {
        queryPoint = new Point(e.getX(),e.getY());
//        shapeMode = false;
//        points.clear();
      }
      // Otherwise, we are working on a shape, so add the current point
      else {
        points.add(new Point(e.getX(),e.getY()));
      }
      this.repaint();
    }

  }
