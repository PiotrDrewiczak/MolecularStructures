package sample;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;

public class N extends Atom{
    public N(String name,double x,double y,double z){
        super(name,x,y,z);
    }

    @Override
    Sphere createMolecule() {
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
        Sphere oxygenSphere = new Sphere(0.07);
        oxygenSphere.setMaterial(blueMaterial);
        oxygenSphere.setTranslateX(this.x);
        oxygenSphere.setTranslateY(this.y);
        oxygenSphere.setTranslateZ(this.z);
        return oxygenSphere;
    }
    @Override
    Point3D getPoint3D() {
        Point3D point = new Point3D(this.x,this.y,this.z);
        return point;
    }

}
