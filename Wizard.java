package falstad;

import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Distance;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui.Globals;


/**
 * This algorithm allows the robot to run through the maze automatically
 * by following the shortest possible distance. It will complete the maze
 * when it reaches the exit.
 */
public class Wizard implements RobotDriver {
	private Robot robot;
	private Distance dist;
	private int pathlength = 0;
	private Handler handler = new Handler();
	private Boolean ispaused =false;

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
		Log.v("Command", "Drive2exit");
		if (ispaused){
			return true;
		}

					if (!(robot.isAtExit()))
					{
						Direction whichdirection = null;
						int[] position = robot.getCurrentPosition();
						int mindist = Integer.MAX_VALUE;
						Direction[] directions = {Direction.LEFT, Direction.RIGHT, Direction.FORWARD, Direction.BACKWARD};

						for (int j = 0; j < directions.length; j++) {

							if (robot.distanceToObstacle(directions[j]) == 0) {
								continue;
							}

							int[] newcell = new int[]{position[0], position[1]};
							newcell = checkdirection(directions[j], newcell);

							if (dist.getDistance(newcell[0], newcell[1]) <= mindist) {
								mindist = dist.getDistance(newcell[0], newcell[1]);
								whichdirection = directions[j];
							}
						}

						if (whichdirection == Direction.BACKWARD) {
							rotate(Turn.AROUND);

						} else if (whichdirection == Direction.RIGHT) {
							rotate(Turn.RIGHT);

						} else if (whichdirection == Direction.LEFT) {
							rotate(Turn.LEFT);
						}
						move(1);
					}
					else {

							if (robot.canSeeExit(Direction.LEFT) == true)
							{
								rotate(Turn.LEFT);
								move1(1);

							} else if (robot.canSeeExit(Direction.RIGHT) == true)

							{
								rotate(Turn.RIGHT);
								move1(1);

							} else

							{
								move1(1);
							}

						//Globals.playActivity.checklose();

						Globals.playActivity.movetoFinishScreen();



					}
//					return;
//				} catch (Exception e) {
//					System.out.println(e);
//				}
//			}
//		};
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


	/**
	 * returns the adjacent cell in the desired direction.
	 * @param relative
	 * @param cell
	 * @return
	 */
	public int[] checkdirection(Direction relative, int[] cell){

			if (robot.getCurrentDirection() == CardinalDirection.East) { //looking east
				if (relative == Direction.FORWARD) { //looking east

					cell[0] += 1;
				}

				if (relative == Direction.BACKWARD) { //looking west of cell
					cell[0] -= 1;
				}

				if (relative == Direction.LEFT) { //looking north of cell
					cell[1] += 1;
				}

				if (relative == Direction.RIGHT) {//looking south of cell
					cell[1] -= 1;
				}
			}
			if (robot.getCurrentDirection() == CardinalDirection.West) { //looking west
				if (relative == Direction.FORWARD) { //looking west of cell
					cell[0] -= 1;
				}
				if (relative == Direction.BACKWARD) { //looking east of cell
					cell[0] += 1;
				}
				if (relative == Direction.LEFT) { //looking south of cell
					cell[1] -= 1;
				}
				if (relative == Direction.RIGHT) {//looking north of cell
					cell[1] += 1;
				}
			}
			if (robot.getCurrentDirection() == CardinalDirection.North) { //looking North
				if (relative == Direction.FORWARD) { //looking North of cell
					cell[1] -= 1;
				}
				if (relative == Direction.BACKWARD) { //looking south of cell
					cell[1] += 1;
				}
				if (relative == Direction.LEFT) { //looking west of cell
					cell[0] += 1;
				}
				if (relative == Direction.RIGHT) {//looking east of cell
					cell[0] -= 1;
				}
			}
			if (robot.getCurrentDirection() == CardinalDirection.South) { //looking South
				if (relative == Direction.FORWARD) { //looking South of cell
					cell[1] += 1;
				}
				if (relative == Direction.BACKWARD) { //looking North of cell
					cell[1] -= 1;
				}
				if (relative == Direction.LEFT) { //looking East of cell
					cell[0] -= 1;
				}
				if (relative == Direction.RIGHT) {//looking West of cell
					cell[0] += 1;
				}
			}
			return cell;

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