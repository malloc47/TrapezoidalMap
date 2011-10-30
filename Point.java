import java.awt.Graphics;
import java.awt.Color;

public class Point {

	public int x;
	public int y;
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Point p) {
		return (p.x == this.x && p.y == this.y);
	}

  public boolean right(Point p) {
    return (this.x >= p.x);
  }

  public void draw(Graphics g) {
    g.fillOval(x-4,y-4,7,7);
  }

  public String print() {
    return "("+this.x+","+this.y+")";
  }

  public void drawLine(Graphics g, Point other) {
    g.setColor(Color.black);
    g.drawLine(x,y,other.x,other.y);
  }
	
}
