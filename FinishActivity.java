package edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui;




        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.R;
        import android.util.*;
        import android.view.*;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.os.Vibrator;

/**
 * Created by jacobharless on 11/9/16.
 */


public class FinishActivity extends AppCompatActivity {
    /**
     * creates the activity
     * @param savedInstanceState
     */
    public TextView battery;
    public TextView pathlength;
    public TextView title;
    public MediaPlayer mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_activity);
        RelativeLayout r =(RelativeLayout) findViewById(R.id.finishscreen);
        battery = (TextView) findViewById(R.id.conAmt);
        pathlength = (TextView) findViewById(R.id.plAmt);
        battery.setText(""+(2500-Globals.battery));
        pathlength.setText(""+Globals.pathlength);

        if (Globals.islose == true){
            r.setBackground(getDrawable(R.drawable.balrog));
            title = (TextView) findViewById(R.id.outside);
            title.setText("You Delved too Deep!");
            mPlayer = MediaPlayer.create(this, R.raw.losingscreen);
            mPlayer.start();

        }
        else{
            mPlayer = MediaPlayer.create(this, R.raw.victorymusic);
            mPlayer.start();
        }

    }



    /**
     * returns to the first screen
     * @param view
     */
    public void returnToFinishScreen(View view){
        Globals.battery =2500;
        Globals.pathlength = 0;
        mPlayer.stop();
        Intent intent = new Intent(this, AMazeActivity.class);
        Log.i("Go Back to","Menu Screen");
        startActivity(intent);

    }


    /**
     * exits application
     * @param view
     */
    public void exit(View view){
        finish();
        System.exit(0);
        mPlayer.stop();
        Log.v("quit","app");
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
        v.vibrate(400);
    }






}