package com.accenture.adf.businesstier.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.adf.businesstier.controller.EventController;

/**
 * Junit test class for EventController
 * 
 */
public class TestEventController {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private ModelAndView modelAndView;
	private EventController controller;

	/**
	 * Sets up initial objects required in other methods
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		modelAndView = new ModelAndView();
		controller = new EventController();
		response = new MockHttpServletResponse();		
	}

	/**
	 * Deallocate the objects after execution of every method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		modelAndView = null;
		controller = null;
		response = null;
		request = null;
	}

	/**
	 * Test case to test the positive scenario for getAvailableEvents method
	 */
	@Test
	public void testGetAvailableEvents_Positive() {

		try {
			request = new MockHttpServletRequest("GET", "/catalog.htm");
			modelAndView = controller.getAvailableEvents(request, response);
		} catch (Exception exception) {
			fail("Exception");
		}
		assertEquals("/eventCatalog.jsp", modelAndView.getViewName());
	}

	/**
	 * Executes the negative scenario for getAvailableEvents method
	 */
	@Test
	public void testGetAvailableEvents_Negative() {

		try {
			request = new MockHttpServletRequest("GET", "/catalog.htm");
			modelAndView = controller.getAvailableEvents(null, response);
		} catch (Exception exception) {
			assertEquals(
					"Error in Transaction, Please re-Try. for more information check Logfile in C:\\FERSLOG folder",
					exception.getMessage());
		}
		assertEquals(null, modelAndView.getViewName());
	}
	
	/**
	 * Test case to test the positive scenario for displayEvent method
	 */
	@Test
	public void testDisplayEvent_Positive() {

		try {
			request = new MockHttpServletRequest("GET", "/displayEvent.htm");
			request.setParameter("eventId", "1001");
			request.setParameter("sessionId", "10001");
			modelAndView = controller.displayEvent(request, response);
		} catch (Exception exception) {
			fail("Exception");
		}
		assertEquals("/addEvent.jsp", modelAndView.getViewName());
	}

	/**
	 * Executes the negative scenario for displayEvent method
	 */
	@Test
	public void testDisplayEvent_Negative() {

		try {
			request = new MockHttpServletRequest("GET", "/displayEvent.htm");
			modelAndView = controller.displayEvent(null, response);
		} catch (Exception exception) {
			assertEquals(
					"Error in Transaction, Please re-Try. for more information check Logfile in C:\\FERSLOG folder",
					exception.getMessage());
		}
		assertEquals(null, modelAndView.getViewName());
	}	
	
	/**
	 * Test case to test the positive scenario for updateEvent method
	 */
	@Test
	public void testUpdateEvent_Positive() {

		try {
			request = new MockHttpServletRequest("GET", "/updateEvent.htm");		
			request.setParameter("eventId", "99");
			request.setParameter("sessionId", "99");
			request.setParameter("eventName", "junit_controller_name");
			request.setParameter("desc", "junit_controller_desc");
			request.setParameter("place", "junit_controller_place");
			request.setParameter("duration", "09:00-05:00");
			request.setParameter("eventType", "junit_controller_eventype");
			request.setParameter("ticket", "99");
			request.setParameter("isAdd", "true");		
			request.setParameter("coordinator", "101");
			request.setParameter("eventSession", "1");
			
			modelAndView = controller.updateEvent(request, response);
		} catch (Exception exception) {
			fail("Exception");			
		}
		assertEquals("/addEvent.jsp", modelAndView.getViewName());
	}

	/**
	 * Executes the negative scenario for updateEvent method
	 */
	@Test
	public void testUpdateEvent_Negative() {

		try {
			request = new MockHttpServletRequest("GET", "/updateEvent.htm");
			modelAndView = controller.updateEvent(null, response);
		} catch (Exception exception) {
			assertEquals(
					"Error in Transaction, Please re-Try. for more information check Logfile in C:\\FERSLOG folder",
					exception.getMessage());
		}
		assertEquals(null, modelAndView.getViewName());
	}
	
	/**
	 * Test case to test the positive scenario for displayEvent method
	 */
	@Test
	public void testDeleteEvent_Positive() {

		try {
			request = new MockHttpServletRequest("GET", "/deleteEvent.htm");
			request.setParameter("eventId", "1007");
			request.setParameter("sessionId", "10007");
			modelAndView = controller.deleteEvent(request, response);
		} catch (Exception exception) {
			fail("Exception");
		}
		assertEquals("/eventCatalog.jsp", modelAndView.getViewName());
	}

	/**
	 * Executes the negative scenario for displayEvent method
	 */
	@Test
	public void testDeleteEvent_Negative() {

		try {
			request = new MockHttpServletRequest("GET", "/deleteEvent.htm");
			modelAndView = controller.deleteEvent(null, response);
		} catch (Exception exception) {
			assertEquals(
					"Error in Transaction, Please re-Try. for more information check Logfile in C:\\FERSLOG folder",
					exception.getMessage());
		}
		assertEquals(null, modelAndView.getViewName());
	}	
	

}
