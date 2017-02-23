package generation;

public class settrack{
	    private int value= 0;
		private int height;
		private int width;
		private int array[][];
		
		
		
		public settrack(int widthp, int heightp){
			width =widthp;
			height =heightp;
			for (int r = 0; r< width; r++){
				for (int c = 0; c< height; c++){
					array[r][c] = 0;
				      }
		        }

        }
		
		public int getvalue(int r, int c){
			return array[r][c];
		}	
		public void setvalue(int r, int c){
			value = value +1;
		    array[r][c] = value;
		}
		
}
