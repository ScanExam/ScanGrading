package fr.istic.tools.scanexam.api

import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.GradeEntry
import fr.istic.tools.scanexam.core.GradeScale
import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.core.QuestionZone
import fr.istic.tools.scanexam.core.StudentSheet
import java.util.List
import java.util.ArrayList

/**
 * Factory qui crée des objets de type Data: {@link Rectangle},   {@link StudentSheet},  {@link Grade},  {@link GradeScale}, {@link Question}
 * 
 * @author Antoine Degas
 */
class DataFactory {
	
	/** 
	 * Un rectangle permet de définir une zone sur l'interface graphique
	 * @param x
	 * @param y
	 * @param width largeur du rectangle
	 * @param height hauteur du rectangle
	 * @return une instance d'objet de type {@link Rectangle}
	 * @author Antoine Degas
	 */
	def QuestionZone createRectangle(float x, float y, float width, float height){
		val rec = CoreFactory.eINSTANCE.createQuestionZone
		rec.x = x
		rec.y = y
		rec.width = width
		rec.heigth = height
		rec
	}
	
	/** 
	 * @return une instance d'objet de type {@link StudentSheet}
	 * @author Antoine Degas
	 */
	def StudentSheet createStudentSheet(int idSheet, List<Integer> pages){
		val sheet = CoreFactory.eINSTANCE.createStudentSheet
		sheet.id = idSheet
		
		sheet.posPage.addAll(pages)
		sheet
	}
	
	/** 
	 * @return une instance d'objet de type {@link Grade}
	 * @author Antoine Degas
	 */
	def Grade createGrade(){
		CoreFactory.eINSTANCE.createGrade
	}
	
	/** 
	 * @return une instance d'objet de type {@link GradeScale}
	 * @author Antoine Degas
	 */
	def GradeScale createGradeScale(){
		CoreFactory.eINSTANCE.createGradeScale
		
	}
	
	/** 
	 * @return une instance d'objet de type {@link GradeEntry}
	 * @author Théo Giraudet
	 */
	def GradeEntry createGradeEntry(int id, String desc, float point) {
		val scale = CoreFactory.eINSTANCE.createGradeEntry
		scale.id = id
		scale.header = desc
		scale.step = point
		scale
	}
	
	/** Une question contient un identifiant et un {@link GradeScale} 
	 * @param id identifiant de la question
	 * @return une instance d'objet de type {@link Question}
	 * @author Antoine Degas
	 */
	def Question createQuestion(int id){
		val question = CoreFactory.eINSTANCE.createQuestion
		question.id = id
		question
	}
}

