package fr.istic.tools.scanexam.view.fX;


import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import java.util.Objects
import javafx.fxml.FXML
import javafx.geometry.Rectangle2D
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.ScrollPane
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import org.apache.logging.log4j.LogManager
import fr.istic.tools.scanexam.core.Question
import javafx.scene.control.Spinner
import fr.istic.tools.scanexam.launcher.LauncherFX

/**
 * Class used by the JavaFX library as a controller for the view. 
 * @author Benjamin Danlos
 */
class ControllerFXCorrector {

	static val logger = LogManager.logger
	/**
	 * High level Controllers to access the Presenters
	 */
	GraduationAdapterFX corrector;

	
	
	/**
	 * setter for the ControllerVueCorrection attribute
	 * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null) 
	 */
	def void setAdapterCorrection(GraduationAdapterFX adapterCor) {
		Objects.requireNonNull(adapterCor)
		corrector = adapterCor
	}

	/**
	 * @return current {@link ControllerVueCorrection} 
	 */
	def getAdapterCorrection() {
		corrector
	}

	boolean botShow = false;
	@FXML
	public Pane topPane;
	@FXML
	public Button topButtonHidden;
	@FXML
	public Button topButtonActive;
	@FXML
	public Button botButtonHidden;
	@FXML
	public Button botButtonActive;
	@FXML
	public Pane bottomPane;
	@FXML
	public Pane imagePane;
	@FXML
	public Pane parentPane;
	@FXML
	public ListView<Label> leftList;
	@FXML
	public ListView<Label> rightList;
	@FXML
	public ImageView imview;
	@FXML
	public ScrollPane scrollMain;
	@FXML
	public ScrollPane scrollBis;
	@FXML
	public VBox studentDetails;
	@FXML
	public VBox questionDetails;
	@FXML
	public Spinner<Double> gradeSpinner;
	@FXML
	public Spinner<Double> totalGradeSpinner;
	
	
	
	@FXML
	def void swapToEditorPressed(){
		LauncherFX.swapToEditor
	}

	// ***********************//
	// ***** UI CONTROLS *****//
	// ***********************//
	/**
	 * Toggles the visibility of the bottom window
	 */
	def void toggleBottom() throws IOException {
		bottomPane.setVisible(!botShow);
		botButtonHidden.setVisible(botShow);
		botShow = !botShow;
	}

