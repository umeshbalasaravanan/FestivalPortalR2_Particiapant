package com.accenture.adf.businesstier.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.accenture.adf.businesstier.controller.VisitorController;
import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.dao.VisitorDAO;
import com.accenture.adf.businesstier.entity.Visitor;

/** 
 * Junit test case to test the class VisitorController
 * 
 */
public class AssemblyTestVisitorController {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	private VisitorController controller;
	private VisitorDAO visitorDao;
	private EventDAO eventDao;

	/**
	 * Set up initial methods required before execution of every method
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		controller = new VisitorController();
		session = new MockHttpSession();
		response = new MockHttpServletResponse();
		visitorDao = new VisitorDAO();
		eventDao = new EventDAO();
	}

	/**
	 * Deallocate objects after execution of every method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		controller = null;
		session = null;
		response = null;
		visitorDao = null;
		eventDao = null;
	}

	/**
	 * Positive test case to test the method newVisitor
	 */
	@Test
	public void testNewVisitor() {
		try {
			request = new MockHttpServletRequest("GET", "/newVistor.htm");

			request.setParameter("USERNAME", "ylee");
			request.setParameter("PASSWORD", "password");
			request.setParameter("FIRSTNAME", "TestVFname");
			request.setParameter("LASTNAME", "lname");
			request.setParameter("EMAIL", "mail");
			request.setParameter("PHONENO", "11111");
			request.setParameter("ADDRESS", "testAddress");
			controller.newVisitor(request, response);
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			assertEquals("ylee", visitor.getUserName());
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Positive test case to test the method searchVisitor
	 */
	@Test
	public void testSearchVisitor() {
		try {
			request = new MockHttpServletRequest("GET", "/searchVisitor.htm");
			request.setParameter("USERNAME", "ylee");
			request.setParameter("PASSWORD", "password");
			controller.searchVisitor(request, response);
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			assertEquals("ylee", visitor.getUserName());
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Positive test case for method registerVisitor
	 */
	@Test
	public void testRegisterVisitor() {
		try {
			request = new MockHttpServletRequest("GET", "/eventreg.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setParameter("eventId", "1001");
			request.setParameter("sessionId", "10001");
			request.setSession(session);
			request.setParameter("USERNAME", "ylee");
			request.setParameter("PASSWORD", "password");
			controller.registerVisitor(request, response);
			boolean status = eventDao
					.checkEventsofVisitor(visitor, 1001, 10001);
			assertEquals(status, true);
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Positive test case for method updateVisitor
	 */
	@Test
	public void testUpdateVisitor() {
		try {
			request = new MockHttpServletRequest("GET", "/updatevisitor.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setSession(session);
			request.setParameter("username", "ylee");
			request.setParameter("password", "password");
			request.setParameter("firstname", "fname");
			request.setParameter("lastname", "lname");
			request.setParameter("email", "mail");
			request.setParameter("phoneno", "3333");
			request.setParameter("address", "testaddress");
			controller.updateVisitor(request, response);
			visitor = visitorDao.searchUser("ylee", "password");
			
			assertEquals("testaddress", visitor.getAddress());
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Positive test case for search events by name
	 */
	@Test
	public void testSearchEventsByName() {
		try {
			request = new MockHttpServletRequest("GET",
					"/searchEventByName.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setSession(session);
			request.setParameter("eventname", "rose");
			controller.searchEventsByName(request, response);
			List<Object[]> eventList = new ArrayList<Object[]>();
			eventList = eventDao.showAllEvents("rose");
			
			assertTrue(eventList.size() != 0);
		} catch (Exception exception) {			
			fail("Exception");
		}
	}

	/**
	 * Positive test case for search events by name catalog
	 */
	@Test
	public void testSearchEventsByNameCatalog() {
		try {
			request = new MockHttpServletRequest("GET",
					"/searchEventByNameCatalog.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setSession(session);
			request.setParameter("eventname", "rose");
			controller.searchEventsByNameCatalog(request,
					response);
			List<Object[]> eventList = new ArrayList<Object[]>();
			eventList = eventDao.showAllEvents("rose");
			
			assertTrue(eventList.size() != 0);
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Test case for show events in asc order
	 */
	@Test
	public void testShowEventsAsc() {
		try {
			request = new MockHttpServletRequest("GET", "/displayasc.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setSession(session);
			controller.showEventsAsc(request, response);
			List<Object[]> eventList = new ArrayList<Object[]>();
			eventList = eventDao.showAllEventsAsc();
			
			assertTrue(eventList.size() != 0);
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Test case for show events in desc order
	 */
	@Test
	public void testShowEventsDesc() {
		try {
			request = new MockHttpServletRequest("GET", "/displaydesc.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setSession(session);
			controller.showEventsDesc(request, response);
			List<Object[]> eventList = new ArrayList<Object[]>();
			eventList = eventDao.showAllEventsDesc();
			
			assertTrue(eventList.size() != 0);
		} catch (Exception exception) {
			fail("Exception");
		}		
	}

	/**
	 * Test case for show events catalog asc order
	 */
	@Test
	public void testShowEventsCatalogAsc() {
		try {
			request = new MockHttpServletRequest("GET",
					"/displaycatalogasc.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setSession(session);
			controller.showEventsCatalogAsc(request, response);
			List<Object[]> eventList = new ArrayList<Object[]>();
			eventList = eventDao.showAllEventsAsc();
			
			assertTrue(eventList.size() != 0);
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Test case for show events catalog desc
	 */
	@Test
	public void testShowEventsCatalogDesc() {
		try {
			request = new MockHttpServletRequest("GET",
					"/displaycatalogdesc.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setSession(session);
			controller.showEventsCatalogDesc(request, response);
			List<Object[]> eventList = new ArrayList<Object[]>();
			eventList = eventDao.showAllEventsDesc();
			
			assertTrue(eventList.size() != 0);	
			
		} catch (Exception exception) {
			fail("Exception");
		}

	}

	/**
	 * Positive test case for change password
	 */
	@Test
	public void testChangePassword() {
		try {
			request = new MockHttpServletRequest("GET", "/changePWD.htm");
			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);
			request.setSession(session);
			request.setParameter("password", "password");
			controller.changePassword(request, response);
			
			Visitor daoVisitor = visitorDao.searchUser("ylee", "password");
			
			assertTrue(daoVisitor != null);
			
		} catch (Exception exception) {
			fail("Exception");
		}
	}

}
