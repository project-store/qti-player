package com.qtitools.player.client.controller.session;

import com.google.gwt.json.client.JSONArray;
import com.qtitools.player.client.controller.communication.Result;

public interface ItemSessionSocket {

	public JSONArray getState(int itemIndex);
	public void setState(int itemIndex, JSONArray state);
	
	public ItemSessionData getSessionData(int itemIndex);
	public void setSessionResult(int itemIndex, Result result);
	public void addSessionCheck(int itemIndex);
	public void addSessionMistake(int itemIndex);
	
	public void beginItemSession(int itemIndex);
	public void endItemSession(int itemIndex);
}