	/**
	 * Used to resize the window containing the corrected exam
	 */
	def void dragBottom(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			bottomPane.setPrefHeight(
				Math.max(0,
					Math.min(bottomPane.getScene().getHeight() - 100,
						bottomPane.getScene().getHeight() - event.getSceneY())));

		}
	}

	var mouseOriginX = 0d;
	var mouseOriginY = 0d;
	var objectOriginX = 0d;
	var objectOriginY = 0d;

	@FXML
	def void MoveImage(MouseEvent e) {

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mouseOriginX = e.screenX
			mouseOriginY = e.screenY
			var source = e.source as Node
			println(source)
			objectOriginX = source.layoutX
			objectOriginY = source.layoutY
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			var source = e.source as Node
			source.layoutX = objectOriginX + (e.screenX - mouseOriginX)
			source.layoutY = objectOriginY + (e.screenY - mouseOriginY)
		}
	}

	@FXML
	def void ZoomImage(ScrollEvent e) {
		var source = e.source as Node
		if (e.deltaY > 0) {
			source.scaleX = source.scaleX * 0.95
			source.scaleY = source.scaleY * 0.95
		} else {
			source.scaleX = source.scaleX * 1.05
			source.scaleY = source.scaleY * 1.05
		}
	}

	/* 
	 * var mouseOriginX = 0d;
	 * var mouseOriginY = 0d;
	 * var objectOriginX = 0d;
	 * var objectOriginY = 0d;
	 * 
	 * def void MoveImage(MouseEvent e) {
	 * 	println("trying to move")
	 * 	if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
	 * 		mouseOriginX = e.x
	 * 		mouseOriginY = e.y
	 * 		var source =  e.source as ImageView
	 * 		var vp = source.viewport
	 * 		objectOriginX = vp.minX
	 * 		objectOriginY = vp.minY
	 * 	}
	 * 	if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
	 * 		var source =  e.source as ImageView
	 * 		var vp = source.viewport
	 * 		var rect = new Rectangle2D(objectOriginX - (e.x - mouseOriginX),objectOriginY - (e.y - mouseOriginY),vp.height,vp.width )
	 * 		source.viewport = rect
	 * 	}
	 * }
	 * 
	 * def void ZoomImage(ScrollEvent e) {
	 * 	var source = e.source as ImageView
	 * 	if (e.deltaY > 0) {
	 * 		var vp = source.viewport
	 * 		source.viewport = new Rectangle2D(vp.minX,vp.minY,vp.height * 0.95,vp.width*0.95)
	 * 	}
	 * 	
	 * 	if (e.deltaY < 0) {
	 * 		var vp = source.viewport
	 * 		source.viewport = new Rectangle2D(vp.minX,vp.minY,vp.height * 1.05,vp.width*1.05)
	 * 	}
	 * }
	 * 
	 */
	// ***********************//
	// * PRESENTER CONTROLS **//
	// ***********************//
	/**
	 * Called when a <b>save</b> button is pressed
	 */
	@FXML
	def void savePressed() {
		println("Saving method");
	}

	/**
	 * Called when a <b>save a</b> button is pressed
	 */
	@FXML
	def void saveAsPressed() {
		println("Saving as method");
	}

	/**
	 * Called when a <b>load</b> button is pressed
	 */
	@FXML
	def void loadPressed() {
		// TODO TEST CONTENT REMOVE FOR PROD
		println("Load method");
		chooseFile
	// TODO TEST CONTENT REMOVE FOR PROD
	}

	/**
	 * Called when a <b>import</b> button is pressed
	 */
	@FXML
	def void importPressed() {
		println("Import method");
	}

	/**
	 * Called when a <b>export</b> button is pressed
	 */
	@FXML
	def void exportPressed() {
		println("Export method");
	}

	/**
	 * Called when a <b>next question</b> button is pressed
	 */
	@FXML
	def void nextQuestionPressed() {
		println("Next question method");
		corrector.nextQuestion;
	}

	/**
	 * Called when a <b>previous question pressed</b> button is pressed
	 */
	@FXML
	def void prevQuestionPressed() {
		println("Previous question method");
		corrector.previousQuestion
	}

	/**
	 * Called when a <b>next student</b> button is pressed
	 */
	@FXML
	def void nextStudentPressed() {
		println("Next student method");
		
		
	}

	/**
	 * Called when a <b>previous student</b> button is pressed
	 */
	@FXML
	def void prevStudentPressed() {
		println("Previous student method");
	}
	
	/**
	 * Called when a grade update button is pressed
	 */
	@FXML
	def void saveGradeButtonPressed() {
		println("save Grade method : "+gradeSpinner.getValue()+"/"+totalGradeSpinner.getValue);
	}

	@FXML
	def void addBaremeList() {
	}

	@FXML
	def void addQuestionList() {
	}

	def void zoomTest() {
		setZoomArea(0, 0, 100, 200)
	}

	def void setZoomArea(int x, int y, int height, int width) {
		var newrect = new Rectangle2D(currentQuestion.x, currentQuestion.y, currentQuestion.w,currentQuestion.h);
		imview.viewport = newrect
	}

	@FXML
	def void resetPosition() {
		imagePane.scaleX = 1;
		imagePane.scaleY = 1;
		imagePane.layoutX = 0;
		imagePane.layoutY = 0;
		imview.viewport = null;
	}
	
	
	//---------------------------------//
	
	QuestionDetails currentQuestion;
	
	
	
	

	
	



	static class QuestionDetails {
		int id;
		String name;
		double x;
		double y;
		double h;
		double w;
		List<Integer> bareme;
		new(String name) {
			this.name = name;
			id = 0;
			x = 0;
			y = 0;
			h = 0;
			w = 0;
			bareme = new ArrayList();
		}
		
		def Label getLabel() {
			return new Label("Question :" + name);
		}
		
		def VBox getDetails() {
			var container = new VBox();
			container.children.add(new Label("Question : " + name))
			container.children.add(new Label("ID :" + id))
			container.children.add(new Label("x:"+x))
			container.children.add(new Label("y:"+y))
			container.children.add(new Label("h:"+h))
			container.children.add(new Label("w:"+w))
			container.children.add(new Label("Bareme: " + bareme))
			return container;
			
		}
		
	}
	
	
	static class StudentItem extends Label {
		int id;
		new(int s,ControllerFXCorrector c) {
			super("Student: " + s)
			id = s;
		}

	}
	def void binds(Node n){
		n.setOnKeyPressed([ event |
			{
				switch event.code {
				case KeyCode.RIGHT : nextQuestionPressed
				case KeyCode.LEFT : prevQuestionPressed
				case KeyCode.UP : nextStudentPressed
				case KeyCode.DOWN : prevStudentPressed
				default : logger.warn("Key not supported.")		}
				event.consume
			}
		])
	}
	def void setKeybinds() {
		var s = imagePane.scene
		s.setOnKeyPressed([ event |
			{
				switch event.code {
				case KeyCode.RIGHT : nextQuestionPressed
				case KeyCode.LEFT : prevQuestionPressed
				case KeyCode.UP : nextStudentPressed
				case KeyCode.DOWN : prevStudentPressed
				default : logger.warn("Key not supported.")
				}
				event.consume
			}
		])
		binds(scrollMain);
		binds(scrollBis);
	}
	
	
	
	def void chooseFile(){
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("XMI files",Arrays.asList("*.xmi")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Documents");
		var file = fileChooser.showOpenDialog(imagePane.scene.window)
		if (file !== null) {
			corrector.loadFile(file);
		}
		else {
			logger.warn("File not chosen")
		}
	}
	
	def void initTests() {
		setKeybinds		
	}
	
	def void initQuestionNames(List<String> names) { //TODO complete, called on load of a new exam template
		rightList.items.clear
		for (String s : names) {
			rightList.items.add(new Label(s));
		}
	}
	
	def void initStudentNames(List<String> names) { //TODO complete, called on load of a new exam template
		leftList.items.clear
		for (String s : names) {
			leftList.items.add(new Label(s))
		}
	}
	
	def void showStudent() {
		
	}
	
	def showQuestion(Question question) {
		
		currentQuestion = new QuestionDetails(question.name);
		currentQuestion.x = question.zone.x
		currentQuestion.y = question.zone.y
		currentQuestion.h = question.zone.heigth
		currentQuestion.w = question.zone.width
		currentQuestion.id = question.id
		
		questionDetails.children.clear
		questionDetails.children.add(currentQuestion.details);
		var i = 0;
		for (Label l : rightList.items) {
			if (l.text.equals(currentQuestion.name)) {
				rightList.selectionModel.select(i);
			}
			i++;
		}
	}
	
	
}
