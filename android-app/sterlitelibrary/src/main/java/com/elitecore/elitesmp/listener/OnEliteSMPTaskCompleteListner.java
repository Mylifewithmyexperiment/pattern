package com.elitecore.elitesmp.listener;

import com.elitecore.elitesmp.pojo.Plan;

import java.util.List;
import java.util.Map;

public interface OnEliteSMPTaskCompleteListner extends IBaseEliteListner {
	
	public void getPackageList(List<Plan> planList, int requestId);
	public void getResponseMap(Map<String, String> responseMap, int requestId);
	public void getGenericResponse(String responseMap, int requestId);

}
