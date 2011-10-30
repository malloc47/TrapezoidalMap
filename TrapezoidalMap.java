//import java.awt.*;
import java.util.*;

public class TrapezoidalMap {

	// private Shape[] shapes;
	private Set<Shape> shapes = new HashSet<Shape>();

	private Graph Dgraph;

	public TrapezoidalMap() {
		super();
		this.shapes = new HashSet<Shape>();
	}

	public TrapezoidalMap(Set<Shape> shapes) {
		super();
		this.shapes = shapes;
	}

	public void setShape(Set<Shape> shapes) {
		this.shapes = shapes;
	}

	@SuppressWarnings("unchecked")
	public Set<TrapezoidFace> incrementalMap(int height, int width) {
		// Set<TrapezoidLine> trapezoidMap = new HashSet<TrapezoidLine>();
		Set<TrapezoidFace> trapezoidFaces = new HashSet<TrapezoidFace>();

		Set<Shape> segments = ((Set<Shape>) ((HashSet<Shape>) shapes).clone());

/*		int index = 0;
		for (Shape s : segments) {
			s.index = index;
			index++;
		}
*/
		// T(nil)
		TrapezoidFace nilFace = new TrapezoidFace();
		trapezoidFaces.add(nilFace);

		// Add T(nil) to D
		// Graph Dgraph = new Graph();
		Dgraph = new Graph();
		Dgraph.add(new Node(nilFace));

		// Debug
		// Dgraph.traverseTree();

		while (segments.size() > 0) {
			Shape seg = randomShape(segments);
			segments.remove(seg);
//			System.out.println("Segment: "+seg.index);

			List<TrapezoidFace> intersectedFaces = FollowSegment(seg);

//			System.out.println("Intersected: " + intersectedFaces.size());

			Point p = seg.getFirst();
			Point q = seg.getLast();

			if (intersectedFaces.size() == 1) {
				TrapezoidFace d = intersectedFaces.get(0);
				trapezoidFaces.remove(d);
				TrapezoidFace A = new TrapezoidFace(d.top, d.bottom, d.leftp, p);
				TrapezoidFace C = new TrapezoidFace(d.top, seg, p, q);
				TrapezoidFace D = new TrapezoidFace(seg, d.bottom, p, q);
				TrapezoidFace B = new TrapezoidFace(d.top, d.bottom, q,d.rightp);

				A.setNeighbors(d.upperLeft, d.lowerLeft, C, D);
				C.setNeighbors(A, A, B, B);
				D.setNeighbors(A, A, B, B);
				B.setNeighbors(C, D, d.upperRight, d.lowerRight);
				
				if(d.upperLeft != null) {
					d.upperLeft.upperRight = A;
					d.upperLeft.lowerRight = A;
				}
				if(d.lowerLeft != null) {
					d.lowerLeft.upperRight = A;
					d.lowerLeft.lowerRight = A;
				}
				
				if(d.upperRight != null) {
					d.upperRight.upperLeft = B;
					d.upperRight.lowerLeft = B;
				}
				
				if(d.lowerRight != null) {
					d.lowerRight.upperLeft = B;
					d.lowerRight.lowerLeft = B;
				}

				trapezoidFaces.add(A);
				trapezoidFaces.add(B);
				trapezoidFaces.add(C);
				trapezoidFaces.add(D);

				Node subRoot = d.n;// Dgraph.retrieveNode(p);

				// Switch node over
				subRoot.t = null;
				subRoot.setType(Node.POINT);
				subRoot.p = p;

				// Set left node
				subRoot.leftNode = new Node(A);
				subRoot.leftNode.parent = subRoot;

				// Set right tree
				subRoot.rightNode = new Node(q);
				subRoot.rightNode.parent = subRoot;

				subRoot.rightNode.rightNode = new Node(B);
				subRoot.rightNode.rightNode.parent = subRoot.rightNode;

				subRoot.rightNode.leftNode = new Node(seg);
				subRoot.rightNode.leftNode.parent = subRoot.rightNode;

				subRoot.rightNode.leftNode.leftNode = new Node(C);
				subRoot.rightNode.leftNode.leftNode.parent = subRoot.rightNode.leftNode;
				subRoot.rightNode.leftNode.rightNode = new Node(D);

				subRoot.rightNode.leftNode.rightNode.parent = subRoot.rightNode.leftNode;

			} else {
				int i = 0;

				Set<TrapezoidFace> newFaces = new HashSet<TrapezoidFace>();

				TrapezoidFace prevUpper = null;
				TrapezoidFace prevLower = null;

				
				for (TrapezoidFace d : intersectedFaces) {
//					if(!trapezoidFaces.contains(d)) System.out.println("AAAAAHHHHHHHHH!!!!");
					// First
					if (i == 0) {
						trapezoidFaces.remove(d);

						TrapezoidFace A = new TrapezoidFace(d.top, d.bottom,d.leftp, p);
						TrapezoidFace B = new TrapezoidFace(d.top, seg, p,d.rightp);
						TrapezoidFace C = new TrapezoidFace(seg, d.bottom, p,d.rightp);

						A.setNeighbors(d.upperLeft, d.lowerLeft, B, C);
						B.setNeighbors(A, A, null, null);
						C.setNeighbors(A, A, null, null);

						if(d.upperLeft!=null) {
							//if(d.upperLeft.lowerRight==d.upperLeft.upperRight)
								d.upperLeft.upperRight = A;
							d.upperLeft.lowerRight = A;
						}
						if(d.lowerLeft!=null) {
							//if(d.lowerLeft.upperRight==d.upperLeft.upperRight)
								d.lowerLeft.lowerRight = A;
							d.lowerLeft.upperRight = A;
							
						}
												
						trapezoidFaces.add(A);
						newFaces.add(B);
						newFaces.add(C);

						prevUpper = B;
						prevLower = C;

						Node subRoot = d.n; // Dgraph.retrieveNode(p);

						subRoot.t = null;
						subRoot.setType(Node.POINT);
						subRoot.p = p;

						// Set left node
						subRoot.leftNode = new Node(A);
						subRoot.leftNode.parent = subRoot;

						// Set right tree
						subRoot.rightNode = new Node(seg);
						subRoot.rightNode.parent = subRoot;

						subRoot.rightNode.leftNode = new Node(B);
						subRoot.rightNode.leftNode.parent = subRoot.rightNode;

						subRoot.rightNode.rightNode = new Node(C);
						subRoot.rightNode.rightNode.parent = subRoot.rightNode;
						
						for(TrapezoidFace e : trapezoidFaces) {
							if(!trapezoidFaces.contains(e.upperLeft)) e.upperLeft=null;
							if(!trapezoidFaces.contains(e.lowerLeft)) e.lowerLeft=null;
							if(!trapezoidFaces.contains(e.upperRight)) e.upperRight=null;
							if(!trapezoidFaces.contains(e.lowerRight)) e.lowerRight=null;
						}

					}
					// Last
					else if (i == intersectedFaces.size() - 1) {
						trapezoidFaces.remove(d);

						TrapezoidFace B = new TrapezoidFace(d.top, seg,d.leftp, q);
						TrapezoidFace C = new TrapezoidFace(seg, d.bottom,d.leftp, q);
						TrapezoidFace A = new TrapezoidFace(d.top, d.bottom, q,d.rightp);

						B.setNeighbors(prevUpper, prevUpper, A, A);
						C.setNeighbors(prevLower, prevLower, A, A);
						A.setNeighbors(B, C, d.upperRight, d.lowerRight);

						prevUpper.upperRight = B;
						prevUpper.lowerRight = B;
						prevLower.upperRight = C;
						prevLower.lowerRight = C;
						
						if(d.upperRight!=null) {
							//if(d.upperRight.upperLeft==d.upperRight.lowerLeft)
								d.upperRight.upperLeft = A;
							d.upperRight.lowerLeft = A;
						}
						if(d.lowerRight!=null) {

							//if(d.lowerRight.upperLeft==d.lowerRight.lowerLeft)
								d.lowerRight.lowerLeft = A;
							d.lowerRight.upperLeft = A;
						}

						
					
						trapezoidFaces.add(A);
						newFaces.add(B);
						newFaces.add(C);

						Node subRoot = d.n; // Dgraph.retrieveNode(q);

						subRoot.t = null;
						subRoot.setType(Node.POINT);
						subRoot.p = q;

						// Set right node
						subRoot.rightNode = new Node(A);
						subRoot.rightNode.parent = subRoot;

						// Set left node
						subRoot.leftNode = new Node(seg);
						subRoot.leftNode.parent = subRoot;

						subRoot.leftNode.leftNode = new Node(B);
						subRoot.leftNode.leftNode.parent = subRoot.leftNode;

						subRoot.leftNode.rightNode = new Node(C);
						subRoot.leftNode.rightNode.parent = subRoot.leftNode;
						
						for(TrapezoidFace e : trapezoidFaces) {
							if(!trapezoidFaces.contains(e.upperLeft)) e.upperLeft=null;
							if(!trapezoidFaces.contains(e.lowerLeft)) e.lowerLeft=null;
							if(!trapezoidFaces.contains(e.upperRight)) e.upperRight=null;
							if(!trapezoidFaces.contains(e.lowerRight)) e.lowerRight=null;
						}
					}
					// Middle
					else {
						trapezoidFaces.remove(d);

						TrapezoidFace A = new TrapezoidFace(d.top, seg,
								d.leftp, d.rightp);
						TrapezoidFace B = new TrapezoidFace(seg, d.bottom,
								d.leftp, d.rightp);

						A.setNeighbors(prevUpper, prevUpper, null, null);
						B.setNeighbors(prevLower, prevLower, null, null);

						prevUpper.upperRight = A;
						prevUpper.lowerRight = A;
						prevLower.upperRight = B;
						prevLower.lowerRight = B;
						
						prevUpper = A;
						prevLower = B;

						newFaces.add(A);
						newFaces.add(B);

						Node subRoot = d.n;

						subRoot.t = null;
						subRoot.setType(Node.SEG);
						subRoot.s = seg;

						// Set left node
						subRoot.leftNode = new Node(A);
						subRoot.leftNode.parent = subRoot;

						subRoot.rightNode = new Node(B);
						subRoot.rightNode.parent = subRoot;

						for(TrapezoidFace e : trapezoidFaces) {
							if(!trapezoidFaces.contains(e.upperLeft)) e.upperLeft=null;
							if(!trapezoidFaces.contains(e.lowerLeft)) e.lowerLeft=null;
							if(!trapezoidFaces.contains(e.upperRight)) e.upperRight=null;
							if(!trapezoidFaces.contains(e.lowerRight)) e.lowerRight=null;
						}
						
					}

					i++;
				}

				boolean allMerged = false;

//				System.out.println("New before merge: " + newFaces.size());

				// Merge trapezoids
				while (!allMerged) {
					for (TrapezoidFace d : newFaces) {
						if (d.rightp != null
								&& !d.rightp.equals(p)
								&& !d.rightp.equals(q)
								&& ((d.top != null && (d.top.above(d.rightp))) || (d.bottom != null && (d.bottom.below(d.rightp))))) {
							TrapezoidFace next = d.upperRight; // Either should work
							d.upperRight = next.upperRight;
							d.lowerRight = next.lowerRight;

							if ((d.top != null && (d.top.above(d.rightp)))) {
								d.upperRight.lowerLeft = d;
								
							}
							else {
								d.upperRight.upperLeft = d;
							}

							d.rightp = next.rightp;

							// Update the node tree as well
							if (next.n.parent.isLeftNode(next.n))
								next.n.parent.leftNode = d.n;
							else
								next.n.parent.rightNode = d.n;

							newFaces.remove(next);
							
							//System.out.println("Removed?: "+ newFaces.remove(next));

							break;
						} else {
							d.merged = true;
						}
					}
					allMerged = true;
					for (TrapezoidFace d : newFaces) {
						if (!d.merged)
							allMerged = false;
					}
				}

//				System.out.println("New after merge: " + newFaces.size());

				for (TrapezoidFace d : newFaces) {
					d.merged = false;
					// d.selected=true;
					trapezoidFaces.add(d);
				}
			}
/*			for(TrapezoidFace d : trapezoidFaces) {
				if(!(trapezoidFaces.contains(d.upperLeft) || d.upperLeft==null) || 
						!(trapezoidFaces.contains(d.upperRight) || d.upperRight==null ) ||
						!(trapezoidFaces.contains(d.lowerLeft) || d.lowerLeft==null ) ||
						!(trapezoidFaces.contains(d.lowerRight) || d.lowerRight==null) ) 
					System.out.println("Sanity check failed");
			}
*/
		}

		int i = 0;

		for (TrapezoidFace f : trapezoidFaces) {
			f.setIndex(i);
			i++;
		}

		Dgraph.traverseTree();
//		System.out.println("----");

		return trapezoidFaces;
	}

