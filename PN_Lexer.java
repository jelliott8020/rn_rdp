
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.Charset;

public class PN_Lexer implements Lexer, PN_Token {
	int lines;
	BufferedReader inFile;

	public PN_Lexer(String filename) {
		System.err.println("Opening: " + filename);
		try {
			inFile = new BufferedReader(
					new InputStreamReader(new FileInputStream(filename), Charset.forName("US-ASCII")));
		} catch (java.io.FileNotFoundException e) {
			System.out.println("ERROR: File not found: " + filename);
			System.exit(0);
		} catch (Exception e) {
			System.out.println("ERROR: Unknown cause.");
			e.printStackTrace();
			System.exit(0);
		}
		lines = 0;
	}

	private void error(String msg) {
		System.out.println("LexError(line " + getLineNum() + "): " + msg);
		System.exit(1);
	}

	public int getLineNum() {
		return lines;
	}

	public int nextToken() {
		char c = ' ';

		// strip any leading whitespace
		while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
			if (c == '\n')
				lines++;
			try {
				int i = inFile.read();
				if (i == -1)
					return EOP_TOK;
				c = (char) i;
			} catch (java.io.IOException e) {
				return EOP_TOK;
			}
		}

		// non-whitespace character!
		if ('(' == c)
			return LPAREN_TOK;
		else if (')' == c)
			return RPAREN_TOK;
		else if ('-' == c)
			return DASH_TOK;
		else if ('0' == c)
			return DIGIT_0_TOK;
		else if ('1' == c)
			return DIGIT_1_TOK;
		else if ('2' == c)
			return DIGIT_2_TOK;
		else if ('3' == c)
			return DIGIT_3_TOK;
		else if ('4' == c)
			return DIGIT_4_TOK;
		else if ('5' == c)
			return DIGIT_5_TOK;
		else if ('6' == c)
			return DIGIT_6_TOK;
		else if ('7' == c)
			return DIGIT_7_TOK;
		else if ('8' == c)
			return DIGIT_8_TOK;
		else if ('9' == c)
			return DIGIT_9_TOK;
		else {
			error("unknown token: " + c);
		}
		return ERROR_TOK;
	}

	// Poor man's test framework
	public static void main(String[] args) {
		/* Create test file! */
		String tmpTestFile = "tmpTestFile.txt";
		String[] lexemes = { "(", ")", "-", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int expectedTok = PN_Token.LPAREN_TOK;

		try {
			PrintWriter writer = new PrintWriter(tmpTestFile, "UTF-8");
			for (String lexeme : lexemes) {
				writer.println(lexeme);
			}
			writer.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

		/**************** TEST OUR LEXER **************************/
		System.out.println("Testing...");
		PN_Lexer lexer = new PN_Lexer(tmpTestFile);
		int tok;
		while ((tok = lexer.nextToken()) != EOP_TOK) {
			if (tok != expectedTok)
				lexer.error("line " + lexer.getLineNum() + ": expected " + expectedTok + " but got " + tok);
			expectedTok++;
		}
		System.out.println("....PASSED!");
		/**********************************************************/

		/* Remove test file. */
		(new File(tmpTestFile)).delete();
	}
}
