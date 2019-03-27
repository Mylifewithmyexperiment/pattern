package com.sterlite.dccmappfordealersterlite.activity.ServiceDetail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.orderdetailreview.OrderDetailsReviewActivity;
import com.sterlite.dccmappfordealersterlite.adapter.ServiceDetailAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityServiceDetailBinding;
import com.sterlite.dccmappfordealersterlite.helper.CustomGridLayoutManager;
import com.sterlite.dccmappfordealersterlite.helper.DataParser;
import com.sterlite.dccmappfordealersterlite.model.Store;


public class ServiceDetailActivity extends BaseActivity implements ServiceDetailContract.View, OnMapReadyCallback, View.OnTouchListener, ViewGroup.OnHierarchyChangeListener {


    private ActivityServiceDetailBinding binding;
    private ServiceDetailContract.Presenter<ServiceDetailContract.View> presenter;


    private ServiceDetailAdapter serviceDetailAdapter;

    private ViewGroup infoWindow;
    private TextView tvShopName, tvShopAddress;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private double latitude;
    private double longitude;
    private LatLng destinationlatlng;
    private Polyline polyline = null;

    private HashMap<String, Marker> markerMap = new HashMap<>();
    private CustomGridLayoutManager customGridLayoutManager;

    private int MAX_ZOOM = 100;
    private int MIN_ZOOM = 50;
    private DisplayMetrics displayMetrics;
    private int maxSize;
    private int lastCnt = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_detail);
        setUpView(binding.toolbarLayout.toolbar, binding.extraView, getString(R.string.title_select_store), true);

        presenter = new ServiceDetailPresenter<>();
        presenter.onAttach(this);
        configureMapParameters();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setMap();
        binding.mapWrapperLayout.setOnTouchListener(this);
        binding.btnbacktoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.slidingLayout.getPanelState() != SlidingUpPanelLayout.PanelState.ANCHORED) {
                    binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                    updateMapZoom(MAX_ZOOM);
                } else {
                    binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });
        binding.slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    updateMapZoom(MIN_ZOOM);
                    hideList();
                }
                if (newState != SlidingUpPanelLayout.PanelState.DRAGGING) {
                    if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        customGridLayoutManager.setScrollEnabled(true);
                    } else {
                        customGridLayoutManager.setScrollEnabled(false);
                    }
                }
            }
        });
        setUpView();
    }


    private void configureMapParameters() {
        MAX_ZOOM = AppUtils.dp2px(this, (int) getResources().getDimension(R.dimen._40sdp));
        MIN_ZOOM = AppUtils.dp2px(this, (int) getResources().getDimension(R.dimen._30sdp));
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        maxSize = (int) getResources().getDimension(R.dimen._50sdp);
    }


    private void setMap() {
        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)); //(SupportMapFragment) this.().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.info_window, null);
        this.tvShopName = (TextView) infoWindow.findViewById(R.id.tvShopName);
        this.tvShopAddress = (TextView) infoWindow.findViewById(R.id.tvShopAddress);

    }

    @Override
    public void onRetryClicked() {
        initReset();
    }

    private void initReset() {

        lastCnt = 0;
        if (mMap != null) {
            mMap.clear();
            markerMap.clear();
        }
        presenter.initReset();
    }


    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            Log.d(TAG, "MapReady");
            mMap = googleMap;
            binding.mapWrapperLayout.init(mMap, getPixelsFromDp(this, 239 + 20));
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    updateMapZoom(MIN_ZOOM);
                    hideList();
                }
            });
            setMyLocationOption();
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(final Marker marker) {
                    if (marker != null) {
                        //remove path
                        if (polyline != null) {
                            polyline.remove();
                        }

                        for (int i = 0; i < serviceDetailAdapter.getAll().size(); i++) {
                            if (AppUtils.isValidateString(marker.getSnippet()) && marker.getSnippet().equalsIgnoreCase(serviceDetailAdapter.get(i).getStoreID())) {
                                Store store = serviceDetailAdapter.get(i);
                                tvShopName.setText(store.getStoreName());
                                tvShopAddress.setText(store.getStoreAddress());
                                break;
                            }
                        }

                        binding.mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                        destinationlatlng = marker.getPosition();
                        hideList();
                        drawPath();
                    }
                    return infoWindow;
                }
            });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    for (int i = 0; i < serviceDetailAdapter.getAll().size(); i++) {
                        if (AppUtils.isValidateString(marker.getSnippet()) && marker.getSnippet().equalsIgnoreCase(serviceDetailAdapter.get(i).getStoreID())) {
                            Intent intent=new Intent(ServiceDetailActivity.this, OrderDetailsReviewActivity.class);
                            intent.putExtra(OrderDetailsReviewActivity.IS_ORDER_PREVIEW,true);
                            intent.putExtra(AppPreferencesHelper.PICK_UP_STORE,true);
                            Log.e("Store Data",serviceDetailAdapter.get(i).toString());
                            intent.putExtra(AppPreferencesHelper.STORE_ADDRESS,serviceDetailAdapter.get(i));
                           // intent.putExtra(OrderReceiveSuccess.ARG_ORDER_NO,"0987654321");
                           // intent.putExtra(OrderReceiveSuccess.ARG_TRANSACTION_NO,"0987654321");
                          //  intent.putExtra(OrderReceiveSuccess.ARG_PAID,"Rp 100.00");
                            startActivity(intent);
                            break;
                        }
                    }

                }
            });
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {

                    updateMapZoom(MIN_ZOOM);
                }
            });
            initReset();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideList() {
        binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    private void putMarkerOnMap() {
        try {

            for (int i = lastCnt; i < serviceDetailAdapter.getAll().size(); i++) {
                final Store incident = serviceDetailAdapter.get(i);
                MarkerOptions markerOptions = new MarkerOptions()
                        .title(incident.getStoreName())
                        .snippet(incident.getStoreID())
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker))
                        .position(new LatLng(incident.getLat(), incident.getLng()));
                Marker marker = mMap.addMarker(markerOptions);
                markerMap.put(incident.getStoreID(), marker);
            }
            lastCnt = serviceDetailAdapter.getAll().size();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void updateMapZoom(int padding) {
        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Store temp : serviceDetailAdapter.getAll()) {
                builder.include(new LatLng(temp.getLat(), temp.getLng()));
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }


    private void setMyLocationOption() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void drawPath() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));

        if (!AppUtils.isConnectingToInternet()) {

//            Toast.makeText(this, getResources().getString(R.string.lbl_network_connection_alert), Toast.LENGTH_LONG).show();

        } else if (destinationlatlng == null) {

//            Toast.makeText(this, this.getResources().getString(R.string.lbl_media_lat_long_not_found), Toast.LENGTH_LONG).show();

        } else if (statusOfGPS) {


            if (latitude == 0.0 && longitude == 0.0) {
//                tvKms.setText("");
            } else {
                LatLng origin = new LatLng(latitude, longitude);
                LatLng dest = destinationlatlng;

                Double distance = SphericalUtil.computeDistanceBetween(origin, dest);

                //Displaying the distance
                Float Distanceinkm = (float) (distance / 1000);
                String km = String.format("%.2f", Distanceinkm);
//                tvKms.setText(km + " Kms");

                if (distance > Constants.DEFAULT_DISTANCE_FOR_PATH_DRAW) {
                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();
                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);


                } else {
                    Log.e(TAG, "Distance  Meter : " + distance);
                }
            }


        } else {
            Toast.makeText(this, "Please on Your GPS", Toast.LENGTH_LONG).show();

        }
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.mapWrapperLayout) {
            Log.e("onTouch", motionEvent.getAction() + "");
            if (motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN) {

            }
        } else if (view.getId() == R.id.mapWrapperLayout) {
            binding.dragView.setEnabled(true);
        }
        return false;
    }

    @Override
    public void onChildViewAdded(View view, View view1) {
        if (view1.getId() == R.id.rvDashboard) {
            ViewGroup.LayoutParams layoutParams = binding.rvDashboard.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            binding.rvDashboard.requestLayout();
        }
    }

    @Override
    public void onChildViewRemoved(View view, View view1) {
        if (view1.getId() == R.id.rvDashboard) {
            ViewGroup.LayoutParams layoutParams = binding.rvDashboard.getLayoutParams();
            layoutParams.height = 0;
            binding.rvDashboard.requestLayout();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);

                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                polyline = mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    @Override
    public void onReceiverNotification(Context context, String type, Intent intent) {

    }

    @Override
    public void setUpView() {
        serviceDetailAdapter = new ServiceDetailAdapter(this, new OnRecyclerViewItemClickListener<Store>() {
            @Override
            public void onClicked(Store bean, View view, int position, ViewType viewType) {
                Intent intent=new Intent(ServiceDetailActivity.this, OrderDetailsReviewActivity.class);
                intent.putExtra(OrderDetailsReviewActivity.IS_ORDER_PREVIEW,true);
                intent.putExtra(AppPreferencesHelper.PICK_UP_STORE,true);
                intent.putExtra(AppPreferencesHelper.STORE_ADDRESS,bean);
               // intent.putExtra(OrderReceiveSuccess.ARG_TRANSACTION_NO,"0987654321");
               // intent.putExtra(OrderReceiveSuccess.ARG_PAID,"Rp 100.00");
                Log.e("Store Data",bean.toString());
                startActivity(intent);

            }

            @Override
            public void onLastItemReached() {

            }
        });
        customGridLayoutManager = new CustomGridLayoutManager(this);
        customGridLayoutManager.setScrollEnabled(false);
        binding.rvDashboard.setLayoutManager(customGridLayoutManager);
        binding.rvDashboard.setAdapter(serviceDetailAdapter);
    }

    @Override
    public void loadDataToView(ArrayList<Store> list) {
        serviceDetailAdapter.addIncidents(list);
        putMarkerOnMap();
        updateMapZoom(MIN_ZOOM);
    }

    @Override
    public void setNotRecordsFoundView(boolean isActive) {
        if (isActive) {
            AppUtils.setVisibility(binding.tvNotFound, View.VISIBLE);
        } else {
            AppUtils.setVisibility(binding.tvNotFound, View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


}
