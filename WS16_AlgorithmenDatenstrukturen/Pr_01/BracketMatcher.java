import java.util.ArrayList;
import java.util.NoSuchElementException;

import vorgaben.praktikum1.BracketPairMatcher;
import vorgaben.praktikum1.BracketStatus;

public class BracketMatcher implements BracketPairMatcher {

	private final char[] openingArray = new char[] { '(', '[', '{', '<' };
	private final char[] closingArray = new char[] { ')', ']', '}', '>' };

	private static ArrayList<Character> opening = new ArrayList<Character>();
	private static ArrayList<Character> closing = new ArrayList<Character>();

	@Override
	public BracketStatus matchMultiple(String arg0) {
		for (char o : openingArray) {
			opening.add(o);
		}
		for (char c : closingArray) {
			closing.add(c);
		}

		/*Stack<Character> brackets = new Stack<Character>();
		for (char x : arg0.toCharArray()) {
			System.out.println("I'm in the foreach with the character " + x);
			if (x == '(' || x == '['||x=='{'|| x=='<') {
				System.out.println("Opening bracket found!");
				brackets.push(x);
			} else if (x == ')' || x == ']'||x=='}'|| x=='>') {
				System.out.println("Closing bracket found!");
				try {
					if (matchSingle(brackets.pop(), x)) {
						System.out.println("Klammer aufgelöst");
					} else {
						System.out.println("Mismatch");
						return BracketStatus.Mismatch;
					}
				} catch (NoSuchElementException e) {
					System.out.println("Too many closing!");
					return BracketStatus.TooManyClosing;
				}

			}
		}*/
		
		Stack<Character> brackets = new Stack<Character>();
		for (char x : arg0.toCharArray()) {
			//System.out.println("I'm in the foreach with the character " + x);
			if (opening.contains(x)) {
				//System.out.println("Opening bracket found!");
				brackets.push(x);
			} else if (closing.contains(x)) {
				//System.out.println("Closing bracket found!");
				try {
					if (matchSingle(brackets.pop(), x)) {
						//System.out.println("Klammer aufgelöst");
					} else {
						//System.out.println("Mismatch");
						return BracketStatus.Mismatch;
					}
				} catch (NoSuchElementException e) {
					//System.out.println("Too many closing!");
					return BracketStatus.TooManyClosing;
				}

			}
		}
		
		if (brackets.isEmpty()) {
			//System.out.println("Ok");
			return BracketStatus.Ok;
		} else {
			//System.out.println("Missing Closing");
			return BracketStatus.MissingClosing;
		}
	}

	@Override
	public boolean matchSingle(char arg0, char arg1) {
		switch (arg0) {
		case '(':
			return (arg1 == ')');
		case '[':
			return (arg1 == ']');
		case '{':
			return (arg1 == '}');
		default:
			return false;
		}
	}

}
