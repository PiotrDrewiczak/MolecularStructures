package sample;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class H extends Atom {

    public H(double x,double y,double z){
        super(x,y,z);
    }

    @Override
     Sphere createAtom() {
        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);
        Sphere sphere = new Sphere(this.radius);
        sphere.setMaterial(greyMaterial);
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



