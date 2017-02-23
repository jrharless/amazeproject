package falstad;
import edu.wm.cs.cs301.amazebyquinnreileyjacobharless.ui.Globals;
import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Distance;
import android.util.Log;
public class ManualDriver implements RobotDriver{
	Robot robot;
	Distance dist;
	int pathlength = 0;
	private boolean ispaused;
	public ManualDriver(){

	}
	@Override
	public void setplay(){
		ispaused = false;
	}

	@Override
	public void setpaused(){
		ispaused = true;
	}
	@Override
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		robot = r;
	}
	@Override
	public void setDimensions(int width, int height) {
		// TODO Auto-generated method stub
		//Globals.order.getMazeConfiguration().setHeight(height);
		//Globals.order.getMazeConfiguration().setWidth(width);

	}
	@Override
	public void setDistance(Distance distance) {
		// TODO Auto-generated method stub
		dist = distance;

	}

	@Override
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		Log.v("Drive2exit","manualdriver");
		return false;
	}



	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return 2500 -robot.getBatteryLevel();
	}
	@Override
	public int getPathLength(){
		// TODO Auto-generated method stub
		int pathlength = ((BasicRobot)robot).getpathlength();
		return pathlength;
	}

	public void moveforward(){

		robot.move(1,true);
		System.out.print("FORWARD");
		System.out.println(robot.distanceToObstacle(Direction.FORWARD));
		System.out.print("RIGHT");
		System.out.println(robot.distanceToObstacle(Direction.RIGHT));
		System.out.print("LEFT");
		System.out.println(robot.distanceToObstacle(Direction.LEFT));
		System.out.print("BACKWARD");
		System.out.println(robot.distanceToObstacle(Direction.BACKWARD));
		pathlength++;
	}
	public void movebackwards(){
		robot.rotate(Turn.AROUND);
		robot.move(1, true);
		pathlength--;
	}
	public void rotateright(){
		System.out.println("rotateright");
		robot.rotate(Turn.RIGHT);

	}
	public void rotateleft(){
		System.out.println("rotateleft");
		robot.rotate(Turn.LEFT);

	}

}