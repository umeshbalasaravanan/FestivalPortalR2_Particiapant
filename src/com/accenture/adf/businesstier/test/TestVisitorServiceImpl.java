package com.accenture.adf.businesstier.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.VisitorServiceImpl;

/**
 * Junit test class for VisitorServiceImpl
 * 
 */
public class TestVisitorServiceImpl {

	private List<Object[]> visitorList;
	private Visitor visitor;
	private VisitorServiceImpl visitorServiceImpl;

	/**
	 * Set up the initial methods
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		visitorServiceImpl = new VisitorServiceImpl();
		visitor = new Visitor();
	}

	/**
	 * Deallocates the objects after execution of every method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		visitorServiceImpl = null;
		visitor = null;
	}

	/**
	 * Test case for method createVisitor
	 */
	@Test
	public void testCreateVisitor() {
		visitor.setUserName("TestVisitor");
		visitor.setFirstName("TestVFname");
		visitor.setLastName("TestVLname");
		visitor.setPassword("ttt");
		visitor.setPhoneNumber("2344");
		visitor.setAddress("TestPlace");
		boolean status = visitorServiceImpl.createVisitor(visitor);
		Assert.assertFalse(status);
	}

	/**
	 * Test case for method createVisitor
	 */
	@Test
	public void testSearchVisitor() {
		visitor = visitorServiceImpl.searchVisitor("TestVisitor", "ttt");
		Assert.assertEquals("TestVisitor", visitor.getUserName());
	}

	/**
	 * Test case for method RegisterVisitor
	 */
	@Test
	public void testRegisterVisitor() {
		visitor = visitorServiceImpl.searchVisitor("TestVisitor", "ttt");
		visitorServiceImpl.RegisterVisitor(visitor, 1001, 10001);
		visitorList = visitorServiceImpl.showRegisteredEvents(visitor);
		Assert.assertTrue(visitorList.size() > 0);
	}

	/**
	 * Test case for method showRegisteredEvents
	 */
	@Test
	public void testShowRegisteredEvents() {
		visitor = visitorServiceImpl.searchVisitor("TestVisitor", "ttt");
		visitorList = visitorServiceImpl.showRegisteredEvents(visitor);
		Assert.assertTrue(visitorList.size() > 0);
	}

	/**
	 * Test case for method updateVisitorDetails
	 */
	@Test
	public void testUpdateVisitorDetails() {
		visitor = visitorServiceImpl.searchVisitor("TestVisitor", "ttt");
		visitor.setFirstName("Updated First Name");
		int status = visitorServiceImpl.updateVisitorDetails(visitor);
		Assert.assertEquals(1, status);
	}

	/**
	 * Test case for method unregisterEvent
	 */
	@Test
	public void testUnregisterEvent() {
		visitor = visitorServiceImpl.searchVisitor("TestVisitor", "ttt");
		visitorServiceImpl.unregisterEvent(visitor, 1001, 10001);
		visitorList = visitorServiceImpl.showRegisteredEvents(visitor);
		Assert.assertTrue(visitorList.size() == 0);
	}
}
