package application;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.image.Image;;

public class XMLchapter {

	private String number;
	private String title;
	private String maintext;
	private String coverpath;
	private String mapsteppath;
	private Image cover;
	private Image mapstep;
	private Element choice1;
	private Element choice2;
	private Element choice3;
	private Element flourishes;

	public XMLchapter(String chapnumber) {
		this.number = chapnumber;
		DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
			Document doc = docBuild.parse("material//affliction_book.xml");
			doc.normalize();
			XPath book = XPathFactory.newInstance().newXPath();

			// set attributes for chapter
			this.title = book.compile("/book/chapter[@id='" + chapnumber + "']/@title").evaluate(doc);
			this.maintext = book.compile("/book/chapter[@id='" + chapnumber + "']/@maintext").evaluate(doc);
			this.cover = new Image(
					new File("application.covers//" + book.compile("/book/chapter[@id='" + chapnumber + "']/@cover").evaluate(doc)).toURI()
							.toString());
			this.mapstep = new Image(
					new File("application.maps//" + book.compile("/book/chapter[@id='" + chapnumber + "']/@mapstep").evaluate(doc)).toURI()
							.toString());
			this.coverpath = book.compile("/book/chapter[@id='" + chapnumber + "']/@cover").evaluate(doc);
			this.mapsteppath = book.compile("/book/chapter[@id='" + chapnumber + "']/@mapstep").evaluate(doc);

			this.flourishes = (Element) book.compile("/book/flourish").evaluate(doc, XPathConstants.NODE);

			// put chapter choices into Elements by way of a NodeList of given
			// chapter's children
			NodeList appendices = (NodeList) book.compile("/book/chapter[@id='" + chapnumber + "']/choice")
					.evaluate(doc, XPathConstants.NODESET);
			this.choice1 = (Element) appendices.item(0);
			this.choice2 = (Element) appendices.item(1);
			this.choice3 = (Element) appendices.item(2);

		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException
				| RuntimeException e) {
			e.printStackTrace();
		}

	}

	/*
	 * The TextOnly constructor is needed to test the class using Chaptertester
	 * without running into a RunTimeException because the images aren't
	 * internally loaded yet
	 */
	public XMLchapter(String chapnumber, boolean textonly) {
		this.number = chapnumber;
		DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
			Document doc = docBuild.parse("src//application//affliction_book.xml");
			doc.normalize();
			XPath book = XPathFactory.newInstance().newXPath();

			// set attributes for chapter
			this.title = book.compile("/book/chapter[@id='" + chapnumber + "']/@title").evaluate(doc);
			this.maintext = book.compile("/book/chapter[@id='" + chapnumber + "']/@maintext").evaluate(doc);
			this.coverpath = book.compile("/book/chapter[@id='" + chapnumber + "']/@cover").evaluate(doc);
			this.mapsteppath = book.compile("/book/chapter[@id='" + chapnumber + "']/@mapstep").evaluate(doc);

			this.flourishes = (Element) book.compile("/book/flourish").evaluate(doc, XPathConstants.NODE);

			// put chapter choices into Elements by way of a NodeList of given
			// chapter's children
			NodeList appendices = (NodeList) book.compile("/book/chapter[@id='" + chapnumber + "']/choice")
					.evaluate(doc, XPathConstants.NODESET);
			this.choice1 = (Element) appendices.item(0);
			this.choice2 = (Element) appendices.item(1);
			this.choice3 = (Element) appendices.item(2);

		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
		}

	}

	public String getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}

	public String getMaintext() {
		return maintext;
	}

	public Image getCover() {
		return cover;
	}

	public Image getMapstep() {
		return mapstep;
	}

	public String toString() {
		/*
		 * Preparing a string of all flourishes available
		 */
		String allflourishes = flourishes.getAttribute("flourish1");
		for (int i = 2; i <= flourishes.getAttributes().getLength(); i++) {
			allflourishes += (", " + flourishes.getAttribute("flourish" + i));
		}
		return "Chapter no." + number + "\nTitle: " + title + "\nMaintext: " + maintext + "\nCover path: " + coverpath
				+ "\nMapstep path: " + mapsteppath + "\nChoice 1 ID: " + choice1.getAttribute("id") + "\nChoice 2 ID: "
				+ choice2.getAttribute("id") + "\nChoice 3 ID: " + choice3.getAttribute("id") + "\nFlourishes: "
				+ allflourishes;
	}


	public Element getChoice(int choice) {
		if (choice == 1) {
			return choice1;
		} else if (choice == 2) {
			return choice2;
		} else if (choice == 3) {
			return choice3;
		} else {
			new NoSuchElementException("Invalid choice.").printStackTrace();
			return null;
		}
	}

	// the flourish is an ascii text decoration
	public Element getFlourishes() {
		return flourishes;
	}
}
