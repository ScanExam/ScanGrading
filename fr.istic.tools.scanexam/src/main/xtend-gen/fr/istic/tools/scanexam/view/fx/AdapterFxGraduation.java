package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.presenter.PresenterGraduation;
import fr.istic.tools.scanexam.view.AdapterGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import java.io.File;
import java.util.List;

@SuppressWarnings("all")
public class AdapterFxGraduation implements AdapterGraduation {
  private PresenterGraduation presenter;
  
  private ControllerFxGraduation corrector;
  
  public void setController(final ControllerFxGraduation controller) {
    this.corrector = controller;
  }
  
  public void loadFile(final File file) {
  }
  
  @Override
  public void nextQuestion() {
  }
  
  @Override
  public void previousQuestion() {
  }
  
  @Override
  public List<String> questionNames() {
    return null;
  }
  
  @Override
  public void thisQuestion(final int index) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public void showQuestion(final Question question) {
  }
  
  @Override
  public void setPresenter(final PresenterGraduation presenter) {
    this.presenter = presenter;
  }
  
  @Override
  public PresenterGraduation getPresenter() {
    return this.presenter;
  }
}
