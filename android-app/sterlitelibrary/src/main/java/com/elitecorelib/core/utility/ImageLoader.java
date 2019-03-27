package com.elitecorelib.core.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.security.EasySSLSocketFactory;


import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    private static final String MODULE = ImageLoader.class.getSimpleName();
    private static ImageLoader singleImageLoader;
    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    ExecutorService executorService;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private int stub_id;

    private ImageLoader(Context context, int stub_id) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(10);
        this.stub_id = stub_id;
    }

    public static ImageLoader getSingleImageLoader(Context context, int stub_id) {
        if (singleImageLoader == null) {
            singleImageLoader = new ImageLoader(context, stub_id);
        }
        return singleImageLoader;
    }

    // final int stub_id = R.drawable.noimage;
    public void DisplayImage(String url, ImageView imageView, int stub_id) {
        imageView.setScaleType(ScaleType.FIT_XY);
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));

    }


    private Bitmap getBitmap(String url, ImageView iv) {
        Bitmap bitmap = null;
        File f = fileCache.getFile(url);

        //from SD cache
        bitmap = decodeFile(f, iv);
        if (bitmap != null)
            return bitmap;

        //from web
        //      		try {
        //      			Bitmap bitmap=null;
        //      			URL imageUrl = new URL(url);
        //      			try {
        //
        //      				HttpClient httpClient = new DefaultHttpClient();
        //      				HttpGet httpGet = new HttpGet(url);
        //      				httpGet.addHeader(BasicScheme.authenticate(
        //      						new UsernamePasswordCredentials(Constants.PROPERTY_APIKEY_VALUE, ""),
        //      						"UTF-8", false));
        //      				HttpResponse result = httpClient.execute(httpGet);
        //      				HttpEntity responseEntity = result.getEntity();
        //      				InputStream is=responseEntity.getContent();
        //      				OutputStream os = new FileOutputStream(f);
        //      				Utils.CopyStream(is, os);
        //      				os.close();
        //      				bitmap = decodeFile(f);
        //
        //
        //
        //      			} catch (Exception e) {
        //      				// TODO: handle exception
        //      				Log.i("lib", "error");
        //      			}
        //      			return bitmap;
        //
        //      		} catch (Exception ex){
        //      			ex.printStackTrace();
        //      			return null;
        //      		}
        //

        //from web
        try {

            String MAIN_URL = null;
            MAIN_URL = url;
            HttpClient client = getNewHttpClient();
            //HttpConnectionParams.setConnectionTimeout(client.getParams(), 20000); //Timeout Limit
            HttpConnectionParams.setSoTimeout(client.getParams(), 20000);
            HttpResponse response;
            try {

                if (MAIN_URL == null)
                    return null;

                EliteSession.eLog.d(MODULE, "Service executed " + MAIN_URL);
                HttpPost post = new HttpPost(MAIN_URL);
                //StringEntity se = new StringEntity(userdata[0]);
                //Log.d("lib", "doInBackground UserData "+userdata[0]);
                //se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                //post.setEntity(se);
                response = client.execute(post);
                EliteSession.eLog.d(MODULE, "Response code" + response.getStatusLine().getStatusCode());

				/*Checking response */
                if (response != null) {
                    InputStream is = response.getEntity().getContent(); //Get the data in the entity
                    OutputStream os = new FileOutputStream(f);
                    ElitelibUtility.CopyStream(is, os);
                    os.close();
                    bitmap = decodeFile(f, iv);
                    //      					BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    //      					while ((str = br.readLine()) != null) {
                    //      						buff.append(str);
                    //      					}
                }
                return bitmap;
            } catch (IOException e) {
                EliteSession.eLog.e(MODULE, e.getMessage());

            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, e.getMessage());

            } finally {

            }


        } catch (Exception e) {
            // TODO: handle exception
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
        return bitmap;
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f, ImageView iv) {


        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            //			final int REQUIRED_SIZE=200;
            //			int width_tmp=o.outWidth, height_tmp=o.outHeight;
            //			int scaleFactor = Math.min(width_tmp/iv.getWidth(), height_tmp/iv.getHeight());
            //			EliteSession.eLog.d("***************************scale factor "+scaleFactor);


            final int REQUIRED_SIZE = 200;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scaleFactor = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scaleFactor *= 2;
            }
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scaleFactor;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    public HttpClient getNewHttpClient() {
        try {
            //Set up your HTTPS connection
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

            HttpParams params = new BasicHttpParams();
            params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
            params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
            params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

            ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);

            return new DefaultHttpClient(cm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

    //Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            Bitmap bmp = getBitmap(photoToLoad.url, photoToLoad.imageView);
            memoryCache.put(photoToLoad.url, bmp);
            if (imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }

}
