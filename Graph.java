public class Graph {
  
  private Node root = null;

  public Graph() {
  }

  public Graph(Node root) {
    this.root = root;
  }

  public TrapezoidFace retrieve(Point p) {

    Node nextNode = root;
    while(nextNode.getType() != Node.LEAF) {
      nextNode = nextNode.getNext(p);
    }
    return nextNode.t;
  }


  public void preorderTraverseTree(Node n) {
    String s = "";
    int d = distanceToRoot(n);
    for(int i = 1; i<d; i++) {
      s = s + " ";
    }
    System.out.println(s + n.print());

    if (n.leftNode != null) preorderTraverseTree(n.leftNode);
    if (n.rightNode != null) preorderTraverseTree(n.rightNode);
  }

  public void inorderTraverseTree(Node n) {
	    if (n.leftNode != null) inorderTraverseTree(n.leftNode);
	    String s = "";
	    int d = distanceToRoot(n);
	    for(int i = 1; i<d; i++) {
	      s = s + " ";
	    }
	    System.out.println(s + n.print());
	    if (n.rightNode != null) inorderTraverseTree(n.rightNode);
  }
  
  public void postorderTraverseTree(Node n) {
	    if (n.leftNode != null) postorderTraverseTree(n.leftNode);
	    if (n.rightNode != null) postorderTraverseTree(n.rightNode);
	    String s = "";
	    int d = distanceToRoot(n);
	    for(int i = 1; i<d; i++) {
	      s = s + " ";
	    }
	    System.out.println(s + n.print());
}
  
  public void traverseTree() {
	System.out.println("Preordered Nodes:");
    preorderTraverseTree(root);
    System.out.println("Inordered Nodes:");
    inorderTraverseTree(root);
    System.out.println("Postordered Nodes:");
    postorderTraverseTree(root);
    System.out.println("Search Tree Output Complete");
    
  }

  private int distanceToRoot(Node n) {
    Node step = n;
    int distance = 0;
    do {
      distance++;
      step = step.parent;
    } while(step != null);

    return distance;
  }

  public Node retrieveNode(Point p) {

	System.out.println("Retrieving p=("+p.x+","+p.y+")");
	  
    Node nextNode = root;
    int steps = 0;
    String s = "";
    while(nextNode.getType() != Node.LEAF) {
      System.out.println(s+nextNode.print());
      nextNode = nextNode.getNext(p);
      steps++;
      s=s+" ";
    }
    System.out.println("Steps: "+steps);
    return nextNode;
  }

  public void add(Node n) {
    if(root == null)
      root = n;
  }

}
