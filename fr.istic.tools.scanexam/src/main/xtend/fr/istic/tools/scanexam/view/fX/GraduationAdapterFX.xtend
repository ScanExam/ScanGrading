package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.presenter.GraduationPresenter
import fr.istic.tools.scanexam.view.GraduationAdapter
import java.io.File
import fr.istic.tools.scanexam.core.Question

class GraduationAdapterFX implements GraduationAdapter {
	
	GraduationPresenter presenter;
	def void loadFile(File file) {
		
	}
	
	override setPresenter(GraduationPresenter presenter) {
		this.presenter = presenter
	}
	
	override void nextQuestion(){
		
	}
	
	override void previousQuestion(){
		
	}
	
	override questionNames() {
		
	}
	
	override thisQuestion(int index) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def void showQuestion(Question question){
		
	}
	
}