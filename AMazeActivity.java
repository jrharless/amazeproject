package edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.widget.Spinner;
import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.R;
import android.view.View;
import android.content.Intent;
import java.util.*;
import android.os.Handler;



/**
 * Created by jacobharless on 11/8/16.
 * Extends AppCombat
 */
public class AMazeActivity extends AppCompatActivity {
    private SeekBar seekbar;
    private Button explore1;
    private Button revisit1;
    private Spinner driver;
    private Spinner algorithm;
    public String Eller = "Eller";
    public String Prim = "Prim";
    public String DFS = "DFS";
    public String File = "Load From File";
    public String Wizard = "Wizard";
    public String Pledges = "Pledge's";
    public String WallFollower = "WallFollower";
    public String Manual = "Manual";
    public String Generator = "DFS";
    public String Driver = "Manual";
    public Integer Skill = 0;
    public MediaPlayer mPlayer;




    /**
     * creates screen and intializes buttons and spinners and seekbar
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_amaze);
        explore1 = (Button) findViewById(R.id.ExploreButton);
        revisit1 = (Button) findViewById(R.id.RevisitButton);
        algorithm = (Spinner) findViewById(R.id.BuilderSpinner);
        driver = (Spinner) findViewById(R.id.DriverSpinner);
        seekbar = (SeekBar) findViewById(R.id.levelSeekBar);
        seekbar.setMax(15);
        givealgorithmSpinnervalues();
        givedriverSpinnervalues();
        mPlayer = MediaPlayer.create(this, R.raw.amonhen);
        mPlayer.start();
    }

    public void makeToast(View view){
        final Toast toast = Toast.makeText(getApplicationContext(),"button clicked", Toast.LENGTH_SHORT);
        toast.show();
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        },100);
    }
    /**
     * give spinner maze algorithms
     */
    private void givealgorithmSpinnervalues(){
        List<String> algorithms = new ArrayList<String>();
        algorithms.add(Eller);
        algorithms.add(Prim);
        algorithms.add(DFS);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, algorithms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        algorithm.setAdapter(adapter);
    }


    /**
     * give spinner value of drivers
     */
    private void givedriverSpinnervalues(){
        List<String> drivers = new ArrayList<String>();
        drivers.add(Manual);
        drivers.add(Wizard);
        drivers.add(WallFollower);
        drivers.add(Pledges);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, drivers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driver.setAdapter(adapter);
    }


    /**
     * moves to generating screen

     */
    public void movetoGenerating(){
        mPlayer.stop();
        Intent intent = new Intent(this, GeneratingActivity.class);
        Generator = ((Spinner)findViewById(R.id.BuilderSpinner)).getSelectedItem().toString();
        Driver =    ((Spinner)findViewById(R.id.DriverSpinner)).getSelectedItem().toString();
        seekbar = (SeekBar) findViewById(R.id.levelSeekBar);
        Skill = seekbar.getProgress();
        intent.putExtra("Skill", Skill);
        intent.putExtra("Algorithm", Generator);
        intent.putExtra("Driver", Driver);
        startActivity(intent);
    }
    public void revisit(View view){
        Skill = seekbar.getProgress();
        if (Skill<=3){
            Globals.isrevisit = true;
        }

        movetoGenerating();
    }
    public void explore(View view){

        Globals.isrevisit = false;
        movetoGenerating();
    }



}
