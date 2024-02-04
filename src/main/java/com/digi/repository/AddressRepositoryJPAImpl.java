package com.digi.repository;

import com.digi.model.Address;
import com.digi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

public class AddressRepositoryJPAImpl implements AddressRepository{
    static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public Address saveAddress(Address address) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(address);
        transaction.commit();
        session.close();

        return address;

    }

    @Override
    public Address getAddressByUserId(int userId) {
        Session session = sessionFactory.openSession();
        NativeQuery<Address> nativeQuery = session.createNativeQuery
                ("select * from address where user_id = ?", Address.class);
        nativeQuery.setParameter(1,userId);

        return nativeQuery.uniqueResult();
    }

    @Override
    public void deleteAddress(int address_id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Address address = session.get(Address.class, address_id);
        session.delete(address);
        transaction.commit();
        session.close();

//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//
//        NativeQuery<Address> nativeQuery = session
//                .createNativeQuery(
//                        "delete from address where address_id = ?",
//                        Address.class
//                );
//        nativeQuery.setParameter(1, addressId);
//        nativeQuery.executeUpdate();
//        transaction.commit();
//        session.close();
    }
}
