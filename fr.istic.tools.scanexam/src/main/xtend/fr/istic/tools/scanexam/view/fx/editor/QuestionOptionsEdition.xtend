package fr.istic.tools.scanexam.view.fx.editor

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

import static fr.istic.tools.scanexam.config.LanguageManager.translate
import javafx.util.converter.NumberStringConverter

/**
 * [JavaFX] Détail d'une question
 * @author Stefan Locke, Julien Cochet
 */
class QuestionOptionsEdition extends VBox {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	GridPane grid
	Label questionName
	TextField renameField
	/* Text field for the scale of the question */
	TextField scaleField
	/* Formatter to allow only number in scale field */
	val TextFormatter<Number> formatter
	Label questionId
	Label page
	Label questionScale
	Label questionCoords
	Label questionDescription // TODO replace with htlm display
	Button remove
	ControllerFxEdition controller

	QuestionItemEdition currentItem

	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEURS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	new(ControllerFxEdition controller) {
		this.controller = controller

		grid = new GridPane

		var l1 = new Label(translate("question.name"))
		var l2 = new Label(translate("question.id"))
		var l3 = new Label(translate("question.page"))
		var l4 = new Label(translate("question.scale"))
		var l6 = new Label(translate("question.position"))
		var l7 = new Label(translate("question.description"))
		grid.add(l1, 0, 0)
		grid.add(l2, 0, 1)
		grid.add(l3, 0, 2)
		grid.add(l4, 0, 3)
		grid.add(l6, 0, 4)
		grid.add(l7, 0, 5)
		
		val editIcon1 = new Label("\u270E")
		val editIcon2 = new Label("\u270E")
		editIcon1.styleClass.add("unicodeLabel")
		editIcon2.styleClass.add("unicodeLabel")
		
		grid.add(editIcon1,2,0)
		grid.add(editIcon2,2,3)
		
		renameField = new TextField
		renameField.prefWidth = grid.prefWidth * 0.7
		scaleField = new TextField
		formatter = new TextFormatter(new NumberStringConverter)
		scaleField.setTextFormatter(formatter)
		scaleField.prefWidth = grid.prefWidth * 0.7

		questionName = new Label();
		questionId = new Label()
		page = new Label();
		questionScale = new Label();
		questionCoords = new Label();
		questionDescription = new Label();
		remove = new Button(translate("question.remove"));
		
		grid.add(questionName, 1, 0)
		grid.add(questionId, 1, 1)
		grid.add(page, 1, 2)
		grid.add(questionScale, 1, 3)
		grid.add(questionCoords, 1, 4)
		grid.add(questionDescription, 1, 5)

		children.addAll(grid, remove)
		hideAll
		setupEvents();
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Selects a question to display
	 * @param item Question sélectionnée
	 */
	def select(QuestionItemEdition item) {
		showAll()
		currentItem = item
		questionName.text = item.name
		renameField.text = item.name
		page.text = item.page + ""
		questionId.text = "" + item.questionId
		questionScale.text = "" + item.scale
		scaleField.text = "" + item.scale
		questionCoords.text = "X:" + item.zone.x + "\nY:" + item.zone.y + "\nH:" + item.zone.height + "\nW:" +
			item.zone.width
		questionDescription.text = "No description";
	}

	def showAll() {
		this.visible = true
	}

	def hideAll() {
		this.visible = false
	}

	/**
	 * Toggles if the textfield is the visible element 
	 */
	def toggleRename(boolean b) {
		if (b) {
			grid.children.remove(questionName);
			grid.add(renameField, 1, 0);
			renameField.requestFocus
			renameField.selectAll
			grid.children.findFirst[n | n !== null && GridPane.getRowIndex(n) == 0 && GridPane.getColumnIndex(n) == 2].visible = false
		} else {
			if (!grid.children.contains(questionName)) {
				grid.children.remove(renameField);
				grid.add(questionName, 1, 0);
				grid.children.findFirst[n | n !== null && GridPane.getRowIndex(n) == 0 && GridPane.getColumnIndex(n) == 2].visible = true
			}
		}
	}

	/**
	 * Toggles if the sclaefield is the visible element 
	 */
	def toggleRescale(boolean b) {
		if (b) {
			grid.children.remove(questionScale);
			grid.add(scaleField, 1, 3);
			scaleField.requestFocus
			scaleField.selectAll
			grid.children.findFirst[n | n !== null && GridPane.getRowIndex(n) == 3 && GridPane.getColumnIndex(n) == 2].visible = false
		} else {
			if (!grid.children.contains(questionScale)) {
				grid.children.remove(scaleField);
				grid.add(questionScale, 1, 3);
				grid.children.findFirst[n | n !== null && GridPane.getRowIndex(n) == 3 && GridPane.getColumnIndex(n) == 2].visible = true
			}
		}
	}

	/**
	 * Called to commit a name change
	 */
	def commitRename() {
		questionName.text = renameField.text
		currentItem.name = renameField.text
		controller.questionList.updateInModel(currentItem)
	}

	/**
	 * Called to commit a scale change
	 */
	def commitRescale() {
		questionScale.text = formatter.value.toString
		currentItem.scale = formatter.value.floatValue
		controller.questionList.updateInModel(currentItem)
	}

	def setupEvents() {
		remove.onAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				controller.questionList.remove(currentItem)
				controller.mainPane.removeZone(currentItem.zone)
				controller.selectQuestion(null)
			}

		}
		
		grid.children.findFirst[n | n !== null && GridPane.getRowIndex(n) == 0 && GridPane.getColumnIndex(n) == 2].onMouseClicked = [e | toggleRename(true)]

		questionName.onMouseClicked = new EventHandler<MouseEvent>() {

			override handle(MouseEvent event) {
				toggleRename(true)
			}

		}

		questionScale.onMouseClicked = new EventHandler<MouseEvent>() {

			override handle(MouseEvent event) {
				toggleRescale(true)
			}

		}

		renameField.onAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				print("Rename actions\n")
				commitRename();
				toggleRename(false);

			}

		}
		
		renameField.focusedProperty.addListener(new ChangeListener<Boolean>() {

			override changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					print("Rename actions\n")
					commitRename();
					toggleRename(false);
				}
			}

		})

		grid.children.findFirst[n | n !== null && GridPane.getRowIndex(n) == 3 && GridPane.getColumnIndex(n) == 2].onMouseClicked = [e | toggleRescale(true)]

		scaleField.onAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				print("Rescale actions\n")
				commitRescale
				toggleRescale(false)

			}

		}

		scaleField.focusedProperty.addListener(new ChangeListener<Boolean>() {

			override changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					print("Rescale actions\n")
					commitRescale
					toggleRescale(false)
				}
			}

		})
	}
}