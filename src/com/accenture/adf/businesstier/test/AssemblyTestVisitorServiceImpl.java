package com.accenture.adf.businesstier.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.VisitorDAO;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.VisitorServiceImpl;

/** 
 * Junit test class for VisitorServiceImpl
 * 
 */
public class AssemblyTestVisitorServiceImpl {

	private List<Object[]> visitorList;
	private Visitor visitor;
	private VisitorServiceImpl visitorServiceImpl;
	private VisitorDAO visitorDAO;

	/**
	 * Set up the initial methods
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		visitorServiceImpl = new VisitorServiceImpl();
		visitor = new Visitor();
		visitorDAO =  new VisitorDAO();
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
		visitorDAO = null;
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
		visitorServiceImpl.createVisitor(visitor);
		
		try{
			Visitor visitor = visitorDAO.searchUser("TestVisitor", "ttt");
		
			Assert.assertEquals("TestVisitor", visitor.getUserName());		
			
		}catch(Exception e){
			Assert.fail("Exception");
		}		
	}

	/**
	 * Test case for method RegisterVisitor
	 */
	@Test
	public void testRegisterVisitor() {
		visitor = visitorServiceImpl.searchVisitor("TestVisitor", "ttt");
		visitorServiceImpl.RegisterVisitor(visitor, 1001, 10001);
		visitorList = visitorServiceImpl.showRegisteredEvents(visitor);
		
		ArrayList<Object[]> showEvents = new ArrayList<Object[]>();
		try {
			showEvents = visitorDAO.registeredEvents(visitor);
			
			Assert.assertTrue(visitorList.size() == showEvents.size());
		} catch(Exception e){
			Assert.fail("Exception");
		}
	}

	/**
	 * Test case for method showRegisteredEvents
	 */
	@Test
	public void testShowRegisteredEvents() {
		visitor = visitorServiceImpl.searchVisitor("TestVisitor", "ttt");
		visitorList = visitorServiceImpl.showRegisteredEvents(visitor);
		ArrayList<Object[]> showEvents = new ArrayList<Object[]>();
		try {
			showEvents = visitorDAO.registeredEvents(visitor);
			
			Assert.assertTrue(visitorList.size() == showEvents.size());
		} catch(Exception e){
			Assert.fail("Exception");
		}	
	}

	/**
	 * Test case for method updateVisitorDetails
	 */
	@Test
	public void testUpdateVisitorDetails() {
		visitor = visitorServiceImpl.searchVisitor("TestVisitor", "ttt");
		visitor.setFirstName("Updated First Name");
		visitorServiceImpl.updateVisitorDetails(visitor);
		try{
			Visitor visitor = visitorDAO.searchUser("TestVisitor", "ttt");
		
			Assert.assertEquals("Updated First Name", visitor.getFirstName());		
			
		}catch(Exception e){
			Assert.fail("Exception");
		}		
	}

}
