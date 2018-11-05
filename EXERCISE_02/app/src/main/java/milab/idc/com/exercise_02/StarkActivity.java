package milab.idc.com.exercise_02;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class StarkActivity extends AppCompatActivity {
    ArrayList<FamilyMember> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stark);
        members = new ArrayList<>();
        RecyclerView rvMembers = findViewById(R.id.rvMembersStark);
        members.add(new FamilyMember("Eddard", ContextCompat.getDrawable(this, R.drawable.eddard)));
        members.add(new FamilyMember("Catelyn", ContextCompat.getDrawable(this, R.drawable.catelyn)));
        members.add(new FamilyMember("Robb", ContextCompat.getDrawable(this, R.drawable.rob)));
        members.add(new FamilyMember("Sansa", ContextCompat.getDrawable(this, R.drawable.sansa)));
        members.add(new FamilyMember("Arya", ContextCompat.getDrawable(this, R.drawable.arya)));
        members.add(new FamilyMember("Bran", ContextCompat.getDrawable(this, R.drawable.bran)));
        members.add(new FamilyMember("Rickon", ContextCompat.getDrawable(this, R.drawable.rickon)));
        members.add(new FamilyMember("Jon Snow", ContextCompat.getDrawable(this, R.drawable.jon_snow)));
        members.add(new FamilyMember("Benjen", ContextCompat.getDrawable(this, R.drawable.benjen)));
        members.add(new FamilyMember("Talisa Maegyr", ContextCompat.getDrawable(this, R.drawable.talisa)));


        MembersAdapter adapter = new MembersAdapter(members);
        rvMembers.setAdapter(adapter);
        rvMembers.setLayoutManager(new LinearLayoutManager(this));
    }
}
