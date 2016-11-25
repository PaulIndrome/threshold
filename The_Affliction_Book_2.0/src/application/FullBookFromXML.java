package application;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.image.Image;;

public class FullBookFromXML {

	private NodeList AllChapters;
	private NodeList flourishes;
	private String fs = System.getProperty("file.separator");
	private Document doc;

	/*
	 * This iteration of XML chapter conversion builds the full book out of the
	 * XML file and means to make better use of common DOM model methods. It is
	 * intended to ease control of what chapters are selected by writing getters
	 * with more specific arguments. Images should no longer be instantiated via
	 * a path in the XML file, instead loaded only at specific points in the
	 * programmatic run.
	 * 
	 */
	public FullBookFromXML() {
		DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
			doc = docBuild.parse("material" + fs + "affliction_book.xml");
			doc.normalize();

			this.AllChapters = doc.getElementsByTagName("chapter");

			this.flourishes = doc.getElementsByTagName("flourish");

		} catch (SAXException | IOException | ParserConfigurationException | RuntimeException e) {
			e.printStackTrace();
		}

	}

	public String getMainText(int chapnumber) {
		StringBuilder sb = new StringBuilder();
		for (char c : AllChapters.item(chapnumber).getAttributes().getNamedItem("maintext").getNodeValue()
				.toCharArray()) {
			if (c == '#')
				sb.append(System.lineSeparator());
			else
				sb.append(c);
		}

		return sb.toString();
	}

	public String getChoiceButtonText(int chapnumber, int choicenumber) {
		NodeList choices = AllChapters.item(chapnumber).getChildNodes();
		String[] choiceTexts = new String[3];
		int s = 0;
		for (int n = 0; n < choices.getLength(); n++) {
			if (choices.item(n).getNodeType() == Node.ELEMENT_NODE) {
				choiceTexts[s] = choices.item(n).getAttributes().getNamedItem("text").getNodeValue();
				s++;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (char c : choiceTexts[choicenumber].toCharArray()) {
			if (c == '#')
				sb.append(System.lineSeparator());
			else
				sb.append(c);
		}
		return sb.toString();
	}

	public String getChoiceAppendix(int chapnumber, int choicenumber) {
		NodeList choices = AllChapters.item(chapnumber).getChildNodes();
		String[] appendixTexts = new String[3];
		int s = 0;
		for (int n = 0; n < choices.getLength(); n++) {
			if (choices.item(n).getNodeType() == Node.ELEMENT_NODE) {
				appendixTexts[s] = choices.item(n).getAttributes().getNamedItem("appendix").getNodeValue();
				s++;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (char c : appendixTexts[choicenumber].toCharArray()) {
			if (c == '#')
				sb.append(System.lineSeparator());
			else
				sb.append(c);
		}
		return sb.toString();
	}

	public String getFlourish(int flourishnumber) {
		if (flourishnumber > flourishes.getLength()) {
			System.out.println("Please choose a flourish number between 0 and " + flourishes.getLength());
			return flourishes.item(0).getTextContent();
		}
		return flourishes.item(flourishnumber).getTextContent();
	}

	public Image getChapterCover(int chapnumber) {
		File coverFolder = new File("covers");
		return new Image(coverFolder.list()[chapnumber]);
	}

	public Image getChapterMap(int chapnumber) {
		File mapFolder = new File("maps");
		return new Image(mapFolder.list()[chapnumber]);
	}
	
	public int getNextChapter(int chapnumber, int choicenumber){
		NodeList choices = doc.getElementsByTagName("choice");
		return Integer.parseInt(choices.item(chapnumber*3 + choicenumber).getAttributes().getNamedItem("nextchapter").getNodeValue());
	}
	
	public int getChoiceScore(int chapnumber, int choicenumber){
		NodeList choices = doc.getElementsByTagName("choice");
		return Integer.parseInt(choices.item(chapnumber*3 + choicenumber).getAttributes().getNamedItem("score").getNodeValue());
	}
	
	public String getChapterTitle(int chapnumber){
		return AllChapters.item(chapnumber).getAttributes().getNamedItem("title").getNodeValue();
	}
}
