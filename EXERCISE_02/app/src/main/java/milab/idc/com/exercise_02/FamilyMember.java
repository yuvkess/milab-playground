package milab.idc.com.exercise_02;

import android.graphics.drawable.Drawable;

public class FamilyMember {
    private String name;
    private Drawable img;

    public FamilyMember(String name, Drawable image){
        this.name = name;
        this.img = image;
    }

    public String getName(){
        return this.name;
    }

    public Drawable getImage(){
        return this.img;
    }






}
