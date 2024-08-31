package com.shah.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView SolutionTxt, ResultTxt;
    MaterialButton btnOpenBracket, btnCloseBracket;
    MaterialButton btnAC, btnDot, btnAdd, btnSubtract, btnMultiple, btnDivide, btnEquals, btnC;
    MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SolutionTxt = findViewById(R.id.solutiontxt);
        ResultTxt = findViewById(R.id.result);

        btnId(btnCloseBracket, R.id._button_closebracket);
        btnId(btnOpenBracket, R.id._button_openbracket);
        btnId(btn0, R.id._button_0);
        btnId(btn1, R.id._button_1);
        btnId(btn2, R.id._button_2);
        btnId(btn3, R.id._button_3);
        btnId(btn4, R.id._button_4);
        btnId(btn5, R.id._button_5);
        btnId(btn6, R.id._button_6);
        btnId(btn7, R.id._button_7);
        btnId(btn8, R.id._button_8);
        btnId(btn9, R.id._button_9);
        btnId(btnAC, R.id._button_clear);
        btnId(btnDot, R.id._button_dot);
        btnId(btnAdd, R.id._button_plus);
        btnId(btnSubtract, R.id._button_minus);
        btnId(btnMultiple, R.id._button_multiply);
        btnId(btnDivide, R.id._button_divide);
        btnId(btnEquals, R.id._button_equal);
        btnId(btnC, R.id._button_c);
    }

    void btnId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonTxt = button.getText().toString();
        String dataConcat = SolutionTxt.getText().toString();

        if (buttonTxt.equals("AC")) {
            SolutionTxt.setText("");
            ResultTxt.setText("0");
            return;
        }

        if (buttonTxt.equals("C")) {
            if (!dataConcat.isEmpty()) {
                dataConcat = dataConcat.substring(0, dataConcat.length() - 1);
            }
        } else if (buttonTxt.equals("=")) {
            String finalResult = getResult(dataConcat);
            if (!finalResult.equals("err")) {
                ResultTxt.setText(finalResult);
            }
            SolutionTxt.setText(ResultTxt.getText());
            return;
        } else {
            dataConcat = dataConcat + buttonTxt;
        }

        String finalResult = getResult(dataConcat);
        if (!finalResult.equals("err")) {
            ResultTxt.setText(finalResult);
        } else {
            ResultTxt.setText("0");
        }

        SolutionTxt.setText(dataConcat);
    }

    String getResult(String data) {
        if (data.isEmpty()) {
            return "0";
        }
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "err";
        } finally {
            Context.exit();
        }
    }
}
