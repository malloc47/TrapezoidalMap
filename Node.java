public class Node {

  public static final int POINT = 0;
  public static final int SEG = 1;
  public static final int LEAF = 2;

  private int type;

  public Point p = null;
  public Shape s = null;
  public TrapezoidFace t = null; 

  public Node parent = null;

  public Node leftNode = null;
  public Node rightNode = null;

  public Node() {
  }

  public Node(Point p) {
    this.p = p;
    this.type = POINT;
  }

  public Node(Shape s) {
    this.s = s;
    this.type = SEG;
  }

  public Node(TrapezoidFace t) {
    this.t = t;
    this.type = LEAF;
    t.n = this;
  }

  public Node(int type) {
    this.type = type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getType() {
    return this.type;
  }

  public String print() {
    String output;
    if(type == POINT) {
      output = "P:("+p.x+","+p.y+")";
    }
    else if(type == SEG) {
      output = "S:"+s.index;
    }
    else {
      output = "T:"+t.getIndex();
    } 
    return output;
  }

  public Node getNext(Point p) {
    // x-node
    if(type == POINT)
      return p.right(this.p) ? rightNode : leftNode;
    // y-node
    else if(type == SEG) {
      return s.above(p) ? leftNode : rightNode;
    }
    return null;
  }

  public boolean isLeftNode(Node other) {
    return other==null ? (p==null && s==null && t==null) : (leftNode.equals(other) ? true : false) ;
  }

  public boolean equals(Node other) {

    boolean test = false;

    if(other==null && this.p==null && this.s==null && this.t==null)
      return true;
    else if(other==null)
      return false;
    else{
      if(this.p != null && other.p != null && this.p.equals(other.p)) test = true;
      if(this.s != null && other.s != null && this.s.equals(other.s)) test = true;
      if(this.t != null && other.t != null && this.t.equals(other.t)) test = true;

      if(this.type != other.getType()) test = false;

      return test; 
    }
  }

}
