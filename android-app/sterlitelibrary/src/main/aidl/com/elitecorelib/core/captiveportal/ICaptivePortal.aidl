// ICaptivePortal.aidl
package com.elitecorelib.core.captiveportal;
/**
 * Created by Chirag Parmar on 10-Jan-17.
 */

// Declare any non-default types here with import statements

interface ICaptivePortal {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    oneway void appResponse(int response);
}
