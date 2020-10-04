package com.example.autoirrigationsystem;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import me.itangqi.waveloadingview.WaveLoadingView;

import static java.lang.String.valueOf;

public class HomePage extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference  valve11 = database.getReference("valve1");
    DatabaseReference  valve22 = database.getReference("valve2");
    DatabaseReference  pump11 = database.getReference("pump1");
    DatabaseReference  pump22 = database.getReference("pump2");
    DatabaseReference  waterratio = database.getReference("waterRatio") ;

    String TAG , valuee;



    // this part of code for switches

   Switch valve1 , valve2 , pump1 , pump2;
   TextView v1state,v2state,p1state,p2state;


    //this part of code for timer

    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;



     TextView t ;
     EditText e;
     Button b;
    WaveLoadingView mWaveLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        valve1=findViewById(R.id.valve1);
        valve2=findViewById(R.id.valve2);
        pump1=findViewById(R.id.pump1);
        pump2=findViewById(R.id.pump2);
        v1state=findViewById(R.id.textViewv1);
        v2state=findViewById(R.id.textViewv2);
        p1state=findViewById(R.id.textViewp1);
        p2state=findViewById(R.id.textViewp2);




        t=findViewById(R.id.text);



        //this part of code for switches



        Animation Animationvalve1 = (Animation) AnimationUtils.loadAnimation(this,R.anim.progresbaranim);
        valve1.startAnimation(Animationvalve1);


        valve1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    valve11.setValue("on");

                    v1state.setText("valve1 is ON");


                }

                else{v1state.setText("valve1 is OFF");

                    valve11.setValue("off");
                }
            }
        });


        Animation Animationvalve2 = (Animation) AnimationUtils.loadAnimation(this,R.anim.leftanime);
        valve2.startAnimation(Animationvalve2);

        valve2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    valve22.setValue("on");
                    v2state.setText("valve2 is ON");

                }

                else{v2state.setText("valve2 is OFF");

                    valve22.setValue("off");}
            }
        });

        Animation Animationpump1 = (Animation) AnimationUtils.loadAnimation(this,R.anim.progresbaranim);
        pump1.startAnimation(Animationpump1);


        pump1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    pump11.setValue("on");

                    p1state.setText(" pump1 is ON");

                }

                else{p1state.setText("pump1 is OFF");

                    pump11.setValue("off");
                }
            }
        });



        Animation Animationpump2 = (Animation) AnimationUtils.loadAnimation(this,R.anim.leftanime);
        pump2.startAnimation(Animationpump2);

        pump2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    pump22.setValue("on");

                    p2state.setText("pump2 is ON");

                }

                else{p2state.setText(" pump2 is OFF");

                    pump22.setValue("off");
                }

            }
        });



   // this part of code for background
        LinearLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();




        // this part of code for switches





        // this part of code for timer

        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);


        Animation AnimationmTextViewCountDown = (Animation) AnimationUtils.loadAnimation(this,R.anim.timeranim);
        mTextViewCountDown.startAnimation(AnimationmTextViewCountDown);


        Animation AnimationmEditTextInput = (Animation) AnimationUtils.loadAnimation(this,R.anim.timeranim);
        mEditTextInput.startAnimation(AnimationmEditTextInput);



        Animation AnimationmButtonSet = (Animation) AnimationUtils.loadAnimation(this,R.anim.timeranim);
        mButtonSet.startAnimation(AnimationmButtonSet);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(HomePage.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(HomePage.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });




        Animation AnimationmButtonStartPause = (Animation) AnimationUtils.loadAnimation(this,R.anim.timeranim);
        mButtonStartPause.startAnimation(AnimationmButtonStartPause);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTimerRunning) {

                    pauseTimer();
                } else {

                    startTimer();
                }
            }
        });



        Animation AnimationmButtonReset = (Animation) AnimationUtils.loadAnimation(this,R.anim.timeranim);
        mButtonReset.startAnimation(AnimationmButtonReset);




        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });


        // Read from the database
        waterratio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 valuee = dataSnapshot.getValue().toString();
                Log.d(TAG, "Value is: " + valuee);

                String x = valuee;

                int y = Integer.parseInt(x);
                mWaveLoadingView .setProgressValue(y);
                t.setText(x);
                Toast.makeText(HomePage.this,valuee,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //int y= Integer.parseInt(x);

       // s.setProgress(y);
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();


                pump11.setValue("on");
                p1state.setText("pump1 is ON");
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();


                pump11.setValue("off");
                p1state.setText("pump1 is OFF");
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();


        pump11.setValue("off");
        p1state.setText("pump1 is OFF");
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }




      //  Animation Animationprogressbar = (Animation) AnimationUtils.loadAnimation(this,R.anim.progresbaranim);
     //   mWaveLoadingView.startAnimation(Animationprogressbar);


       // t  = (TextView)findViewById(R.id.text);
        Animation Animationtextview = (Animation) AnimationUtils.loadAnimation(this,R.anim.progresbaranim);
        t.startAnimation(Animationtextview);






        mWaveLoadingView =  findViewById(R.id.waveLoadingView);
       mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setAnimDuration(2000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();




    }








    }






