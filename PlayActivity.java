package edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui;

import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.widget.Spinner;
import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.R;
import falstad.*;

import android.content.Intent;
import android.util.*;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.os.Handler;
import android.os.Message;
import android.media.MediaPlayer;
import android.os.Vibrator;


/**
 * Created by jacobharless on 11/9/16.
 */
public class PlayActivity extends AppCompatActivity {
    Boolean isManualDriver;
    ProgressBar battery;
    Boolean ispaused;
    Handler handler;
    private MediaPlayer mPlayer;
    Thread driver;
    MazeFileWriter writer =  new MazeFileWriter();



    /**
     * moves to oncreate
     *
     * @param savedinstancestate
     */
    @Override
    protected void onCreate(Bundle savedinstancestate) {
        MazePanel panel;
        Globals.playActivity = this;
        super.onCreate(savedinstancestate);
        ispaused = false;
        setContentView(R.layout.play_activity);


        mPlayer = MediaPlayer.create(this, R.raw.loth);
        mPlayer.start();


        //panel = new MazePanel(this);
        //panel.setBackgroundColor(Color.WHITE);
        setContentView(R.layout.play_activity);
        Bundle extras = getIntent().getExtras();
        isManualDriver = extras.getBoolean("Boolean");
        Log.v("Driver is:", "" + isManualDriver);
        battery = (ProgressBar) findViewById(R.id.batteryBar);
        battery.setProgress(Globals.battery);
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.yas);
        // Globals.panel = (MazePanel) findViewById(R.id.panel);
        Globals.panel = new MazePanel(this, null);
        Globals.order.switchToPlayingScreen();
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        //Globals.panel.setLayoutParams(params);
        r1.addView(Globals.panel, params);

        if (Globals.skill<=3){
            String filename = getFilesDir().toString() + "/maze" + Globals.skill;
            writer.store(filename, Globals.order.getMazeConfiguration().getWidth(),
                    Globals.order.getMazeConfiguration().getHeight(), Constants.SKILL_ROOMS[Globals.skill],
                    Constants.SKILL_PARTCT[Globals.skill], Globals.order.getMazeConfiguration().getRootnode()
                    , Globals.order.getMazeConfiguration().getMazecells(),
                    Globals.order.getMazeConfiguration().getMazedists().getDists(),
                    Globals.order.getMazeConfiguration().getStartingPosition()[0],
                    Globals.order.getMazeConfiguration().getStartingPosition()[1]);

        }

        if (isManualDriver == true) {
            setvisibleManualButtons();
        } else {
            setvisibleRobotButtons();
        }

        BasicRobot robot = new BasicRobot();
        robot.setMaze(Globals.order);
        Globals.driver.setDistance(Globals.order.getMazeConfiguration().getMazedists());
        Globals.driver.setRobot(robot);
        //Globals.order.getfinishedrobotdriver(Globals.driver);


        //if (Globals.order.state == Constants.StateGUI.STATE_PLAY) Log.v("Is at","State Play");
    }


    @Override
    public void onResume() {
        super.onResume();
        driver = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                        if (!isManualDriver) {
                            try {
                                Globals.driver.drive2Exit();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }

        });
        driver.run();

    }


    /**
     * set the buttons visible for the manual screen
     */
    public void setvisibleManualButtons(){
        ImageButton forwardbtn = (ImageButton) findViewById(R.id.forward);
        forwardbtn.setVisibility(View.VISIBLE);
        ImageButton backwardsbtn = (ImageButton) findViewById(R.id.backward);
        backwardsbtn.setVisibility(View.VISIBLE);
        ImageButton rightbtn = (ImageButton) findViewById(R.id.right);
        rightbtn.setVisibility(View.VISIBLE);
        ImageButton leftbtn = (ImageButton) findViewById(R.id.left);
        leftbtn.setVisibility(View.VISIBLE);
    }


    /**
     * set the play and pause buttons visible
     */
    public void setvisibleRobotButtons(){
        ImageButton pausebtn = (ImageButton) findViewById(R.id.pause);
        pausebtn.setVisibility(View.VISIBLE);
        ImageButton playbtn = (ImageButton) findViewById(R.id.play);
        playbtn.setVisibility(View.VISIBLE);


    }


    public void moveForwards(View view){
        Log.i("Move","Forwards");
        Globals.driver.moveforward();

        Globals.pathlength +=1;
        Globals.battery -=5;
        battery.setProgress(Globals.battery);
        checklose();

        if(Globals.order.boolisoutside == true){
            movetoFinishScreen();
        }
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
    public void moveBackwards(View view){
        Globals.pathlength +=1;
        Globals.battery -=5;
        battery.setProgress(Globals.battery);
        Globals.driver.movebackwards();
        checklose();
        Log.i("Move","Backwards")
        ;
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
    public void moveRight(View view){
        Log.v("Turn","Right");
        Globals.battery -=3;
        battery.setProgress(Globals.battery);
        Globals.driver.rotateright();
        checklose();
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
    public void moveLeft(View view){
        Log.v("Turn","Left");
        Globals.battery-=3;
        battery.setProgress(Globals.battery);
        Globals.driver.rotateleft();
        checklose();
    }

    public void checklose(){
        if(Globals.battery<=0){
            Globals.islose =true;
            Log.v("you","lose");
        movetoFinishScreen();}
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
    public void pause(View view){

        Log.v("Robot: ","Pause");
        Globals.driver.setpaused();
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
    public void play(View view){
        Log.v("Robot: ","Play");
        Globals.driver.setplay();
    }
    /**
     * logs what buttons are pressed
     */
    public void movetoFinishScreen(){
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        Log.v("Vibrations","are happening");
        Log.v("Moving to","Finish Screen");
        // Vibrate for 400 milliseconds
        v.vibrate(400);

        mPlayer.stop();
        Intent intent = new Intent(this, FinishActivity.class);
        startActivity(intent);
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
    public void showVisibleWalls(View view) {
        //Intent intent = new Intent(this, PlayActivity.class);
        Log.v("Show ", "Visible Walls");
        Globals.order.keyDown('\t');
        //startActivity(intent);
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
    public void showFullMaze(View view){
        //Intent intent = new Intent(this,PlayActivity.class);
        Log.v("Show ","Full Maze");
        Globals.order.keyDown('z');
        //startActivity(intent);
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
    public void showSolution(View view){
        //Intent intent = new Intent(this,PlayActivity.class);
        Log.v("Show","Solution");
        Globals.order.keyDown('s');
        //startActivity(intent);


    }


    /**
     * logs what buttons are pressed
     * @param view
     */
    public void goBackToMenutwo(View view){
        Intent intent = new Intent(this, AMazeActivity.class);

        Globals.driver.setpaused();
        mPlayer.stop();

        Log.i("Go Back to","Menu Screen");
        startActivity(intent);
    }
    /**
     * logs what buttons are pressed
     * @param view
     */
//    public void solve(View view){
//        Intent intent = new Intent(this, FinishActivity.class);
//        Log.i("Moving to","Finish Screen");
//        startActivity(intent);
//    }






}
