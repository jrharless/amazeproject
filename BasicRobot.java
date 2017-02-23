package falstad;

//import java.awt.Event;
import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui.Globals;
import generation.CardinalDirection;
import generation.Distance;
import generation.Cells;
import android.util.Log;

public class BasicRobot implements Robot{

	private float battery;
	private MazeController maze;
	private Robot robot;
	private int width;
	private int height;
	private Distance distance;
	private float intialbattery;
	private int pathlength = 0;

	/**
	 * This method sets the path length to 0, and the battery to 2500 when the maze is started.
	 */
	public BasicRobot() {
		battery = 2500;
	}

	/**
	 * returns path length
	 * @return
	 */
	public int getpathlength() {
		return pathlength;
	}

	/**
	 * This method gives the user the ability to rotate and face different directions within the Maze.
	 * After doing this, it then deducts the appropriate amount of batter from the robot's total battery.
	 * For a single turn the batter is deducted by 3 points, and for a 180 degree rotation, the batter is
	 * deducted 6 points.
	 */
	@Override
	public void rotate(Turn turn) {
		System.out.println("ROTATE:");
		System.out.println(turn);
		if (battery <=0 || Globals.battery<=0){
			Globals.playActivity.checklose();
			Globals.playActivity.movetoFinishScreen();
		}
		if (turn == Robot.Turn.LEFT) {

			Globals.battery -=3;
			battery = battery-3;
			maze.keyDown('h');
		}
		else if (turn == Robot.Turn.RIGHT) {
			Globals.battery -=3;
			battery = battery-3;
			maze.keyDown('l');
		}
		else if(turn == Robot.Turn.AROUND) {
			Globals.battery -=6;
			battery = battery-6;
			maze.keyDown('l');
			maze.keyDown('l');


		}
	}

	/**
	 * This method allows the robot to move through the maze, and deducts the appropriate amount from the
	 * battery per move (5 points). After the move is completed it updates the path length from the
	 * entrance of the maze the appropriate amount.
	 */
	@Override
	public void move(int distance, boolean manual) {
		System.out.println("MOVE");
		if (battery <=0 || Globals.battery<=0){
			Log.v("Battery","Less than 0");
			Globals.playActivity.checklose();
			Globals.playActivity.movetoFinishScreen();
		}
		if (manual == true && battery>0) {
			while (distance>0 && battery>0) {
				if (distanceToObstacle(Robot.Direction.FORWARD) == 0) {
					break;
				}
				else {
					maze.keyDown('k');
					distance--;
					Globals.pathlength++;
					Globals.battery -=5;
					pathlength++;
					battery = battery-5;
				}
			}
		}
	}

	/**
	 * This method finds and returns the robot's current position in the Maze.
	 * @return
	 */
	@Override
	public int[] getCurrentPosition() throws Exception {
		int[]array = new int[2];
		array[0] = maze.px;
		array[1]= maze.py;
		return array;
	}

	/**
	 * This method gives the robot a reference for the maze that it is in.
	 */
	@Override
	public void setMaze(MazeController maz) {
		maze = maz;
	}

