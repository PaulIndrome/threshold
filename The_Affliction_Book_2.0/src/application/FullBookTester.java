package application;

public class FullBookTester {

	public static void main(String[] args) {
		int chapter = 1;

		FullBookFromXML book = new FullBookFromXML();

		System.out.println(book.getNextChapter(chapter, 0));
		System.out.println(book.getNextChapter(chapter, 1));
		System.out.println(book.getNextChapter(chapter, 2));
	}

}
