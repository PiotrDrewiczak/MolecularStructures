package sample;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;

public class C extends Atom{
    public C(String name,double x,double y,double z){
        super(name,x,y,z);
    }

    @Override
    Sphere createMolecule() {
        final PhongMaterial khakiMaterial = new PhongMaterial();
        khakiMaterial.setDiffuseColor(Color.DARKKHAKI);
        khakiMaterial.setSpecularColor(Color.KHAKI);
        Sphere oxygenSphere = new Sphere(0.07);
        oxygenSphere.setMaterial(khakiMaterial);
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
