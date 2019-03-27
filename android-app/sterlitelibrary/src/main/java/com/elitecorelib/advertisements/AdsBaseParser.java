package com.elitecorelib.advertisements;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;

import com.elitecorelib.advertisements.pojo.InLine;
import com.elitecorelib.advertisements.pojo.MediaFile;
import com.elitecorelib.advertisements.pojo.MediaFiles;
import com.elitecorelib.advertisements.pojo.Tracking;
import com.elitecorelib.advertisements.pojo.TrackingEvents;
import com.elitecorelib.advertisements.pojo.Video;
import com.elitecorelib.core.EliteSession;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by harshesh.soni on 12/11/2018.
 */

public class AdsBaseParser extends DefaultHandler {

    private String MODULE = "AdsBaseParser";

    // names of the XML tags
    static final String VIDEOADSERVINGTEMPLATE = "VideoAdServingTemplate";
    static final String AD = "Ad";
    static final String INLINE = "InLine";
    static final String VIDEO = "Video";
    static final String MEDIAFILES = "MediaFiles";
    static final String MEDIAFILE = "MediaFile";
    static final String TRACKINGEVENTS = "TrackingEvents";

    static final String ADSYSTEM = "AdSystem";
    static final String ADTITLE = "AdTitle";
    static final String DESCRIPTION = "Description";

    static final String DURATION = "Duration";
    static final String ADID = "AdID";
    static final String URL = "URL";

    private InLine inLine;
    private Video video;
    private MediaFiles mediaFiles;
    private MediaFile mediaFile;
    private TrackingEvents trackingEvents;
    private Tracking tracking;

    private final java.net.URL feedUrl;
    AdsCompleteListner mAdsCompleteListner;
    String mStringURL = "";

    public AdsBaseParser(AdsCompleteListner mAdsCompleteListner, String mStringURL) {
        try {
            this.mAdsCompleteListner = mAdsCompleteListner;
            this.mStringURL = mStringURL;
            EliteSession.eLog.d(MODULE, "Main url : " + this.mStringURL);
            this.feedUrl = new URL(this.mStringURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void parse() {
        if (inLine == null)
            inLine = new InLine();
        RootElement root = new RootElement(VIDEOADSERVINGTEMPLATE);
        Element rootitem = root.getChild(AD);
        Element item = rootitem.getChild(INLINE);
        Element itemVideo = item.getChild(VIDEO);
        Element itemTrackingEvents = item.getChild(TRACKINGEVENTS);

        item.setStartElementListener(new StartElementListener() {
            public void start(Attributes attributes) {
                inLine = new InLine();
            }
        });
        item.getChild(ADSYSTEM).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                inLine.setAdSystem(body);
            }
        });
        item.getChild(ADTITLE).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                inLine.setAdTitle(body);
            }
        });
        item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                inLine.setDescription(body);
            }
        });
        item.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                inLine.setVideo(video);
            }
        });

        itemVideo.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                video = new Video();
            }
        });
        itemVideo.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                video.setMediaFiles(mediaFiles);
            }
        });
        itemVideo.getChild(DURATION).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String Duration) {
                video.setDuration(Duration);
            }
        });
        itemVideo.getChild(ADID).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String AdID) {
                video.setAdID(AdID);
            }
        });
        itemVideo.getChild(MEDIAFILES).setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                mediaFiles = new MediaFiles();
            }
        });
        itemVideo.getChild(MEDIAFILES).setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                mediaFiles.setMediaFile(mediaFile);
            }
        });
        itemVideo.getChild(MEDIAFILES).getChild(MEDIAFILE).getChild(URL).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                mediaFile = new MediaFile();
                mediaFile.setURL(body);
            }
        });
        itemTrackingEvents.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                trackingEvents = new TrackingEvents();
            }
        });
        itemTrackingEvents.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                trackingEvents.setTracking(tracking);
                inLine.setTrackingEvents(trackingEvents);
            }
        });
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(this.getInputStream(), null);
            ArrayList<Tracking> mTrackings = parseXML(parser);

            if (mTrackings != null && mTrackings.size() != 0) {
                tracking = new Tracking();
                for (int i = 0; i < mTrackings.size(); i++) {
                    if (mTrackings.get(i).getEvent().equals("start")) {
                        tracking.setURL(mTrackings.get(i).getURL());
                    } else if (mTrackings.get(i).getEvent().equals("complete")) {
                        tracking.setCOMPLETE_URL(mTrackings.get(i).getURL());
                    } else if (mTrackings.get(i).getEvent().equals("midpoint")) {
                        tracking.setMIDPOINT_URL(mTrackings.get(i).getURL());
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        this.mAdsCompleteListner.getInLine(inLine, 10);
    }

    private ArrayList<Tracking> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Tracking> mTrackings = null;
        int eventType = parser.getEventType();
        Tracking country = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    mTrackings = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("Tracking")) {
                        country = new Tracking();
                            country.setEvent(parser.getAttributeValue(null, "event"));
                    } else if (country != null) {
                            if (name.equals("URL")) {
                                    country.setURL(parser.nextText());
                            }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("Tracking") && country != null) {
                        mTrackings.add(country);
                    }
            }
            eventType = parser.next();
        }

        return mTrackings;

    }
}