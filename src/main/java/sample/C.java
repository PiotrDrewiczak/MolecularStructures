package sample;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class C extends Atom {
    public C(String name,double x,double y,double z){
        super(name,x,y,z);
    }

    @Override
    Sphere createAtom() {
        final PhongMaterial khakiMaterial = new PhongMaterial();
        khakiMaterial.setDiffuseColor(Color.DARKKHAKI);
        khakiMaterial.setSpecularColor(Color.KHAKI);
        Sphere sphere = new Sphere(0.2);
        sphere.setMaterial(khakiMaterial);
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
