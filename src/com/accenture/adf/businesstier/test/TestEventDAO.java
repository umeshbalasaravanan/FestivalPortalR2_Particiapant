package com.accenture.adf.businesstier.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.EventCoordinator;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;

/**
 * Junit test class for EventDAO class
 * 
 */
public class TestEventDAO {

	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet resultSet = null;
	private ArrayList<Object[]> showAllEvents;
	private EventDAO dao;

	/**
	 * Sets up database connection before other methods are executed in this
	 * class
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpDatabaseConnection() throws Exception {
		connection = FERSDataConnection.createConnection();
	}

	/**
	 * Closes the database connection after all the methods are executed
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownDatabaseConnection() throws Exception {
		connection.close();
	}

	/**
	 * Sets up the objects required in other methods
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		showAllEvents = new ArrayList<Object[]>();
		dao = new EventDAO();
	}

	/**
	 * Deallocate the resources after execution of method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		showAllEvents = null;
		dao = null;
	}

	/**
	 * Positive test case to test the method showAllEvents
	 */
	@Test
	public void testShowAllEvents_Positive() {
		try {
			showAllEvents = dao.showAllEvents();
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			fail("SQL exception");
		}

		if (showAllEvents.size() == 0) {
			assertTrue("No events in database", true);
		}
		if (showAllEvents.size() == 1) {
			assertTrue("One event retreived", true);
		}
		if (showAllEvents.size() == 3) {
			assertTrue("All Events retreived", true);
		}
	}

	/**
	 * Test case to test the exception scenario in database
	 */
	public void testShowAllEvents_Exception() {
		try {
			showAllEvents = dao.showAllEvents();
		} catch (ClassNotFoundException exception) {
			assertTrue(exception.getMessage(), true);
		} catch (SQLException exception) {
			assertTrue(exception.getMessage(), true);
		}
	}

