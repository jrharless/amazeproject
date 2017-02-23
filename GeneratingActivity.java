package edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui;




import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.R;
import android.content.Intent;
import android.util.*;
import android.view.*;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Handler;

import falstad.Constants;
import falstad.ManualDriver;
import falstad.MazeController;
import falstad.MazeFileWriter;
import falstad.Pledge;
import falstad.WallFollower;
import falstad.Wizard;
import generation.Cells;
import generation.MazeBuilder;
import generation.MazeBuilderEller;
import generation.MazeBuilderPrim;
import generation.Order;
import android.os.Message;
import falstad.MazePanel;
import java.io.File;

/**
 * Created by jacobharless on 11/9/16.
 */
public class GeneratingActivity extends AppCompatActivity {
    public Integer skill;
    public String algorithm;
    public String Driver = "Manual";
    public Boolean bool = true;
    ProgressBar bar;
    MazeBuilder builder;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            bar.incrementProgressBy(1);
        }
    };
    boolean isRunning = false;
    boolean moveto= true;



    /**
     * creates screen and generates bundle and logs the info from the main screen
     *
     * @param savedinstancestate
     */
    @Override
    protected void onCreate(Bundle savedinstancestate) {
        super.onCreate(savedinstancestate);

        setContentView(R.layout.generating_activity);
        Bundle extras = getIntent().getExtras();
        skill = extras.getInt("Skill");
        Globals.skill = skill;
        algorithm = extras.getString("Algorithm");
        Driver = extras.getString("Driver");
        Log.v("Algorithm is", "" + algorithm);
        assigndriver(Driver);
        Log.v("Driver is", Driver);
        Log.v("Skill is", "" + skill);
        bar = (ProgressBar) findViewById(R.id.ProgressBar);
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (Globals.isrevisit){
            filetime();
        }
        else {
            Log.v("Generating","New Maze");
            Globals.order = new MazeController();
            Globals.order.init();
            setBuilder(algorithm);
            Globals.order.setSkillLevel(skill);
            builder.buildOrder(Globals.order);
        }



    }

    public void filetime(){
        //if a file exists
        String filename = getFilesDir().toString() + "/maze" + Globals.skill;
        File file  = new File(filename);
        if (!file.exists()){
            Log.v("generating","maze without file");
            Globals.order = new MazeController();
            Globals.order.init();
            setBuilder(algorithm);
            Globals.order.setSkillLevel(skill);
            builder.buildOrder(Globals.order);
            Globals.isrevisit =false;
        }
        else {
            Log.v("generating","maze with file");

            Globals.order = new MazeController(filename);
            Globals.order.init();


        }
    }

    public void setBuilder(String alg){
        if (alg.equals("DFS")){Globals.order.setBuilder(Order.Builder.DFS);
        builder = new MazeBuilder();}
        else if (alg.equals("Eller")){Globals.order.setBuilder(Order.Builder.Eller);
        builder = new MazeBuilderEller();}
        else if (alg.equals("Prim")){Globals.order.setBuilder(Order.Builder.Prim);
        builder = new MazeBuilderPrim();}
    }


    @Override
    public void onStart(){
        super.onStart();

        bar.setProgress(0);

        Thread build = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Thread.sleep(4000);

                }catch(Exception e){
                    System.out.println(e);
                }


                if (!Globals.isrevisit) {
                    builder.run();
                }

                if (moveto) {
                            moveToPlayScreen();
                        }


            }
        });

        Thread background = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try
                {

                    Integer count = 0;
                    while (Integer.valueOf(Globals.order.getPercentDone()) <100)
                    {
                        if(count !=Integer.valueOf(Globals.order.getPercentDone())) {
                            count = Integer.valueOf(Globals.order.getPercentDone());
                            handler.sendMessage(handler.obtainMessage());
                            Log.v("Percent Done", Globals.order.getPercentDone());
                        }
                    }
                }
                catch (Throwable t)
                {
                    // just end the background thread
                }
            }
        });
        isRunning = true;

        build.start();

        background.start();



    }

    public void onStop()
    {
        super.onStop();
        isRunning = false;
    }


    /**
     * moves to play screen
     *
     */
    public void moveToPlayScreen() {
        Log.v("Go to","play screen");
        if (Driver.equals("Manual")) {
            bool = true;
        } else {
            bool = false;
        }
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("Boolean", bool);
        startActivity(intent);
    }

    /**
     * moves to menu screen
     *
     * @param view
     */
    public void goBackToMenu(View view) {
        moveto=false;
        Intent intent = new Intent(this, AMazeActivity.class);
        Log.i("Go Back to", "Menu Screen");
        startActivity(intent);
    }
    public void build(MazeBuilder builder,int skill){

    }
    public void assigndriver(String driver){
        if (driver.equals("Wizard")){Globals.driver = new Wizard();
        Log.v("Wizard","Assigned");}
        else if( driver.equals("Pledge's")){Globals.driver = new WallFollower();}
        else if( driver.equals("WallFollower")){Globals.driver = new WallFollower();}
        else {Globals.driver = new ManualDriver();}
    }

}