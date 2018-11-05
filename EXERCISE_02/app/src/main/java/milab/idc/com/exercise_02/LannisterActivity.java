package milab.idc.com.exercise_02;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class LannisterActivity extends AppCompatActivity {
    ArrayList<FamilyMember> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lannister);
        members = new ArrayList<>();
        RecyclerView rvMembers = findViewById(R.id.rvMembersLannister);
        members.add(new FamilyMember("Tywin", ContextCompat.getDrawable(this, R.drawable.tywin)));
        members.add(new FamilyMember("Cersei", ContextCompat.getDrawable(this, R.drawable.cersei)));
        members.add(new FamilyMember("Jaimie", ContextCompat.getDrawable(this, R.drawable.jamie)));
        members.add(new FamilyMember("Tyrion", ContextCompat.getDrawable(this, R.drawable.tyrion)));
        members.add(new FamilyMember("Joffrey Baratheon", ContextCompat.getDrawable(this, R.drawable.joffrey)));
        members.add(new FamilyMember("Myrcella Baratheon", ContextCompat.getDrawable(this, R.drawable.myrcella)));
        members.add(new FamilyMember("Tommen Baratheon", ContextCompat.getDrawable(this, R.drawable.tommen)));
        members.add(new FamilyMember("Lancel", ContextCompat.getDrawable(this, R.drawable.lancel)));

        MembersAdapter adapter = new MembersAdapter(members);
        rvMembers.setAdapter(adapter);
        rvMembers.setLayoutManager(new LinearLayoutManager(this));
    }
}
