package fr.istic.tools.scanexam.view.fX.editor;

import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class HTMLView {
  @FXML
  private HTMLEditor html;
  
  @FXML
  private String onMouseClicked() {
    return InputOutput.<String>println(this.html.getHtmlText());
  }
}