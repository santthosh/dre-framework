package edu.ncsu.cobu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SearchInterface implements EntryPoint {		
		
	public void onModuleLoad() {
		RootPanel.get();
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");

		final Grid grid = new Grid();
		rootPanel.add(grid);
		grid.setWidth("100%");
		grid.resize(1, 2);
		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label whatHelpIsLabel = new Label("How helpful is a zillion results?! Find what you want, where you want and how you want it!");
		grid.setWidget(0, 1, whatHelpIsLabel);
		whatHelpIsLabel.setStyleName("gwt-Instructions");
		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label welcomeToCobuLabel = new Label("Welcome to Cobu!");
		grid.setWidget(0, 0, welcomeToCobuLabel);
		welcomeToCobuLabel.setStyleName("gwt-Instructions");

		final Grid operatorGrid = new Grid();
		rootPanel.add(operatorGrid);
		operatorGrid.resize(1, 1);
		operatorGrid.setSize("100%", "100%");

		final TabPanel tabPanel = new TabPanel();
		tabPanel.setSize("100%", "100%");
		operatorGrid.setWidget(0, 0, tabPanel);
		operatorGrid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		final Grid search = new Grid();
		tabPanel.add(search, "  Search  ");
		search.resize(4,1);
		search.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		search.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);

		final FlowPanel resultFlowPanel = new FlowPanel();
		search.setWidget(3, 0, resultFlowPanel);
		search.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_TOP);
		search.getCellFormatter().setHeight(3, 0, "300");

		final HTML html = new HTML("<p> </br></p>\r\n<p><font face=\"arial\" size=2 color=\"gray\">\r\nCobu is a distributed search engine prototype based on Federate Search Framework. It helps you to search across several search engines (or service providers),\r\nfile systems, local, enterprise and internet for information, the way you wish it to be.\r\n</font></p></br>\r\n<hr>\r\n");
		resultFlowPanel.add(html);
		search.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
		search.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		search.getCellFormatter().setHeight(1, 0, "50");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();		
		search.getCellFormatter().setHeight(1, 0, "1");
		horizontalPanel.setCellHeight(search, "25");
		horizontalPanel.setVisible(true);
		
		final Image image_mini = new Image();
		horizontalPanel.add(image_mini);
		image_mini.setUrl("Cobu-Mini.JPG");

		final SuggestBox suggestBox_mini = new SuggestBox();
		horizontalPanel.add(suggestBox_mini);
		suggestBox_mini.setWidth("200");
		suggestBox_mini.setLimit(1000);
		horizontalPanel.setCellVerticalAlignment(suggestBox_mini, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(suggestBox_mini, HasHorizontalAlignment.ALIGN_CENTER);

		final Button simpleSearchButton_mini = new Button();
		horizontalPanel.add(simpleSearchButton_mini);
		horizontalPanel.setCellVerticalAlignment(simpleSearchButton_mini, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(simpleSearchButton_mini, HasHorizontalAlignment.ALIGN_CENTER);
		simpleSearchButton_mini.setText("Simple Search");

		final Button documentResearchButton_mini = new Button();
		horizontalPanel.add(documentResearchButton_mini);
		horizontalPanel.setCellVerticalAlignment(documentResearchButton_mini, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(documentResearchButton_mini, HasHorizontalAlignment.ALIGN_CENTER);
		documentResearchButton_mini.setText("Document Research");

		final Grid queryAnalysis = new Grid();
		tabPanel.add(queryAnalysis, "  Query Analysis");
		queryAnalysis.resize(1, 1);

		final Grid configureEngine = new Grid();
		tabPanel.add(configureEngine, "  Configure Engine");
		configureEngine.resize(1, 1);

		final Grid documentation = new Grid();
		tabPanel.add(documentation, "  Documentation");
		documentation.resize(1, 1);
		
		tabPanel.selectTab(0);

		final VerticalPanel verticalPanel = new VerticalPanel();
		search.setWidget(2, 0, verticalPanel);
				
		final Image image = new Image();
		verticalPanel.add(image);
		verticalPanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
		search.getCellFormatter().setHeight(2, 0, "1");
		image.setUrl("Cobu-Small.JPG");

		final SuggestBox suggestBox = new SuggestBox();
		verticalPanel.add(suggestBox);
		
		suggestBox.setLimit(1000);
		suggestBox.setWidth("475");

		final FlowPanel searchFlowPanel = new FlowPanel();
		verticalPanel.add(searchFlowPanel);
		verticalPanel.setCellHorizontalAlignment(searchFlowPanel, HasHorizontalAlignment.ALIGN_CENTER);

		final Button simpleSearchButton = new Button();
		searchFlowPanel.add(simpleSearchButton);		
		simpleSearchButton.setText("Simple Search");

		final Button documentResearchButton = new Button();
		searchFlowPanel.add(documentResearchButton);
		documentResearchButton.setText("Document Research");
		
		final FlowPanel menuPanel = new FlowPanel();
		search.getCellFormatter().setHeight(0, 0, "10");
		search.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		{
			final MenuBar menuBar = new MenuBar();
			menuPanel.add(menuBar);

			final MenuItem searchHeader = menuBar.addItem("", (Command)null);
			searchHeader.setHTML("<b>Search Results</b>");			
		}
		
		simpleSearchButton.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) 
			{
				if(suggestBox.getText()==null || suggestBox.getText().trim().compareTo("")==0)
				{return;}
				SimpleSearch.Util.getInstance().doLiteralSearch(suggestBox.getText(), new AsyncCallback() {					
		            public void onSuccess(Object result) {
						String value = (String) result;
						html.setHTML(value);	            	
						
						search.setWidget(0, 0, horizontalPanel);
						search.setWidget(1, 0, menuPanel);
						verticalPanel.removeFromParent();
		            }
		            public void onFailure(Throwable caught) {
		            	html.setHTML("<hr><p><font face=\"arial\" size=2 color=\"red\">Error: " + caught.getMessage() + "</font></p>");	            			            	
		            	
		            	search.setWidget(0, 0, horizontalPanel);
		            	search.setWidget(1, 0, menuPanel);
						verticalPanel.removeFromParent();
		            }		            		           				            		          
		        });
			}
		});		
		
		suggestBox.addKeyboardListener(new KeyboardListenerAdapter() {
	 		public void onKeyPress(Widget sender, char keyCode, int modifiers) {
	 			if(keyCode!=KEY_ENTER)
	 				return;	 			
				if(suggestBox.getText()==null || suggestBox.getText().trim().compareTo("")==0)
					return;
				SimpleSearch.Util.getInstance().doLiteralSearch(suggestBox.getText(), new AsyncCallback() {					
		            public void onSuccess(Object result) {
						String value = (String) result;
						html.setHTML(value);	            	
						
						search.setWidget(0, 0, horizontalPanel);	
						search.setWidget(1, 0, menuPanel);
						verticalPanel.removeFromParent();
		            }
		            public void onFailure(Throwable caught) {
		            	html.setHTML("<hr><p><font face=\"arial\" size=2 color=\"red\">Error: " + caught.getMessage() + "</font></p>");	            			            	
		            	
		            	search.setWidget(0, 0, horizontalPanel);
		            	search.setWidget(1, 0, menuPanel);
						verticalPanel.removeFromParent();
		            }		            		           				            		          
		        });
			}
		});
		
		simpleSearchButton_mini.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) 
			{
				if(suggestBox_mini.getText()==null || suggestBox_mini.getText().trim().compareTo("")==0)
				{return;}
				SimpleSearch.Util.getInstance().doLiteralSearch(suggestBox_mini.getText(), new AsyncCallback() {					
		            public void onSuccess(Object result) {
						String value = (String) result;
						html.setHTML(value);	            	
						
						search.setWidget(0, 0, horizontalPanel);						
						search.setWidget(1, 0, menuPanel);
						verticalPanel.removeFromParent();
		            }
		            public void onFailure(Throwable caught) {
		            	html.setHTML("<hr><p><font face=\"arial\" size=2 color=\"red\">Error: " + caught.getMessage() + "</font></p>");	            			            	
		            	
		            	search.setWidget(0, 0, horizontalPanel);						
		        		search.setWidget(1, 0, menuPanel);
						verticalPanel.removeFromParent();
		            }		            		           				            		          
		        });
			}
		});
		
		suggestBox_mini.addKeyboardListener(new KeyboardListenerAdapter() {
	 		public void onKeyPress(Widget sender, char keyCode, int modifiers) {
	 			if(keyCode!=KEY_ENTER)
	 				return;	 
	 			SimpleSearch.Util.getInstance().doLiteralSearch(suggestBox_mini.getText(), new AsyncCallback() {					
		            public void onSuccess(Object result) {
						String value = (String) result;
						html.setHTML(value);	            	
						
						search.setWidget(0, 0, horizontalPanel);						
						search.setWidget(1, 0, menuPanel);
						verticalPanel.removeFromParent();
		            }
		            public void onFailure(Throwable caught) {
		            	html.setHTML("<hr><p><font face=\"arial\" size=2 color=\"red\">Error: " + caught.getMessage() + "</font></p>");	            			            	
		            	
		            	search.setWidget(0, 0, horizontalPanel);						
		        		search.setWidget(1, 0, menuPanel);
						verticalPanel.removeFromParent();
		            }		            		           				            		          
		        });
			}
		});

	}
}
