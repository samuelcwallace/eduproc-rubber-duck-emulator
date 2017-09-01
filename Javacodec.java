package eduproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*String pattern = "(\\w)(\\s+)([\\.,])";
System.out.println(EXAMPLE_TEST.replaceAll(pattern, "$1$3"));
*/
public class Javacodec extends AbstractCodec {

	
	//make a new Can of Worms called nutrimunch with parameters dirt
	String createGenericObjectRegex = "make a new (\\w+) of (\\w+) called (\\w+)?(?!\\s*with parameters)(.+)$";
	// for every integer i between 0 and 9
	String forRegex = "^\\s*for every integer (\\w+) between (\\d+) and (\\d+),?$";
	//for every Fish in theSea,
	String forEachRegex = "^\\s*for every (\\w+) in (\\w+),?$";
	//instructions on how to make a breadbox:
	String constructorRegex = "^\\s*instructions on how to make a (\\w+)[:,]?$";
	//this is a flammenwerfer:
	String classDefineRegex = "^\\s*this is a (\\w+)[:,]?$";
	//if b is less than 3, then
	String ifRegex = "^\\s*if (.+), then$";
	// otherwise, if i is greater than 7, then
	String elseIfRegex = "^\\s*otherise, if (.+), then$";
	// as long as a is equal to 42, then
	String whileRegex = "^\\s*as long as (\\w+) is (.+), then$";
	// otherise,
	String elseRegex = "^\\s*otherwise,$";
	// function called gale that returns int and takes int i, double d,
	String funParamRegex = "^\\s*function called (\\w+) that returns (\\w+) and takes (.*),?$";
	// main functio
	String mainFunRegex = "^\\s*main function";
	// call an int i
	String varDeclareRegex = "^\\s*call a(?:n?)?(\\w+) (\\w+)$";
	// function called lemon that returns String
	String funNoParamRegex = "^\\s*function called (\\w+) that returns (\\w+),?";
	// calculate the value of foo with function bar
	String setFcnNoParamRegex = "^\\s*calculate the value of (\\w+) with function (.+)$";
	// calculate the value of bar with function milk with parameters i, a
	String setFcnRegex = "^\\s*calculate the value of (\\w+) with function (.+) with parameters(.+)$";
	// make a new Scanner called scan with parameters System.in
	String objectCreateParamRegex = "^\\smake a new (\\w+) called (\\w+) with parameters (.+)$";
	// make a new Object called Oliver
	String objectNoParamCreateRegex = "^\\s*make a new (\\w+) called (\\w+)$";
	// display the value of i
	String printRegex = "\\s*display (?:the value of ?)?(.+)";
	// set foo to 20
	String setRegex = "^\\s*set (\\w+) to (.+)$";
	// end
	String endRegex = "^\\s*end$";
	// say hello world!
	String sayRegex = "^\\s*say (.+)$";
	// NOTE: Buy milk, cheese
	String commentRegex = "^\\s*NOTE:(.+)$";
	//call the function begin
	String callRegexNP = "^\\s*call the function (\\w+)";
	//call the function move with parameters north, west
	String callRegex = "^\\s*call the function (\\w+) with parameters (.+)$";
	