	public void retrievePoint(Point p) {
		Dgraph.retrieveNode(p).t.selected = true;
//		System.out.println("Point retrieved");
	}

	private List<TrapezoidFace> FollowSegment(Shape seg) {
		List<TrapezoidFace> traversed = new ArrayList<TrapezoidFace>();

		Point p = seg.getFirst();
		Point q = seg.getLast();

/*		if (p.equals(q))
			System.out.println("AAAAAAHHHH!");
*/
		TrapezoidFace start = Dgraph.retrieve(p);

		traversed.add(start);

		TrapezoidFace j = start;

		while (j!=null && (j.rightp != null && q.right(j.rightp))) {
			if (seg.above(j.rightp))
				j = j.lowerRight;
			else
				j = j.upperRight;

			if(j!=null)traversed.add(j);
		}

		return traversed;
	}

	private Shape randomShape(Set<Shape> segments) {
		Random rand = new Random();
		int n = rand.nextInt(segments.size());
		int i = 0;
		for (Shape seg : segments) {
			if (i == n)
				return seg;
			i = i + 1;
		}
		return null;
	}

	/*
	 * Creates trapezoidal maps the "naive" way. More specifically, this
	 * function iterates through each shape, and each point of each shape,
	 * creating a TrapezoidLine for each point with an initially long length.
	 * Then, this length is minimized over all the x intersections for above the
	 * point. This is repeated for all the lines below the point. This is
	 * primarily used for drawing the visual lines on the screen (as any time
	 * savings are already negated when having to draw all objects anyway).
	 */
	public Set<TrapezoidLine> naiveMap(int height, int width) {

		Set<TrapezoidLine> trapezoidMap = new HashSet<TrapezoidLine>();

		// Add the borders of the window as temporary shapes
		Shape border = new Shape();
		Shape border2 = new Shape();

		border.getPoints().add(new Point(0, 0));
		border.getPoints().add(new Point(width, 0));

		border2.getPoints().add(new Point(0, height));
		border2.getPoints().add(new Point(width, height));

		shapes.add(border);
		shapes.add(border2);

		// Step through each shape
		Iterator<Shape> s = shapes.iterator();
		while (s.hasNext()) {
			Shape sh = s.next();
			// Ignore the border shapes so their points do not appear as part of
			// the trapezoidal map
			if (sh.equals(border) || sh.equals(border2))
				continue;
			// Step through the points in the current shape
			Iterator<Point> p = sh.getPoints().iterator();
			while (p.hasNext()) {
				Point pt = p.next();
				// Generate two trapezoidal lines for each point (up and down)
				TrapezoidLine t = new TrapezoidLine(pt, height + 1, true);
				TrapezoidLine t2 = new TrapezoidLine(pt, height + 1, false);

				// Debug
				// System.out.println("Point: ("+pt.x+","+pt.y+")");

				// Iterate over all shapes, intersecting the trapezoidal lines
				// with each of the shapes
				Iterator<Shape> s2 = shapes.iterator();
				while (s2.hasNext()) {
					Shape sh2 = s2.next();

					// if(!sh.equals(sh2)) {
					// If the intersection yields a positive difference that is
					// smaller than the previous length, update t
					if (t.getStart().y - sh2.intersect(t, height + 1) > 0) {
						t.setLength(Math.min((int) (t.getStart().y - sh2
								.intersect(t, height + 1)), t.getLength()));
					}
					// if the intersection yields a negative difference that is
					// absolutely smaller than the previous length, update t2
					else if (t2.getStart().y - sh2.intersect(t2, height + 1) < 0) {
						t2.setLength(Math.min((int) Math.abs(t2.getStart().y
								- sh2.intersect(t2, height + 1)), t2
								.getLength()));
					}
					// }

				}
				// If the lengths have been updated to a reasonable value, add
				// them
				if (t.getLength() < height) {
					trapezoidMap.add(t);
				}
				if (t2.getLength() < height) {
					trapezoidMap.add(t2);
				}
			}
		}

		// Remove the borders so they are not displayed
		shapes.remove(border);
		shapes.remove(border2);

		return trapezoidMap;
	}
}
