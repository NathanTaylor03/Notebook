module notebook {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.xml;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