	public void init() {
		//make a new Can of Worms called nutrimunch with parameters dirt
		String createGenericObjectRegex = "make a new (\\w+) of (\\w+) called (\\w+)?(?!\\s*with parameters)(.+)$";
		// for every integer i between 0 and 9
		String forRegex = "^\\s*for every integer (\\w+) between (\\d+) and (\\d+),?$";
		//for every Fish in theSea,
		String forEachRegex = "^\\s*for every (\\w+) in (\\w+),?$";
		//instructions on how to make a breadbox:
		String constructorRegex = "^\\s*instructions on how to make a (\\w+)[:,]?$";
		//this is a flammenwerfer:
		String classDefineRegex = "^\\s*this is a (\\w+)[:,]?$";
		//if b is less than 3, then
		String ifRegex = "^\\s*if (.+), then$";
		// otherwise, if i is greater than 7, then
		String elseIfRegex = "^\\s*otherise, if (.+), then$";
		// as long as a is equal to 42, then
		String whileRegex = "^\\s*as long as (\\w+) is (.+), then$";
		// otherise,
		String elseRegex = "^\\s*otherwise,$";
		// function called gale that returns int and takes int i, double d,
		String funParamRegex = "^\\s*function called (\\w+) that returns (\\w+) and takes (.*),?$";
		// main functio
		String mainFunRegex = "^\\s*main function";
		// call an int i
		String varDeclareRegex = "^\\s*call a(?:n?)?(\\w+) (\\w+)$";
		// function called lemon that returns String
		String funNoParamRegex = "^\\s*function called (\\w+) that returns (\\w+),?";
		// calculate the value of foo with function bar
		String setFcnNoParamRegex = "^\\s*calculate the value of (\\w+) with function (.+)$";
		// calculate the value of bar with function milk with parameters i, a
		String setFcnRegex = "^\\s*calculate the value of (\\w+) with function (.+) with parameters(.+)$";
		// make a new Scanner called scan with parameters System.in
		String objectCreateParamRegex = "^\\smake a new (\\w+) called (\\w+) with parameters (.+)$";
		// make a new Object called Oliver
		String objectNoParamCreateRegex = "^\\s*make a new (\\w+) called (\\w+)$";
		// display the value of i
		String printRegex = "\\s*display (?:the value of ?)?(.+)";
		// set foo to 20
		String setRegex = "^\\s*set (\\w+) to (.+)$";
		// end
		String endRegex = "^\\s*end$";
		// say hello world!
		String sayRegex = "^\\s*say (.+)$";
		// NOTE: Buy milk, cheese
		String commentRegex = "^\\s*NOTE:(.+)$";
		//call the function begin
		String callRegexNP = "^\\s*call the function (\\w+)";
		//call the function move with parameters north, west
		String callRegex = "^\\s*call the function (\\w+) with parameters (.+)$";
		
		regexMap = new HashMap<String, String>();
		patternMap = new HashMap <String,Pattern>();

		regexMap.put("for each", forEachRegex);
		regexMap.put("create generic object", createGenericObjectRegex);
		regexMap.put("constructor",constructorRegex);
		regexMap.put("define class", classDefineRegex);
		regexMap.put("for", forRegex);
		regexMap.put("if", ifRegex);
		regexMap.put("else if", elseIfRegex);
		regexMap.put("while", whileRegex);
		regexMap.put("else", elseRegex);
		regexMap.put("declare function", funParamRegex);
		regexMap.put("declare function np", funNoParamRegex);
		regexMap.put("main function", mainFunRegex);
		regexMap.put("var declare", varDeclareRegex);
		regexMap.put("fun no param", funNoParamRegex);
		regexMap.put("set to function", setFcnRegex);
		regexMap.put("set to function np", setFcnNoParamRegex);
		regexMap.put("object creation", objectCreateParamRegex);
		regexMap.put("object creation np", objectNoParamCreateRegex);
		regexMap.put("print", printRegex);
		regexMap.put("set", setRegex);
		regexMap.put("end", endRegex);
		regexMap.put("comment", commentRegex);
		regexMap.put("say", sayRegex);
		
		for (String s : regexMap.keySet()) {
			System.out.println("adding regex!");
			System.out.println(s);
			System.out.println(regexMap);
			System.out.println(patternMap);
			System.out.println();
			if (regexMap.get(s)==null) System.out.println("no!");
			patternMap.put(s, Pattern.compile(regexMap.get(s)));
		}
	}

	public List<String> elements(String inString) {
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

	Pattern setPattern = Pattern.compile(setRegex);

	@Override
	public ArrayList<String> convert(String inString) {
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
			return bottle (FAIL,"/* this makes no sense:\n" + inString+"*/", inString,null);
		}
		switch (match) {
		case "for":
			return bottle (SUCCESS,forLoop(mData.group(1), mData.group(2), mData.group(3)), inString, match);
		case "print":
			return bottle (SUCCESS,printStatement(mData.group(1)),inString,match );
		case "main function":
			return bottle (SUCCESS,mainFunction(), inString, match);
		case "if":
			return bottle (SUCCESS,ifStatement(mData.group(1)), inString, match );
		case "say":
			return bottle (SUCCESS,sayStatement(mData.group(1)), inString, match );
		case "else if":
			return bottle (SUCCESS,elseIfStatement(mData.group(1)), inString, match );
		case "while":
			return bottle (SUCCESS,whileLoop(mData.group(1)), inString, match );
		case "var declare":
			return bottle (SUCCESS,createVar(mData.group(1), mData.group(2)), inString, match );
		case "object creation":
			return bottle (SUCCESS,objectCreation(mData.group(1), mData.group(2), mData.group(3).split(" ")), inString, match );
		case "declare function":
			return bottle (SUCCESS,functionDeclare(mData.group(2), mData.group(1), mData.group(3).split(" ")), inString, match );
		case "declare function np":
			return bottle (SUCCESS,functionDeclare(mData.group(2), mData.group(1)), inString, match );
		case "object creation np":
			return bottle (SUCCESS,objectCreation(mData.group(1), mData.group(2)), inString, match );
		case "set to fcn np":
			return bottle (SUCCESS,set2Fcn(mData.group(1), mData.group(2)), inString, match );
		case "set":
			return bottle (SUCCESS,setVar(mData.group(1), mData.group(2)), inString, match );
		case "else":
			return bottle (SUCCESS,"else {", inString, match );
		case "end":
			return bottle (SUCCESS,"}", inString, match );
		case "comment":
			return bottle (SUCCESS, comment(mData.group(1)), inString, match );
		default:
			return null;
		}

	}

