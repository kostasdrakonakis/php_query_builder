package parsers;


import android.util.Log;

import org.jsoup.Jsoup;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import activityobjects.TromaktikoObject;

public class XMLParserTromaktiko extends DefaultHandler{

    private String URL_MAIN = "http://feeds.feedburner.com/blogspot/hyMBI?format=xml";
    String TAG = "XMLHelper";

    Boolean currTag = false;
    String currTagVal = "";
    private TromaktikoObject post = null;
    private ArrayList<TromaktikoObject> posts = new ArrayList<>();

    public ArrayList<TromaktikoObject> getPostsList() {
        return this.posts;
    }

    public void get() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser mSaxParser = factory.newSAXParser();
            XMLReader mXmlReader = mSaxParser.getXMLReader();
            mXmlReader.setContentHandler(this);
            InputStream mInputStream = new URL(URL_MAIN).openStream();
            mXmlReader.parse(new InputSource(mInputStream));
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (currTag) {
            currTagVal = currTagVal + new String(ch, start, length);
            currTag = false;
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        currTag = false;

        if (localName.equalsIgnoreCase("title")) {
            post.setTitle(currTagVal);
        }

        else if (localName.equalsIgnoreCase("pubDate")) {
            post.setDescription(currTagVal);

        }

        else if (localName.equalsIgnoreCase("link")) {
            post.setLink(currTagVal);
        }

        else if (localName.equalsIgnoreCase("item")) {
            posts.add(post);
        }
    }

    public String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        Log.i(TAG, "START TAG: " + localName);

        currTag = true;
        currTagVal = "";
        if (localName.equals("title"))
            post = new TromaktikoObject();
    }

}
