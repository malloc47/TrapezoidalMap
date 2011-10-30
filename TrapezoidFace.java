import java.util.*;
import java.awt.Graphics;
import java.awt.Color;

public class TrapezoidFace {
  public Shape top;
  public Shape bottom;
  public Point leftp;
  public Point rightp;

  public TrapezoidFace upperLeft;
  public TrapezoidFace lowerLeft;
  public TrapezoidFace upperRight;
  public TrapezoidFace lowerRight;

  public Node n = null;

  public boolean merged = false;
  public boolean selected = false;

  private int index = 0;
  
  public TrapezoidFace() {
    this.top = null;
    this.bottom = null;
    this.leftp = null;
    this.rightp = null;

    this.upperLeft = null;
    this.lowerLeft = null;
    this.upperRight = null;
    this.lowerRight = null;
  }

  public TrapezoidFace(Shape top, Shape bottom, Point leftp, Point rightp) {
    this.top = top;
    this.bottom = bottom;
    this.leftp = leftp;
    this.rightp = rightp;

    this.upperLeft = null;
    this.lowerLeft = null;
    this.upperRight = null;
    this.lowerRight = null;
  }

  public TrapezoidFace(Shape top, Shape bottom, Point leftp, Point rightp, TrapezoidFace upperLeft, TrapezoidFace lowerLeft, TrapezoidFace upperRight, TrapezoidFace lowerRight) {
    this.top = top;
    this.bottom = bottom;
    this.leftp = leftp;
    this.rightp = rightp;
    this.upperLeft = upperLeft;
    this.lowerLeft = lowerLeft;
    this.upperRight = upperRight;
    this.lowerRight = lowerRight;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public Shape getTop(){
    return top;
  }
  public Shape getBottom() {
    return bottom;
  }
  public Point getLeft() {
    return leftp;
  }
  public Point getRight() {
    return rightp;
  }
  public void setTop(Shape top) {
    this.top = top;
  }
  public void setBottom(Shape bottom) {
    this.bottom = bottom;
  }
  public void setLeft(Point leftp) {
    this.leftp = leftp;
  }
  public void setRight(Point rightp) {
    this.rightp = rightp;
  }

  public void setNeighbors(TrapezoidFace upperLeft, TrapezoidFace lowerLeft, TrapezoidFace upperRight, TrapezoidFace lowerRight) {
    this.upperLeft = upperLeft;
    this.lowerLeft = lowerLeft;
    this.upperRight = upperRight;
    this.lowerRight = lowerRight;
  }

  public List<TrapezoidFace> getNeighbors() {
    List<TrapezoidFace> neighbors = new ArrayList<TrapezoidFace>();
    if(upperLeft != null) neighbors.add(upperLeft);
    if(lowerLeft != null) neighbors.add(lowerLeft);
    if(upperRight != null) neighbors.add(upperRight);
    if(lowerRight != null) neighbors.add(lowerRight);
    return neighbors;
  } 
  
  public void draw(Graphics g, int width, int height) {

    /* Point classification:
     * 1. Leftp is in top, rightp is in bottom
     * 2. Leftp is in top, rightp is in neither
     * 3. Leftp is in bottom, rightp is in neither
     * 4. Leftp is in bottom, rightp is in top
     * 5. Rightp is in top, leftp is in bottom
     * 6. Rightp is in top, leftp is in neither
     * 7. Rightp is in bottom, leftp is in neither
     * 8. Rightp is in bottom, leftp is in top
     * 9. Leftp is in top, rightp is in top
     * 10.Leftp is in bottom, rightp is in bottom
     */

    Point l = new Point(0,0);
    Point r = new Point(width,height);

    if(leftp!=null) {
      l.x = leftp.x;
      l.y = leftp.y;
    }

    if(rightp!=null) {
      r.x = rightp.x;
      r.y = rightp.y;
    }
    
    if(l.x > r.x) {
    	int tx = l.x;
    	int ty = l.y;
    	l.x = r.x;
    	l.y = r.y;
    	r.x = tx;
    	r.y = ty;    	
    }
    
    
/*    Point c1 = new Point(Math.min(l.x,r.x),Math.max(l.y,r.y));
    Point c2 = new Point(Math.max(l.x,r.x),Math.min(l.y,r.y));

    g.setColor(Color.green);

    if(top==null) {
      c1.y = 0;
      c2.y = 0;
      c1.x = Math.max(l.x,r.x);
      c2.x = Math.min(l.x,r.y);
    
      g.drawLine(l.x,l.y,c2.x,c2.y);
      g.drawLine(c2.x,c2.y,c1.x,c1.y);
      g.drawLine(c1.x,c1.y,r.x,r.y);
      g.drawLine(r.x,r.y,l.x,l.y);
    }
    else if(bottom==null) {
      c1.y = height;
      c2.y = height;
      c1.x = Math.max(l.x,r.x);
      c2.x = Math.min(l.x,r.y);

      g.drawLine(l.x,l.y,c2.x,c2.y);
      g.drawLine(c2.x,c2.y,c1.x,c1.y);
      g.drawLine(c1.x,c1.y,r.x,r.y);
      g.drawLine(r.x,r.y,l.x,l.y);

    }
    else {
      g.drawLine(l.x,l.y,c2.x,c2.y);
      g.drawLine(c2.x,c2.y,r.x,r.y);
      g.drawLine(r.x,r.y,c1.x,c1.y);
      g.drawLine(c1.x,c1.y,l.x,l.y);

    }

    g.setColor(Color.blue);
*/
/*    if(top==null ^ bottom==null){ 
      if(top==null) 
        g.drawString("" + this.index,(r.x+l.x)/2,Math.max(r.y,l.y)-30);
      else 
        g.drawString("" + this.index,(r.x+l.x)/2,Math.min(r.y,l.y)+30); 


//        g.drawString("" + this.index,l.x,l.y);
    }
    else {
        g.drawString("" + this.index,(r.x+l.x)/2,(r.y+l.y)/2);
    }
*/

      if(top==null) r.y = 0;
      if(bottom==null) l.y = height;
    
      if(selected) g.setColor(Color.green);
      g.drawString("" + this.index,(r.x+l.x)/2,(r.y+l.y)/2);
      g.setColor(Color.blue);


      if(top!=null) {
        g.drawLine(l.x,l.y,l.x,top.intersect(l));
        g.drawLine(r.x,r.y,r.x,top.intersect(r));
      }
      else {
        g.drawLine(l.x,l.y,l.x,0);
        g.drawLine(r.x,r.y,r.x,0);
      }
      if(bottom!=null) {
        g.drawLine(l.x,l.y,l.x,bottom.intersect(l));
        g.drawLine(r.x,r.y,r.x,bottom.intersect(r));
      }
      else {
        g.drawLine(l.x,l.y,l.x,height);
        g.drawLine(r.x,r.y,r.x,height);
 
      }


/*    else if(rightp==null && leftp!=null && bottom!=null)
      g.drawString("" + this.index,(width+leftp.x)/2,(height+leftp.y)/2);
    else if(leftp==null && rightp!=null)
      g.drawString("" + this.index,(rightp.x)/2,(rightp.y)/2);
    else if(leftp==null && rightp==null)
      g.drawString("" + this.index,(width)/2,(height)/2);
*/

/*    if(top!=null && leftp!=null && !top.contains(leftp))
      g.drawLine(leftp.x,leftp.y,leftp.x,top.intersect(leftp));
    else if(leftp!=null)
      g.drawLine(leftp.x,leftp.y,leftp.x,height);

    if(bottom!=null && leftp!=null && !bottom.contains(leftp))
      g.drawLine(leftp.x,leftp.y,leftp.x,bottom.intersect(leftp));
    else if(leftp!=null)
      g.drawLine(leftp.x,leftp.y,leftp.x,0);
*/

/*    if(top!=null && rightp!=null && !top.contains(rightp))
      g.drawLine(rightp.x,rightp.y,rightp.x,top.intersect(rightp));
    if(bottom!=null && rightp!=null && !bottom.contains(rightp))
      g.drawLine(rightp.x,rightp.y,rightp.x,bottom.intersect(rightp));
*/


/*    Point left;
    Point right;

    if(leftp==null) left = new Point(0,0);
    else left = leftp;
    
    if(rightp==null) right = new Point(width,height);
    else right = rightp;

    int lb = height;
    int lt = 0;
    int rb = height;
    int rt = 0;

    boolean lint=false;
    boolean linb=false;
    boolean rint=false;
    boolean rinb=false;

    if(bottom!=null) { 
      lb = bottom.intersect(left); 
      System.out.println("Result: "+lb);
      rb = bottom.intersect(right); 
      System.out.println("Result: "+rb);
      linb = bottom.contains(left);
      rinb = bottom.contains(right);
    }
    if(top!=null) { 
      lt = top.intersect(left); 
      System.out.println("Result: "+lt);
      rt = top.intersect(right); 
      System.out.println("Result: "+rt);
      lint = top.contains(left);
      rint = top.contains(right);
    }

    if(linb) g.drawLine(left.x,left.y,left.x,lt);
    else if(lint) g.drawLine(left.x,left.y,left.x,lb);

    if(rinb) g.drawLine(right.x,right.y,right.x,rt);
    else if(rint) g.drawLine(right.x,right.y,right.x,rb); 

    if(leftp!=null) {
      if(top != null && top.contains(leftp)) {
        if(bottom != null)
          g.drawLine(leftp.x,leftp.y,leftp.x,bottom.intersect(leftp));
        else
          g.drawLine(leftp.x,leftp.y,leftp.x,1000);
      }
      else if(bottom != null && bottom.contains(leftp)) {
        if(top != null)
          g.drawLine(leftp.x,leftp.y,leftp.x,top.intersect(leftp));
        else
          g.drawLine(leftp.x,leftp.y,leftp.x,0);
      }
    }

    if(rightp!=null) {
      if(top != null && top.contains(rightp)) {
        if(bottom != null)
          g.drawLine(rightp.x,rightp.y,rightp.x,bottom.intersect(rightp));
        else
          g.drawLine(rightp.x,rightp.y,rightp.x,1000);
      }
      else if(bottom != null && bottom.contains(rightp)) {
        if(top != null)
          g.drawLine(rightp.x,rightp.y,rightp.x,top.intersect(rightp));
        else
          g.drawLine(rightp.x,rightp.y,rightp.x,0);
      }
    }


//    Random rand = new Random();

    g.drawString("" + this.index,(left.x+right.x) / 2,(left.y + right.y) / 2 );
    g.drawString("" + this.index,(right.x+right.x) / 2,(right.y + left.y) / 2);
*/
  }

  private boolean isEmpty() {
    return top==null && bottom==null && leftp==null && rightp==null;
  }

  public boolean equals(TrapezoidFace other) {
    return other==null ? isEmpty() : ( (top==null ? other.top==null : top.equals(other.top)) &&
        (bottom==null ? other.bottom==null : bottom.equals(other.bottom)) &&
        (leftp==null ? other.leftp==null : leftp.equals(other.leftp)) &&
        (rightp==null ? other.rightp==null : rightp.equals(other.rightp)) &&
        upperLeft==other.upperLeft &&
        lowerLeft==other.lowerLeft &&
        upperRight==other.upperRight &&
        lowerRight==other.lowerRight);

  }

}
