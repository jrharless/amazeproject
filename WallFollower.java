package falstad;

import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.Distance;
import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui.Globals;
import android.os.Handler;
import android.util.Log;

/**
 * This algorithm allows the robot to find the exit of the maze solely by traveling along the walls to its left.
 * When it reaches the exit, it passes through it, thus completing the maze.
 * @author Jacob Harless and Quinn Reiley
 */
public class WallFollower implements RobotDriver{
	private Robot robot;
	private Distance dist;
	private int pathlength = 0;
	private Boolean ispaused =false;
	Handler handler = new Handler();

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
		if (ispaused){
			return true;
		}
		if(!robot.isAtExit() && Globals.battery>0) {
			System.out.print("distance to obstacle is     ");
			System.out.println(robot.distanceToObstacle(Direction.LEFT));

			if(robot.distanceToObstacle(Direction.LEFT) != 0) {
				rotate(Turn.LEFT);
				move(1);
			}

			else if(robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.RIGHT) >0) {
				rotate(Turn.RIGHT);
				move(1);
			}

			else if(robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.RIGHT) == 0) {
				rotate(Turn.AROUND);
				move(1);
			}

			else {
				move(1);
			}
		}

		else{
		if (Globals.battery>0){
		if (robot.canSeeExit(Direction.LEFT) == true ) {
			System.out.println("right");
			rotate(Turn.LEFT);
			move1(1);
		}

		else if (robot.canSeeExit(Direction.RIGHT) == true) {
			System.out.println("left");
			rotate(Turn.RIGHT);
			move1(1);
		}

		else {
			move1(1);

		}
			//Globals.playActivity.checklose();
			Globals.playActivity.movetoFinishScreen();
}}

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

	private void rotate(Turn turn){
		final Turn turn1 = turn;
		handler.postDelayed(new Runnable(){
			@Override
			public void run(){
				try{
					robot.rotate(turn1);
				}
				catch(Exception e){
					Log.v("Exception","rotate");
				}
			}
		},100);
	}
	@Override
	public float getEnergyConsumption() {
		return 2500 -robot.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
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