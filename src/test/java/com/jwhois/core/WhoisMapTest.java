package com.jwhois.core;

import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: salil
 * Date: 4/20/12
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class WhoisMapTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testParseA_NAME_DOT_COM() throws Exception {
        WhoisMap whoisMap = new WhoisMap();
        List<String> rawData = WhoisEngineTest.getMockResponseForLevel2("fullcontact.com");
        new WhoisEngine("fullcontact.com").processSocketResponse(rawData);
        Map<String, String> contacts = XMLHelper.getTranslateMap( "Contacts", "whois.name.com" );
        Map output = whoisMap.parseA(rawData, contacts);
        assertNotNull(output);
        assertEquals("Name.com LLC", output.get("registrar"));
        assertEquals("2000-06-06 10:55:15", output.get("creation date"));
        assertEquals(6, ((List)output.get("regrinfo.domain.nserver")).size());
        assertNotNull(output.get("regrinfo.owner"));
        assertNotNull(output.get("regrinfo.admin"));
    }

    public void testParseA_NETWORKSOLUTIONS_DOT_NET() throws Exception {
        WhoisMap whoisMap = new WhoisMap();
        List<String> rawData = WhoisEngineTest.getMockResponseForLevel2("rayofsolaris.net");
        new WhoisEngine("rayofsolaris.net.com").processSocketResponse(rawData);
        Map<String, String> contacts = XMLHelper.getTranslateMap( "Contacts", "whois.networksolutions.com" );
        Map output = whoisMap.parseA(rawData, contacts);
        assertNotNull(output);
        assertEquals("[Registrant:, S Shah, 98 Vernon Drive, Stanmore, Middlesex HA7 2BL, UK]", output.get("regrinfo.owner").toString());
        assertEquals("RAYOFSOLARIS.NET", output.get("domain name"));
        assertNotNull(output.get("regrinfo.admin,regrinfo.tech"));
        assertNotNull(output.get("regrinfo.domain.nserver"));
    }

    public void testParseB() throws Exception {
        WhoisMap whoisMap = new WhoisMap();
        List<String> rawData = WhoisEngineTest.getMockResponseForLevel1("1001games.fr");
        new WhoisEngine("1001games.fr").processSocketResponse(rawData);

        String blockHead = XMLHelper.getTranslateAttr( "BlockHead", "whois.nic.fr" );
        String contactHandle = XMLHelper.getTranslateAttr( "ContactHandle", "whois.nic.fr" );
        String[] blockHeads = Utility.isEmpty( blockHead ) ? null : blockHead.split( "," );
        String[] contactHandles = Utility.isEmpty( contactHandle ) ? null : contactHandle.split( "," );
        Map<String, String> contacts = XMLHelper.getTranslateMap( "Contacts", "whois.nic.fr" );
        Map output = whoisMap.parseB(rawData, contacts, blockHeads, contactHandles );

        assertNotNull(output);
        assertEquals("[COMBELL GROUP NV]", output.get("registrar").toString());
        assertEquals("17/01/2007", output.get("created"));
        assertEquals("ACTIVE", output.get("status"));
        assertEquals(3, ((List)output.get("nserver")).size());
        assertNotNull(output.get("regrinfo.owner"));
        assertNotNull(output.get("regrinfo.admin"));
        assertNotNull(output.get("regrinfo.tech"));
    }

    public void testParseC() throws Exception {
        WhoisMap whoisMap = new WhoisMap();
        List<String> rawData = WhoisEngineTest.getMockResponseForLevel2("melbourneit.com");
        new WhoisEngine("melbourneit.com").processSocketResponse(rawData);
        Map output = whoisMap.parseC(rawData);
        assertNotNull(output);
        assertEquals("1999-04-05", output.get("creation date....."));
        assertEquals("2021-04-05", output.get("expiry date......."));
        assertEquals("Melbourne IT Ltd", output.get("organisation name."));
        assertEquals("Account Manager", output.get("tech name........."));
        assertEquals("Account Manager", output.get("admin name........"));
        assertEquals("cdm@melbourneit.com.au", output.get("admin email......."));
    }

    public void testParse_USING_TYPE_A_RESPONSE() throws Exception {
        // TODO:
        /*
        for(Object key : output.keySet()){
            System.out.println(key + " --- " + output.get(key));
        }
         */
    }

    public void testParse_USING_TYPE_B_RESPONSE() throws Exception {
        // TODO:
    }

    public void testParse_USING_TYPE_C_RESPONSE() throws Exception {
        // TODO:
    }

}