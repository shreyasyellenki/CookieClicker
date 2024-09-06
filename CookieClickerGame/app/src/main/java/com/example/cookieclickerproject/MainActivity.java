package com.example.cookieclickerproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
 ConstraintLayout myLayout;
TextView scoreText;
ImageView imageView;
static int score = 0;
static int homerCounter = 0;
ImageView homerClicker;
TextView homerTextView;
TextView chiefTextView;
ImageView chiefClicker;
Button frenzyButton;
static boolean frenzy = false;
static boolean appear = true;
static boolean appear2 = true;
int count = 0;
int rand = (int)(Math.random()*21+10);
int timeCount = 10;
int random;
ArrayList<Integer> integers = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLayout = findViewById(R.id.myLayout);
        scoreText = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        homerClicker = findViewById(R.id.imageView3);
        homerTextView = findViewById(R.id.homerTextView);
        homerClicker.setImageResource(R.drawable.homer);
        homerClicker.setVisibility(View.INVISIBLE);
        homerTextView.setVisibility(View.INVISIBLE);
        imageView.setImageResource(R.drawable.donut);
        chiefClicker = findViewById(R.id.imageView4);
        chiefTextView = findViewById(R.id.textView3);
        chiefClicker.setImageResource(R.drawable.chief);
        chiefClicker.setVisibility(View.INVISIBLE);
        chiefTextView.setVisibility(View.INVISIBLE);
        frenzyButton = findViewById(R.id.button);
        frenzyButton.setVisibility(View.INVISIBLE);
        for(int i = 10; i <= 30;i++){
            integers.add(i);
        }
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.25f,1.0f,1.25f, Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,.5f);
        scaleAnimation.setDuration(250);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(scaleAnimation);
                TextView plusOne = new TextView(MainActivity.this);
                plusOne.setId(View.generateViewId());
                //plusOne.setTypeface(null,Typeface.BOLD);
                plusOne.setTextSize(17f);
                if(frenzy)
                    plusOne.setText("+2");
                else
                    plusOne.setText("+1");
                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                plusOne.setLayoutParams(lp);
                myLayout.addView(plusOne);
                ConstraintSet cs = new ConstraintSet();
                cs.clone(myLayout);
                cs.connect(plusOne.getId(),ConstraintSet.TOP,view.getId(),ConstraintSet.TOP);
                cs.connect(plusOne.getId(),ConstraintSet.LEFT,view.getId(),ConstraintSet.LEFT);
                cs.connect(plusOne.getId(),ConstraintSet.RIGHT,view.getId(),ConstraintSet.RIGHT);
                cs.connect(plusOne.getId(),ConstraintSet.BOTTOM,view.getId(),ConstraintSet.BOTTOM);
                float rand = (float)(Math.random()*1);
                cs.setHorizontalBias(plusOne.getId(),rand);
                cs.setVerticalBias(plusOne.getId(),rand);
                cs.applyTo(myLayout);
                final TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,plusOne.getX(),Animation.RELATIVE_TO_SELF,plusOne.getX(),Animation.RELATIVE_TO_SELF,plusOne.getY(),Animation.RELATIVE_TO_SELF,-10f);
                translateAnimation.setDuration(2500);
                plusOne.startAnimation(translateAnimation);
               deleteTextView(plusOne);
               if(frenzy)
                add(2);
               else
                   add(1);
                scoreText.setText("Donuts: "  + score);
                Log.d("CCLog","score: " + score);

            }
        });
        frenzyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frenzy = true;

            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                count++;
                if(count > 10 && integers.size() >0){

                    if(rand == integers.get(integers.size()-1))
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                frenzyButton.setVisibility(View.VISIBLE);
                            }
                        });

                    integers.remove(integers.size()-1);
                }
                if(frenzy) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(timeCount >= 10)
                              frenzyButton.setText("0:" + timeCount);
                            else
                                frenzyButton.setText("0:0" + timeCount);

                            frenzyButton.setBackgroundColor(Color.rgb(255,215,0));
                            new Timer().scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    if(timeCount > 0)
                                        frenzyBackground();
                                     random = (int)(Math.random()*200+100);
                                }
                            },random,400);
                        }
                    });
                    timeCount--;
                    if(timeCount == 0) {
                        frenzy = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                frenzyButton.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }
               setText(scoreText,"Donuts: "+score);
                if(score >= 10){
                    Log.d("CCLog","got here");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(appear) {
                                fadeIn(homerClicker);
                                appear = false;
                            }
                            homerClicker.setVisibility(View.VISIBLE);
                            homerTextView.setVisibility(View.VISIBLE);
                        }
                    });

                    Log.d("CCLog","got here4");
                    homerClicker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            add(-10);
                            homerSpawn();
                            appear = false;
                            homerCounter++;
                            new homerThread().start();
                            homerClicker.setVisibility(View.INVISIBLE);
                            homerTextView.setVisibility(View.INVISIBLE);
                        }
                    });

                }
                if(score >= 20){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(appear2) {
                                fadeIn(chiefClicker);
                                appear2 = false;
                            }
                            chiefClicker.setVisibility(View.VISIBLE);
                            chiefTextView.setVisibility(View.VISIBLE);
                        }
                    });
                    chiefClicker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            add(-20);
                            appear2 = false;
                            if(score < 10) {
                                fadeOut(homerClicker);
                                homerClicker.setVisibility(View.INVISIBLE);
                                homerTextView.setVisibility(View.INVISIBLE);
                            }
                            chiefSpawn();
                            new chiefThread().start();
                            fadeOut(chiefClicker);
                            chiefClicker.setVisibility(View.INVISIBLE);
                            chiefTextView.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            }
        }, 0, 1000);
    }
    public static synchronized void add(int i){
        score+= i;
        if(score < 0)
            score = 0;
    }
    public void setText(TextView textView, String string){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(string);
            }
        });
    }

    public void homerSpawn(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView homer = new ImageView(MainActivity.this);
                homer.setId(View.generateViewId());
                homer.setImageResource(R.drawable.homer);
                AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0, .5F);
                alphaAnimation.setDuration(500);
                ConstraintLayout.LayoutParams lp2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                homer.setLayoutParams(lp2);
                homer.getLayoutParams().height = 250;
                homer.requestLayout();
                homer.getLayoutParams().width = 250;
                homer.requestLayout();
                myLayout.addView(homer);
                ConstraintSet cs2 = new ConstraintSet();
                cs2.clone(myLayout);
                Log.d("CCLog","got here2");
                cs2.connect(homer.getId(),ConstraintSet.TOP,imageView.getId(),ConstraintSet.TOP);
                cs2.connect(homer.getId(),ConstraintSet.LEFT,myLayout.getId(),ConstraintSet.LEFT);
                cs2.connect(homer.getId(),ConstraintSet.RIGHT,myLayout.getId(),ConstraintSet.RIGHT);
                cs2.connect(homer.getId(),ConstraintSet.BOTTOM,myLayout.getId(),ConstraintSet.BOTTOM);
                double rand = (Math.random()*.5+.1);
                //float randy = (float)(Math.random()*1);
                cs2.setHorizontalBias(homer.getId(), (float) rand);
                cs2.setVerticalBias(homer.getId(),1);
                Log.d("CCLog","got here3");
                cs2.applyTo(myLayout);
                homer.startAnimation(alphaAnimation);
                Log.d("CCLog","homer drawable:" + homer.getDrawable());
                Log.d("CCLog","homer x: " + homer.getX());
                Log.d("CCLog","homer y: " + homer.getY());
            }
        });

    }
    public void fadeIn(ImageView imageView){
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0, .5F);
        alphaAnimation.setDuration(500);
        imageView.setAnimation(alphaAnimation);
    }
    public void fadeOut(ImageView imageView){
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) .5F, 0);
        alphaAnimation.setDuration(500);
        imageView.setAnimation(alphaAnimation);
    }
    public void chiefSpawn(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView chief = new ImageView(MainActivity.this);
                chief.setId(View.generateViewId());
                chief.setImageResource(R.drawable.chief);
                AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0, .5F);
                alphaAnimation.setDuration(500);
                ConstraintLayout.LayoutParams lp2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                chief.setLayoutParams(lp2);
                chief.getLayoutParams().height = 250;
                chief.requestLayout();
                chief.getLayoutParams().width = 250;
                chief.requestLayout();
                myLayout.addView(chief);
                ConstraintSet cs2 = new ConstraintSet();
                cs2.clone(myLayout);
                cs2.connect(chief.getId(),ConstraintSet.TOP,imageView.getId(),ConstraintSet.TOP);
                cs2.connect(chief.getId(),ConstraintSet.LEFT,myLayout.getId(),ConstraintSet.LEFT);
                cs2.connect(chief.getId(),ConstraintSet.RIGHT,myLayout.getId(),ConstraintSet.RIGHT);
                cs2.connect(chief.getId(),ConstraintSet.BOTTOM,myLayout.getId(),ConstraintSet.BOTTOM);
                double rand = (Math.random()*.5+.1);
                Log.d("CCLog","rand: " + rand);
                //float randy = (float)(Math.random()*1);
                cs2.setHorizontalBias(chief.getId(), (float) rand);
                cs2.setVerticalBias(chief.getId(),.8F);
                Log.d("CCLog","chief 2");
                cs2.applyTo(myLayout);
                chief.setVisibility(View.VISIBLE);
               //  chief.startAnimation(alphaAnimation);
                Log.d("CCLog","chief drawable:" + chief.getDrawable());
                //Log.d("CCLog","chief x: " + chief.get);
                Log.d("CCLog","chief y: " + chief.getY());

            }
        });
    }


    public void deleteTextView(TextView textView){
        CountDownTimer timer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                textView.setVisibility(View.GONE);
            }
        };
        timer.start();
    }

    public void frenzyBackground(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView goldDonut = new ImageView(MainActivity.this);
                goldDonut.setId(View.generateViewId());
                goldDonut.setImageResource(R.drawable.pleas);
                TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,2000);
                translateAnimation.setDuration(2000);
                ConstraintLayout.LayoutParams lp2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                goldDonut.setLayoutParams(lp2);
                goldDonut.getLayoutParams().height = 150;
                goldDonut.requestLayout();
                goldDonut.getLayoutParams().width = 150;
                goldDonut.requestLayout();
                myLayout.addView(goldDonut);
                ConstraintSet cs2 = new ConstraintSet();
                cs2.clone(myLayout);
                cs2.connect(goldDonut.getId(),ConstraintSet.TOP,myLayout.getId(),ConstraintSet.TOP);
                cs2.connect(goldDonut.getId(),ConstraintSet.LEFT,myLayout.getId(),ConstraintSet.LEFT);
                cs2.connect(goldDonut.getId(),ConstraintSet.RIGHT,myLayout.getId(),ConstraintSet.RIGHT);
                cs2.connect(goldDonut.getId(),ConstraintSet.BOTTOM,myLayout.getId(),ConstraintSet.BOTTOM);
                double rand = Math.random()*1;
                Log.d("CCLog","rand: " + rand);
                //float randy = (float)(Math.random()*1);
                cs2.setHorizontalBias(goldDonut.getId(), (float)(Math.random()));
                cs2.setVerticalBias(goldDonut.getId(),.03f);
                cs2.applyTo(myLayout);
                goldDonut.setZ(-1);
                goldDonut.setAnimation(translateAnimation);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        goldDonut.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });

    }

    public static class homerThread extends Thread {
        public void run() {

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                 Log.d("CCLog","homerCounter: " + homerCounter);
              //  for(int i = 0; i < homerCounter;i++)
                    if(frenzy)
                        add(2);
                    else
                        add(1);
                }
            }, 1000, 1000);

        }
    }

    public static class chiefThread extends Thread {
        public void run() {

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Log.d("CCLog","homerCounter: " + homerCounter);
                    //  for(int i = 0; i < homerCounter;i++)
                    if(frenzy)
                         add(6);
                    else
                        add(3);
                }
            }, 1000, 1000);

        }
    }


}