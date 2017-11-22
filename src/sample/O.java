package sample;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;

public class O extends Atom{
    public O(String name,double x,double y,double z){
        super(name,x,y,z);
    }

    @Override
    Sphere createMolecule() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        Sphere oxygenSphere = new Sphere(0.07);
        oxygenSphere.setMaterial(redMaterial);
        oxygenSphere.setTranslateX(this.x);
        oxygenSphere.setTranslateY(this.y);
        oxygenSphere.setTranslateZ(this.z);
        return oxygenSphere;
    }
    @Override
    ArrayList get3DPoint() {
        ArrayList<Double> arrayList = new ArrayList<Double>();
        arrayList.add(this.x);
        arrayList.add(this.y);
        arrayList.add(this.z);
        return arrayList;
    }
    @Override
    Point3D getPoint3D() {
        Point3D point = new Point3D(this.x,this.y,this.z);
        return point;
    }

}
