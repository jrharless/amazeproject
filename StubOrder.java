package generation;

public class StubOrder implements Order{
	MazeConfiguration maze;
	int skill;
	Builder builder;
	boolean perfect;
	
	
	public StubOrder(int skill, Builder builder, boolean perfect) {
		this.skill = skill;
		this.builder = builder;
		this.perfect = perfect;
	}
    public MazeConfiguration getMaze(){
    	return maze;
    }
    
	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return skill;
	}

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return builder;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return perfect;
	}

	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		// TODO Auto-generated method stub
		maze = mazeConfig;
	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		
	}

}