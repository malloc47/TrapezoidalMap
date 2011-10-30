import java.awt.*;

public class TrapezoidLine {

	private Point start;
  private Point end;
	private int length;
  private boolean up;
	
	public TrapezoidLine() {
		length = 0;
		start = new Point();
    end = new Point();
	}
	public TrapezoidLine(Point start, int length, boolean up) {
		super();
		this.start = start;
		this.length = length;
    this.up = up;
    this.end = new Point(this.start.x,up ? this.start.y - length : this.start.y + length);
	}
  
  public TrapezoidLine(Point start, Point end) {
    this.start = start;
    this.end = end;
    this.length = Math.abs(start.y - end.y);
    this.up = (start.y - end.y > 0);
  }

	public TrapezoidLine(int x1, int y1, int x2, int y2) {
    this.start = new Point(x1,y1);
    this.end = new Point(x2,y2);
    this.length = Math.abs(y1 - y2);
    this.up = (y1 - y2 > 0);
  }

	public TrapezoidLine(int x, int y, int length, boolean up) {
		super();
		this.start = new Point(x,y);
    this.end = new Point(x,up ? y-length : y+length);
		this.length = length;
    this.up = up;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
    this.end.y = up ? this.start.y-length : this.start.y+length;
	}
	public Point getStart() {
		return start;
	}
	public Point getEnd() {
		return end;
	}
	public void setStart(Point start) {
		this.start = start;
	}
	public void setEnd(Point end) {
		this.end = end;
    this.length = Math.abs(start.y - end.y);
    this.up = (start.y - end.y > 0);
	}
  public void setUp(boolean up) {
    this.up = up;
    this.end.y = up ? this.start.y-length : this.start.y+length;
  }
  public boolean getUp() {
    return this.up;
  }
	
	public boolean equals(TrapezoidLine t) {
		return (t.start.equals(this.start) && 
        t.length==this.length && 
        t.getUp() == this.up);
	}

  public void draw(Graphics g) {
    g.setColor(Color.blue);
    if(up) {
      g.drawLine(start.x,start.y,start.x,start.y - length);
    }
    else {
      g.drawLine(start.x,start.y,start.x,start.y + length);
    }
  }


}
