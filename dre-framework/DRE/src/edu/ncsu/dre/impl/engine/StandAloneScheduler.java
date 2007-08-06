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
 * File: edu.ncsu.dre.impl.engine.StandAloneScheduler.java
 * Created by: selvas
 * TimeStamp: Jul 24, 2007 11:23:32 AM
 */
package edu.ncsu.dre.impl.engine;

import java.util.*;

import edu.ncsu.dre.configuration.Component;
import edu.ncsu.dre.engine.*;

/**
 * <code>StandAloneScheduler</code> is a research scheduler that schedules the search for artifact 
 * subsets across different data service providers. It takes the list of configured service providers
 * from the framework. After all scheduled searches have completed the scheduler returns a map containing
 * the pair of objects containing the artifact subset and the search results 
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class StandAloneScheduler implements ResearchScheduler {
	
	private static final long serialVersionUID = 86761654684684654L;
	
	public Map<Object,Object> scheduleResearch(Collection<Object> artifact, List<Component> searchProviders)
	{		
		for(int i=0;i<searchProviders.size();i++)
		{
			try
			{
				Class searchProvider = Class.forName(searchProviders.get(i).getHandler());
				ServiceProvider serviceProvider = (ServiceProvider) searchProvider.newInstance();
				
				serviceProvider.gatherInformation(artifact, searchProviders.get(i).getOption());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

}
