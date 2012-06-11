package com.jwhois.core;

import junit.framework.TestCase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public void testLineFilter() throws Exception{
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