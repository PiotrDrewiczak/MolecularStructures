package sample;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class N extends Atom {
    public N(double x,double y,double z){
        super(x,y,z);
    }

    @Override
    Sphere createAtom() {
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
        Sphere sphere = new Sphere(this.radius);
        sphere.setMaterial(blueMaterial);
        sphere.setTranslateX(this.x);
        sphere.setTranslateY(this.y);
        sphere.setTranslateZ(this.z);
        return sphere;
    }
    @Override
    Point3D getPoint3D() {
        Point3D point = new Point3D(this.x,this.y,this.z);
        return point;
    }

}
