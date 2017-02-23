package falstad;

import android.util.Log;

import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui.Globals;
import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Distance;
import java.util.*;
import android.os.Handler;

/**
 * This algorithm will randomly chose a 'main direction' for the robot to travel on.
 * Once it hits an obstacle, it will use the wall follower algorithm, tracking the turns it makes along the way.
 * Each left turn counts as a -1, and each right turn counts as a +1. When that number is 0, then the robot
 * is facing it's main direction and will travel along that direction until it hits another obstacle, and the process repeats.
 * Eventually it will find the exit and pass through it, thus completing the maze.
 * @author Jacob Harless and Quinn Reiley
 */
public class Pledge implements RobotDriver{
	private Robot robot;
	private Distance dist;
	private int pathlength = 0;
	private CardinalDirection maindirection;
	private Boolean ispaused =false;
	private Handler handler = new Handler();
	int turncounter = 0;

	@Override
	public void setplay(){
		ispaused = false;
		try{
			drive2Exit();
		}
		catch(Exception e){

		}
	}
	@Override
	public void setpaused(){
		ispaused = true;

	}

	@Override
	public void setRobot(Robot r) {
		robot = r;
	}

	@Override
	public void setDimensions(int width, int height) {
	}

	@Override
	public void setDistance(Distance distance) {
		dist = distance;
	}

	@Override
	public boolean drive2Exit() throws Exception {
		if (ispaused == true){
			return true;
		}

//		randomdirection();
//		while(robot.getCurrentDirection() != maindirection){
//			rotate(Turn.LEFT);
//		}

		if(!robot.isAtExit()) {
			if((turncounter == 0) && robot.distanceToObstacle(Direction.FORWARD) > 0) {
				if (turncounter == 4||turncounter == -4){
					turncounter =0;
				}
				//System.out.println("pledge");
				move(1);
			}

			else {

				if(robot.distanceToObstacle(Direction.LEFT) != 0){
					turncounter ++;
					rotate(Turn.LEFT);
					move(1);

				}

				else if(robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.RIGHT) >0){
					turncounter --;
					rotate(Turn.RIGHT);
					move(1);

				}

				else if(robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.RIGHT) == 0){
					turncounter -=2;
					rotate(Turn.AROUND);
					move(1);

				}

				else{
					move(1);
				}
			}
		}
		else{
		if (Globals.battery>0){
		if (robot.canSeeExit(Direction.LEFT) == true){
			System.out.println("right");
			rotate(Turn.LEFT);
			move1(1);
		}

		else if (robot.canSeeExit(Direction.RIGHT) == true){
			System.out.println("left");
			rotate(Turn.RIGHT);
			move1(1);
		}

		else {
			move1(1);
		}}}
		//Globals.playActivity.checklose();
		Globals.playActivity.movetoFinishScreen();
		return true;
	}


	private void move(int dist){
		final int distance = dist;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					robot.move(distance,true);
					drive2Exit();
				} catch (Exception e) {
					Log.v("Exception", e.toString());
				}
			}
		}, 100);
	}
	private void move1(int dist){
		final int distance = dist;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					robot.move(distance,true);

				} catch (Exception e) {
					Log.v("Exception", e.toString());
				}
			}
		}, 100);
	}

	private void rotate(Turn turn) {
		final Turn turn1 = turn;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					robot.rotate(turn1);
				} catch (Exception e) {
					Log.v("Exception", "rotate");
				}
			}
		}, 100);
	}





	/**
	 * This method chooses a random direction to act as the main direction for the algorithm.
	 */
	public void randomdirection() {
		Random rand = new Random();
		int num = rand.nextInt(4);

		if(num == 0) {
			maindirection = CardinalDirection.North;
		}

		if(num == 1) {
			maindirection = CardinalDirection.East;
		}

		if(num == 2) {
			maindirection = CardinalDirection.South;
		}

		if(num == 3) {
			maindirection = CardinalDirection.West;
		}
	}

	@Override
	public float getEnergyConsumption() {
		return 2500 -robot.getBatteryLevel();
	}

	@Override
	public int getPathLength(){
		int pathlength = ((BasicRobot)robot).getpathlength();
		return pathlength;
	}

	@Override
	public void moveforward() {
	}

	@Override
	public void movebackwards() {
	}

	@Override
	public void rotateright() {
	}

	@Override
	public void rotateleft() {
	}
}