package fr.istic.tools.scanexam.interfaceFX;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.MouseInfo;
import java.io.IOException;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.geom.Point2D;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.services.ServiceImpl;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.ControllerRoot;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class InterfaceFXTest {

	/**
	 * EMPLACEMENT DES BOUTONS
	 * package fr.istic.tools.scanexam.view.fx.editor; -> ControllerFxEdition
	 * package fr.istic.tools.scanexam.view.fx.graduation -> ControllerFxGraduation
	 * 
	 * Fichier fxml : src/main/resources/viewResources
	 * ConfigUI
	 */


	/**
	 * Lancement de l'interface FX
	 * @throws IOException 
	 */
	@Start
	public void start(Stage stage) throws IOException {
		LanguageManager.init(Locale.FRENCH);
		final FXMLLoader editionLoader = new FXMLLoader();
		final FXMLLoader graduationLoader = new FXMLLoader();
		final FXMLLoader rootLoader = new FXMLLoader();

		editionLoader.setResources(LanguageManager.getCurrentBundle());
		graduationLoader.setResources(LanguageManager.getCurrentBundle());
		rootLoader.setResources(LanguageManager.getCurrentBundle());

		final Parent mainRoot = rootLoader.load(ResourcesUtils.getInputStreamResource("viewResources/RootUI.fxml"));
		final Node editionRoot = editionLoader.load(ResourcesUtils.getInputStreamResource("viewResources/EditorUI.fxml"));
		final Node graduationRoot = graduationLoader.load(ResourcesUtils.getInputStreamResource("viewResources/CorrectorUI.fxml"));


		final ControllerFxEdition controllerEdition = (ControllerFxEdition) editionLoader.getController();
		final ControllerFxGraduation controllerGraduation = (ControllerFxGraduation) graduationLoader.getController();
		final ControllerRoot controllerRoot = (ControllerRoot) rootLoader.getController();

		final ServiceImpl service = new ServiceImpl();

		controllerRoot.setEditorNode(editionRoot);
		controllerRoot.setGraduationNode(graduationRoot);

		final Scene rootScene = new Scene(mainRoot, 1280, 720);

		Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
		StyleManager.getInstance().addUserAgentStylesheet("viewResources/MyStyle.css");

		controllerGraduation.init(service);


		controllerEdition.init(service);

		controllerRoot.setGraduationController(controllerGraduation);
		controllerRoot.setEditionController(controllerEdition);
		controllerRoot.init(service, service);
		stage.setTitle("ScanExam");
		stage.setScene(rootScene);
		stage.setMinHeight(720);
		stage.setMinWidth(720);
		stage.getIcons().add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));

		stage.show();

	}
	/*
		Pour exécuter certains tests, il faut avoir un modèle et les copies à corriger (avoir exporté le modèle pour l'examen).
		Il faudra pendant le test, sélectionner le fichier qui correspond. Une indication avant chaque test vous indique quel 
		fichier sera utile.
	 */
	/*
	@DisplayName("Premier Test : Clique Simple")
	@Test
	public void cliqueSimple(FxRobot robot) {
		robot.clickOn("#nextPageButton");
		assertTrue(true);
	}*/
	/*
	@DisplayName("Changement onglet: Editeur Correcteur")
	@Test
	public void changeTab(FxRobot robot) throws InterruptedException {
		robot.clickOn("#correctorTab");
		Thread.sleep(1000); // fait une pause (1 sec = 1000)
		robot.clickOn("#editorTab");
		Thread.sleep(1000);
		robot.clickOn("#correctorTab");	
		Thread.sleep(1000);
		robot.clickOn("#editorTab");	
		assertTrue(true);
	}*/
	/*
	@DisplayName("Ouvrir les menus")
	@Test
	public void openMenu(FxRobot robot) throws InterruptedException {
		robot.clickOn("#menuFile");
		Thread.sleep(1000);
		robot.clickOn("#menuEdit");
		Thread.sleep(1000);
		robot.clickOn("#menuView");		
		assertTrue(true);
	}*/

	// Besoin d'un pdf pour modèle
	@DisplayName("SCENARIO 1 : Editeur")
	@Test
	public void CreationTemplateAndSave(FxRobot robot) throws InterruptedException {

		
		//clique sur file editor
		robot.clickOn("#menuFile");
		Thread.sleep(1000);
		//clique sur create new template
		robot.clickOn("#newTemplate");	
		Thread.sleep(1000);
		//Selectionner et charger le pdf
		robot.clickOn("#btnBrowser");	
		Thread.sleep(10000);
		robot.clickOn("#txtFldTemplateName");	
		robot.write("TestProjet1");
		Thread.sleep(1000);
		robot.clickOn("#btnOk");
		Thread.sleep(2000);

		//Creation nouvelle question Q1
		robot.clickOn("#createBoxButton");
		robot.drag(640, 211, MouseButton.PRIMARY);
		robot.dropTo(950, 282);

		//Creation nouvelle question Q2
		robot.clickOn("#createBoxButton");
		robot.drag(640, 450, MouseButton.PRIMARY);
		robot.dropTo(950, 577);

		//Creation nouvelle question Q3
		robot.clickOn("#createBoxButton");
		robot.drag(693, 318, MouseButton.PRIMARY);
		robot.dropTo(917, 386);
		Thread.sleep(1000);
		//Reforme question 2
		robot.drag(693, 318, MouseButton.PRIMARY);
		robot.dropTo(648, 348);
		robot.drag(917, 386, MouseButton.PRIMARY);
		robot.dropTo(979, 395);
		Thread.sleep(1000);
		//Supprime la question 3
		robot.clickOn(398, 372, MouseButton.PRIMARY);
		Thread.sleep(1000);

		//Barem et nom des question pour question 1
		robot.clickOn(478, 250, MouseButton.PRIMARY);
		//Rename
		robot.clickOn(1465, 237, MouseButton.PRIMARY);
		robot.write("Question N°1");
		robot.clickOn("#questionListContainer");
		Thread.sleep(1000);
		//Barem
		robot.clickOn(1411, 288, MouseButton.PRIMARY);
		robot.write("4");
		robot.clickOn("#questionListContainer");
		Thread.sleep(1000);


		//Barem et nom des question pour question 2
		robot.clickOn(478, 312, MouseButton.PRIMARY);
		//Rename
		robot.clickOn(1465, 237, MouseButton.PRIMARY);
		robot.write("Question N°2");
		robot.clickOn("#questionListContainer");
		Thread.sleep(1000);
		//Barem
		robot.clickOn(1411, 288, MouseButton.PRIMARY);
		robot.write("2");
		robot.clickOn("#questionListContainer");
		Thread.sleep(1000);


		//Sauvegarde

		//clique sur file editor
		robot.clickOn("#menuFile");
		Thread.sleep(1000);
		//clique sur save template
		robot.clickOn("#saveTemplateButton");
		Thread.sleep(10000);


		//Chargement du template
		robot.clickOn("#menuFile");
		Thread.sleep(1000);
		robot.clickOn("#loadTemplate");	
		Thread.sleep(10000);

		robot.clickOn(477, 250, MouseButton.PRIMARY);
		robot.clickOn(477, 360, MouseButton.PRIMARY);
		robot.clickOn(477, 302, MouseButton.PRIMARY);
		
		
		
		
		//CORRECTION
		robot.clickOn("#menuFile");
		Thread.sleep(500);
		robot.clickOn("#loadStudent");
		Thread.sleep(500);
		robot.clickOn("#rbLoadModel");
		robot.clickOn("#btnBrowse");
		Thread.sleep(6000);
		robot.clickOn("#btnBrowseGraduation");
		Thread.sleep(6000);
		robot.clickOn("#txtFldGraduationName");
		robot.write("TestCorrection");
		robot.clickOn("#btnOk");
		Thread.sleep(10000);
		robot.clickOn("#correctorTab");
		Thread.sleep(500);
		
		while(true) {
			System.out.println(MouseInfo.getPointerInfo().getLocation());
		}
	}

	/*
	@DisplayName("Creation d'une nouvelle question")
	@Test
	public void CreationAeraQuestion(FxRobot robot) throws InterruptedException {
		//clique sur file editor
		robot.clickOn("#questionAreaPressed");
		robot.drag(25, 25, MouseButton.PRIMARY);
		robot.dropTo(40, 40);
		Thread.sleep(1000);

	}*/
	/*
	//PDF a plusieurs page 
	@DisplayName("Navigation dans les pages : PDF a plusieurs page seulement")
	@Test
	public void navigQuestion(FxRobot robot) throws InterruptedException {
		
		//clique sur file editor
		robot.clickOn("#menuFile");
		Thread.sleep(1000);
		//clique sur create new template
		robot.clickOn("#newTemplate");	
		Thread.sleep(1000);
		//Selectionner et charger le pdf
		robot.clickOn("#btnBrowser");	
		Thread.sleep(10000);
		robot.clickOn("#txtFldTemplateName");	
		robot.write("TestProjet1");
		Thread.sleep(1000);
		robot.clickOn("#btnOk");
		Thread.sleep(2000);
		robot.clickOn(475, 800, MouseButton.PRIMARY);
		robot.clickOn(457, 850, MouseButton.PRIMARY);
		robot.clickOn("#previousPageButton");
		Thread.sleep(500);
		robot.clickOn("#nextPageButton");
		assertTrue(true);
	}*/

	/*
	// Besoin d'un pdf pour modèle
	@DisplayName("Sauvegarde d'une nouvelle template")
	@Test
	public void saveTemplate(FxRobot robot) throws InterruptedException {
		CreationTemplateAndSave(robot);
		//clique sur file editor
		robot.clickOn("#menuFile");
		Thread.sleep(1000);
		//clique sur save template
		robot.clickOn("#saveTemplateButton");
		Thread.sleep(1000);
		assertTrue(true);
	}
	 */

	/*
	// Besoin d'un pdf pour modèle
	@DisplayName("Changer de page Editeur")
	@Test
	public void changePage(FxRobot robot) throws InterruptedException {
		CreationTemplateAndSave(robot);
		Thread.sleep(500);
		robot.clickOn("#nextPageButton");
		Thread.sleep(500);
		robot.clickOn("#nextPageButton");
		Thread.sleep(500);
		robot.clickOn("#previousPageButton");
		Thread.sleep(500);
		robot.clickOn("#previousPageButton");
		Thread.sleep(500);		
		robot.clickOn("#pageChoice");
		Thread.sleep(500);
		assertTrue(true);
	}*/
	/*
	// Besoin d'un modèle et des copies à corriger
	@DisplayName("Correction : Changement d'élève ou de question")
	@Test
	public void changeCorrection(FxRobot robot) throws InterruptedException {
		robot.clickOn("#menuFile");
		Thread.sleep(500);
		robot.clickOn("#loadStudent");
		Thread.sleep(500);
		robot.clickOn("#rbLoadModel");
		robot.clickOn("#btnBrowse");
		Thread.sleep(6000);
		robot.clickOn("#btnBrowseGraduation");
		Thread.sleep(6000);
		robot.clickOn("#txtFldGraduationName");
		robot.write("TestCorrection");
		robot.clickOn("#btnOk");
		Thread.sleep(10000);
		robot.clickOn("#correctorTab");
		Thread.sleep(500);
		robot.clickOn("#nextQuestionButton");
		Thread.sleep(500);
		robot.clickOn("#nextQuestionButton");
		Thread.sleep(500);
		robot.clickOn("#nextStudentButton");
		Thread.sleep(500);
		robot.clickOn("#prevStudentButton");
		Thread.sleep(500);
		robot.clickOn("#nextStudentButton");
		Thread.sleep(500);
		robot.clickOn("#prevStudentButton");
		Thread.sleep(500);
		robot.clickOn("#prevQuestionButton");
		Thread.sleep(500);
		robot.clickOn("#prevQuestionButton");
		assertTrue(true);
	}
	 */
}
