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
 * File: eval.ncsu.dre.run.YahooEvaluation.java
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

import edu.ncsu.dre.DREFramework;
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
public class YahooEvaluation {

	static Logger logger = Logger.getLogger("eval.ncsu.dre.run.YahooEvaluation");
	
	public YahooEvaluation()
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
		
			DREFramework framework = new DREFramework(new java.io.File("YahooProviderConfiguration.xml"));
						
			List<Topic> top = webtrack.getTop();
			WritableWorkbook w = Workbook.createWorkbook(new java.io.File("data/YahooReport.xls"));
			WritableSheet s = w.createSheet("Report", 0);	
			
			int lagCounter = 0;
			for(int i=0;i<top.size();i++)
			{
				Topic firstTop = top.get(i);
								
				s.addCell(new Label(0,i+lagCounter,String.valueOf(firstTop.getNum())));
				s.addCell(new Label(1,i+lagCounter,firstTop.getTitle().trim()));
				s.addCell(new Label(2,i+lagCounter,firstTop.getDesc().trim()));
				s.addCell(new Label(3,i+lagCounter,firstTop.getNarr().trim()));	
				
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
						  ResultComponent resultComponent = resultList.get(j);						  
						  s.addCell(new jxl.write.Number(4,i+lagCounter,resultComponent.getRank()));
						  s.addCell(new Label(5,i+lagCounter,resultComponent.getTitle()));
						  s.addCell(new Label(6,i+lagCounter,resultComponent.getUrl()));
						  s.addCell(new Label(7,i+lagCounter,resultComponent.getDescription()));
						  s.addCell(new Label(8,i+lagCounter,resultComponent.getDisplayUrl()));
						  s.addCell(new Label(9,i+lagCounter,resultComponent.getCacheUrl()));
						  s.addCell(new Label(10,i+lagCounter,resultComponent.getCacheSize()));
						  s.addCell(new Label(11,i+lagCounter,resultComponent.getMimetype()));
						  s.addCell(new Label(12,i+lagCounter,resultComponent.getModificationDate()));
						  lagCounter++;
					  }					  					  
				}
				catch(XMLStreamException xse)
				{
					logger.error("XMLStreamException occured!",xse);			
					throw new DRERuntimeException(DRERuntimeException.FAILED_CONTENT_EXTRACTION,null);								  
				}						  			 															
			}
			
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
		new YahooEvaluation();
	}
}
