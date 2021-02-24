package fr.istic.tools.scanexam.view;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Classe pour offrir plus d'options à un PdfPanel (zoom, déplacement à la souris)
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class AdapterSwingPdfPanel {
  /**
   * Largeur de la fenêtre
   */
  protected int width;
  
  /**
   * Hauteur de la fenêtre
   */
  protected int height;
  
  /**
   * PDF a afficher
   */
  protected Image pdf;
  
  /**
   * Echelle pour l'affichage
   */
  protected int scale;
  
  /**
   * Point d'origine sur l'axe X
   */
  protected int originX;
  
  /**
   * Point d'origine sur l'axe Y
   */
  protected int originY;
  
  /**
   * Indique si la mise à l'échelle se base sur la largueur ou non
   */
  protected boolean scaleOnWidth;
  
  /**
   * Vue
   */
  protected Optional<JPanel> view;
  
  /**
   * Dernier point cliqué par l'utilisateur
   */
  protected Optional<Point> lastClickPoint;
  
  /**
   * Handler pour les événements liés à la souris
   */
  protected MouseAdapter mouseHandler;
  
  /**
   * Constructeur
   * @param width Largeur de la fenêtre
   * @param height Hauteur de la fenêtre
   * @param pdfInput InputStream du pdf
   */
  public AdapterSwingPdfPanel(final int width, final int height, final InputStream pdfInput) {
    this.width = width;
    this.height = height;
    this.setPDF(pdfInput, 0);
    this.setScaleOnWidth(false);
    this.originX = 0;
    this.originY = 0;
    this.view = Optional.<JPanel>empty();
    this.lastClickPoint = Optional.<Point>empty();
    this.mouseHandler = new MouseAdapter() {
      @Override
      public void mousePressed(final MouseEvent e) {
        if (((e.getButton() == 2) || (e.getButton() == 3))) {
          AdapterSwingPdfPanel.this.lastClickPoint = Optional.<Point>of(e.getPoint());
        }
      }
      
      @Override
      public void mouseReleased(final MouseEvent e) {
        AdapterSwingPdfPanel.this.lastClickPoint = Optional.<Point>empty();
      }
      
      @Override
      public void mouseDragged(final MouseEvent e) {
        AdapterSwingPdfPanel.this.moveOrigin(e);
      }
      
      @Override
      public void mouseWheelMoved(final MouseWheelEvent e) {
        AdapterSwingPdfPanel.this.incrScale(e.getWheelRotation());
      }
    };
  }
  
  /**
   * Déplacement de le contenu
   * @param e Mouvement de la souris
   */
  protected void moveOrigin(final MouseEvent e) {
    boolean _isPresent = this.lastClickPoint.isPresent();
    if (_isPresent) {
      Point dragPoint = e.getPoint();
      int _originX = this.originX;
      double _x = this.lastClickPoint.get().getX();
      double _minus = (dragPoint.x - _x);
      this.originX = (_originX + ((int) _minus));
      int _originY = this.originY;
      double _y = this.lastClickPoint.get().getY();
      double _minus_1 = (dragPoint.y - _y);
      this.originY = (_originY + ((int) _minus_1));
      this.repaint();
      this.lastClickPoint = Optional.<Point>of(dragPoint);
    }
  }
  
  /**
   * Incremente l'échelle
   * @param value Valeur à ajouter
   */
  protected void incrScale(final int value) {
    if (((this.scale + value) < 1)) {
      this.scale = 1;
    } else {
      int _scale = this.scale;
      this.scale = (_scale + value);
    }
    this.repaint();
  }
  
  /**
   * Actualise la vue
   */
  protected void repaint() {
    if ((this.view != null)) {
      this.view.get().repaint();
    }
  }
  
  /**
   * GETTERS
   */
  public Image getPdf() {
    return this.pdf;
  }
  
  public int getScale() {
    return this.scale;
  }
  
  public int getOriginX() {
    return this.originX;
  }
  
  public int getOriginY() {
    return this.originY;
  }
  
  public MouseAdapter getMouseHandler() {
    return this.mouseHandler;
  }
  
  /**
   * SETTERS
   */
  public void setPDF(final InputStream pdfInput, final int pageindex) {
    try {
      PDDocument document = PDDocument.load(pdfInput);
      PDFRenderer renderer = new PDFRenderer(document);
      BufferedImage img = renderer.renderImageWithDPI(pageindex, 300);
      document.close();
      this.pdf = img;
      this.repaint();
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  public void setScaleOnWidth(final boolean scaleOnWidth) {
    this.scaleOnWidth = scaleOnWidth;
    if (this.scaleOnWidth) {
      int _width = this.pdf.getWidth(null);
      int _divide = (_width / this.width);
      int _plus = (_divide + 1);
      this.scale = _plus;
    } else {
      int _height = this.pdf.getHeight(null);
      int _divide_1 = (_height / this.height);
      int _plus_1 = (_divide_1 + 1);
      this.scale = _plus_1;
    }
  }
  
  public void setView(final JPanel view) {
    this.view = Optional.<JPanel>of(view);
  }
}