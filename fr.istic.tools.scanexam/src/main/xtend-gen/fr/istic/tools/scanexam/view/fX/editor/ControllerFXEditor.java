package fr.istic.tools.scanexam.view.fX.editor;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.launcher.LauncherFX;
import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX;
import fr.istic.tools.scanexam.view.fX.FXSettings;
import fr.istic.tools.scanexam.view.fX.editor.Box;
import fr.istic.tools.scanexam.view.fX.editor.PdfPane;
import fr.istic.tools.scanexam.view.fX.editor.QuestionItemEditor;
import fr.istic.tools.scanexam.view.fX.editor.QuestionListEditor;
import fr.istic.tools.scanexam.view.fX.editor.QuestionOptionsEditor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class ControllerFXEditor {
  public enum SelectedTool {
    NO_TOOL,
    
    QUESTION_AREA,
    
    ID_AREA,
    
    QR_AREA,
    
    MOVE_CAMERA_TOOL,
    
    MOVE_TOOL,
    
    RESIZE_TOOL;
  }
  
  private EditorAdapterFX editor;
  
  public void setEditorAdapterFX(final EditorAdapterFX editor) {
    this.editor = editor;
  }
  
  public EditorAdapterFX getEditor() {
    return this.editor;
  }
  
  private double maxX;
  
  private double maxY;
  
  private boolean pdfLoaded = false;
  
  private Logger logger = LogManager.getLogger();
  
  public ControllerFXEditor.SelectedTool getSelectedTool() {
    return this.currentTool;
  }
  
  private PdfPane mainPane;
  
  @FXML
  private ScrollPane questionListContainer;
  
  @FXML
  private ScrollPane gradeListContainer;
  
  @FXML
  private ChoiceBox<Integer> pageChoice;
  
  @FXML
  private Label pageNumberLabel;
  
  @FXML
  private ToggleButton createBoxButton;
  
  private QuestionListEditor questionList;
  
  private QuestionOptionsEditor questionEditor;
  
  @FXML
  private AnchorPane mainPaneContainer;
  
  @FXML
  public void pressed() {
  }
  
  @FXML
  public void questionAreaPressed() {
    boolean _isSelected = this.createBoxButton.isSelected();
    if (_isSelected) {
      this.setToQuestionAreaTool();
    } else {
      this.setToNoTool();
    }
  }
  
  @FXML
  public void iDAreaPressed() {
    this.setToIDAreaTool();
  }
  
  @FXML
  public void qRArearessed() {
    this.setToQRAreaTool();
  }
  
  @FXML
  public void movePressed() {
    this.setToMoveCameraTool();
  }
  
  @FXML
  public void nextPagePressed() {
    if (this.pdfLoaded) {
      this.nextPage();
    }
  }
  
  @FXML
  public void newTemplatePressed() {
    this.loadPdf();
  }
  
  @FXML
  public void saveTemplatePressed() {
    if (this.pdfLoaded) {
      this.saveTemplate();
    }
  }
  
  @FXML
  public void loadTemplatePressed() {
    this.loadTemplate();
  }
  
  @FXML
  public void previousPagePressed() {
    if (this.pdfLoaded) {
      this.previousPage();
    }
  }
  
  @FXML
  public void switchToCorrectorPressed() {
    LauncherFX.swapToGraduator();
  }
  
  @FXML
  public void mainMouseEvent(final MouseEvent e) {
    if (this.pdfLoaded) {
      MouseButton _button = e.getButton();
      boolean _equals = Objects.equal(_button, MouseButton.SECONDARY);
      if (_equals) {
        this.moveImage(e);
      } else {
        this.chooseMouseAction(e);
      }
    }
  }
  
  public PdfPane getMainPane() {
    return this.mainPane;
  }
  
  public QuestionListEditor getQuestionList() {
    return this.questionList;
  }
  
  /**
   * Called When we decide to focus on a specific question
   */
  public void selectQuestion(final QuestionItemEditor item) {
    if ((item == null)) {
      this.questionList.removeFocus();
      this.questionEditor.hideAll();
      return;
    }
    this.questionList.select(item);
    this.questionEditor.select(item);
  }
  
  public void init() {
    PdfPane _pdfPane = new PdfPane(this);
    this.mainPane = _pdfPane;
    this.mainPaneContainer.getChildren().add(this.mainPane);
    QuestionListEditor _questionListEditor = new QuestionListEditor(this);
    this.questionList = _questionListEditor;
    this.questionListContainer.setContent(this.questionList);
    QuestionOptionsEditor _questionOptionsEditor = new QuestionOptionsEditor(this);
    this.questionEditor = _questionOptionsEditor;
    this.gradeListContainer.setContent(this.questionEditor);
    final EventHandler<ActionEvent> _function = (ActionEvent event) -> {
      PresenterPdf pdfPresenter = this.editor.getPresenter().getPresenterPdf();
      int selectedIndex = this.pageChoice.getSelectionModel().getSelectedIndex();
      pdfPresenter.goToPdfPage(selectedIndex);
      this.renderDocument();
    };
    this.pageChoice.setOnAction(_function);
  }
  
  public void chooseMouseAction(final MouseEvent e) {
    final ControllerFXEditor.SelectedTool currentTool = this.currentTool;
    if (currentTool != null) {
      switch (currentTool) {
        case QUESTION_AREA:
          this.createBox(e);
          break;
        case ID_AREA:
          this.createBox(e);
          break;
        case QR_AREA:
          this.createBox(e);
          break;
        case MOVE_TOOL:
          this.moveBox(e);
          break;
        case RESIZE_TOOL:
          this.resizeBox(e);
          break;
        case MOVE_CAMERA_TOOL:
          this.moveImage(e);
          break;
        case NO_TOOL:
          break;
        default:
          break;
      }
    }
  }
  
  private double mouseOriginX = 0d;
  
  private double mouseOriginY = 0d;
  
  private double objectOriginX = 0d;
  
  private double objectOriginY = 0d;
  
  private Box currentRectangle = null;
  
  /**
   * Called when we click and drag on the pdf with the create question too selected
   * will not create the question if the zone is too small
   */
  public void createBox(final MouseEvent e) {
    double mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FXSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FXSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = mousePositionX;
      this.mouseOriginY = mousePositionY;
      this.currentRectangle = this.createZone(mousePositionX, mousePositionY);
      this.mainPane.addZone(this.currentRectangle);
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      double xDelta = (mousePositionX - this.mouseOriginX);
      double yDelta = (mousePositionY - this.mouseOriginY);
      if ((xDelta > 0)) {
        this.currentRectangle.width(xDelta);
      } else {
        this.currentRectangle.width(Math.abs(xDelta));
        double _abs = Math.abs(xDelta);
        double _minus = (this.mouseOriginX - _abs);
        this.currentRectangle.x(_minus);
      }
      if ((yDelta > 0)) {
        this.currentRectangle.height(yDelta);
      } else {
        this.currentRectangle.height(Math.abs(yDelta));
        double _abs_1 = Math.abs(yDelta);
        double _minus_1 = (this.mouseOriginY - _abs_1);
        this.currentRectangle.y(_minus_1);
      }
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
      if (((this.currentRectangle.getWidth() > FXSettings.MINIMUM_ZONE_SIZE) && (this.currentRectangle.getHeight() > FXSettings.MINIMUM_ZONE_SIZE))) {
        this.questionList.newQuestion(this.currentRectangle);
      } else {
        this.mainPane.removeZone(this.currentRectangle);
      }
      this.setToNoTool();
      this.createBoxButton.setSelected(false);
    }
  }
  
  /**
   * OLD CODE
   * Called when we click on a pdf with the move tool selected
   * the box is limited to inside the pdf
   */
  public void moveBox(final MouseEvent e) {
    double mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FXSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FXSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      double _width = this.currentRectangle.getWidth();
      double _minus = ((this.maxX - FXSettings.BOX_BORDER_THICKNESS) - _width);
      this.currentRectangle.x(
        Math.min(mousePositionX, _minus));
      double _height = this.currentRectangle.getHeight();
      double _minus_1 = ((this.maxY - FXSettings.BOX_BORDER_THICKNESS) - _height);
      this.currentRectangle.y(
        Math.min(mousePositionY, _minus_1));
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
    }
  }
  
  public void resizeBox(final MouseEvent e) {
    double mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getX(), (this.maxX - FXSettings.BOX_BORDER_THICKNESS)));
    double mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS, 
      Math.min(e.getY(), (this.maxY - FXSettings.BOX_BORDER_THICKNESS)));
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      double _x = this.currentRectangle.getX();
      double _minus = (_x - mousePositionX);
      this.currentRectangle.width(Math.abs(_minus));
      double _y = this.currentRectangle.getY();
      double _minus_1 = (_y - mousePositionY);
      this.currentRectangle.height(Math.abs(_minus_1));
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
    }
  }
  
  /**
   * Used to move around the image in the parent pane
   * Called when we right click on the pdf
   */
  public void moveImage(final MouseEvent e) {
    EventType<? extends MouseEvent> _eventType = e.getEventType();
    boolean _equals = Objects.equal(_eventType, MouseEvent.MOUSE_PRESSED);
    if (_equals) {
      this.mouseOriginX = e.getScreenX();
      this.mouseOriginY = e.getScreenY();
      Object _source = e.getSource();
      Node source = ((Node) _source);
      this.objectOriginX = source.getLayoutX();
      this.objectOriginY = source.getLayoutY();
      this.mainPane.setCursor(Cursor.CLOSED_HAND);
    }
    EventType<? extends MouseEvent> _eventType_1 = e.getEventType();
    boolean _equals_1 = Objects.equal(_eventType_1, MouseEvent.MOUSE_DRAGGED);
    if (_equals_1) {
      Object _source_1 = e.getSource();
      Node source_1 = ((Node) _source_1);
      double _screenX = e.getScreenX();
      double _minus = (_screenX - this.mouseOriginX);
      double _plus = (this.objectOriginX + _minus);
      source_1.setLayoutX(_plus);
      double _screenY = e.getScreenY();
      double _minus_1 = (_screenY - this.mouseOriginY);
      double _plus_1 = (this.objectOriginY + _minus_1);
      source_1.setLayoutY(_plus_1);
    }
    EventType<? extends MouseEvent> _eventType_2 = e.getEventType();
    boolean _equals_2 = Objects.equal(_eventType_2, MouseEvent.MOUSE_RELEASED);
    if (_equals_2) {
      this.mainPane.setCursor(Cursor.DEFAULT);
    }
  }
  
  /**
   * Used to zoom in and out the pdf image
   * 
   * Using the scale allows the children of the pane to also scale accordingly
   */
  @FXML
  public void ZoomImage(final ScrollEvent e) {
    Object _source = e.getSource();
    Node source = ((Node) _source);
    double _deltaY = e.getDeltaY();
    boolean _lessThan = (_deltaY < 0);
    if (_lessThan) {
      double _scaleX = source.getScaleX();
      double _multiply = (_scaleX * 0.95);
      source.setScaleX(_multiply);
      double _scaleY = source.getScaleY();
      double _multiply_1 = (_scaleY * 0.95);
      source.setScaleY(_multiply_1);
    } else {
      double _scaleX_1 = source.getScaleX();
      double _multiply_2 = (_scaleX_1 * 1.05);
      source.setScaleX(_multiply_2);
      double _scaleY_1 = source.getScaleY();
      double _multiply_3 = (_scaleY_1 * 1.05);
      source.setScaleY(_multiply_3);
    }
  }
  
  /**
   * Setters for the current tool selected
   */
  private ControllerFXEditor.SelectedTool currentTool = ControllerFXEditor.SelectedTool.NO_TOOL;
  
  public void setToMoveCameraTool() {
    this.mainPane.setCursor(Cursor.OPEN_HAND);
    this.currentTool = ControllerFXEditor.SelectedTool.MOVE_CAMERA_TOOL;
  }
  
  public void setToQuestionAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXEditor.SelectedTool.QUESTION_AREA;
  }
  
  public void setToIDAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXEditor.SelectedTool.ID_AREA;
  }
  
  public void setToQRAreaTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXEditor.SelectedTool.QR_AREA;
  }
  
  public void setToMoveTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXEditor.SelectedTool.MOVE_TOOL;
  }
  
  public void setToResizeTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXEditor.SelectedTool.RESIZE_TOOL;
  }
  
  public void setToNoTool() {
    this.mainPane.setCursor(Cursor.DEFAULT);
    this.currentTool = ControllerFXEditor.SelectedTool.NO_TOOL;
  }
  
  /**
   * returns a new Box with the right type corresponding to the current tool //TODO maybe move to box as a static method
   */
  public Box createZone(final double x, final double y) {
    return new Box(x, y, 0, 0);
  }
  
  /**
   * Called when we press create template
   * load a new pdf to start the creation of a new template
   */
  public Boolean loadPdf() {
    boolean _xblockexpression = false;
    {
      FileChooser fileChooser = new FileChooser();
      ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
      List<String> _asList = Arrays.<String>asList("*.pdf");
      FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("PDF files", _asList);
      _extensionFilters.add(_extensionFilter);
      String _property = System.getProperty("user.home");
      String _property_1 = System.getProperty("file.separator");
      String _plus = (_property + _property_1);
      String _plus_1 = (_plus + 
        "Documents");
      File _file = new File(_plus_1);
      fileChooser.setInitialDirectory(_file);
      File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
      boolean _xifexpression = false;
      if ((file != null)) {
        boolean _xblockexpression_1 = false;
        {
          this.clearVue();
          this.editor.getPresenter().getPresenterPdf().create(file);
          this.renderDocument();
          _xblockexpression_1 = this.postLoad();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        this.logger.warn("File not chosen");
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
  }
  
  /**
   * Saves the current model to a XMI file
   */
  public void saveTemplate() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    List<String> _asList = Arrays.<String>asList("*.xmi");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("XMI files", _asList);
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
      this.editor.getPresenter().save(file.getPath());
    } else {
      this.logger.warn("File not chosen");
    }
  }
  
  /**
   * Loads new model from an xmi file
   */
  public Boolean loadTemplate() {
    boolean _xblockexpression = false;
    {
      FileChooser fileChooser = new FileChooser();
      ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
      List<String> _asList = Arrays.<String>asList("*.xmi");
      FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("XMI Files", _asList);
      _extensionFilters.add(_extensionFilter);
      String _property = System.getProperty("user.home");
      String _property_1 = System.getProperty("file.separator");
      String _plus = (_property + _property_1);
      String _plus_1 = (_plus + 
        "Documents");
      File _file = new File(_plus_1);
      fileChooser.setInitialDirectory(_file);
      File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
      boolean _xifexpression = false;
      if ((file != null)) {
        boolean _xblockexpression_1 = false;
        {
          this.clearVue();
          this.editor.getPresenter().load(file.getPath());
          this.renderDocument();
          this.loadBoxes();
          _xblockexpression_1 = this.postLoad();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        this.logger.warn("File not chosen");
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
  }
  
  /**
   * called to load each question from the model into the vue
   */
  public void loadBoxes() {
    for (int p = 0; (p < this.editor.getPresenter().getPresenterPdf().totalPdfPageNumber()); p++) {
      {
        LinkedList<Integer> ids = this.editor.getPresenter().getPresenterQuestionZone().initLoading(p);
        for (final int i : ids) {
          {
            double _questionX = this.editor.getPresenter().getPresenterQuestionZone().questionX(i);
            double _multiply = (_questionX * this.maxX);
            double _questionY = this.editor.getPresenter().getPresenterQuestionZone().questionY(i);
            double _multiply_1 = (_questionY * this.maxY);
            double _questionWidth = this.editor.getPresenter().getPresenterQuestionZone().questionWidth(i);
            double _multiply_2 = (_questionWidth * this.maxX);
            double _questionHeight = this.editor.getPresenter().getPresenterQuestionZone().questionHeight(i);
            double _multiply_3 = (_questionHeight * this.maxY);
            Box box = new Box(_multiply, _multiply_1, _multiply_2, _multiply_3);
            double _questionWidth_1 = this.editor.getPresenter().getPresenterQuestionZone().questionWidth(i);
            String _plus = ((("loading width for " + Integer.valueOf(i)) + " = ") + Double.valueOf(_questionWidth_1));
            InputOutput.<String>print(_plus);
            this.mainPane.addZone(box);
            this.questionList.loadQuestion(box, this.editor.getPresenter().getPresenterQuestionZone().questionName(i), p, i, this.editor.getPresenter().getPresenterQuestionZone().questionWorth(i));
          }
        }
      }
    }
    this.questionList.showOnlyPage(this.editor.getPresenter().getPresenterPdf().currentPdfPageNumber());
  }
  
  public boolean postLoad() {
    return this.pdfLoaded = true;
  }
  
  /**
   * initialise the choicebox containing all the page numbers of the pdf
   * to call whenever we load a new pdf into the editor
   */
  public void initPageSelection() {
    this.pageChoice.getItems().clear();
    PresenterPdf pdfPresenter = this.editor.getPresenter().getPresenterPdf();
    for (int i = 1; (i <= pdfPresenter.totalPdfPageNumber()); i++) {
      boolean _contains = this.pageChoice.getItems().contains(Integer.valueOf(i));
      boolean _not = (!_contains);
      if (_not) {
        this.pageChoice.getItems().add(Integer.valueOf(i));
      }
    }
  }
  
  /**
   * feches the current buffered image in the presenter representing the pdf and converts it and loads into the imageview
   */
  public void renderDocument() {
    this.initPageSelection();
    final BufferedImage image = this.editor.getPresenter().getPresenterPdf().getCurrentPdfPage();
    this.mainPane.setImage(SwingFXUtils.toFXImage(image, null));
    this.maxX = this.mainPane.getImageViewWidth();
    this.maxY = this.mainPane.getImageViewHeight();
    String _translate = LanguageManager.translate("label.page");
    int _currentPdfPageNumber = this.editor.getPresenter().getPresenterPdf().currentPdfPageNumber();
    int _plus = (_currentPdfPageNumber + 1);
    String _plus_1 = (_translate + Integer.valueOf(_plus));
    String _plus_2 = (_plus_1 + " / ");
    int _talPdfPageNumber = this.editor.getPresenter().getPresenterPdf().totalPdfPageNumber();
    String _plus_3 = (_plus_2 + Integer.valueOf(_talPdfPageNumber));
    this.pageNumberLabel.setText(_plus_3);
    int _currentPdfPageNumber_1 = this.editor.getPresenter().getPresenterPdf().currentPdfPageNumber();
    int _plus_4 = (_currentPdfPageNumber_1 + 1);
    this.pageChoice.setValue(Integer.valueOf(_plus_4));
  }
  
  /**
   * changes the selected page to load and then renders it
   */
  public void selectPage(final int pageNumber) {
    this.editor.getPresenter().getPresenterPdf().goToPdfPage(pageNumber);
    this.renderDocument();
    this.questionList.showOnlyPage(this.editor.getPresenter().getPresenterPdf().currentPdfPageNumber());
  }
  
  /**
   * goes to the next page of the current pdf
   */
  public void nextPage() {
    this.editor.getPresenter().getPresenterPdf().nextPdfPage();
    this.renderDocument();
    this.questionList.showOnlyPage(this.editor.getPresenter().getPresenterPdf().currentPdfPageNumber());
  }
  
  public void previousPage() {
    this.editor.getPresenter().getPresenterPdf().previousPdfPage();
    this.renderDocument();
    this.questionList.showOnlyPage(this.editor.getPresenter().getPresenterPdf().currentPdfPageNumber());
  }
  
  public double getMaxY() {
    return this.maxY;
  }
  
  public double getMaxX() {
    return this.maxX;
  }
  
  public void clearVue() {
    this.mainPane.clear();
    this.questionList.clear();
    this.questionEditor.hideAll();
  }
}