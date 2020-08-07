package com.accenture.adf.businesstier.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.EventCoordinator;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.EventServiceImpl;

/** 
 * Junit test case to test class EventServiceImpl
 * 
 */
public class AssemblyTestEventServiceImpl {

	private List<Object[]> eventList;
	private Visitor visitor;
	private EventServiceImpl eventServiceImpl;
	private EventDAO eventDao;

	/**
	 * Set up the objects required before execution of every method
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		eventDao = new EventDAO();
		eventServiceImpl = new EventServiceImpl();
		visitor = new Visitor();
	}

	/**
	 * Deallocates the objects after execution of every method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		eventDao = null;
		eventServiceImpl = null;
		visitor = null;
	}

	/**
	 * Test case to test the method getAllEvents
	 */
	@Test
	public void testGetAllEvents() {
		try {
			eventList = eventServiceImpl.getAllEvents();
			
			ArrayList<Object[]> showEvents = new ArrayList<Object[]>();
			
			showEvents = eventDao.showAllEvents();
				
			assertEquals(eventList.size() , showEvents.size());
		
		} catch (Exception e){
			fail("Exception");
		}
	}

	/**
	 * Test case to test the method checkEventsofVisitor
	 */
	@Test
	public void testCheckEventsofVisitor() {
		visitor.setVisitorId(1001);
		int eventid = 1001;
		int eventSessionId = 10001;
		boolean eventStatus = eventServiceImpl.checkEventsofVisitor(visitor,
				eventid, eventSessionId);
		
		boolean daoStatus = false;
		try {	
			daoStatus = eventDao.checkEventsofVisitor(visitor, eventid, eventSessionId);
		} catch (Exception exception) {
			fail("Exception");
		}
		
		assertEquals(eventStatus, daoStatus);		
	}	

	/**
	 * Junit test case for getEventCoordinator
	 */
	@Test
	public void testGetEventCoordinator() {
		List<EventCoordinator> eventCoordinatorList = eventServiceImpl
				.getAllEventCoordinators();
		Assert.assertEquals(eventCoordinatorList.size() > 0, true);
	}

	/**
	 * Junit test case for getEvent
	 */
	@Test
	public void testGetEvent() {		
		try {
			Event event = eventServiceImpl.getEvent(1001, 10001);
			
			Event daoEvent = eventDao.getEvent(1001,10001);
			
			Assert.assertEquals(event.getEventid(), daoEvent.getEventid());
		} catch(Exception e){
			fail("Exception");
		}		
	}

	/**
	 * Junit test case for updateEvent
	 */
	@Test
	public void testInsertEvent() {
		Event insertEvent = new Event();
		insertEvent.setEventtype("junit_event_type");
		insertEvent.setName("junit_event_name");
		insertEvent.setDescription("junit_event_desc");
		insertEvent.setPlace("junit_event_place");
		insertEvent.setDuration("09:00-06:00");
		insertEvent.setSeatsavailable("88");
		insertEvent.setEventCoordinatorId(101);

		eventServiceImpl.insertEvent(insertEvent);
		
		try {		
			
			List<Object[]> eventList = eventDao.showAllEvents("junit_event_type");
			
			Event selectEvent = new Event();

			for (Object[] eveObject : eventList) {
				selectEvent.setEventid((Integer) eveObject[0]);
				selectEvent.setSessionId((Integer) eveObject[7]);
			}
			
			Assert.assertTrue(selectEvent!=null);
		} catch(Exception e){
			fail("Exception");
		}	
	}

	/**
	 * Junit test case for updateEvent
	 */
	@Test
	public void testUpdateEvent() {
		List<Object[]> eventObjectList = eventServiceImpl
				.getAllEvents("junit_event_name");
		Event updateEvent = new Event();
		for (Object[] eveObject : eventObjectList) {
			updateEvent.setEventid((Integer) eveObject[0]);
			updateEvent.setSessionId((Integer) eveObject[7]);
		}
		updateEvent.setEventtype("junit_event_type_update");
		updateEvent.setName("junit_event_name_update");
		updateEvent.setDescription("junit_event_desc_update");
		updateEvent.setPlace("junit_event_place_update");
		updateEvent.setDuration("09:00-06:00_update");
		updateEvent.setSeatsavailable("99");

		eventServiceImpl.updateEvent(updateEvent);
		
		try {			
			
			Event daoEvent = eventDao.getEvent(updateEvent.getEventid(),updateEvent.getSessionId());
			
			Assert.assertEquals(updateEvent.getEventid(), daoEvent.getEventid());
			
		} catch(Exception e){
			fail("Exception");
		}		
	}

	/**
	 * Junit test case for deleteEvent
	 */
	@Test
	public void testDeleteEvent() {
		List<Object[]> eventObjectList = eventServiceImpl.getAllEvents("junit_event_name_update");

		Event deleteEvent = new Event();

		for (Object[] eveObject : eventObjectList) {
			deleteEvent.setEventid((Integer) eveObject[0]);
			deleteEvent.setSessionId((Integer) eveObject[7]);
		}

		eventServiceImpl.deleteEvent(deleteEvent.getEventid(),	deleteEvent.getSessionId());
		
		try {			
			
			Event daoEvent = eventDao.getEvent(deleteEvent.getEventid(),deleteEvent.getSessionId());
			
			Assert.assertTrue(daoEvent != null);
			
		} catch(Exception e){
			fail("Exception");
		}		
	}

}
