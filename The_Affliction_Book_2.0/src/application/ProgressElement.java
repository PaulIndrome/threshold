package application;

public class ProgressElement implements Comparable<ProgressElement>{

	private int chapter;
	private int choice;
	private int score;
	private String title;
	private String choicetext;
	
	
	public ProgressElement(int chapter, int choice, int score, String title, String choicetext){
		this.chapter = chapter;
		this.choice = choice;
		this.score = score;
		this.title = title;
		this.choicetext = choicetext;
	}
	
	public int getChapter() {
		return chapter;
	}

	public int getChoice() {
		return choice;
	}

	public int getScore() {
		return score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChoicetext() {
		return choicetext;
	}

	public void setChoicetext(String choicetext) {
		this.choicetext = choicetext;
	}
	
	public String toString(){
		return "Chapter: " + chapter + ". Title: " + title + ". Choice: " + choice + ". ChoiceText: " + choicetext + ". Score: " + score;
	}


	@Override
	public int compareTo(ProgressElement o) {
		return this.chapter-o.chapter;
	}
	
}
