package com.jwhois.core;

import junit.framework.TestCase;

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
public class WhoisClientTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    //TODO: need to mock the responses (hitting internet)
    public void testDomLookup() throws Exception {
        //String[] names = new String[]{"finddesire.com", "fullcontact.com", "yahoo.com", "google.com"};
        String[] names = new String[]{"finddesire.com"};
        for (String name : names) {
            System.out.println("============ " + name + " ====================");
            WhoisEngine whoisEngine = new WhoisEngine(name);
            WhoisMap whoisMap = whoisEngine.build();
            if ((whoisMap != null) && (whoisMap.getMap() != null))
                for (String key : whoisMap.getMap().keySet()) {
                    System.out.println(key + " : " + whoisMap.get(key));
                    System.out.println("-------------------------");
                }
            System.out.println("================================\n\n");
        }
    }

}