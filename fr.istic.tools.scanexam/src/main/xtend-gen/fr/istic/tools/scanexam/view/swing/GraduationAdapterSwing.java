package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.presenter.GraduationPresenter;
import fr.istic.tools.scanexam.view.GraduationAdapter;
import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfPanel;
import fr.istic.tools.scanexam.view.swing.GraduationViewSwing;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Controlleur swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class GraduationAdapterSwing implements GraduationAdapter {
  /**
   * Presenter de la correction d'exman
   */
  private GraduationPresenter graduationPresenter;
  
  /**
   * Vue de la création d'exman
   */
  private GraduationViewSwing view;
  
  /**
   * Présentateur du pdf
   */
  private AdapterSwingPdfPanel adapterPdf;
  
  /**
   * Constructeur
   */
  public GraduationAdapterSwing() {
    InputStream pdfInput = null;
    AdapterSwingPdfPanel _adapterSwingPdfPanel = new AdapterSwingPdfPanel(1280, 720, pdfInput);
    this.adapterPdf = _adapterSwingPdfPanel;
    GraduationViewSwing _graduationViewSwing = new GraduationViewSwing(this.adapterPdf);
    this.view = _graduationViewSwing;
    this.addActionListeners();
    this.view.getWindow().setVisible(true);
  }
  
  /**
   * Ajoute les action listeners aux boutons de la vue
   */
  private void addActionListeners() {
    JMenuItem _mnItemLoad = this.view.getMnItemLoad();
    _mnItemLoad.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        try {
          GraduationAdapterSwing.this.openFile();
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    });
  }
  
  /**
   * Ouvre un fichier pdf
   */
  public void openFile() throws IOException, ClassNotFoundException {
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");
    JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Open your file");
    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    File _file = new File(".");
    fc.setCurrentDirectory(_file);
    fc.setFileFilter(filter);
    int result = fc.showOpenDialog(this.view.getWindow());
    if ((result == JFileChooser.CANCEL_OPTION)) {
    } else {
      if ((result == JFileChooser.APPROVE_OPTION)) {
        File selectedFile = fc.getSelectedFile();
        String path = selectedFile.getAbsolutePath();
        InputStream pdfInput = new FileInputStream(path);
        this.adapterPdf.setPDF(pdfInput, 0);
      }
    }
  }
  
  @Override
  public List<String> questionNames() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void nextQuestion() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void previousQuestion() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void thisQuestion(final int index) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void setPresenter(final GraduationPresenter presenter) {
    this.graduationPresenter = presenter;
  }
  
  @Override
  public GraduationPresenter getPresenter() {
    return this.getPresenter();
  }
}