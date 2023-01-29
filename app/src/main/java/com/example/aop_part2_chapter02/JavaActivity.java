package com.example.aop_part2_chapter02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class JavaActivity extends AppCompatActivity {

    private Button clearButton;
    private Button addButton;
    private Button runButton;
    private NumberPicker numberPicker;
    private ArrayList<TextView> numberTextViewList;

    private Boolean didRun = false;
    private HashSet<Integer> pickNumberSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runButton = findViewById(R.id.runButton);
        addButton = findViewById(R.id.addButton);
        clearButton = findViewById(R.id.clearButton);
        numberPicker = findViewById(R.id.numberPicker);

        numberTextViewList = new ArrayList<>();
        numberTextViewList.add(findViewById(R.id.textView1));
        numberTextViewList.add(findViewById(R.id.textView2));
        numberTextViewList.add(findViewById(R.id.textView3));
        numberTextViewList.add(findViewById(R.id.textView4));
        numberTextViewList.add(findViewById(R.id.textView5));
        numberTextViewList.add(findViewById(R.id.textView6));

        pickNumberSet = new HashSet<>();

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(45);

        initRunButton();
        initAddButton();
        initClearButton();
    }

    private void initRunButton() {
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> list = getRandomNumber();
                didRun = true;


                for (int number : list) {
                    TextView textView = numberTextViewList.get(list.indexOf(number));
                    textView.setText(String.valueOf(number));
                    textView.setVisibility(View.VISIBLE);
                    setNumberBackground(number, textView);
                }

                Log.d("JavaActivity", list.toString());
            }
        });
    }

    private List<Integer> getRandomNumber() {
        List<Integer> numberList = new ArrayList<>();
        for (int i = 1; i <= 45; i++) {
            if (pickNumberSet.contains(i)) {
                continue;
            }
            numberList.add(i);
        }
        Collections.shuffle(numberList);
        List<Integer> newList = new ArrayList<>(pickNumberSet);
        newList.addAll(numberList.subList(0, 6 - pickNumberSet.size()));
        Collections.sort(newList);
        return newList;
    }

    private void initAddButton() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didRun) {
                    Toast.makeText(JavaActivity.this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pickNumberSet.size() >= 5) {
                    Toast.makeText(JavaActivity.this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pickNumberSet.contains(numberPicker.getValue())) {
                    Toast.makeText(JavaActivity.this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                TextView textView = numberTextViewList.get(pickNumberSet.size());
                textView.setVisibility(View.VISIBLE);
                Log.d("JavaActivity", String.valueOf(numberPicker.getValue()));
                textView.setText(String.valueOf(numberPicker.getValue()));

                setNumberBackground(numberPicker.getValue(), textView);
                pickNumberSet.add(numberPicker.getValue());
            }
        });
    }

    private void setNumberBackground(int number, TextView textView) {
        if (number >= 1 && number <= 10) {
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_yellow));
        } else if (number >= 11 && number <= 20) {
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_blue));
        } else if (number >= 21 && number <= 30) {
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_red));
        } else if (number >= 31 && number <= 40) {
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_gray));
        } else {
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_green));
        }
    }

    private void initClearButton() {
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickNumberSet.clear();
                for (TextView tv : numberTextViewList) {
                    tv.setVisibility(View.INVISIBLE);
                }
                didRun = false;
            }
        });
    }
}
