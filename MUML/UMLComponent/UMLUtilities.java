package UMLComponent;

import java.awt.*;
import java.util.*;

public class UMLUtilities {
	// Return the button after changing the zoom
	public static Point convertPoint(UMLShape from, Point p, UMLShape to) {
		ArrayList<UMLShape> pass = new ArrayList<UMLShape>();
		ArrayList<Point> passPt = new ArrayList<Point>();

		do {
			pass.add(from);
			passPt.add(p);
			p = new Point(p.x + from.getX(), p.y + from.getY());
			from = from.getContainerParent(); // Return the parent container of the object
		} while (from != null); // Find in order the parent container of form object
		
		return p;	
	}

	// find the peak parent container of form object
	public static UMLShapeContainer getConainterAncestor(UMLShape e) {
		UMLShapeContainer p = e.getContainerParent(), q = null;

		while (p != null) {
			q = p;
			p = p.getContainerParent();
		}
		return q;
	}
}
