package edu.ncsu.cobu.server;

import edu.ncsu.cobu.client.SimpleSearch;
import edu.ncsu.dre.DREFramework;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SimpleSearchImpl extends RemoteServiceServlet implements SimpleSearch {
	
	private static final long serialVersionUID = 234827492138L;
		
	public String doLiteralSearch(String args)
	{
		DREFramework dummyFramework = new DREFramework(new java.io.File("configuration.xml"));
		javax.xml.transform.stream.StreamResult result = dummyFramework.processLiteralArtifact(new edu.ncsu.dre.impl.data.TextArtifact(args));
		
		String value = result.getOutputStream().toString();
		
		return value;
	}
}

