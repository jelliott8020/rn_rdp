
public abstract class RDP implements Token {

	private Lexer lexer;
	protected int currToken;

	public RDP(Lexer lexer) {
		this.lexer = lexer;
	}

	public void parse() {
		currToken = lexer.nextToken();
		startSymbol();

		if (currToken == EOP_TOK)
			System.out.println("Congratulations! File parses successfully.");
		else
			error("Extraneous input in file.");
	}

	protected void error(String message) {
		System.out.println("ERROR on line " + lexer.getLineNum() + ": " + message);
		System.exit(0);
	}

	protected void error() {
		error("No message provided.");
	}

	protected void match(int expectedToken) {
		if (currToken == expectedToken) {
			currToken = lexer.nextToken();
		} else {
			error("Expected token " + expectedToken + " but found token " + currToken + ".");
		}
	}

	protected void lambda() {
		// lambda function "does nothing" ;-)
	}

	protected boolean youMustFinish(String msg) {
		if (msg != null) {
			throw new RuntimeException("YOU MUST FINISH " + msg);
		}
		return false;
	}

	/*********** USER MUST IMPLEMENT THIS! (and subsequent grammar) *******/
	protected abstract void startSymbol();
}
