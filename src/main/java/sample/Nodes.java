package sample;

import javafx.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Nodes {
    private Point3D firstPoint;
    private Point3D secondPoint;


    public Nodes(Point3D a, Point3D b){
            this.firstPoint=a;
            this.secondPoint=b;
    }
    public Cylinder createConnection() {
        final PhongMaterial greyMaterial = new PhongMaterial();
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = this.secondPoint.subtract(this.firstPoint);
        double height = diff.magnitude();

        Point3D mid = this.secondPoint.midpoint(this.firstPoint);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.1, height);
        line.setMaterial(greyMaterial);
        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }
}
