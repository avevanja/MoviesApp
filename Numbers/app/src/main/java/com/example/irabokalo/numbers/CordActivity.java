package com.example.irabokalo.numbers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by irabokalo on 06.09.2016.
 */
public class CordActivity extends AppCompatActivity{
    Button iterButton, newtonButton;
        EditText editOne;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cord);

        iterButton = (Button) findViewById(R.id.iter_btn);
        newtonButton = (Button) findViewById(R.id.nuton_btn);

        editOne = (EditText) findViewById(R.id.res2);
        iterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iter_method();
            }
        });
        newtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newtonMethod();
            }
        });
    }
    private double fi(double x)
    {
        return 3+ Math.cos(x);
    }
    private double ksi(double x)
    {
        return 0.5-Math.cos(x-1);
    }
    private double derFiX()
    {
        return 0;
    }
    private double derFiY(double y)
    {
       return -(Math.sin(y));
    }
    private double derKsiX(double x)
    {
        return Math.sin(x-1);
    }
    private double derKsiY()
    {
        return 0;
    }
    private void iter_method() {
        int counter = 1;
        double x0=3;
        double xn2=0;
        double yn2=0;
        double y0=1;
        double eps = 0.001;
        if((((Math.abs(derFiX()))+Math.abs(derKsiX(x0)))<1)&&(((Math.abs(derFiY(y0)))+Math.abs(derKsiY()))<1))
        {
            xn2 =fi(y0);
            yn2 = ksi(x0);
            while((Math.abs(xn2-x0)>eps)||(Math.abs(yn2-y0)>eps))
            {
            x0 = xn2;
                y0 = yn2;
                xn2 =fi(y0);
                yn2 = ksi(x0);
               counter++;
            }
        }
        DecimalFormat numberFormat = new DecimalFormat("#.00");

editOne.setText("x=" + " " +String.valueOf(numberFormat.format(xn2))+" "+"\n" +"y=" + String.valueOf(numberFormat.format(yn2)));
        Toast.makeText(getApplicationContext(),String.valueOf(counter),Toast.LENGTH_LONG).show();
    }
    private double F(double x, double y)
    {
     return Math.cos(x-1) + y - 0.5;
    }
    private double G (double x, double y)
    {
        return x- Math.cos(y) -3;
    }
    private double dFx(double x)
    {
         return -(Math.sin(x-1));
    }
    private double dFy()
    {
        return 1;
    }
    private double dGx()
    {
        return 1;
    }
    private double dGy(double y)
    {
        return Math.sin(y);
    }
    private double determine(double r1,double r2,double r3,double r4)
    {
        return ((r1*r4) - (r2*r3));
    }
    private void newtonMethod()
    {   int counter = 1;
        double x0 = 3;
        double y0 = 1;
        double eps = 0.001;
        double xn2 , yn2;
        xn2 = x0 + ((-(determine(F(x0,y0),dFy(),G(x0,y0),dGy(y0))))/(determine(dFx(x0),dFy(),dGx(),dGy(y0))));
        yn2 = y0 + ((determine(F(x0,y0),dFx(x0),G(x0,y0),dGx()))/(determine(dFx(x0),dFy(),dGx(),dGy(y0))));
        while((Math.abs(xn2-x0)>eps)&& (Math.abs(yn2-y0)>eps))
        {
            x0 = xn2;
            y0= yn2;
            xn2 = x0 + ((-(determine(F(x0,y0),dFy(),G(x0,y0),dGy(y0))))/(determine(dFx(x0),dFy(),dGx(),dGy(y0))));
            yn2 = y0 + (((determine(F(x0,y0),dFx(x0),G(x0,y0),dGx())))/(determine(dFx(x0),dFy(),dGx(),dGy(y0))));
            counter++;
        }
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        Toast.makeText(getApplicationContext(),String.valueOf(counter),Toast.LENGTH_LONG).show();
        editOne.setText("x=" + " " +String.valueOf(numberFormat.format(xn2))+" "+"\n" +"y=" + String.valueOf(numberFormat.format(yn2)));
    }
}
