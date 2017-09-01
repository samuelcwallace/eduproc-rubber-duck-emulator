package eduproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.HashMap;

public abstract class AbstractCodec implements CodecInterface {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	public static final String ERROR = "ERROR";

	HashMap <String, String> regexMap;
	HashMap <String, Pattern> patternMap;

	public AbstractCodec(){
		init();
	}
	List <String> listPacker (String...strings){
		ArrayList <String> toReturn = new ArrayList<String>();
		for (String s:strings){
			toReturn.add(s);
		}
		return toReturn;
	}
	public String indent(int indents){
		String result = "";
		for (int i = 0; i<indents; i++)
			result += "\t";
		return result;
	}

	public ArrayList<String> bottle ( String status, String consequent, String antecedent, String informant ) {
		return new ArrayList<String>( Arrays.asList( new String[ ] { status, consequent, informant, antecedent } ) );
	}
	
}
