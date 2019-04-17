import dao.DeveloperRepository;
import entities.Developer;
import entities.Project;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistence.HibernateUtil;

import java.util.List;

public class StartPoint {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Developer.class);
        Criteria criteria1 = session.createCriteria(Project.class);
        List<Developer> developers = criteria.add(Restrictions.between("salary", 400, 600))
                .add(Restrictions.like("surname", "D%"))
                .list();
        List<Project> projects = criteria1.add(Restrictions.eq("cost", 1000)).list();
        List<Developer> allDevelopers = session.createCriteria(Developer.class).list();
        DeveloperRepository developerRepository = new DeveloperRepository();
        List<Developer> list1;
        session.beginTransaction();
        list1 = developerRepository.getAll();

        developers.forEach(System.out::println);
        System.out.println("\n");
        projects.forEach(System.out::println);
        System.out.println("\n");
        allDevelopers.forEach(System.out::println);
        if (session.isOpen() && session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }
        HibernateUtil.shutdown();
    }
}
