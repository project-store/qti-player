/*
  The MIT License
  
  Copyright (c) 2009 Krzysztof Langner
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/
package com.klangner.qtieditor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.klangner.qtieditor.client.model.EditableModuleFactory;
import com.klangner.qtiplayer.client.model.Assessment;
import com.klangner.qtiplayer.client.model.AssessmentItem;
import com.klangner.qtiplayer.client.model.IDocumentLoaded;
import com.klangner.qtiplayer.client.model.LoadException;
import com.klangner.qtiplayer.client.model.XMLDocument;

public class Editor {

  /** player node id */
  private String              id;
  /** Javascript object representing this java object */
  private JavaScriptObject    jsObject;
  /** Assessment played by this player*/
  private Assessment          assessment;
  /** current item object */
  private AssessmentItem  currentItem = null;
  /** current index */
//  private int             currentIndex;
  /** Editor view */
  private ItemEditor					ItemEditor;

  
  /**
   * constructor
   * @param id
   */
  public Editor(String id){
  
    this.id = id;
    this.jsObject = JavaScriptObject.createFunction();
  }
  
  public JavaScriptObject getJavaScriptObject(){
    return jsObject;
  }
  
  /**
   * Load assessment file from server
   */
  public void loadAssessment(String url){
    
    assessment = new Assessment();
    try {
			assessment.load(GWT.getHostPageBaseURL() + url, new IDocumentLoaded(){

			public void finishedLoading(XMLDocument doc) {
			    onAssessmentLoaded();
			  }
			});
		} catch (LoadException e) {
			e.printStackTrace();
		}
  }
  
  /**
   * Create user interface
   */
  private void onAssessmentLoaded() {

    RootPanel rootPanel = RootPanel.get(id);
    // remove children
    Element element = rootPanel.getElement();
    Node node = element.getFirstChild();
    if(node != null)
      element.removeChild(node);
    
    rootPanel.add(new Toolbar());

    HorizontalPanel hsp = new HorizontalPanel();
    hsp.setWidth("100%");
    hsp.setStyleName("qe-split-panel");
    hsp.add(new ItemList(assessment));
    ItemEditor = new ItemEditor(); 
    hsp.add(ItemEditor);
    rootPanel.add(hsp);
    
    rootPanel.setStyleName("qe-editor");
    
    // Load first item
    loadAssessmentItem(0);
    
  }
  
  /**
   * Show assessment item in body part of player
   * @param index
   */
  private void loadAssessmentItem(int index){
    
    if(index >= 0 && index < assessment.getItemCount()){
      
//      currentIndex = index;
      String  url = assessment.getItemRef(index);

      currentItem = new AssessmentItem(new EditableModuleFactory());
      try {
        currentItem.load(url, new IDocumentLoaded(){

          public void finishedLoading(XMLDocument doc) {
            onItemLoaded();
          }
        });
      } catch (LoadException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * create view for assessment item
   */
  private void onItemLoaded(){
    
//    pageCounter.setText((currentIndex+1) + "/" + assessment.getItemCount());
    ItemEditor.showPage(currentItem);
//    if(currentIndex > 0)
//      prevButton.setEnabled(true);
//    else
//      prevButton.setEnabled(false);
//    
//    if(currentIndex < assessment.getItemCount()-1)
//      nextButton.setEnabled(true);
//    else
//      nextButton.setEnabled(false);
    
  }  
  
}
