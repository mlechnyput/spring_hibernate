package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {

        sessionFactory.getCurrentSession().save(user);
        //   System.out.println("Transaction open?"+ TransactionSynchronizationManager.isActualTransactionActive());

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getUserByCar(Car car) {
        String hql = "from User where car.model = :x and car.series = :y";
        Query query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("x", car.getModel());
        query.setParameter("y", car.getSeries());

        return query.getResultList();
    }

}
