package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.exportation.GradesExportImpl;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe gérant l'interface pour l'export des notes en fichier excel
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ControllerGradeExport {
  /**
   * Logger du programme
   */
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Pane principale de la vue
   */
  @FXML
  public Pane mainPane;
  
  /**
   * Indique si les élèves doivent être rangé par ordre alphabétique
   */
  @FXML
  public CheckBox order;
  
  /**
   * Menu des formats d'étiquettes prédéfinis
   */
  @FXML
  public MenuButton formatMenu;
  
  /**
   * Bouton de validation
   */
  @FXML
  public Button btnOk;
  
  /**
   * Controller gérant la correction
   */
  private ControllerFxGraduation controllerGraduation;
  
  /**
   * METHODES
   */
  @FXML
  public void saveAndQuit() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    String _translate = LanguageManager.translate("exportExcel.fileFormat");
    List<String> _asList = Arrays.<String>asList("*.xlsx");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter(_translate, _asList);
    _extensionFilters.add(_extensionFilter);
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + 
      "Documents");
    File _file = new File(_plus_1);
    fileChooser.setInitialDirectory(_file);
    File file = fileChooser.showSaveDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      boolean _contains = file.getName().contains(".xlsx");
      boolean _not = (!_contains);
      if (_not) {
        String _absolutePath = file.getAbsolutePath();
        String _plus_2 = (_absolutePath + ".xlsx");
        File _file_1 = new File(_plus_2);
        file = _file_1;
      }
      this.controllerGraduation.saveTemplate(file.getPath());
      ControllerGradeExport.logger.info("Export grade in Excel");
    } else {
      ControllerGradeExport.logger.warn("File not chosen");
    }
    new GradesExportImpl().exportGrades(this.controllerGraduation.getService().getStudentSheets(), file);
    this.quit();
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  /**
   * Initialise le composant avec le controller gérant la correction
   * @param controllerGraduation Controller gérant la correction
   */
  public void initialize(final ControllerFxGraduation controllerGraduation) {
    this.controllerGraduation = controllerGraduation;
    final EventHandler<ActionEvent> formatMenuEvent = new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent e) {
        Object _source = e.getSource();
        final String selection = ((MenuItem) _source).getText();
        ControllerGradeExport.this.formatMenu.setText(selection);
      }
    };
    ObservableList<MenuItem> _items = this.formatMenu.getItems();
    String _translate = LanguageManager.translate("studentsTab.tableView.ID");
    MenuItem _menuItem = new MenuItem(_translate);
    _items.add(_menuItem);
    ObservableList<MenuItem> _items_1 = this.formatMenu.getItems();
    String _translate_1 = LanguageManager.translate("studentsTab.tableView.firstName");
    MenuItem _menuItem_1 = new MenuItem(_translate_1);
    _items_1.add(_menuItem_1);
    ObservableList<MenuItem> _items_2 = this.formatMenu.getItems();
    String _translate_2 = LanguageManager.translate("studentsTab.tableView.lastName");
    MenuItem _menuItem_2 = new MenuItem(_translate_2);
    _items_2.add(_menuItem_2);
    ObservableList<MenuItem> _items_3 = this.formatMenu.getItems();
    for (final MenuItem item : _items_3) {
      item.setOnAction(formatMenuEvent);
    }
  }
}