package asteroids.model.programs.Statement;
import asteroids.model.programs.*;

public abstract class Statement extends Code {
	
	public Statement(int line, int column) {
		super (line, column);
	}
	
	public abstract void execute();

}