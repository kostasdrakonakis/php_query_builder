package parsers;


import android.util.Log;

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

import activityobjects.KouneliObject;

public class XMLParserTreloKouneli extends DefaultHandler{

    private String URL_MAIN = "http://www.trelokouneli.gr/feed/?format=xml";
    String TAG = "XMLHelper";

    Boolean currTag = false;
    String currTagVal = "";
    private KouneliObject post = null;
    private ArrayList<KouneliObject> posts = new ArrayList<>();

    public ArrayList<KouneliObject> getPostsList() {
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

        else if (localName.equalsIgnoreCase("description")) {
            post.setDescription(currTagVal);

        }

        else if (localName.equalsIgnoreCase("link")) {
            post.setLink(currTagVal);
        }

        /*else if(localName.equalsIgnoreCase("encoded")){
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet get1 = new HttpGet(URL_MAIN);
            HttpResponse resp = null;
            try {
                resp = client.execute(get1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String content = null;
            try {
                content = EntityUtils.toString(resp.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Document doc = Jsoup.parse(content);
            Elements ele = doc.select("img.alignleft size-full wp-image-166849");
            post.setContent(ele.text());
        }*/

        else if (localName.equalsIgnoreCase("item")) {
            posts.add(post);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        Log.i(TAG, "START TAG: " + localName);

        currTag = true;
        currTagVal = "";
        if (localName.equals("title"))
            post = new KouneliObject();
    }

}
