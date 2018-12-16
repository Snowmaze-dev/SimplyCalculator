package com.example.snowmaze.smallapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView input;
    private TextView output;
    MatchParser pm;
    ArrayList<Character> signs = new ArrayList<>();
        public void Calc(String sign) {
        try {
            String inp = input.getText().toString();
            if(sign.equals(")")) {
                int countl = inp.length() - inp.replace(")", "").length();
                int countr = inp.length() - inp.replace("(", "").length();
                if(countl>=countr && countl-countr>=0) {
                return;
            }
            }
            if (inp.length() > 0) {
                char lastsymb = inp.charAt(inp.length()-1);
                if (lastsymb == '.' && !Character.isDigit(sign.charAt(0))) {
                        return;
                }
                if(inp.length()>= 4) {
                        if(inp.substring(inp.length() - 4,inp.length()-1).equals("PI^") && Character.isDigit(lastsymb) && sign.equals("PI")) {
                            sign = "";
                            inp = inp.substring(0,inp.length()-1) + (Character.getNumericValue(lastsymb)+1);
                            input.setText(inp);
                    }
                }
                if(inp.length()>= 3) {
                    if(inp.substring(inp.length() - 3,inp.length()-1).equals("e^") && Character.isDigit(lastsymb) && sign.equals("e")) {
                        sign = "";
                        inp = inp.substring(0,inp.length()-1) + (Character.getNumericValue(lastsymb)+1);
                        input.setText(inp);
                    }
                }
                if(lastsymb == 'I' || lastsymb == 'e') {
                    if(sign.equals(".")) {
                        sign = "*0.";
                    }
                    if(Character.isDigit(sign.charAt(0))) {
                        sign = "*" + sign;
                    }
                    if(sign.equals("PI") || sign.equals("e")) {
                        sign = "^2";
                    }
                }
                if(lastsymb == ')' || Character.isDigit(lastsymb) || lastsymb == 'I' || lastsymb == 'e') {
                    if (!signs.contains(sign) && !sign.equals("")) {
                        if (sign.equals("(")) {
                            sign = "*(";
                        }
                        if (sign.equals("!")) {
                            sign = "*!";
                        }
                        if (sign.equals("SQRT")) {
                            sign = "*sqrt(";
                        }
                        if (sign.equals("PI")) {
                            sign = "*PI";
                        }
                        if (sign.equals("e")) {
                            sign = "*e";
                        }
                    }
                }
                if(lastsymb == ')') {
                    if(Character.isDigit(sign.charAt(0))) {
                        sign = "*" + sign;
                    }
                    if(sign.equals(".")) {
                        sign = "*0.";
                    }
                }
                if(sign.equals("-") && !(lastsymb == '-')) {
                }
                else {
                if (signs.contains(lastsymb) || lastsymb == '(') {
                    if(signs.contains(sign.charAt(0)) || sign.equals(")")) {
                        return;
                    }
                }
                }
            }
                if(sign.equals("SQRT")) {
                    sign = "sqrt(";
                }
                if(sign.equals("(")){
            }
                if(sign.equals("-")){
                }
            if(inp.length() == 0 && signs.contains(sign.charAt(0))) {
                if(!sign.equals("-")) {
                    return;
                }
            }
                input.setText(inp + sign);
                output.setText(String.valueOf(pm.Parse(input.getText().toString())));
        }
        catch(Exception e){
            //Log.e("myActivity", e.toString() + " " + e.getLocalizedMessage());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = new MatchParser();
        pm.setVariable("PI",Math.PI);
        pm.setVariable("e",Math.E);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        signs.add('+');
        signs.add('-');
        signs.add('*');
        signs.add('/');
        signs.add('^');

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("input",input.getText().toString());
        outState.putString("output",output.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        input.setText(savedInstanceState.getString("input"));
        output.setText(savedInstanceState.getString("output"));
    }
    public void onButton(View v) {
        Calc(((Button) v).getText().toString());
    }
    public void clear(View v) {
        String inp = input.getText().toString();
        if(inp.length()-1 < 0) {
            return;
        }
        int flag = 0;
        if(inp.length() > 3) {
        if(inp.substring(inp.length() - 2,inp.length()).equals("t(")) {
                input.setText(inp.substring(0, inp.length() - 5));
                flag = 1;
            }
        }
        if(inp.length() > 1) {
            if(inp.substring(inp.length() - 1,inp.length()).equals("I")) {
                input.setText(inp.substring(0, inp.length() - 2));
                flag = 1;
            }
        }
        if(flag == 0) {
            input.setText(inp.substring(0, inp.length() - 1));
        }
        if(input.getText().toString().equals("")) {
            output.setText("");
        }
        Calc("");
    }
}