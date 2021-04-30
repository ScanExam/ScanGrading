package fr.istic.tools.scanexam.view.fx.editor

import javafx.scene.layout.VBox
import javafx.scene.layout.Priority
import javafx.scene.Node
import org.apache.logging.log4j.LogManager

class QuestionListEdition extends VBox {
	
	static val logger = LogManager.logger
	
	//---Controller---//
	new(ControllerFxEdition controller) {
		this.controller = controller
		this.vgrow = Priority.ALWAYS
	}
	//----------------//
	
	//---FX vars---//
	ControllerFxEdition controller
	//-------------//
	
	//---GETTERS/SETTERS---//
	def getController(){
		controller
	}
	//---------------------//
	
	//---Methods---//
	def loadQuestion(Box box, String name,int page,int id,float questionWorth) {
		var item = new QuestionItemEdition(this,box,name,page,id);
		item.scale = questionWorth
		add(item);
	}

	/*
	 * Create new question and add to model
	 */
	def void newQuestion(Box box) {
		var type = BoxType.QUESTION
		switch controller.selectedTool {
			case QUESTION_AREA: {
				type = BoxType.QUESTION
			}
			case ID_AREA: {
				type = BoxType.ID
			}
			case QR_AREA: {
				type = BoxType.QR
			}
			default: {
			}
		}
		println("page : " + controller.pdfManager.currentPdfPageNumber)
		var item = new QuestionItemEdition(this, box, type,
			controller.pdfManager.currentPdfPageNumber);

		addToModel(item)
		add(item)
	}

	def removeQuestion(QuestionItemEdition item) {
		removeFocus
		controller.mainPane.removeZone(item.zone)
		remove(item)
		removeFromModel(item)
	}
	
	def void select(QuestionItemEdition item) {
		for (Node n : children) {
			var question = n as QuestionItemEdition
			if (question == item) {
				question.setFocus(true)
			}
			else {
				question.setFocus(false)
			}
		}
	}
	
	

	def void removeFocus() {
		for (Node e : children) {
			if (e instanceof QuestionItemEdition) {
				(e as QuestionItemEdition).focus = false
			}
		}
	}

	def void showOnlyPage(int page) {
		for (Node e : children) {
			if (e instanceof QuestionItemEdition) {
				checkAndDisplay(page, e as QuestionItemEdition)
			}

		}
	}

	def void checkAndDisplay(int page, QuestionItemEdition item) {
		if (item.page == page) {
			item.zone.isVisible(true)
		} else {
			item.zone.isVisible(false)
		}

	}

	def add(QuestionItemEdition item) {
		this.children.add(item)
	}

	def remove(QuestionItemEdition item) {
		System.out.println(this)
		this.children.remove(item)
		logger.info("remove")
		System.out.println(this.children.toString())
	}
	
	def clear(){
		controller.selectQuestion(null)
		children.clear
	}
	//-------------//

	//---Model Interactions---//
	def addToModel(QuestionItemEdition item) {
		item.questionId = controller.createQuestion(item.zone.x/controller.maxX, item.zone.y/controller.maxY,item.zone.height/controller.maxY, item.zone.width/controller.maxX) // TODO convert
		updateInModel(item)
	}


	def updateInModel(QuestionItemEdition item) {
		controller.moveQuestion(item.questionId, item.zone.x/controller.maxX, item.zone.y/controller.maxY) 
		controller.resizeQuestion(item.questionId, item.zone.height/controller.maxY,item.zone.width/controller.maxX)
		controller.renameQuestion(item.questionId,item.name)
		controller.changeQuestionWorth(item.questionId,item.scale)
	}

	def removeFromModel(QuestionItemEdition item) {
		controller.removeQuestion(item.questionId)
	}
	//------------------------//
	
	
	

}
