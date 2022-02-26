package application;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class XMLHandler {
	//parses the xml file and returns a document object
	public static Document docCreator() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("note-entries.xml");
			return document;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//modifies the Document to include the new task
	public static void addEntry(String title, String notes) {
		//getting the Document
		Document document = docCreator();
		
		if(document == null) {
			System.out.println("OOF"); //debug
			return;
		}
		
		Element root = document.getDocumentElement();
		Element entryElement = document.createElement("entry");
		Element titleElement = document.createElement("title");
		Element notesElement = document.createElement("notes");
		
		Text titleText = document.createTextNode(title);
		Text notesText = document.createTextNode(notes);
		
		titleElement.appendChild(titleText);
		notesElement.appendChild(notesText);
		
		entryElement.appendChild(titleElement);
		entryElement.appendChild(notesElement);
		
		root.appendChild(entryElement);
		//calls xmlWriter() and passes the modified Document to be written to the xml file
		xmlWriter(document);
	}
	
	//writes the contents of a Document to the xml file
	public static void xmlWriter(Document doc) {
		DOMSource source = new DOMSource(doc);
		
		Result result = new StreamResult("note-entries.xml");
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//returns a String array containing the task titles
	public static String[] getEntryTitles() {
		Document document = docCreator();
		
		NodeList list = document.getElementsByTagName("title");
		
		String[] titleList = new String[list.getLength()];
		
		for(int i = 0; i < titleList.length; i++) {
			titleList[i] = list.item(i).getTextContent();
		}
		return titleList;
	}
	
	public static String getEntryNotes(int index) {
		Document document = docCreator();
		String note = "";
		
		NodeList list = document.getElementsByTagName("notes");
		note = list.item(index).getTextContent();
		
		return note;
	}
}
