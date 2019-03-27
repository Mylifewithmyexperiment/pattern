
package com.sterlite.dccmappfordealersterlite.data;

import java.util.List;

import com.sterlite.dccmappfordealersterlite.data.db.DbHelper;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.PreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanLists;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {
    interface LoadIncidentCallback {

        void onTasksLoaded(List<String> tasks);

        void onDataNotAvailable();
    }

    void getMakeMyPlan(OnApiCallback<MakeMyPlanLists> onApiCallback);
}
