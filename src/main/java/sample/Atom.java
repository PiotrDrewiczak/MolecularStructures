package sample;

import javafx.geometry.Point3D;
import javafx.scene.shape.Sphere;

public abstract class Atom {

    protected double x, y, z;
    protected static final double radius=0.2;

    public Atom(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z; }

    abstract Sphere createAtom();
    abstract Point3D getPoint3D();
}

