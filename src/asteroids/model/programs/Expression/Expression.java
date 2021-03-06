package asteroids.model.programs.Expression;
import asteroids.model.programs.*;
import asteroids.model.*;

public abstract class Expression extends Code {
	

		protected Expression(int line,int column, Program program, Type type) {
			super(line,column, program);
			setType(type);
		}
		
		protected Expression(int line, int column, Program program) {
			super(line,column, program);
		}
		
		
		public Type getType() {
			return type;
		}
		
		public void setType(Type type) {
			this.type = type;
		}
		
		private Type type;

		public abstract Type getValue();
}


