package com.jwhois.core;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: salil
 * Date: 4/20/12
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class UtilityTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testIsValidDom() throws Exception {
        assertFalse(Utility.isValidDom("fullcontact"));
        assertTrue(Utility.isValidDom("fullcontact.com"));
        assertFalse(Utility.isValidDom("api.fullcontact.com"));
        assertTrue(Utility.isValidDom("fullcontact.co.uk"));
    }

}