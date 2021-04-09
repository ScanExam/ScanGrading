package fr.istic.tools.scanexam.view.fx.editor

import javafx.fxml.FXML
import javafx.scene.web.HTMLEditor

class HTMLView {
	@FXML  HTMLEditor html
	/* Permet de ne pouvoir qu'un seul éditeur à la fois */
	static public boolean isHTMLEditorOpen = false
	
	@FXML
	private def onMouseClicked() {
		//label.setText(html.getHtmlText());
	    //webEngine = webView.getEngine();
		//webEngine.loadContent(html.getHtmlText());
		println(html.getHtmlText())
	}
}