
-keep class com.google.android.** {
   *;
}
-keepattributes Signature


-dontpreverify
-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-keepattributes *Annotation*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep class com.elitecore.eliteconnectlibrary.pojo.** {
   *;
}

-keep  class  com.elitecore.eliteconnectlibrary.EliteConnect{
   *;
}
#-keep  class  com.elitecore.eliteconnectlibrary.utility.ConnectionManagerRegistration

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-dontwarn org.apache.log4j.**
-dontwarn com.google.android.maps.**
-dontwarn org.xmlpull.v1.**
-dontwarn org.apache.http.**
-dontwarn android.support.**
-dontwarn com.google.android.gms.**
-dontwarn com.sun.activation.registries.**
-dontwarn javax.activation.**
-dontwarn com.sun.mail.**
-dontwarn javax.mail.**