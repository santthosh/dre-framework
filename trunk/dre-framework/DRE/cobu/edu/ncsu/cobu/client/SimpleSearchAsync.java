package edu.ncsu.cobu.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimpleSearchAsync {
	public void doLiteralSearch(String args, AsyncCallback callback);
}
