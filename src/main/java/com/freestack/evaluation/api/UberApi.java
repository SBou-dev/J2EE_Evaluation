package com.freestack.evaluation.api;

import com.freestack.evaluation.models.Booking;
import com.freestack.evaluation.models.UberDriver;
import com.freestack.evaluation.models.UberUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.print.Book;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UberApi {

    private static final String PERSISTANCE_UNIT_NAME = "myPostGreSqlEntityManager";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
    // private static EntityManager em = emf.createEntityManager();


    /**
     * Méthode qui enregistre un UberUser en bdd
     * @param uberUser à enregistrer
     */
    public static void enrollUser(UberUser uberUser) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(uberUser);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Méthode qui enregistre un UberDriver en bdd
     * @param uberDriver à enregistrer
     */
    public static void enrollDriver(UberDriver uberDriver) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(uberDriver);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Méthode qui réserve un conducteur pour une course et le rend indisponible sur la plateforme
     * @param uberUser
     * @return
     */
    public static Booking bookOneDriver(UberUser uberUser) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Booking booking = new Booking();
        // Récupère la liste de tous les drivers libres
        List<UberDriver> drivers = new ArrayList<>();
        drivers = em.createQuery("Select d FROM UberDriver d WHERE d.available = true").getResultList();
        if(drivers.isEmpty()) {
            // Si pas de drivers libre renvoyer null
            return null;
        }else {
            // si il y a un driver libre, on créer une reservation
            booking.setDriver(drivers.get(0));
            booking.setUser(uberUser);
            booking.setStartOfTheBooking(Instant.now());
            // change le statut du driver
            drivers.get(0).setAvailable(); // setAvailable modifie directement en !available

            em.persist(booking);
            em.getTransaction().commit();
            em.close();
            return  booking;
        }

    }

    /**
     * Méthode qui termine la course
     * @param booking1 la course à cloturer
     */
    public static void finishBooking(Booking booking1) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Booking booking = (Booking) em.createQuery("Select b FROM Booking b WHERE b.id = :id")
                .setParameter("id", booking1.getId()).getSingleResult();
        booking.setEndOfTheBooking(Instant.now());
        UberDriver driver = (UberDriver) em.createQuery("Select d FROM UberDriver d WHERE d.id = :id")
                .setParameter("id", booking1.getDriver().getId())
                .getSingleResult();
        driver.setAvailable();

        em.getTransaction().commit();
        em.close();
    }

    /**
     * Méthode qui ajoute une note à la course
     * @param booking1 la course
     * @param evaluationOfTheUser le score
     */
    public static void evaluateDriver(Booking booking1, int evaluationOfTheUser) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Booking booking = (Booking) em.createQuery("Select b FROM Booking b WHERE b.id = :id")
                .setParameter("id", booking1.getId()).getSingleResult();
        booking.setScore(evaluationOfTheUser);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Méthode qui renvoie la liste des courses d'un driver
     * @param uberDriver le driver
     * @return la liste de courses
     */
    public static List<Booking> listDriverBookings(UberDriver uberDriver) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Booking> bookings = (List<Booking>) em.createQuery("Select b FROM Booking b WHERE b.driver.id = :id")
                .setParameter("id", uberDriver.getId()).getResultList();
        em.close();
        return bookings;
    }

    /**
     * Méthode qui renvoie la liste des courses en cours
     * @return liste de course
     */
    public static List<Booking> listUnfinishedBookings() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Booking> bookings = (List<Booking>) em.createQuery("Select b FROM Booking b WHERE b.endOfTheBooking = null").getResultList();
        em.close();
        return bookings;
    }

    /**
     * Méthode qui renvoie le score moyen d'un driver
     * @param uberDriver le driver
     * @return le score moyen
     */
    public static Double meanScore(UberDriver uberDriver) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Double avgScore = (Double) em.createQuery("Select AVG(b.score) FROM Booking b WHERE b.driver.id = :id")
                .setParameter("id", uberDriver.getId()).getSingleResult();
        em.close();
        return avgScore;
    }

}
