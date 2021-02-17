package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterMarkingScheme;
import fr.istic.tools.scanexam.presenter.PresenterQRCode;
import fr.istic.tools.scanexam.presenter.PresenterRectangle;
import fr.istic.tools.scanexam.sessions.Session;
import fr.istic.tools.scanexam.view.ControllerVueCreation;
import java.util.Objects;

/**
 * Class defining the presenter for the exam creation view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterVueCreation {
  /**
   * Bidirectional associations with the concrete presenters
   * and main controller of the view
   */
  private PresenterQRCode presQRCode;
  
  private PresenterRectangle presRectangle;
  
  private PresenterMarkingScheme presMarkingScheme;
  
  private ControllerVueCreation controller;
  
  private Session session;
  
  /**
   * Setter for the session API
   * @param {@link Session} session of the API)
   */
  public Session setPresenterQRCode(final Session apiSession) {
    Session _xblockexpression = null;
    {
      Objects.<Session>requireNonNull(apiSession);
      _xblockexpression = this.session = apiSession;
    }
    return _xblockexpression;
  }
  
  /**
   * @return API session
   */
  public Session getSessionAPI() {
    return this.session;
  }
  
  /**
   * setter for the PresenterQRCode attribute
   * @param {@link PresenterQRCode} pres instance of the presenter (not null)
   */
  public PresenterQRCode setPresenterQRCode(final PresenterQRCode pres) {
    PresenterQRCode _xblockexpression = null;
    {
      Objects.<PresenterQRCode>requireNonNull(pres);
      _xblockexpression = this.presQRCode = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterQRCode}
   */
  public PresenterQRCode getPresenterQRCode() {
    return this.presQRCode;
  }
  
  /**
   * Setter for {@link PresenterRectangle} attribute
   * @param {@link PresenterRectangle} pres an instance (not null)
   */
  public PresenterRectangle setPresenterRectangle(final PresenterRectangle pres) {
    PresenterRectangle _xblockexpression = null;
    {
      Objects.<PresenterRectangle>requireNonNull(pres);
      _xblockexpression = this.presRectangle = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterRectangle}
   */
  public PresenterRectangle getPresenterRectangle() {
    return this.presRectangle;
  }
  
  /**
   * Setter for {@link PresenterMarkingScheme} attribute
   * @param {@link PresenterMarkingScheme} pres an instance (not null)
   */
  public PresenterMarkingScheme setPresenterMarkingScheme(final PresenterMarkingScheme pres) {
    PresenterMarkingScheme _xblockexpression = null;
    {
      Objects.<PresenterMarkingScheme>requireNonNull(pres);
      _xblockexpression = this.presMarkingScheme = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterMarkingScheme}
   */
  public PresenterMarkingScheme getPresenterMarkingScheme() {
    return this.presMarkingScheme;
  }
  
  /**
   * Sets a {@link ControllerVueCreation} the link with the view
   * @param {@link ControllerVueCreation} contr an instance (not null)
   */
  public ControllerVueCreation setControllerVueCreation(final ControllerVueCreation contr) {
    ControllerVueCreation _xblockexpression = null;
    {
      Objects.<ControllerVueCreation>requireNonNull(contr);
      _xblockexpression = this.controller = contr;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link ControllerVueCreation}
   */
  public ControllerVueCreation getControllerVueCreation() {
    return this.controller;
  }
}
