package chapter07.validated;

import static org.testng.Assert.fail;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import au.com.aussie.chapter07.validated.Coordinate;
import au.com.aussie.util.SessionUtil;

public class CoordinateTest {
	
	@Test(dataProvider="validCoordinates")
	public void testVaildCoordinate(Integer x, Integer y){
		Coordinate c = Coordinate.builder().x(x).y(y).build();
		persist(c);
	}

	@Test(expectedExceptions=ConstraintViolationException.class)
	public void testInvalidCoordinate(){
		testVaildCoordinate(-1, -1);
		fail("Should have gotten a constraint violation");
	}
	
	private Coordinate persist(Coordinate entity){
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.persist(entity);
		tx.commit();
		session.close();
		
		return entity;
	}
	
	@DataProvider(name="validCoordinates")
	private Object[][] validCoordinates(){
		return new Object[][]{
				{1,1},
				{1,0},
				{-1,1},
				{0,1},
				{-1,0},
				{0,-1},
				{1,-1},
				{0,0}
		};
	}

}