	/**
	 * This method returns true if the robot is at the exit of the Maze. It returns false otherwise.
	 * @return
	 */
	@Override
	public boolean isAtExit() {
		Cells cells = maze.getMazeConfiguration().getMazecells();
		if (cells.isExitPosition(maze.px, maze.py) == true){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * This method checks to see if the robot is facing and can see the exit of the Maze.
	 * @return
	 */
	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		System.out.println("canseeexit");
		battery --;
		Cells cells = maze.getMazeConfiguration().getMazecells();
		CardinalDirection direct = getCurrentDirection();

		if (direction == Direction.LEFT) {
			direct = getCurrentDirection().rotateClockwise().rotateClockwise().rotateClockwise();
		}
		if (direction == Direction.BACKWARD) {
			direct = getCurrentDirection().rotateClockwise().rotateClockwise();
		}
		if (direction == Direction.RIGHT) {
			direct = getCurrentDirection().rotateClockwise();
		}
//		System.out.println(direct);

		int[] cell = {maze.px, maze.py};
		//int[]cell1 = {maze.px,maze.py};
		System.out.println(distanceToObstacle(direction));
		cell = checkdirection(direction,cell);

		if (distanceToObstacle(direction) !=0  && ((cell[0] <0 || cell[0]== maze.getMazeConfiguration().getWidth()) || (cell[1]<0 || cell[1] == maze.getMazeConfiguration().getHeight()))) {
			System.out.println(distanceToObstacle(direction));
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 *
	 */
	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		Cells cells = maze.getMazeConfiguration().getMazecells();
		if (cells.isInRoom(maze.px, maze.py) == true) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasRoomSensor() {
		return true;
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		return maze.getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		return battery;
	}

	@Override
	public void setBatteryLevel(float level) {
		this.battery = level;
		this.intialbattery = battery;
	}

	@Override
	public float getEnergyForFullRotation() {
		return 12;
	}

	@Override
	public float getEnergyForStepForward() {
		return 5;
	}

	@Override
	public boolean hasStopped() {
		if (battery == 0 || distanceToObstacle(Robot.Direction.FORWARD) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		battery--;
		int tempx=maze.px;
		int tempy =maze.py;
		int distanceto =0;
		int[] cell = {tempx,tempy};
		Cells cells = maze.getMazeConfiguration().getMazecells();
		CardinalDirection direct = this.getCurrentDirection();

		if (direction == Direction.LEFT) {
			direct = this.getCurrentDirection().rotateClockwise();
		}
		if (direction == Direction.BACKWARD) {
//			if (this.getCurrentDirection() == CardinalDirection.East||
//					this.getCurrentDirection() == CardinalDirection.West){
			direct = this.getCurrentDirection().rotateClockwise().rotateClockwise();
			//}
		}
		if (direction == Direction.RIGHT) {
			direct = this.getCurrentDirection().rotateClockwise().rotateClockwise().rotateClockwise();
		}

//		System.out.print("direct:  ");
//		System.out.println(direct);
		while(cell[0]>=0 && cell[0] < maze.getMazeConfiguration().getWidth()
				&& cell[1]>=0 && cell[1] < maze.getMazeConfiguration().getHeight()	&&
				cells.hasNoWall(cell[0], cell[1], direct)) {
//				System.out.println(cell[0]);
//				System.out.println(cell[1]);
				cell = checkdirection(direction, cell);

				distanceto +=1;
		}

		return distanceto;
	}

	/**
	 *
	 * @param relative- relative direction passed in
	 * @param cell -cell we are in
	 * @return a cell that is the relative direction we wants
	 */

	public int[] checkdirection(Direction relative, int[] cell) {
		//System.out.println(this.getCurrentDirection());
		if (this.getCurrentDirection() == CardinalDirection.East) { //looking east
			//System.out.println(relative);
			if (relative == Direction.FORWARD) { //looking east

				cell[0] +=1;
			}

			if (relative == Direction.BACKWARD) { //looking west of cell
				cell[0] -=1;
			}

			if (relative == Direction.LEFT) { //looking north of cell
				cell[1] +=1;
			}

			if (relative == Direction.RIGHT) {//looking south of cell
				cell[1] -=1;
			}
		}
		if (this.getCurrentDirection() == CardinalDirection.West) { //looking west
			if (relative == Direction.FORWARD) { //looking west of cell
				cell[0] -=1;
			}
			if (relative == Direction.BACKWARD) { //looking east of cell
				cell[0] +=1;
			}
			if (relative == Direction.LEFT) { //looking south of cell
				cell[1] -=1;
			}
			if (relative == Direction.RIGHT) {//looking north of cell
				cell[1] +=1;
			}
		}
		if (this.getCurrentDirection() == CardinalDirection.North) { //looking North
			if (relative == Direction.FORWARD) { //looking North of cell
				cell[1] -=1;
			}
			if (relative == Direction.BACKWARD) { //looking south of cell
				cell[1] +=1;
			}
			if (relative == Direction.LEFT) { //looking west of cell
				cell[0] +=1;
			}
			if (relative == Direction.RIGHT) {//looking east of cell
				cell[0] -=1;
			}
		}
		if (this.getCurrentDirection() == CardinalDirection.South) { //looking South
			if (relative == Direction.FORWARD) { //looking South of cell
				cell[1] +=1;
			}
			if (relative == Direction.BACKWARD) { //looking North of cell
				cell[1] -=1;
			}
			if (relative == Direction.LEFT) { //looking East of cell
				cell[0] -=1;
			}
			if (relative == Direction.RIGHT) {//looking West of cell
				cell[0] +=1;
			}
		}
//		System.out.print("Cell returned position: ");
//
//		System.out.print(cell[0]);
//		System.out.print(" , ");
//		System.out.println(cell[1]);
		return cell;
	}

	@Override
	public boolean hasDistanceSensor(Direction direction) {
		return true;
	}
}