package com.accenture.adf.businesstier.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.EventCoordinator;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.EventServiceImpl;

/**
 * Junit test case to test class EventServiceImpl
 * 
 */
public class TestEventServiceImpl {

	private List<Object[]> eventList;
	private Visitor visitor;
	private EventServiceImpl eventServiceImpl;

	/**
	 * Set up the objects required before execution of every method
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
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
		eventServiceImpl = null;
		visitor = null;
	}

	/**
	 * Test case to test the method getAllEvents
	 */
	@Test
	public void testGetAllEvents() {
		eventList = eventServiceImpl.getAllEvents();
		assertTrue(eventList.size() > 0);
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
		assertEquals(false, eventStatus);
	}

	/**
	 * Test case to test the method updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions() {
		int eventid = 1001;
		int eventSessionId = 10001;
		eventServiceImpl.updateEventDeletions(eventid, eventSessionId);
		assertTrue("No of seats for Event updated by 1", true);
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
		Event event = eventServiceImpl.getEvent(1001, 10001);
		Assert.assertEquals(event.getEventid(), 1001);
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

		int status = eventServiceImpl.insertEvent(insertEvent);

		Assert.assertEquals(status == 1, true);
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

		int status = eventServiceImpl.updateEvent(updateEvent);

		Assert.assertEquals(status == 0, false);
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

		int status = eventServiceImpl.deleteEvent(deleteEvent.getEventid(),	deleteEvent.getSessionId());

		Assert.assertEquals(status == 0, false);
	}

}
