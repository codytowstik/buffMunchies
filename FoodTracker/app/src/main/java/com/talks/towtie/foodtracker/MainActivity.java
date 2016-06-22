package com.talks.towtie.foodtracker;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;


public class MainActivity extends AppCompatActivity {

    // URL Address
    String buffmanagerurl = "https://services.jsatech.com/index.php?cid=59";
    ProgressDialog mProgressDialog;
    Boolean showPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        // Locate Buttons in activity_main.xml
        Button loadButton = (Button) findViewById(R.id.loadButton);
        //Button campusButton = (Button) findViewById(R.id.clearButton);



        //Button swipesbutton = (Button) findViewById(R.id.swipesbutton);

        // Capture button click
        loadButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute munch money AsyncTask
                new Munch().execute();
            }
        });

//        campusButton.setOnClickListener(new OnClickListener() {
//            public void onClick(View arg0) {
//                // Execute munch money AsyncTask
//                new Campus().execute();
//            }
//        });


    }
/*
    protected void getMunchies(){
        try{

        }catch(IOException e){
            e.printStackTrace();
        }
    }
*/


    private class Munch extends AsyncTask<Void, Void, Void> {
        String munchMoneyText;
        String campusCashText;
        String mealSwipesText;
        String hms;
        Boolean isEmpty = false;
        Boolean infoCorrect = true;

        EditText identiField = (EditText) findViewById(R.id.identikeyField);
        EditText passField = (EditText) findViewById(R.id.passwordField);


        String identiKey = identiField.getText().toString();
        String userPass = passField.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (identiKey.matches("")||userPass.matches("")) {
                isEmpty = true;
                return;
            }

            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Tummy Status");
            mProgressDialog.setMessage("Rumbling...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            if (isEmpty == true){
                return null;
            }

            try {

                Document skeyParser = Jsoup.connect("https://services.jsatech.com/login.php?cid=59&")
                        .followRedirects(true)
                        .userAgent("Chrome/49.0.2623.110")
                        .get();
                Elements skeyCode = skeyParser.select("table[class=formbody] form[action]");


                String preIteration = skeyCode.attr("action");
                String skeyFinal = preIteration.substring(51);

                //String skeyFinal = "5aead4e7600fc887bb52e29dbcf1d2a8";
                String secureURLLogin = "https://services.jsatech.com/login.php?cid=59&skey=" + skeyFinal;
                String secureURLGoTo1 ="https://services.jsatech.com/login.php?cid=59&skey=" + skeyFinal;
                String secureURLGoTo2 = "https://services.jsatech.com/login.php?skey=" + skeyFinal +"&cid=59&fullscreen=1&wason=";
                String secureURLGoTo3 = "https://services.jsatech.com/index.php?skey=" + skeyFinal + "&cid=59&";

                //System.out.println("Login Form Data: ");
                //System.out.println(skeyCode);
                //System.out.println(skeyFinal);

                String referURL= "Referer:https://services.jsatech.com/login.php?skey=" +skeyFinal + "&cid=59&fullscreen=1&wason=";

                //munchy = secureURL;
                Connection.Response response = Jsoup
                        .connect(secureURLLogin)
                        .followRedirects(true)
                        .userAgent("Chrome/49.0.2623.110")
                        .method(Connection.Method.GET)
                        .execute();

                //System.out.println(response.url().toString());

                Map<String, String> cookies = response.cookies();

                //munchy = skeyFinal + response.url().toString();
                //System.out.println("Step .5");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("Step 1");
                response = Jsoup.connect(secureURLLogin)
                        .followRedirects(true)
                        .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .header("Accept-Encoding","gzip, deflate, sdch")
                        .header("Accept-Language","en-US,en;q=0.8")
                        .header("Cache-Control","max-age=0")
                        .header("Connection", "keep-alive")
                        .header("DNT","1")
                        .header("Host", "services.jsatech.com")
                        .header("Upgrade-Insecure-Requests", "1")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36")
                        .data("skey", skeyFinal)
                        .data("cid", "59")
                        .data("loginphrase", identiKey)
                        .data("save","1")
                        .referrer(referURL)
                        .data("password", userPass)
                        .cookies(cookies)
                        .method(Connection.Method.POST)
                        .execute();

                //munchy = munchy + response.url().toString();
               //System.out.println(response.url().toString());
                //System.out.println("Step 2");
                try {
                    Thread.sleep(5500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("Step 3");
                /*
                Document touch = Jsoup.connect(secureURLGoTo2)
                        .followRedirects(true)
                        .cookies(cookies)
                        .get();
                */

                Connection.Response touch = Jsoup
                        .connect(secureURLGoTo2)
                        .followRedirects(true)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36")
                        .method(Connection.Method.GET)
                        .execute();
                //System.out.println("Step 4");
                try {
                    Thread.sleep(5500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("Step 5");

                Connection.Response touch2 = Jsoup
                        .connect(secureURLGoTo3)
                        .followRedirects(true)
                        .method(Connection.Method.GET)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36")
                        .execute();

                //System.out.println("Step 6");
                //System.out.println(touch.url().toString());
                //System.out.println(touch2.url().toString());

                Document homePage = Jsoup.connect(secureURLGoTo3)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36")
                        .get();


                //System.out.println("Step 7");
                //Document document1 = Jsoup.connect("http://armorgames.com/settings/general");
                //Elements dooby = homePage.getElementsByClass("tabletitlebarnum");
                //Elements dooby = homePage.getElementsByClass("formbody");

                //Elements dooby = homePage.getElementsByClass("tabletitlebarnum");
                //*****

                Elements dooby2 = homePage.getElementsContainingText("Print Quota");
                Elements dooby3 = homePage.getElementsContainingText("Help us to better serve you");
                Elements dooby = homePage.select("td[class=formtitle]");


                //System.out.println(dooby2.text());
                if(dooby2.text() == ""){
                    infoCorrect = false;
                    return null;
                }
                //System.out.println("Step 8");

                String munchPre;
                String campusPre;
                String swipesPre;

                //System.out.println("---Useful Info---");
                //Find Campus Cash
                Elements campusCash = homePage.getElementsContainingOwnText("Current CC");
                //System.out.println(campusCash.text() + "?");
                campusPre = campusCash.text();
                campusCashText = "Campus Cash: " + campusPre.substring(20);

                //Find Munch Money
                Elements munchMoney = homePage.getElementsContainingOwnText("Current MM");
                //System.out.println(munchMoney.text() + "?");
                munchPre = munchMoney.text();
                if(munchPre != "") {
                    munchMoneyText = "Munch Money: " + munchPre.substring(20);
                }else{
                    munchMoneyText = "Munch Money: None";
                }

                //Find Meal Swipes
                Elements mealSwipes = homePage.getElementsContainingOwnText("Current MP");
                //System.out.println(mealSwipes.text() + "?");
                swipesPre = mealSwipes.text();
                //System.out.println(swipesPre.length());
                if(swipesPre.length()==24){
                    mealSwipesText = "Meal Swipes: " + swipesPre.substring(20,21);
                }else if(swipesPre.length()==25){
                    mealSwipesText = "Meal Swipes: " + swipesPre.substring(20,22);
                }
                else{
                    munchMoneyText="None";
                }

                //How long until rollover
                Calendar rightNow = Calendar.getInstance();
                Calendar wednesday = Calendar.getInstance();
                wednesday.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                wednesday.set(Calendar.HOUR_OF_DAY,0);
                wednesday.set(Calendar.MINUTE,0);
                wednesday.set(Calendar.SECOND, 0);
                DateFormat df=new SimpleDateFormat("EEE yyyy/MM/dd HH:mm:ss");
                //System.out.println(df.format(wednesday.getTime()));
                long millis = (wednesday.getTimeInMillis() - rightNow.getTimeInMillis());
                hms = String.format("Time Until Meal Swipe Reset: \n %02d day, %02d hours, %02d minutes", TimeUnit.MILLISECONDS.toDays(millis),
                        TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                //System.out.println(hms);




                //Elements dooby = homePage.getElementsContainingText("Welcome to the Buff OneCard online account management web site!");
                //munchy = homePage.text();

                //System.out.println("Step 9");

//
//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//                System.out.println("Form Data: ");
//                System.out.println(dooby2 + "dooby2");
//                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//                System.out.println(dooby3 + "dooby3");
//                System.out.println("*****************************************************");
//                System.out.println(dooby);
//                System.out.println("########################################################");

                //****
//                System.out.println(secureURLGoTo3 + "url3");
                //munchy = skeyFinal + response.url().getQuery();
                //munchy = dooby.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView campusView = (TextView) findViewById(R.id.campustxt);
            campusView.setText(campusCashText);

            TextView munchView = (TextView) findViewById(R.id.munchtxt);
            munchView.setText(munchMoneyText);

            TextView swipesView = (TextView) findViewById(R.id.swipestxt);
            swipesView.setText(mealSwipesText);

            TextView timeView = (TextView) findViewById(R.id.timeUntilView);
            timeView.setText(hms);

            if(isEmpty == true){
                isEmpty = false;
            }else if(infoCorrect == false){
                infoCorrect = true;
                mProgressDialog.dismiss();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Login Fail")
                        .setMessage("Are you sure your information is correct?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else{
                mProgressDialog.dismiss();

            }


        }
    }

    private class Campus extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txtdesc1 = (TextView) findViewById(R.id.munchtxt);
            txtdesc1.setText("");
            TextView txtdesc2 = (TextView) findViewById(R.id.campustxt);
            txtdesc2.setText("");
            TextView txtdesc3 = (TextView) findViewById(R.id.swipestxt);
            txtdesc3.setText("");
            TextView txtdesc4 = (TextView) findViewById(R.id.timeUntilView);
            txtdesc4.setText("");

        }
    }
}