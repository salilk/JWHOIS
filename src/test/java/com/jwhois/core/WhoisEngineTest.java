package com.jwhois.core;

import junit.framework.TestCase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: salil
 * Date: 4/20/12
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class WhoisEngineTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBuild_DEEP() throws Exception {
//        WhoisEngine engine = new WhoisEngine("fullcontact.com", false);
//        engine.build();
//        engine.setLineFilter("com.whois-servers.net");
        //System.out.println("in LINE FILTER: ");
    }

    public void testResponseFilterForLevel1() throws Exception{
        WhoisEngine engine = new WhoisEngine("fullcontact.com", false);
        engine.setLineFilter("com.whois-servers.net");
        List<String> responseLines = getMockResponseLines("fullcontact.com");
        engine.processSocketResponse(responseLines);
        assertEquals(13, responseLines.size());
        assertTrue(responseLines.contains("Domain Name: FULLCONTACT.COM"));
        assertTrue(responseLines.contains("Creation Date: 06-jun-2000"));
        assertTrue(responseLines.contains("Expiration Date: 06-jun-2013"));
        assertTrue(responseLines.contains("Updated Date: 05-oct-2011"));
        assertTrue(responseLines.contains("Name Server: DNS1.NETTICA.COM"));
        assertTrue(responseLines.contains("Whois Server: whois.name.com"));
        assertTrue(responseLines.contains("Registrar: NAME.COM LLC"));
    }

    public void testRawDataParserForLevel1() throws Exception{
        WhoisEngine engine = new WhoisEngine("fullcontact.com", false);
        engine.setLineFilter("com.whois-servers.net");
        List<String> rawData = getMockResponseLines("fullcontact.com");
        engine.processSocketResponse(rawData);
        WhoisMap whoisMap = new WhoisMap();
        whoisMap.set( "rawdata", rawData );
        whoisMap.parse("com.whois-servers.net");
        Map infoMap = whoisMap.getMap();
        assertTrue(infoMap.containsKey("rawdata"));
        assertEquals(rawData, infoMap.get("rawdata"));
        assertTrue(infoMap.containsKey("regyinfo"));
        assertEquals(4, ((Map)infoMap.get("regyinfo")).size());
        assertTrue((Boolean)whoisMap.get("regyinfo.hasrecord"));
        assertEquals("whois.name.com", whoisMap.get("regyinfo.whois"));
        assertEquals("06-jun-2013", whoisMap.get("regrinfo.domain.expires"));
        assertEquals("06-jun-2000", whoisMap.get("regrinfo.domain.created"));
        assertEquals("05-oct-2011", whoisMap.get("regrinfo.domain.changed"));
        assertEquals(5, ((List)whoisMap.get("regrinfo.domain.nserver")).size());
    }

    private List<String> getMockResponseLines(String domain) throws IOException {
        List<String> responseLines = new ArrayList<String>();
        File file = new File("src/test/data/level1/"+domain+".txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while ((line = br.readLine()) != null) {
            responseLines.add(line.trim());
        }
        fis.close();

        return responseLines;
    }


}