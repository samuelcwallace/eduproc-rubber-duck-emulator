package eduproc;

import java.util.ArrayList;
import java.util.List;

public interface CodecInterface {
	void init();

	List<String> elements(String inString);

	
	
	ArrayList <String> convert(String in);

	String ifStatement(String condition);

	String comment (String in);	
	
	String elseIfStatement(String condition);

	String whileLoop(String condition);

	String objectCreation(String type, String name, String[] parameters);

	String elseStatement();

	String objectCreation(String type, String name);

	String forLoop(String counter, String condition, String action);

	String printStatement(String toPrint);

	String functionDeclare(String returnType, String functionName, String... params);

	String functionDeclare(String returnType, String functionName);

	String createVar(String type, String name);

	String setVar(String var, String value);

	String set2Fcn(String var, String fcn);

	String set2Fcn(String var, String fcn, String... params);

	// String compareVals (String arg1, String arg2);
	String convertCondition(String condition);

	String end();
}
