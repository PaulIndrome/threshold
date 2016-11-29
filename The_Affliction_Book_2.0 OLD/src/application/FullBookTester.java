package application;

public class FullBookTester {

	public static void main(String[] args) {
		int chapter = 1;

		FullBookFromXML book = new FullBookFromXML();

		System.out.println(book.getMainText(0));

		System.out
				.println("The possible choices of chapter " + chapter + " are\n" + book.getChoiceButtonText(chapter, 0)
						+ "\n" + book.getChoiceButtonText(chapter, 1) + "\n" + book.getChoiceButtonText(chapter, 2));

		System.out.println(
				"The appendices for the choices of chapter " + chapter + " are\n" + book.getChoiceAppendix(chapter, 0)
						+ "\n" + book.getChoiceAppendix(chapter, 1) + "\n" + book.getChoiceAppendix(chapter, 2));

	}

}
