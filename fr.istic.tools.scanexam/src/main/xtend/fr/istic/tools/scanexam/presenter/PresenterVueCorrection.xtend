package fr.istic.tools.scanexam.presenter

import java.util.Objects
import fr.istic.tools.scanexam.view.ControllerVueCorrection
import fr.istic.tools.scanexam.services.Service
import fr.istic.tools.scanexam.services.ExamGraduationService

/**
 * Class defining the presenter for the exam correction view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
class PresenterVueCorrection {
	/**
	 * Bidirectional associations with the concrete presenters
	 * and main controller of the view
	 */
	PresenterQuestion presQuestion
	PresenterCopy presCopy
	PresenterMarkingScheme presMarkingScheme
	ControllerVueCorrection controller;
	ExamGraduationService service;
	
	def PresenterVueCorrection(ExamGraduationService service)
	{
		Objects.requireNonNull(service);
		this.service = service;
	}

	/**
	 * @return API session 
	 */
	def getServiceAPI(){
		service
	}
	
	
	/**
	 * setter for the PresenterQuestion attribute
	 * @param {@link PresenterQuestion} pres instance of the presenter (not null) 
	 */
	def setPresenterQuestion(PresenterQuestion pres){
		Objects.requireNonNull(pres)
		presQuestion = pres
	}
	/**
	 * @return current {@link PresenterQuestion} 
	 */
	def getPresenterQuestion(){
		presQuestion
	}
	
	/**
	 * Setter for {@link PresenterCopy} attribute
	 * @param {@link PresenterCopy} pres an instance (not null)
	 */
	def setPresenterCopy(PresenterCopy pres){
		Objects.requireNonNull(pres)
		presCopy = pres
	}
	/**
	 * @return current {@link PresenterCopy} 
	 */
	def getPresenterCopy(){
		presCopy
	}
	
	/**
	 * Setter for {@link PresenterMarkingScheme} attribute
	 * @param {@link PresenterMarkingScheme} pres an instance (not null)
	 */
	def setPresenterMarkingScheme(PresenterMarkingScheme pres){
		Objects.requireNonNull(pres)
		presMarkingScheme = pres
	}
	/**
	 * @return current {@link PresenterMarkingScheme} 
	 */
	def getPresenterMarkingScheme(){
		presMarkingScheme
	}
	
	/**
	 * Sets a {@link ControllerVueCorrection} the link with the view
	 * @param {@link ControllerVueCorrection} contr an instance (not null)
	 */
	def setControllerVueCorrection(ControllerVueCorrection contr){
		Objects.requireNonNull(contr)
		controller = contr
	}
	/**
	 * @return current {@link ControllerVueCorrection} 
	 */
	def getControllerVueCorrection(){
		controller
	}
	
	/**
	 * @return next question
	 */
	def getNextQuestion(int question){
		presQuestion.getNextQuestion(question)
	}
	
	/**
	 * @param question is the actual question
	 * @return previous question
	 */
	def getPreviousQuestion(int question){
		presQuestion.getPreviousQuestion(question)
	}
}