package com.jwhois.core;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: salil
 * Date: 4/20/12
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class WhoisClientTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDomLookup() throws Exception {
        WhoisEngine whoisEngine = new WhoisEngine("fullcontact.com");
        WhoisMap map = whoisEngine.build();
        System.out.println(map.getMap());
    }

}