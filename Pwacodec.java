package eduproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pwacodec extends AbstractCodec {
	int indents;
	int lastChange;

	public void init() {

		regexMap.put("call function", "^\\s*[^.]*\\.?(\\w+)\\((.+)\\);$");
		regexMap.put("for each","for \\((\\w+) (\\w+) ?: ?(\\.+)\\)\\{$");
		regexMap.put("for", "^\\s*for \\(\\w+ (\\w+) ?= ?(\\d+); ?\\w+ ?< ?(\\d+); ?\\w\\+\\+\\) ?\\{");
		regexMap.put("if", "^\\s*if\\s*?\\((.+)\\)\\{$");
		regexMap.put("while", "^\\s*while\\s*\\((.+)\\)\\{$");
		regexMap.put("else if", "^\\s*else if\\s*?\\((.+)\\)\\{$");
		regexMap.put("define function", "^\\s*(\\w+) (\\w+) ?\\(([\\w\\s]*)\\) ?\\{");
		regexMap.put("set to function", "^\\s*(\\w+) ?= ? (.+) ?\\((.*)\\);");
		regexMap.put("create object", "^\\s*(\\w+) (\\w+) ?= ?new \\w+ ?\\((.*)\\);");
		regexMap.put("declare variable", "^\\s*(\\w+) (\\w+);$");
		regexMap.put("display value", "^\\s*System\\.out\\.println ?\\((.+)\\);");
		regexMap.put("set variable", "^\\s*(.+) ?= ?(?!\\s*new)(.+);");
		regexMap.put("end", "^\\s*\\}$");
		regexMap.put("else", "^\\s*else ?\\{$");
		regexMap.put("comment", "^\\s*//.+$");
		regexMap.put("main method", "^\\s*public static void main \\(.+\\)\\{$");
		for (String s : regexMap.keySet()) {
			System.out.println("added pattern!");
			patternMap.put(s, Pattern.compile(regexMap.get(s)));
		}
		indents = 0;
	}

	@Override
	public ArrayList <String> convert(String inString) {
		HashMap<String, Matcher> matcherMap = new HashMap<String, Matcher>();
		for (String s : patternMap.keySet()) {
			matcherMap.put(s, patternMap.get(s).matcher(inString.toLowerCase()));
			System.out.println("added matcher!");
			System.out.println(matcherMap.get(s));
		}
		String match = null;
		Matcher mData = null;
		// for, if, while, else if, define function, set to function, create
		// object, declare variable, display value,
		// print string, set variable, end, else

		for (String s : matcherMap.keySet()) {
			System.out.println(s);
			if (matcherMap.get(s).matches()) {
				System.out.println(s);
				System.out.println(matcherMap.get(s));
				match = s;
				mData = matcherMap.get(s);
				break;
			}
		}
		if (match == null || mData == null) {
			return bottle (FAIL, "NOTE: THIS DOESN'T MAKE SENSE\n" + "NOTE:" + inString, inString, match);
		}
		if (match.equals("end"))
			indents--;
		return bottle (SUCCESS, indent(indents) + matchSwitch(mData, match, inString), inString, match);

	}

	String matchSwitch(Matcher mData, String match, String inString) {
		switch (match) {
		case "comment":
			return comment (mData.group(1));
		case "for":
			indents++;
			return forLoop(mData.group(1), mData.group(2), mData.group(3));

		case "if":
			indents++;
			return ifStatement(convertCondition(mData.group(1)));

		case "while":
			indents++;
			return whileLoop(convertCondition(mData.group(1)));

		case "else if":
			indents++;
			return elseIfStatement(convertCondition(mData.group(1)));

		case "define function":
			indents++;
			return functionDeclare(mData.group(1), mData.group(2), mData.group(3));

		case "set to function":

			return set2Fcn(mData.group(1), mData.group(2), mData.group(3));
		case "create object":
			return objectCreation(mData.group(1), mData.group(2), new String[] { mData.group(3) });

		case "declare variable":
			return createVar(mData.group(1), mData.group(2));
		case "main method":
			indents++;
			return "main method";
		case "display value":
			return printStatement(mData.group(1));

		case "print string":
			return printStatement(mData.group(1));

		case "set variable":
			return setVar(mData.group(1), mData.group(2));

		case "end":
			return end();
		

		case "else":
			return elseStatement();
		default:
			break;
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ifStatement(String condition) {
		// TODO Auto-generated method stub
		return "if " + condition + ", then";
	}

	@Override
	public String elseIfStatement(String condition) {
		// TODO Auto-generated method stub
		return "otherwise, if " + condition + ", then";
	}

	@Override
	public String whileLoop(String condition) {
		// TODO Auto-generated method stub
		return "as long as " + condition + ", then";
	}

	@Override
	public String objectCreation(String type, String name, String[] parameters) {
		// TODO Auto-generated method stub
		return "make a new " + type + " called " + name + " with parameters " + parameters[0];
	}

	@Override
	public String objectCreation(String type, String name) {
		// TODO Auto-generated method stub
		return "make a new " + type + " called " + name;
	}

	@Override
	public String forLoop(String name, String min, String max) {
		// TODO Auto-generated method stub
		return "for every integer " + name + " between " + min + " and " + max + ",";
	}

	@Override
	public String printStatement(String toPrint) {
		// TODO Auto-generated method stub
		return "display " + toPrint;
	}

	@Override
	public String functionDeclare(String returnType, String functionName, String... params) {
		// TODO Auto-generated method stub
		return "function called " + functionName + " that returns " + returnType + " with parameters"
				+ String.join(",", params) + ",";
	}

	@Override
	public String functionDeclare(String returnType, String functionName) {
		// TODO Auto-generated method stub
		return "function called " + functionName + " that returns " + returnType + ",";
	}

	@Override
	public String createVar(String type, String name) {
		// TODO Auto-generated method stub
		return "call a " + type + " " + name;
	}

	@Override
	public String setVar(String var, String value) {
		// TODO Auto-generated method stub
		return "set " + var + " to " + value;
	}

	@Override
	public String set2Fcn(String var, String fcn) {
		// TODO Auto-generated method stub
		return "calculate the value of " + var + " with function " + fcn;
	}

	@Override
	public String set2Fcn(String var, String fcn, String... params) {
		// TODO Auto-generated method stub
		return "calculate the value of " + var + " with function " + fcn + " with parameters "
				+ String.join(",", params);
	}

	@Override
	public String convertCondition(String condition) {
		// TODO Auto-generated method stub
		if (condition.contains("<=")) {
			return condition.replace("<=", " is less than or equal to ");
		} else if (condition.contains("<")) {
			return condition.replace("<", " is less than ");
		} else if (condition.contains("==")) {
			return condition.replace("==", " is equal to ");
		} else if (condition.contains(">")) {
			return condition.replace(">", " is greater than ");
		} else if (condition.contains(">=")) {
			return condition.replace(">=", " is greater than or equal to");
		}

		return null;
	}

	@Override
	public String end() {
		// TODO Auto-generated method stub
		return "end";
	}

	@Override
	public String elseStatement() {
		// TODO Auto-generated method stub
		return "otherwise,";
	}

	@Override
	public List<String> elements(String inString) {
		// TODO Auto-generated method stub
		HashMap<String, Matcher> matcherMap = new HashMap<String, Matcher>();
		for (String s : patternMap.keySet()) {
			matcherMap.put(s, patternMap.get(s).matcher(inString));
		}
		String match = null;
		Matcher mData = null;
		// for, if, while, else if, define function, set to function, create
		// object, declare variable, display value,
		// print string, set variable, end, else

		for (String s : matcherMap.keySet()) {
			System.out.println(s);
			if (matcherMap.get(s).matches()) {
				System.out.println(s);
				System.out.println(matcherMap.get(s));
				match = s;
				mData = matcherMap.get(s);
				break;
			}
		}
		if (match == null) {
			return listPacker("syntax error", inString);
		}
		switch (match) {
		case "for":
			return listPacker(match, mData.group(1), mData.group(2), mData.group(3));
		case "print":
			return listPacker(match, mData.group(1));
		case "main function":
			return listPacker(match);
		case "if":
			return listPacker(match, mData.group(1));
		case "say":
			return listPacker(match, mData.group(1));
		case "else if":
			return listPacker(match, mData.group(1));
		case "while":
			return listPacker(match, mData.group(1));
		case "var declare":
			return listPacker(match, mData.group(1), mData.group(2));
		case "object creation":
			return listPacker(match, mData.group(1), mData.group(2), mData.group(3));
		case "declare function":
			return listPacker(match, mData.group(2), mData.group(1), mData.group(3));
		case "declare function np":
			return listPacker(match, mData.group(2), mData.group(1));
		case "object creation np":
			return listPacker(match, mData.group(1), mData.group(2));
		case "set to fcn np":
			return listPacker(match, mData.group(1), mData.group(2));
		case "set":
			return listPacker(match, mData.group(1), mData.group(2));
		case "else":
			return listPacker(match);
		case "end":
			return listPacker(match);
		default:
			return null;

		}
	}

	public Pwacodec() {
		super();
	}

	@Override
	public String comment(String in) {
		// TODO Auto-generated method stub
		return "NOTE:" +in;
	}
}
