import java.awt.Graphics;
import java.awt.Color;
import java.util.*;

public class Shape {
//	private Point[] points;
  private List<Point> points; //= new ArrayList<Point>();

  public int index = 0;

  public Shape() {
    this.points = new ArrayList<Point>();
  }

  public Shape(List<Point> points) {
    this.points = points;
  }

	public int numPoints(){
		return points.size();
	}
	
	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

  public Point getFirst() {
//    return points.get(0).x > points.get(points.size() -1).x ? points.get(0) : points.get(points.size() -1) ;
    return points.get(0);
  }

  public Point getLast() {
//    return points.get(0).x <= points.get(points.size() -1).x ? points.get(0) : points.get(points.size() -1) ;
    return points.get(points.size() -1);

  } 
  
  public boolean above(Point p) {

    if(contains(p)) return false;

    // Assume that p is within the proper x coords of the shape
    int x1 = getFirst().x;
    int x2 = getLast().x;
    int y1 = getFirst().y;
    int y2 = getLast().y;
    return (p.y < lineAprox(x1,y1,x2,y2,p.x));

  }

  public boolean below(Point p) {

    if(contains(p)) return false;

    // Assume that p is within the proper x coords of the shape
    int x1 = getFirst().x;
    int x2 = getLast().x;
    int y1 = getFirst().y;
    int y2 = getLast().y;
    return (p.y > lineAprox(x1,y1,x2,y2,p.x));

  }

  private float lineAprox(int x1, int y1, int x2, int y2, int x){
//    return (float)(((y2-y1)/(x2-x1))*(x - x1)+y1);
    return (float)((float)(((float)(y2-y1)/(x2-x1))*(x-x1))+y1);
  }
	
  private boolean consider(Point a, Point b, Point c) {
    return ((a.x > b.x && a.x < c.x ) || (a.x > c.x && a.x < b.x) /* && (a.y > b.y || a.y > c.y) */ );
  }

  public boolean contains(Point pt) {
    return points.contains(pt);
  }

  public int intersect(Point pt) {
    int x1 = getFirst().x;
    int y1 = getFirst().y;
    int x2 = getLast().x;
    int y2 = getLast().y;
    return Math.round( lineAprox(x1,y1,x2,y2,pt.x) );
  }

  /* Intersect the given trapezoidal line with 
   * all other lines in this shape. */
  public float intersect(TrapezoidLine t,int max) {
    float min = max;
    Iterator<Point> it = points.iterator();
    while(it.hasNext()) {
      Point pt = it.next();
      if(points.indexOf(pt) > 0){
        Point prevpt = points.get(points.indexOf(pt)-1);
        if(consider(t.getStart(),pt,prevpt)) {
          min = Math.min(min,(float)(pt.y - prevpt.y)/(pt.x-prevpt.x)*(t.getStart().x-prevpt.x)+prevpt.y);
        }
      }
    }
    return min;
  }

/*  public boolean intersect(Shape other) {
    boolean val = false;
    Iterator<Point> it = points.iterator();
    while(it.hasNext()) {
      Point pt = it.next();
      if(points.indexOf(pt) > 0){
        Point prevpt = points.get(points.indexOf(pt)-1);
        Iterator<Point> it2 = other.getPoints().iterator();
        while(it2.hasNext()) {
          Point otherpt = it2.next();
          if(other.getPoints().indexOf(otherpt) > 0) {
            otherprevpt = other.getPoints().get(other.getPoints().indexOf(otherpt)-1);
            float otherpty = (float)(pt.y - prevpt.y)/(pt.x-prevpt.x)*(otherpt.x-prevpt.x)+prevpt.y; 
            float otherprevpty = (float)(pt.y - prevpt.y)/(pt.x-prevpt.x)*(otherprevpt.x-prevpt.x)+prevpt.y; 
            if( (consider(pt,otherpt,otherprevpt) || consider(prevpt,otherpt,otherprevpt)) &&
                otherpty <   &&
                (float)(pt.y - prevpt.y)/(pt.x-prevpt.x)*(otherprevpt.x-prevpt.x)+prevpt.y 
                ) {
              val = true;

            }
          } 
        }
      }
    }
    return val;
  }
*/	
	public boolean equals(Shape s) {
		return s==null ? points==null : s.getPoints().equals(points);
	}

  public void draw(Graphics g) {
    for(int i = 0; i<points.size(); i++) {
      g.setColor(Color.red);
      points.get(i).draw(g);
      if(i>0) points.get(i).drawLine(g,points.get(i-1));
    }
  }

}
