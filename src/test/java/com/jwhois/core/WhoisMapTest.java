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

    public void testParseA() throws Exception {
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

    public void testParseB() throws Exception {
        WhoisMap whoisMap = new WhoisMap();
        List<String> rawData = WhoisEngineTest.getMockResponseForLevel1("google.fr");
        new WhoisEngine("google.fr").processSocketResponse(rawData);

        String blockHead = XMLHelper.getTranslateAttr( "BlockHead", "whois.nic.fr" );
        String contactHandle = XMLHelper.getTranslateAttr( "ContactHandle", "whois.nic.fr" );
        String[] blockHeads = Utility.isEmpty( blockHead ) ? null : blockHead.split( "," );
        String[] contactHandles = Utility.isEmpty( contactHandle ) ? null : contactHandle.split( "," );
        Map<String, String> contacts = XMLHelper.getTranslateMap( "Contacts", "whois.nic.fr" );
        Map output = whoisMap.parseB(rawData, contacts, blockHeads, contactHandles );

        System.out.println(output);
        assertNotNull(output);
        //TODO: assert Values
    }

    public void testParseC() throws Exception {
        WhoisMap whoisMap = new WhoisMap();
        List<String> rawData = WhoisEngineTest.getMockResponseForLevel2("melbourneit.com");
        new WhoisEngine("melbourneit.com").processSocketResponse(rawData);
        Map output = whoisMap.parseC(rawData);

        System.out.println(output);
        assertNotNull(output);
        //TODO: assert Values

    }

    public void testParse() throws Exception {
        // TODO:
    }

}