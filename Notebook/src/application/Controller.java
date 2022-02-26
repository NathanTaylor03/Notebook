package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class Controller implements Initializable{

	@FXML
	private ListView<String> entryListView;
	@FXML
	private TextField titleTextField;
	@FXML
	private TextArea notesTextArea;
	
	public void newEntry(ActionEvent e) {
		System.out.println("hello"); //debug
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//gets the titles of the tasks
		String[] entryTitles = XMLHandler.getEntryTitles();
		
		//adds the entryTitles to the listView
		entryListView.getItems().addAll(entryTitles);
		//listens for ListView item selection
		entryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				
				int in = entryListView.getSelectionModel().getSelectedIndex();
				String currentTitle = entryTitles[in];
				
				titleTextField.setText(currentTitle);
				notesTextArea.setText(XMLHandler.getEntryNotes(in));
			}
		});
	}
}
