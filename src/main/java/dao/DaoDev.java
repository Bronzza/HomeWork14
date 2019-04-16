package dao;

import entities.Developer;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.criterion.Restrictions;
import persistence.HibernateUtil;

import javax.annotation.PostConstruct;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import java.util.List;

@Log4j
public class DaoDev extends Dao<Developer> {

    @Override
    public List<Developer> getAll() {
        writeLogBefore();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            return session.createQuery(" from developers").list();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    @Override
    public List<Developer> get(String surName) {
        writeLogBefore();
        try {
            Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(Developer.class);
            Hibernate.initialize(criteria.add(Restrictions.eq("surName", surName)).list());
            return criteria.add(Restrictions.eq("surName", surName)).list();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    @Override
    public void delete(String surName) {
        List<Developer> developersToDelete = null;
        int sizeOfArray;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Developer.class);
            developersToDelete = criteria.add(Restrictions.eq("surName", surName)).list();
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
