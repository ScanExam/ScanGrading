package fr.istic.tools.scanexam.qrCode.writer

import org.apache.pdfbox.pdmodel.PDDocument
import java.util.concurrent.CountDownLatch

class QRThreadWriter extends Thread implements Runnable {

	QRCodeGeneratorImpl writer
	int borneInf
	int borneMax
	PDDocument docSujetMaitre
	int nbPages
	CountDownLatch countDown
	String name
	String pathImage

	new(QRCodeGeneratorImpl writer, int inf, int max, PDDocument docSujetMaitre, int nbPages, CountDownLatch countDown,
		String name, String pathImage) {
		this.writer = writer
		this.borneInf = inf;
		this.borneMax = max
		this.docSujetMaitre = docSujetMaitre
		this.nbPages = nbPages
		this.countDown = countDown
		this.name = name
		this.pathImage = pathImage
	}

	override run() {
		for (i : borneInf ..< borneMax) {
			writer.insertQRCodeInSubject(name, docSujetMaitre, i, nbPages, pathImage)
		}
		countDown.countDown
	}

}