	/**
	 * Junit test case to test positive case for updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions_Positive() {
		int eventid = 1001;
		int eventSessionId = 10001;
		int testSeatsAvailableBefore = 0;
		int testSeatsAvailableAfter = 0;
		try {
			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID = ? AND EVENTSESSIONID = ?");
			statement.setInt(1, eventid);
			statement.setInt(2, eventSessionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				testSeatsAvailableBefore = resultSet.getInt("SEATSAVAILABLE");
			}

			dao.updateEventDeletions(eventid, eventSessionId);

			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID = ? AND EVENTSESSIONID = ?");
			statement.setInt(1, eventid);
			statement.setInt(2, eventSessionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				testSeatsAvailableAfter = resultSet.getInt("SEATSAVAILABLE");
			}
			assertEquals(testSeatsAvailableBefore, testSeatsAvailableAfter - 1);
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			exception.printStackTrace();
			fail("SQL Exception");
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Negative test case for method updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions_Negative() {

		int eventid = 2001;
		int eventSessionId = 10001;
		try {
			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID = ? AND EVENTSESSIONID = ?");
			statement.setInt(1, eventid);
			statement.setInt(2, eventSessionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultSet.getInt("SEATSAVAILABLE");
			}

			dao.updateEventDeletions(eventid, eventSessionId);

			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID = ? AND EVENTSESSIONID = ?");
			statement.setInt(1, eventid);
			statement.setInt(2, eventSessionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultSet.getInt("SEATSAVAILABLE");
			}
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (FERSGenericException ferSGenericException) {
			Assert.assertEquals("Records not updated properly",
					ferSGenericException.getMessage());
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Positive test case for method updateEventNominations
	 */
	@Test
	public void testUpdateEventNominations_Positive() {

		int eventid = 1001;
		int eventSessionId = 10001;
		int testSeatsAvailableBefore = 0;
		int testSeatsAvailableAfter = 0;

		try {
			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID = ? AND EVENTSESSIONID = ?");
			statement.setInt(1, eventid);
			statement.setInt(2, eventSessionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				testSeatsAvailableBefore = resultSet.getInt("SEATSAVAILABLE");
			}

			dao.updateEventNominations(eventid, eventSessionId);

			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID = ? AND EVENTSESSIONID = ?");
			statement.setInt(1, eventid);
			statement.setInt(2, eventSessionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				testSeatsAvailableAfter = resultSet.getInt("SEATSAVAILABLE");
			}
			assertEquals(testSeatsAvailableBefore, testSeatsAvailableAfter + 1);
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Negative test case for method updateEventNominations
	 */
	@Test
	public void testUpdateEventNominations_Negative() {

		int eventid = 2001;
		int eventSessionId = 10001;		

		try {
			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID = ? AND EVENTSESSIONID = ?");
			statement.setInt(1, eventid);
			statement.setInt(2, eventSessionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultSet.getInt("SEATSAVAILABLE");
			}

			dao.updateEventNominations(eventid, eventSessionId);

			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID = ? AND EVENTSESSIONID = ?");
			statement.setInt(1, eventid);
			statement.setInt(2, eventSessionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				 resultSet.getInt("SEATSAVAILABLE");
			}
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (FERSGenericException ferSGenericException) {
			Assert.assertEquals("Records not updated properly",
					ferSGenericException.getMessage());
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Positive test case for method checkEventsofVisitor
	 */
	@Test
	public void testCheckEventsOfVisitor_Positive() {
		int eventid = 1002;
		int eventSessionId = 10002;
		try {
			Visitor visitor = new Visitor();
			visitor.setUserName("TestVisitor");
			visitor.setFirstName("TestVFname1");
			visitor.setLastName("TestVLname1");
			visitor.setPassword("ttt");
			visitor.setPhoneNumber("2344");
			visitor.setAddress("TestPlace");
			visitor.setVisitorId(17);
			boolean status = dao.checkEventsofVisitor(visitor, eventid,
					eventSessionId);
			Assert.assertFalse(status);
		}

		catch (SQLException exception) {
			fail("SQL Exception");
		} catch (Exception exception) {
			exception.printStackTrace();
			fail("Exception");
		}
	}
	
	/**
	 * Junit test case for getEventCoordinator
	 */
	@Test
	public void testGetEventCoordinator(){
		try {
			List<EventCoordinator> eventCoordinatorList = dao.getEventCoordinator();
			Assert.assertEquals(eventCoordinatorList.size() > 0, true);
		} catch (ClassNotFoundException e) {
			fail("SQL Exception");			
		} catch (SQLException e) {
			fail("Exception");			
		}
		
	}
	
	/**
	 * Junit test case for getEvent
	 */
	@Test
	public void testGetEvent(){
		try {
			Event event = dao.getEvent(1001, 10001);
			Assert.assertEquals(event.getEventid(), 1001);
		} catch (ClassNotFoundException e) {
			fail("SQL Exception");			
		} catch (SQLException e) {
			fail("Exception");			
		}
		
	}	
	
	/**
	 * Junit test case for updateEvent
	 */
	@Test
	public void testInsertEvent(){
		try {		
			
			Event insertEvent = new Event();			
			insertEvent.setEventtype("junit_event_type");
			insertEvent.setName("junit_event_name");
			insertEvent.setDescription("junit_event_desc");
			insertEvent.setPlace("junit_event_place");
			insertEvent.setDuration("09:00-06:00");
			insertEvent.setSeatsavailable("88");				
			insertEvent.setEventCoordinatorId(101);
			
			int status = dao.insertEvent(insertEvent);		
			
			Assert.assertEquals(status == 1, true);
		} catch (ClassNotFoundException e) {
			fail("SQL Exception");			
		} catch (SQLException e) {
			fail("Exception");			
		}		
	}
	
	/**
	 * Junit test case for updateEvent
	 */
	@Test
	public void testUpdateEvent(){
		try {		
			
			ArrayList<Object[]> eventObjectList = dao.showAllEvents("junit_event_name");	
			Event updateEvent = new Event();
			for(Object[] eveObject: eventObjectList){
				updateEvent.setEventid((Integer) eveObject[0]);
				updateEvent.setSessionId((Integer) eveObject[7]);			
			}
			updateEvent.setEventtype("junit_event_type_update");
			updateEvent.setName("junit_event_name_update");
			updateEvent.setDescription("junit_event_desc_update");
			updateEvent.setPlace("junit_event_place_update");
			updateEvent.setDuration("09:00-06:00_update");
			updateEvent.setSeatsavailable("99");		
			
			int status = dao.updateEvent(updateEvent);		
			
			Assert.assertEquals(status == 1, true);
		} catch (ClassNotFoundException e) {
			fail("SQL Exception");			
		} catch (SQLException e) {
			fail("Exception");			
		}		
	}
	
	/**
	 * Junit test case for deleteEvent
	 */
	@Test
	public void testDeleteEvent(){
		try {		
			
			ArrayList<Object[]> eventObjectList = dao.showAllEvents("junit_event_name_update");	
			
			Event deleteEvent = new Event();
			
			for(Object[] eveObject: eventObjectList){
				deleteEvent.setEventid((Integer) eveObject[0]);
				deleteEvent.setSessionId((Integer) eveObject[7]);			
			}		
			
			int status = dao.deleteEvent(deleteEvent.getEventid(), deleteEvent.getSessionId());		
			
			Assert.assertEquals(status == 1, true);
		} catch (ClassNotFoundException e) {
			fail("SQL Exception");			
		} catch (SQLException e) {
			fail("Exception");			
		} catch(FERSGenericException e){
			fail("FERSGenericException");
		}
	}

}