	@Override
	public String ifStatement(String condition) {
		String convertedCondition = convertCondition(condition);
		String result = "if (" + convertedCondition + "){";
		return result;
	}

	@Override
	public String whileLoop(String condition) {

		String convertedCondition = convertCondition(condition);
		String result = "while (" + convertedCondition + "){";
		return result;
	}

	@Override
	public String objectCreation(String type, String name, String[] parameters) {
		String result = type + " " + name + "= new " + type + "(" + String.join(",", parameters) + ");";
		return result;
	}

	public String objectCreation(String type, String name) {
		String result = type + " " + name + ";";
		return result;
	}

	@Override
	public String forLoop(String counter, String limit, String action) {
		String result = ("for (int " + counter + "=0; " + counter + "<" + (Integer.parseInt(limit)-1) + "; " + counter + "++){");
		return result;
	}

	@Override
	public String printStatement(String toPrint) {
		String result = "System.out.println(" + toPrint + ");";
		return result;
	}

	public String sayStatement(String toSay) {
		String result = "System.out.println (\"" + toSay + "\");";
		return result;
	}

	@Override
	public String functionDeclare(String returnType, String functionName, String... params) {
		String result = returnType + " " + functionName + "( " + params + "){";
		return result;
	}

	@Override
	public String createVar(String type, String name) {
		return type + " " + name + ";";
	}

	@Override
	public String setVar(String var, String value) {
		return var + "=" + value + ";";
	}

	public String mainFunction() {
		return "public static void main (String... args){";
	}

	// @Override
	// public String compareVals(String arg1, String arg2) {
	// // TODO Auto-generated method stub
	// String result =
	// }
	@Override
	public String convertCondition(String condition) {
		String comparisonRegex = "(\\w+) is (\\w+ \\w+) (\\w+)";
		Pattern comparePattern = Pattern.compile(comparisonRegex);
		Matcher compareMatcher = comparePattern.matcher(condition);
		if (compareMatcher.matches()) {
			String relation = compareMatcher.group(2);
			switch (relation.toUpperCase()) {
			case "LESS THAN":
				return compareMatcher.group(1) + "<" + compareMatcher.group(3);

			case "GREATER THAN":
				return compareMatcher.group(1) + ">" + compareMatcher.group(3);
			case "EQUAL TO":
				return compareMatcher.group(1) + "==" + compareMatcher.group(3);
			default:
				break;
			}

		}
		return condition;
	}

	@Override
	public String end() {
		return "}";
	}

	@Override
	public String functionDeclare(String returnType, String functionName) {
		// TODO Auto-generated method stub
		return returnType + " " + functionName + "(){";

	}

	@Override
	public String elseIfStatement(String condition) {
		return "else if " + convertCondition(condition) + " {";
	}

	@Override
	public String set2Fcn(String var, String fcn) {
		return var + " = " + fcn + "();";
	}

	@Override
	public String set2Fcn(String var, String fcn, String... params) {
		return var + " = " + fcn + " (" + String.join(",", params) + ");";
	}

	@Override
	public String elseStatement() {
		// TODO Auto-generated method stub
		return "else{";
	}

	public Javacodec() {
		super();
	}

	@Override
	public String comment(String in) {
		// TODO Auto-generated method stub
		return "//"+in;
	}
}
