package edu.ncsu.cobu.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SimpleSearch extends RemoteService {
	/**
	 * Utility class for simplifing access to the instance of async service.
	 */
	public static class Util {
		private static SimpleSearchAsync instance;
		public static SimpleSearchAsync getInstance(){
			if (instance == null) {
				instance = (SimpleSearchAsync) GWT.create(SimpleSearch.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL() + "/SimpleSearch");
			}
			return instance;
		}
	}
	
	public String doLiteralSearch(String args);
}
