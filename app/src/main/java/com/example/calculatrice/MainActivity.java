package com.example.calculatrice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView Screen;
    private String input="",Answer;
    private boolean clearResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Screen=findViewById(R.id.screen);
    }
    public void ButtonClick(View view){
        Button button= (Button) view;
        String data=button.getText().toString();

        switch (data) {
            case "⬅":
                if (input.length() > 0) {
                    clearResult = false;
                    input = input.substring(0, input.length() - 1);
                }
                break;
            case "C":
                input = "";
                break;
            case "^":
                clearResult = false;
                Resolve();
                input += "^";
                break;
            case "∗":
                clearResult = false;
                Resolve();
                input += "*";
                break;
            case "%":
                clearResult = false;
                input += "%";
                Resolve();
                break;
            case "⅟":
                clearResult = false;
                Resolve();
                input = "1/" + input;
                break;
            case "√":
                clearResult = false;
                Resolve();
                input = "√" + input;
                break;
            case "±":
                clearResult = false;
                try {
                    //trouver le signe du nombre
                    double sign = Math.signum(Double.parseDouble(input));
                    //inverser le signe ou vider le input si on ne reçoit pas un numero
                    if ( sign == -1.0){
                        input="+"+input;
                    }else if (sign == 1.0) {
                        input = "-"+input;
                    }else{
                        input = "";
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
                break;
            case "=":
                clearResult=true;
                Resolve();
                Answer=input;
                break;

            default:
                if(input==null){
                    input="";
                }
                if(data.equals("+") || data.equals("-") || data.equals("/")){ //
                    clearResult=false;
                    Resolve();
                }
                else if(clearResult){
                    input="";
                    clearResult=false;
                }
                input+=data;
        }
        Screen.setText(input);
    }
    public void Resolve(){
        if(input.split("\\*").length==2){
            String[] numbers =input.split("\\*");
            try{
                double mul=Double.parseDouble(numbers[0])*Double.parseDouble(numbers[1]);
                input=mul+"";
            }
            catch (Exception e){
                Log.e("Error", e.toString());
            }
        }
        else if(input.split("/").length==2){
            String[] numbers =input.split("/");
            try{
                double div=Double.parseDouble(numbers[0])/Double.parseDouble(numbers[1]);
                input=div+"";
            }
            catch (Exception e){
                Log.e("Error", e.toString());
            }
        }
        else if(input.split("\\^").length==2){
            String[] numbers =input.split("\\^");
            try{
                double pow=Math.pow(Double.parseDouble(numbers[0]),Double.parseDouble(numbers[1]));
                input=pow+"";
            }
            catch (Exception e){
                Log.e("Error", e.toString());
            }
        }
        else if(input.split("\\+").length==2){
            String[] numbers =input.split("\\+");
            try{
                double sum=Double.parseDouble(numbers[0])+Double.parseDouble(numbers[1]);
                input=sum+"";
            }
            catch (Exception e){
                Log.e("Error", e.toString());
            }
        }
        else if(input.split("-").length>1){
            String[] numbers =input.split("-");
            if(numbers[0]=="" && numbers.length==2){
                numbers[0]=0+"";
            }
            try{
                double sub=0;
                if(numbers.length==2) {
                    sub = Double.parseDouble(numbers[0]) - Double.parseDouble(numbers[1]);
                }
                else if(numbers.length==3){
                    sub = -Double.parseDouble(numbers[1]) - Double.parseDouble(numbers[2]);
                }
                input=sub+"";
            }
            catch (Exception e){
                //Display error
                Log.e("Error", e.toString());
            }
        }
        else if(input.contains("%")){
            String[] numbers = input.split("%");
            try{
                double percent = Double.parseDouble(numbers[0])/100.0;
                input = percent+"";
            }
            catch (Exception e){
                //Display error
                Log.e("Error", e.toString());
                input = "";
            }
        }
        else if(input.split("⅟").length==2){
            String[] numbers = input.split("⅟");
            try{
                Log.i("recip",numbers[0]);
                double recip = 1/Double.parseDouble(numbers[0]);
                input = recip+"";
            }
            catch (Exception e){
                //Display error
                Log.e("Error", e.toString());
            }
        }
        else if(input.contains("√")){
            String[] numbers = input.split("√");
            try{
                double sqrt = Math.sqrt(Double.parseDouble(numbers[1]));
                input = sqrt+"";
            }
            catch (Exception e){
                Log.e("Error", e.toString());
            }
        }
        String[] n = input.split("\\.");
        if(n.length>1){
            if(n[1].equals("0")){
                input=n[0];
            }
        }
        Screen.setText(input);
    }
}