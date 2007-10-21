/*
 * Licensed to the Santthosh Babu Selvadurai (sbselvad@ncsu.edu) under 
 * one or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information regarding 
 * copyright ownership.
 * 
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL 
 * THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR 
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * WITH THE SOFTWARE.
 *
 * File: eval.ncsu.dre.run.AlexaSearchEvaluation.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Sep 9, 2007 8:12:27 PM
 */
package eval.ncsu.dre.run;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.StringReader;
import java.util.*;

import org.apache.log4j.Logger;

import edu.ncsu.dre.*;
import edu.ncsu.dre.engine.*;
import edu.ncsu.dre.configuration.*;
import edu.ncsu.dre.impl.engine.*;
import edu.ncsu.dre.data.results.ResultComponent;
import edu.ncsu.dre.data.results.ResultSubSet;
import edu.ncsu.dre.exception.*;
import eval.ncsu.dre.data.*;

import jxl.Workbook;
import jxl.write.*;

/**
 * 
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class AlexaEvaluation {

	static Logger logger = Logger.getLogger("eval.ncsu.dre.run.AlexaSearchEvaluation");
	
	static boolean CONTENT_DIRECTION = true;
	
	public AlexaEvaluation()
	{
		generateReport(new java.io.File("data/TREC2002WebTrackTD.xml"));
	}
	
	public void generateReport(java.io.File configurationFile)
	{	
		try
		{
			JAXBContext binderContext = JAXBContext.newInstance("eval.ncsu.dre.data");
			Unmarshaller unmarshaller = binderContext.createUnmarshaller();
			
			Webtrack webtrack = (Webtrack) unmarshaller.unmarshal(configurationFile);						
						
			List<Topic> top = webtrack.getTop();
			WritableWorkbook w = Workbook.createWorkbook(new java.io.File("data/AlexaSearchReportCG.xls"));
			WritableSheet s = w.createSheet("Report", 0);	
			WritableSheet c = w.createSheet("Chart", 1);
			
			int lagCounter = 0;
			for(int i=0;i<top.size();i++)
			{
				Topic firstTop = top.get(i);
				
				int row = lagCounter+1;
				
				s.addCell(new Label(0,i+lagCounter,String.valueOf(i+1)));
				s.addCell(new Label(1,i+lagCounter,String.valueOf(firstTop.getNum())));
				s.addCell(new Label(2,i+lagCounter,firstTop.getTitle().trim()));
				s.addCell(new Label(3,i+lagCounter,firstTop.getDesc().trim()));
				s.addCell(new Label(4,i+lagCounter,firstTop.getNarr().trim()));	
				s.addCell(new Formula(5,i+lagCounter,"SUM($H"+String.valueOf(i+row)+":$H"+String.valueOf(i+row+4)+")/5"));
				s.addCell(new Formula(6,i+lagCounter,"SUM($H"+String.valueOf(i+row)+":$H"+String.valueOf(i+row+9)+")/10"));	
				
				c.addCell(new Label(0,i+1,String.valueOf(i+1)));
				c.addCell(new Label(1,i+1,"0.456666667"));				
				c.addCell(new Formula(2,i+1,"Report!F"+String.valueOf(i+row)));
				c.addCell(new Label(3,i+1,"0.292896825"));
				c.addCell(new Formula(4,i+1,"Report!G"+String.valueOf(i+row)));
				
				DREConfiguration configuration = new DREConfiguration();
				configuration.setSegregator(new DummySegregator());
				configuration.setScheduler(new StandAloneScheduler());
				Aggregator aggregator = new SRRSimCGXMLAggregator();
				configuration.setAggregator(aggregator);
				
				List<Component> serviceProvider = new ArrayList<Component>();
				Component alexaSearchProvider = new Component();
				alexaSearchProvider.setHandler("edu.ncsu.dre.impl.engine.AlexaSearchProvider");			
				serviceProvider.add(alexaSearchProvider);
				configuration.setServiceProvider(serviceProvider);
																								
				DREFramework framework = new DREFramework();
				if(CONTENT_DIRECTION)
					framework.setConfiguration(configuration, firstTop.getNarr().trim());
				else
					framework.setConfiguration(configuration);
				
				javax.xml.transform.stream.StreamResult result = framework.processLiteralArtifact(new edu.ncsu.dre.impl.data.TextArtifact(firstTop.getTitle().trim()));
				
				try
				{
					  StringReader stringReader = new StringReader(result.getOutputStream().toString());
					  XMLInputFactory factory = XMLInputFactory.newInstance();
					  XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(stringReader);
					  
					  JAXBContext resultBinderContext = JAXBContext.newInstance("edu.ncsu.dre.data.results");
					  Unmarshaller resultUnmarshaller = resultBinderContext.createUnmarshaller();
					  Marshaller marshaller = resultBinderContext.createMarshaller();
					  
					  ResultSubSet resultSubSet = (ResultSubSet) resultUnmarshaller.unmarshal(xmlStreamReader);
					  
					  List<ResultComponent> resultList = resultSubSet.getResult();
					  
					  for(int j=0;j<resultList.size();j++)						  						 
					  {						 						 
						  s.addCell(new Formula(7,i+lagCounter,"IF(I"+(i+row+j)+"=\"Y\",1/$J"+(i+row+j)+",0)"));
						  s.addCell(new Label(8,i+lagCounter,"Y"));
						  
						  ResultComponent resultComponent = resultList.get(j);						  
						  s.addCell(new jxl.write.Number(9,i+lagCounter,j+1));
						  s.addCell(new Label(10,i+lagCounter,resultComponent.getTitle()));
						  s.addCell(new Label(11,i+lagCounter,resultComponent.getUrl()));
						  s.addCell(new Label(12,i+lagCounter,resultComponent.getDescription()));
						  s.addCell(new Label(13,i+lagCounter,resultComponent.getDisplayUrl()));
						  s.addCell(new Label(14,i+lagCounter,resultComponent.getCacheUrl()));
						  s.addCell(new Label(15,i+lagCounter,resultComponent.getCacheSize()));
						  s.addCell(new Label(16,i+lagCounter,resultComponent.getMimetype()));
						  s.addCell(new Label(17,i+lagCounter,resultComponent.getModificationDate()));
						  lagCounter++;
					  }					  					  
				}
				catch(XMLStreamException xse)
				{
					logger.error("XMLStreamException occured!",xse);			
					throw new DRERuntimeException(DRERuntimeException.FAILED_CONTENT_EXTRACTION,null);								  
				}						  			 															
			}
			
			s.addCell(new Formula(5,555,"SUM(F1:F550)/50"));
			s.addCell(new Formula(6,555,"SUM(G1:G550)/50"));
			
			c.addCell(new Label(1,55,"TSAP@5"));				
			c.addCell(new Formula(2,55,"SUM(C1:C51)/50"));
			c.addCell(new Label(3,55,"TSAP@10"));
			c.addCell(new Formula(4,55,"SUM(E1:E51)/50"));
			
			w.write();
			w.close();
		}
		catch(JAXBException je)
		{
			logger.error("JAXBException occured!",je);			
			throw new DREIllegalArgumentException(DREIllegalArgumentException.CONFIGURATION_FILE_PARSE_ERROR,null);						
		}
		catch(java.io.IOException e)
		{
			logger.error("IOException occured!",e);			
			throw new DRERuntimeException(DRERuntimeException.FAILED_CONTENT_EXTRACTION,null);						
		}
		catch(WriteException we)
		{
			logger.error("WriteException occured!",we);			
			throw new DRERuntimeException(DRERuntimeException.FAILED_CONTENT_EXTRACTION,null);						
		}
	}
	
	public static void main(String args[])
	{
		new AlexaEvaluation();
	}
}
