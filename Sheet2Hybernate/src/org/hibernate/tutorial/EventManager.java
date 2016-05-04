package org.hibernate.tutorial;
 
import org.hibernate.Session;
 
import java.util.*;
 
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.util.HibernateUtil;
 
public class EventManager {
 
    public static void main(String[] args) {
        EventManager mgr = new EventManager();
 
        mgr.createAndStoreEvent("My First Event", new Date());
 
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2012); // Year
        calendar.set(Calendar.MONTH, 0);   // Month, The first month of the year is JANUARY which is 0
        calendar.set(Calendar.DATE, 1);    // Day, The first day of the month has value 1.
        mgr.createAndStoreEvent("My Second Event", calendar.getTime());
 
        List events = mgr.listEvents();
        for (int i = 0; i < events.size(); i++) {
            Event theEvent = (Event) events.get(i);
            System.out.println(
                    "Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate()
            );
        }
 
        HibernateUtil.getSessionFactory().close();
    }
 
    private void createAndStoreEvent(String title, Date theDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
 
        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);
        session.save(theEvent);
 
        session.getTransaction().commit();
    }
 
    private List listEvents() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }
 
}