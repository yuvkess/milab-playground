package milab.idc.com.exercise_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button starkButton = findViewById(R.id.starkButton);
        Button lannisterButton = findViewById(R.id.lannisterButton);

        starkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StarkActivity.class);
                startActivity(intent);
            }
        });

        lannisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LannisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
