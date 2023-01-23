package com.dk.test_sport;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    TextView questions;
    String[] questionArray;
    String[] variable1;
    String[] variable2;
    String[] variable3;

    ImageButton btn_next;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    int count = 1;
    int btn_var1 = 0;
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //НАЧАЛО ЛОГИКИ ИГРЫ
        questions = findViewById(R.id.questions);
        questionArray = getResources().getStringArray(R.array.questionArray);
        variable1 = getResources().getStringArray(R.array.variable1);
        variable2 = getResources().getStringArray(R.array.variable2);
        variable3 = getResources().getStringArray(R.array.variable3);
        btn_next = findViewById(R.id.btn_next);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);

        questions.setText(questionArray[0]);
        btn_1.setText(variable1[0]);
        btn_2.setText(variable2[0]);
        btn_3.setText(variable3[0]);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= 7) {
                    questions.setText(questionArray[count]);
                    btn_1.setText(variable1[count]);
                    btn_2.setText(variable2[count]);
                    btn_3.setText(variable3[count]);
                    count ++;
                    btn_1.setVisibility(View.VISIBLE);
                    btn_2.setVisibility(View.VISIBLE);
                    btn_3.setVisibility(View.VISIBLE);
                    k ++;
                }
                else {
                    if (btn_var1 >= 7) {
                        questions.setText(R.string.test_success);
                    }
                    else if (btn_var1 < 7 && btn_var1 > 2){
                        questions.setText(R.string.test_merge);
                    }
                    else if (btn_var1 <= 2){
                        questions.setText(R.string.test_failed);
                    }
                    k = -1;
                    count = 0;
                    btn_var1 = 0;
                    btn_1.setVisibility(View.INVISIBLE);
                    btn_2.setVisibility(View.INVISIBLE);
                    btn_3.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k == 0 || k == 4){
                    btn_var1 += 1;
                }
                btn_2.setVisibility(View.INVISIBLE);
                btn_3.setVisibility(View.INVISIBLE);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k == 3 || k == 5){
                    btn_var1 += 1;
                }
                btn_1.setVisibility(View.INVISIBLE);
                btn_3.setVisibility(View.INVISIBLE);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k == 1 || k == 2 || k == 6 || k == 7){
                    btn_var1 += 1;
                }
                btn_1.setVisibility(View.INVISIBLE);
                btn_2.setVisibility(View.INVISIBLE);
            }
        });
    }
    //КОНЕЦ ЛОГИКИ ИГРЫ

}
