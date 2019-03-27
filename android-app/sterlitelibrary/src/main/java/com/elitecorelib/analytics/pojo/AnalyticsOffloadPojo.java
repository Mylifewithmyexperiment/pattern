package com.elitecorelib.analytics.pojo;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsOffloadPojo {

    List<AnalyticsOffloadSuccess> success = new ArrayList();
    List<AnalyticsOffloadFail> fail = new ArrayList();
    List<AnalyticsPolicyDetails> policyfetch = new ArrayList();

    public List<AnalyticsOffloadSuccess> getSuccess() {
        return success;
    }

    public void addSuccess(List<AnalyticsOffloadSuccess> success) {
        this.success.addAll(success);
    }

    public List<AnalyticsOffloadFail> getFail() {
        return fail;
    }

    public void addFail(List<AnalyticsOffloadFail> fail) {
        this.fail.addAll(fail);
    }

    public List<AnalyticsPolicyDetails> getPolicyfetch() {
        return policyfetch;
    }

    public void addPolicyfetch(List<AnalyticsPolicyDetails> policyfetch) {
        this.policyfetch.addAll(policyfetch);
    }
}
