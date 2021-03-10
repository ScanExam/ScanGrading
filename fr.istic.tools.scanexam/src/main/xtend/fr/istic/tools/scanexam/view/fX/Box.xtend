package fr.istic.tools.scanexam.view.fX

import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.lang.LiveStackFrame.PrimitiveSlot
import javafx.scene.text.Text

class Box extends Rectangle {
	
		static int ID = 0;
		static def int newID(){
			ID++;
		}
		new(String name ,int page ,BoxType type,double x, double y,double h, double w) {
			super(x,y,h,w);
			boxId = newID();
			this.page = page
			this.type = type
			listViewBox = new ListViewBox(name,this);
			setFill(Color.rgb(200, 200, 200, 0.2));
			setStroke(Color.BLACK);
			setStrokeWidth(FXSettings.BOX_BORDER_THICKNESS);
			this.text = new Text(x,y-5,name);
			
		}
	
		new(String name ,int page ,BoxType type,double x, double y) {
			this(name,page,type,x,y,0,0);
			
		}
		new(int page ,BoxType type,double x, double y) {
			this("box",page,type,x,y);

			
		}
		new(BoxType type,double x, double y) {
			this(0,type,x,y);	
		}
		new(BoxType type) {
			this(type,0,0)
		}
		
		enum BoxType {
			QUESTION,
			ID,
			QR
		}
		
		ListViewBox listViewBox;
		BoxType type;
		int boxId;
		int page;
	
		Text text
		
		
		def getText(){
			text
		}
		
		def getListViewBox(){
			listViewBox
		}
		
		def getPageNumber(){
			page
		}
		
		def getType(){
			type
		}
		def getBoxId(){
			boxId
		}
		def String getName(){
			listViewBox.name
		}
			
		
		def setBoxId(int id){
			this.boxId = id
		}
		
		def void setFocus(boolean b) {
			if (b) {
				setStroke(Color.web("#0093ff"));
			}
			else {
				setStroke(Color.BLACK);
			}
		}

	}