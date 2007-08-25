package edu.ncsu.cobu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
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
		search.resize(5,1);

		final SuggestBox suggestBox = new SuggestBox();
		search.setWidget(2, 0, suggestBox);
		suggestBox.setLimit(1000);
		search.getCellFormatter().setHeight(2, 0, "22");
		suggestBox.setWidth("475");
		//suggestBox.setHeight("20");
		search.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);

		final FlowPanel searchFlowPanel = new FlowPanel();
		search.setWidget(3, 0, searchFlowPanel);
		search.getCellFormatter().setHeight(3, 0, "22");
		search.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);

		final Button simpleSearchButton = new Button();
		searchFlowPanel.add(simpleSearchButton);		
		simpleSearchButton.setText("Simple Search");

		final Button documentResearchButton = new Button();
		searchFlowPanel.add(documentResearchButton);
		documentResearchButton.setText("Document Research");

		final FlowPanel resultFlowPanel = new FlowPanel();
		search.setWidget(4, 0, resultFlowPanel);
		search.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_TOP);
		search.getCellFormatter().setHeight(4, 0, "300");

		final HTML html = new HTML("<p> </br></p>\r\n<p><font face=\"arial\" size=2 color=\"gray\">\r\nCobu is a distributed search engine prototype based on DRE framework. It helps you to search across several search engines (or service providers),\r\nfile systems, local, enterprise and internet for information, the way you wish it to be.\r\n</font></p></br>\r\n<hr>\r\n");
		resultFlowPanel.add(html);
				
		final Image image = new Image();
		search.setWidget(1, 0, image);
		search.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		search.getCellFormatter().setHeight(1, 0, "40");
		image.setUrl("Cobu-Small.JPG");
		search.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		search.getCellFormatter().setHeight(0, 0, "50");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		search.setWidget(0, 0, horizontalPanel);
		search.getCellFormatter().setHeight(0, 0, "1");
		horizontalPanel.setCellHeight(search, "25");
		horizontalPanel.setVisible(false);
		
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
		
		simpleSearchButton.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) 
			{
				SimpleSearch.Util.getInstance().doLiteralSearch(suggestBox.getText(), new AsyncCallback() {					
		            public void onSuccess(Object result) {
						String value = (String) result;
						html.setHTML(value);	            	
						
						horizontalPanel.setCellHeight(search, "25");
						horizontalPanel.setVisible(true);
						
						image.setVisible(false);
						image.setHeight("1");
						
						searchFlowPanel.setVisible(false);
						searchFlowPanel.setHeight("1");
						
						suggestBox.setVisible(false);						
						suggestBox.setHeight("1");
		            }
		            public void onFailure(Throwable caught) {
		            	html.setHTML("Error: " + caught.getMessage() + "\n" + caught.getStackTrace().toString());	            			            	
		            }		            		           		            
		        });
			}
		});

	}
}
