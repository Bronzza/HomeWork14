package dao;

import entities.Developer;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistence.HibernateUtil;

import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import java.util.List;

@Log4j
public class DeveloperRepository implements DataBaseRepository<Developer> {

    @Override
    public List<Developer> getAll() {
        writeLogBefore();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            return session.createQuery(" from developer").list();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    @Override
    public List<Developer> get(String surname) {
        writeLogBefore();
        try {
            Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(Developer.class);
            Hibernate.initialize(criteria.add(Restrictions.eq("surName", surname)).list());
            return criteria.add(Restrictions.eq("surName", surname)).list();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    @Override
    public void delete(String surname) {
        List<Developer> developersToDelete = null;
        int sizeOfArray;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Developer.class);
            developersToDelete = criteria.add(Restrictions.eq("surName", surname)).list();
            sizeOfArray = developersToDelete.size();
            session.beginTransaction();
            developersToDelete.forEach(session::delete);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            writeLogAfterDelete();
            HibernateUtil.shutdown();
        }
    }

    @PrePersist
    private void writeLogBefore() {
        log.info("Transaction is starting");
    }

    @PostRemove
    private void writeLogAfterDelete() {
        log.info("Remove trasaction has been finished");
    }
}
