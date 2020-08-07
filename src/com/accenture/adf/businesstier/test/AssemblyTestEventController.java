package com.accenture.adf.businesstier.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.accenture.adf.businesstier.controller.EventController;
import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.entity.Event;

/** 
 * Junit test class for EventController
 * 
 */
public class AssemblyTestEventController {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;	
	private EventController controller;
	private EventDAO eventDao;

	/**
	 * Sets up initial objects required in other methods
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		eventDao = new EventDAO();		
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
		eventDao = null;		
		controller = null;
		response = null;
		request = null;
	}

	/**
	 * Test case to test the positive scenario for getAvailableEvents method
	 */
	@Test
	public void testGetAvailableEvents() {

		ArrayList<Object[]> showEvents = new ArrayList<Object[]>();

		try {
			request = new MockHttpServletRequest("GET", "/catalog.htm");
			
			controller.getAvailableEvents(request, response);		
			
			showEvents = eventDao.showAllEvents();
			
		} catch (Exception exception) {			
			fail("Exception");
		}
		assertEquals(showEvents.size() > 0, true);
	}
	
	/**
	 * Test case to test the positive scenario for displayEvent method
	 */
	@Test
	public void testDisplayEvent() {

		try {
			request = new MockHttpServletRequest("GET", "/displayEvent.htm");
			request.setParameter("eventId", "1001");
			request.setParameter("sessionId", "10001");
			controller.displayEvent(request, response);
			
			Event event = eventDao.getEvent(1001, 10001);
			
			assertEquals(1001, event.getEventid());
			
		} catch (Exception exception) {			
			fail("Exception");
		}
		
	}
	
	/**
	 * Test case to test the positive scenario for updateEvent method
	 */
	@Test
	public void testUpdateEvent() {

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
			
			controller.updateEvent(request, response);
			
			List<Object[]> event = eventDao.showAllEvents("junit_controller_name");
			
			assertEquals(true, event.size() > 0);
			
		} catch (Exception exception) {			
			fail("Exception");			
		}	
	}	
	
	/**
	 * Test case to test the positive scenario for displayEvent method
	 */
	@Test
	public void testDeleteEvent() {

		try {
			request = new MockHttpServletRequest("GET", "/deleteEvent.htm");
			
			List<Object[]> eventList = eventDao.showAllEvents("junit_controller_name");
			
			Event deleteEvent = new Event();

			for (Object[] eveObject : eventList) {
				deleteEvent.setEventid((Integer) eveObject[0]);
				deleteEvent.setSessionId((Integer) eveObject[7]);
			}
			
			request.setParameter("eventId", deleteEvent.getEventid() + "");
			request.setParameter("sessionId", deleteEvent.getSessionId() + "");
			controller.deleteEvent(request, response);
			
			Event event = eventDao.getEvent(deleteEvent.getEventid() , deleteEvent.getSessionId());
			
			assertEquals(event !=null , true);
			
		} catch (Exception exception) {			
			fail("Exception");
		}		
	}

}
