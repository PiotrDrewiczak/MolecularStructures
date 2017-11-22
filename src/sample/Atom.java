package sample;

import javafx.geometry.Point3D;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;

public abstract class Atom {
    protected String name;
    protected double x, y, z;

    public Atom(String name, double x, double y, double z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    abstract Sphere createMolecule();

    abstract ArrayList get3DPoint();
    abstract Point3D getPoint3D();
}

