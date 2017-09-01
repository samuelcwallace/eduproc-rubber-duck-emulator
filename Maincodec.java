package eduproc;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/* File to read file and execute codec
 * Can be run in one of two ways: 
 * >through the terminal, specifying file in, and file out, and the output code
 * >through public methods which take in a line delineated String and output the converted file
 * 
 */
public class Maincodec {
	CodecInterface codec;
	File in;
	File out;
	StringBuffer i;

	public String java2English(String input) {
		codec = new Pwacodec();
		String[] toEncode = input.split("\n");
		StringBuilder result = new StringBuilder();
		for (String s : toEncode) {
			result.append(codec.convert(s).get(1));
			result.append("\n");
		}
		return result.toString();

	}

	// extracts info FROM JAVA
	public List<String> javaExtract(String input) {
		// make a Pwacodec because it reads java
		codec = new Pwacodec();
		return codec.elements(input);
	}

	public List<String> pwaExtract(String input) {
		codec = new Javacodec();
		return codec.elements(input);
	}

	public String english2Java(String input) {
		codec = new Javacodec();
		String[] toDecode = input.split("\n");
		StringBuilder result = new StringBuilder();
		for (String s : toDecode) {
			System.out.println(input);
			result.append(codec.convert(s).get(1));
			result.append("\n");
		}
		System.out.println(result.toString());
		return result.toString();

	}

	public Maincodec(String fileInName, String fileOutName) {
		in = new File(fileInName);
		out = new File(fileOutName);
	}

	public Maincodec() {

	}

	void E2JFromFileTest() throws IOException {
		Scanner scan = new Scanner(in);
		PrintWriter pw = new PrintWriter(out);
		StringBuilder sb = new StringBuilder();
		while (scan.hasNextLine()) {
			sb.append(scan.nextLine() + "\n");
		}
		scan.close();
		pw.write(english2Java(sb.toString()));
		pw.close();
	}

	void J2EFromFileTest() throws IOException {
		Scanner scan = new Scanner(in);
		PrintWriter pw = new PrintWriter(out);
		StringBuilder sb = new StringBuilder();
		while (scan.hasNextLine()) {
			sb.append(scan.nextLine() + "\n");
		}
		scan.close();

		pw.write(java2English(sb.toString()));
		pw.close();
	}

	void readFromFile(String languageOut) throws FileNotFoundException {
		System.out.println("Reading file:");
		switch (languageOut.toUpperCase()) {
		case "JAVA":
			codec = new Javacodec();
			break;
		case "PWA":
			codec = new Pwacodec();
			break;
		default:
			System.out.println("unsupported language");
			return;
		}
		Scanner scan = new Scanner(in);
		PrintWriter pw = new PrintWriter(out);
		if (codec instanceof Javacodec) {
			pw.println("import java.util.Scanner;");
			pw.println("class pwaGenerated{");
		}
		System.out.println("got here!");
		while (scan.hasNext()) {
			String read = scan.nextLine();
			String processed = codec.convert(read).get(1);
			pw.println(processed);
			System.out.println(processed);
		}
		scan.close();

		if (codec instanceof Javacodec)
			pw.println("}");
		pw.close();
	}

	public static void main(String[] args) {
		String usageMessage = "usage: java maincodec input.pwa output.java" + "\n help: java maincodec help";
		String helpMessage = "Program to convert  program writing assistant files (.pwa) to Java files (.java)"
				+ "This is just a parser, not a compiler. The resulting .java file can be compiled as any other would be,"
				+ "For syntax in .pwa files, read parserspec.pwa";
		String targetLanguage = "JAVA";
		if (args.length < 2) {
			System.out.println(usageMessage);
		} else if (args[0].equalsIgnoreCase("HELP")) {
			System.out.println(helpMessage + "\n" + usageMessage);
		}

		else {
			if (args.length == 3)
				targetLanguage = args[2].toUpperCase();
			String fileInName = args[0];
			String fileOutName = args[1];

			Maincodec self = new Maincodec(fileInName, fileOutName);
			try {
				self.readFromFile(targetLanguage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}