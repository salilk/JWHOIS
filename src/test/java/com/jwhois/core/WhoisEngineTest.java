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
        List<String> responseLines = getMockResponseForLevel1("fullcontact.com");
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
        List<String> rawData = getMockResponseForLevel1("fullcontact.com");
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

    public void testResponseFilterForLevel2() throws Exception{
        WhoisEngine engine = new WhoisEngine("fullcontact.com", false);
        engine.setLineFilter("whois.name.com");
        List<String> responseLines = getMockResponseForLevel2("fullcontact.com");
        engine.processSocketResponse(responseLines);
        assertEquals(55, responseLines.size());
        assertTrue(responseLines.contains("Domain Name:     fullcontact.com"));
        assertTrue(responseLines.contains("Registrar:       Name.com LLC"));
        assertTrue(responseLines.contains("Expiration Date: 2013-06-06 10:55:15"));
        assertTrue(responseLines.contains("Creation Date:   2000-06-06 10:55:15"));
        assertTrue(responseLines.contains("Name Servers:"));
        assertTrue(responseLines.contains("REGISTRANT CONTACT INFO"));
        assertTrue(responseLines.contains("ADMINISTRATIVE CONTACT INFO"));
        assertTrue(responseLines.contains("TECHNICAL CONTACT INFO"));
        assertTrue(responseLines.contains("BILLING CONTACT INFO"));
    }

    public void testRawDataParserForLevel2() throws Exception{
        WhoisEngine engine = new WhoisEngine("fullcontact.com", false);
        engine.setLineFilter("whois.name.com");
        List<String> rawData = getMockResponseForLevel2("fullcontact.com");
        engine.processSocketResponse(rawData);
        WhoisMap whoisMap = new WhoisMap();
        whoisMap.set( "rawdata", rawData );
        whoisMap.parse("whois.name.com");
        Map infoMap = whoisMap.getMap();

        assertTrue(infoMap.containsKey("rawdata"));
        assertTrue(infoMap.containsKey("regyinfo"));
        assertEquals(rawData, infoMap.get("rawdata"));
        assertTrue((Boolean)whoisMap.get("regyinfo.hasrecord"));
        assertEquals(2, ((Map)infoMap.get("regyinfo")).size());

        assertEquals("2013-06-06 10:55:15", whoisMap.get("regrinfo.domain.expires"));
        assertEquals("2000-06-06 10:55:15", whoisMap.get("regrinfo.domain.created"));
        assertTrue(((List)whoisMap.get("regrinfo.domain.nserver")).contains("dns1.nettica.com"));

        assertEquals("+1.3037369406", whoisMap.get("regrinfo.owner.phone"));
        assertNotNull(whoisMap.get("regrinfo.owner.info"));
        assertNotNull(whoisMap.get("regrinfo.admin.info"));
        assertNotNull(whoisMap.get("regrinfo.tech.info"));
        assertNotNull(whoisMap.get("regrinfo.bill.info"));
    }

    protected static List<String> getMockResponseForLevel1(String domain) throws IOException {
        return getMockResponseLines("src/test/data/level1/"+domain+".txt");
    }

    protected static List<String> getMockResponseForLevel2(String domain) throws IOException {
        return getMockResponseLines("src/test/data/level2/"+domain+".txt");
    }

    protected static List<String> getMockResponseLines(String filePath) throws IOException {
        List<String> responseLines = new ArrayList<String>();
        File file = new File(filePath);
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