package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.api.DataFactory;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import fr.istic.tools.scanexam.utils.ResourcesUtils;

public class TestReaderWithQRCode {

	PdfReader readerGood;
	PdfReader readerDirty;
	int nbPages = 8;
	int nbCopies = 4;

	PDDocument docGood;
	PDDocument docDirty;
	
	@BeforeEach
	void init() throws IOException {
		InputStream inStreamGood = ResourcesUtils.getInputStreamResource("QRCode/pfo_example_Inserted_Good.pdf");
		if (inStreamGood != null) {
			docGood = PDDocument.load(inStreamGood);
			readerGood = new PdfReaderWithoutQrCodeImpl(docGood, nbPages, nbCopies);
		}
		InputStream inStreamDirty = ResourcesUtils.getInputStreamResource("QRCode/pfo_example_Inserted_Dirty.pdf");
		if (inStreamDirty != null) {
			docDirty = PDDocument.load(inStreamDirty);
			readerDirty = new PdfReaderWithoutQrCodeImpl(docDirty, nbPages, nbCopies);
			}
	}

	@AfterEach
	void close() throws IOException{
		docGood.close();
		docDirty.close();
	}
	
	@Test
	@DisplayName("Test de lecture d'un pdf complet")
	void readPdfTestGood() {
		assertTrue(readerGood.readPDf());
	}
	
	@Test
	@DisplayName("Test de lecture d'un pdf ou il manque une page")
	void readPdfTestDirty() {
		assertTrue(readerDirty.readPDf());
	}
	
	@Test
	@DisplayName("Test getNbPagesPdf dans le pdf complet")
	void getNbPagesPdfTestGood() {
		assertTrue(readerGood.readPDf());
		assertEquals(nbCopies * nbPages, readerGood.getNbPagesPdf());
	}

	@Test
	@DisplayName("Test getNbPagesPdf dans un pdf incomplet")
	void getNbPagesPdfTestDirty() {
		assertTrue(readerDirty.readPDf());
		assertEquals(nbCopies * nbPages - 1, readerDirty.getNbPagesPdf());
	}
	
	@Test
	@DisplayName("Test getNbPagesTraitee")
	void getNbPagesTraiteePdfTest() {
		assertEquals(0, readerGood.getNbPagesTreated());
		assertTrue(readerGood.readPDf());
		assertEquals(nbCopies * nbPages, readerGood.getNbPagesTreated());
	}
	
	@Test
	@DisplayName("Test du renvoi de la structure des copies complètes au format de l'API quand toutes les pages sont là")
	void getCompleteStudentSheetsTestGood() {
		assertEquals(true, readerGood.readPDf());
		
		DataFactory dF = new DataFactory();
		List<StudentSheet> collection = new ArrayList<>();
		
		
		for(int i = 0; i<nbCopies; i++) {
			List<Integer> pages = new ArrayList<>();
			for(int j = 0; j < nbPages; j++) {
				pages.add((i * nbPages)+j);
				
			}
			collection.add(dF.createStudentSheet(i, pages));
		}
		List<StudentSheet> arr = new ArrayList<>(readerGood.getCompleteStudentSheets());		
		
		boolean bool = true;
		
		for(StudentSheet stdSh : arr){
			try{
				StudentSheet temp = collection.get(stdSh.getId());
				
				bool &= stdSh.getPosPage().containsAll(temp.getPosPage());
				bool &= temp.getPosPage().containsAll(stdSh.getPosPage());
				
			}catch(IndexOutOfBoundsException e ) {
				bool &= false;
			}
		}
		
		bool &= arr.size() == collection.size();
		
		assertEquals(true, bool);
	}

	@Test
	@DisplayName("Test du renvoi de la structure des copies complètes au format de l'API quand il manque une page")
	void getCompleteStundentSheetsTestDirty() {
		assertEquals(true, readerDirty.readPDf());
		
		DataFactory dF = new DataFactory();
		List<StudentSheet> collection = new ArrayList<>();
		
		
		for(int i = 0; i<nbCopies; i++) {
			List<Integer> pages = new ArrayList<>();
			for(int j = 0; j < nbPages; j++) {
				pages.add((i * nbPages)+j);
				
			}

			collection.add(dF.createStudentSheet(i, pages));
		}
		List<StudentSheet> arr = new ArrayList<>(readerDirty.getCompleteStudentSheets());		
		
		
		boolean bool = true;
		
		for(StudentSheet stdSh : arr){
			try{
				StudentSheet temp = collection.get(stdSh.getId());
				
				bool &= stdSh.getPosPage().containsAll(temp.getPosPage());
				bool &= temp.getPosPage().containsAll(stdSh.getPosPage());
				
			}catch(IndexOutOfBoundsException e ) {
				bool &= false;
			}
		}
		
		bool &= arr.size() == collection.size();
		assertEquals(false, bool);
	}
	
	//FIXME trouver ou il y a un print des copies
	@Test
	@DisplayName("Test du renvoi des copies non complètes sur un examen complet")
	void getUncompleteStudentSheetsGood() {
		assertTrue(readerGood.readPDf());
		assertEquals(0, readerGood.getUncompleteStudentSheets().size());
	}
	
	@Test
	@DisplayName("Test du renvoi des copies non complètes sur un examen non complet")
	void getUncompleteStudentSheetsDirty() {
		//TODO
	}
}