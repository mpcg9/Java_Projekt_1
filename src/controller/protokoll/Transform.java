package controller.protokoll;

import java.awt.geom.Point2D;

import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

/**
 * Klasse zur Transformation von 2D-Punkten mit Hilfe der Geotools Pakete
 * @author André Henn
 *
 */
public class Transform {
	/**
	 * 
	 */
	private static MathTransform transform= null;

	/**
	 * Statische Methode zur Transformation eines Punktes <i>p</i>
	 * EPSG-Codes sind auf http://spatialreference.org/ zu finden
	 * @param p Punkt
	 * @param fromEPSG EPSG-Code des Referenzsystems des Punktes p (bspw. "EPSG:25832" für UTM)
	 * @param toEPSG EPSG-Code des Zielreferenzsystemes des Neupunktes n (bspw. "EPSG:4326" für WGS86)
	 * @return Neupunkt n
	 */
	public static Point2D.Double transformPoint(Point2D.Double p, String fromEPSG, String toEPSG){
		Point2D.Double transf = null;
		try {

			if(transform ==null){
				transform = CRS.findMathTransform( 
						CRS.decode( fromEPSG),
						CRS.decode( toEPSG )
				);

			}
			double[] transformed = new double[2];
			if (fromEPSG.contains("31466")){
				transform.transform( new double[] {p.getY(),p.getX()}, 0, transformed, 0, 1);
			}else{
				transform.transform( new double[] {p.getX(),p.getY()}, 0, transformed, 0, 1);
			}
			
			transf = new Point2D.Double(transformed[1], transformed[0]);
		} catch (NoSuchAuthorityCodeException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		} catch (TransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transf;
	}
}