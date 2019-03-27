package com.elitecorelib.core.listner;


public abstract interface CoreTaskCompleteListner {
    // it will call when background process finish
    public abstract void onServiceTaskComplete(String result, int requestId);
  
}