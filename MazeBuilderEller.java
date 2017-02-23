package generation;


import java.util.ArrayList;
import java.util.List;
/**
 * mazebuildereller extends mazebuilder using Ellers Algorithm to create the maze
 * @author jacobharless
 *
 */
public class MazeBuilderEller extends MazeBuilder implements Runnable {
	int row = 0;
	Cells cell;
	int[][] array;
	int counter = 1;
	int[][]board;

	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	//if it is deterministic
	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	/**
	 * returns true 50% of the time and False 50% of the time. useful for deleting walls randomly	
	 * @return a boolean
	 */
	public boolean randomBoolean(){
		return Math.random() < 0.5;
	}
	/**
	 * overrides generatepathways() from mazebuilder. this is the bulk of ellers. i use a sets class and 2 other helper methods
	 */
    @Override
	protected void generatePathways() {
		int[][]board = new int[width][width];
		for(int r=0;r<(width-1);r++){
			for(int c=0;c<(width-1);c++){
				
				board[r][c] = 0; 				
			}
		}
		array=board;
		//cell = new Cells(height,width);
		Sets[][] nodearray = new Sets[width][width];
		row =0;
		//go through each row in the maze (except for last row(special case))
		while (row < height-1){

			//go through row and assign a value 0 to each position
			for (int postion =0; postion < width; postion++){
				if (array[row][postion] ==0){	

					array[row][postion] = counter;
					//create a node called Sets which stores postionx, positiony and value
					Sets node = new Sets(counter,row,postion);
					nodearray[row][postion] = node;
					counter ++;
					
				}
			}
			for (int post =0; post< width-1; post++){

				//randomly decided to delete a wall horizontally
				if (randomBoolean() == true){
					//create a wall and delete it
					Wall wall = new Wall(post, row, CardinalDirection.East);
					cells.deleteWall(wall);
					//make sure to update value when deleted
					(nodearray[row][post+1]).value = (nodearray[row][post]).value;
					array[row][post+1] = array[row][post];
				}



			}
			//create a list that keeps track of values we create sets for
			List <Integer> list1= new ArrayList <Integer> ();
			//go through row
			for (int post1=0; post1 <width; post1++){
				
				if (! (list1.contains(nodearray[row][post1].value))){
					//for every value in the row, we create a new array that stores the nodes with the same value
					Sets[] setarray = checkrowforsets(row, nodearray, nodearray[row][post1].value);
					list1.add(nodearray[row][post1].value);
					//if the setarray is length 1, we must delete downwards
					if (setarray.length ==1){
						Wall wall1 = new Wall(setarray[0].positiony, row, CardinalDirection.South);
						cells.deleteWall(wall1);
						Sets node = new Sets(nodearray[row][post1].value,row+1,post1);
						nodearray[row+1][post1] = node;
						array[row+1][post1] = node.value;
						
					}
					
					else{
						int countbool=0;
						//go through the setarray and randomly delete walls downwards
						for (int i=0; i<setarray.length-1; i++){
							
								if (randomBoolean()==true){
									Wall wall2 = new Wall(setarray[i].positiony, row, CardinalDirection.South);
									cells.deleteWall(wall2);
									Sets node = new Sets(setarray[i].value,setarray[i].positiony +1,post1);
									nodearray[row+1][post1] = node;
									array[row+1][post1] = node.value;
									countbool++;	
								}
						}
						
						//if a wall was not deleted downwards, we delete a wall at the first position
						if (countbool==0){
							
							Wall wall3 = new Wall(setarray[0].positiony,row,CardinalDirection.South);
							cells.deleteWall(wall3);
							Sets node = new Sets(setarray[0].value,setarray[0].positiony +1,post1);
							nodearray[row+1][post1] = node;
							array[row+1][post1] = node.value;
									
								
					}
				}


				}
			}
			//increment row
			row+=1;		
		}
		//for last row we go through and give each cell a value if it does not have one
		for (int post2=0; post2<width; post2++){
			if (array[row][post2] == 0){	

				array[row][post2] = counter;
				
				Sets node = new Sets(counter,row,post2);
				nodearray[row][post2] = node;
				counter ++;
				
			}
		}
		//then go through and make all sets that are not the same value are connected
		for (int post3 = 0; post3<width-1; post3++){
			if (nodearray[row][post3].value != nodearray[row][post3+1].value){
				Wall wall4 = new Wall(post3, row, CardinalDirection.East);
				cells.deleteWall(wall4);
				
				(nodearray[row][post3+1]).value = (nodearray[row][post3]).value;
				array[row][post3+1] = array[row][post3];
			}
		}
			


		}
	

    /*
     * creates an array that contains every node from a row and uses it to create an array
     * @param row
     * @param nodearray-array of maze containing the Set class node
     * @param setnumber-value of set
     * @return setarray-array containing the nodes in a row with the same value
     */
	public Sets[] checkrowforsets(int row, Sets nodearray[][], int setnumber){
		int arraylen=0;
		
		for (int m=0; m<width;m++){
			
			if (nodearray[row][m].value == setnumber){
				arraylen ++;
			}
		}
		
		Sets[]setarray = new Sets[arraylen];
		int setarraylength = 0;
		for (int i=0;i<width;i++){
			if (nodearray[row][i].value == setnumber){
				setarray[setarraylength] = nodearray[row][i];
				setarraylength ++;

			}
		}
		return setarray;
	}

}
