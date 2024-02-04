package com.digi.repository;

import com.digi.enums.Status;
import com.digi.exceptions.BadRequestException;
import com.digi.exceptions.NotFoundException;
import com.digi.exceptions.ResourceAlreadyExistException;
import com.digi.model.Address;
import com.digi.model.User;
import com.digi.util.EmailSender;
import com.digi.util.GenerateToken;
import com.digi.util.HibernateUtil;
import com.digi.util.PasswordEncoder;
import com.digi.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;



public class UserRepositoryJPAImpl implements UserRepository {
    private static final String REDIRECT_URL = "http://localhost:8080/set-password-page.jsp";
    static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public User saveUser(User user) {
        UserValidator.validateFields(user);
        UserValidator.validatePassword(user.getPassword());
        String verifyCode = user.getVerifyCode();
        User userDB = getByEmail(user.getEmail());
        if (userDB != null) {
            throw new ResourceAlreadyExistException("Resource already exist with given email");
        }
        Session session = sessionFactory.openSession();
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        EmailSender.sendEmail(user.getEmail(), "verification code",
                "Your verification code is " + verifyCode);

        return user;

//        UserValidator.validatorFields(user);
//        UserValidator.validatePassword(user.getPassword());
//        User userDB = getByEmail(user.getEmail());
//        String verifyCode = user.getVerifyCode();
//        if (userDB != null) {
//            throw new ResourceAlreadyException("User already exist with given email");
//        }
//
//        Session session = sessionFactory.openSession();
//        user.setPassword(PasswordEncoder.encoder(user.getPassword()));
//        Transaction transaction = session.beginTransaction();
//        session.save(user);
//        transaction.commit();
//        session.close();
//
//        EmailSender.sendEmail(
//                user.getEmail(),
//                "verification code",
//                "Your verification code is " + verifyCode
//        );
//        return user;
    }

    @Override
    public User getByEmail(String email) {
        Session session = sessionFactory.openSession();
        NativeQuery<User> nativeQuery = session.createNativeQuery
                ("select * from users where email = ?", User.class);
        nativeQuery.setParameter(1, email);

        return nativeQuery.uniqueResult();
    }


    @Override
    public boolean verification(String email, String verifyCode) {
        User user = getByEmail(email);
        if (!user.getVerifyCode().equals(verifyCode)) {
            throw new BadRequestException("Wrong verify code");
        }
        user.setVerifyCode(null);
        user.setStatus(Status.ACTIVE);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        return true;

//        User user = getByEmail(email);
//        if (!user.getVerifyCode().equals(verifyCode)) {
//            throw new BadRequestException("Wrong verify code");
//        }
//
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        NativeQuery<User> nativeQuery = session.createNativeQuery
//                ("update users set verification_code =?,status = ? where email = ?", User.class);
//        nativeQuery.setParameter(1, null);
//        nativeQuery.setParameter(2, Status.ACTIVE.toString());
//        nativeQuery.setParameter(3, email);
//        nativeQuery.executeUpdate();
//        transaction.commit();
//        session.close();
//
//        return true;
    }


    @Override
    public void forgotPassword(String email) {
        User user = getByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found with given email");
        }
        String token = GenerateToken.generateResetToken();
        user.setResetToken(token);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        EmailSender.sendEmail(user.getEmail(), "Redirect url", "Please click here " + REDIRECT_URL);


//        User user = getByEmail(email);
//        if (user == null) {
//            throw new NotFoundException("User not found with given email");
//        }
//        String token = GenerateToken.generateResetToken();
//
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        NativeQuery<User> nativeQuery = session.createNativeQuery
//                ("update users set reset_token = ? where email = ?", User.class);
//        nativeQuery.setParameter(1,token);
//        nativeQuery.setParameter(2,email);
//        nativeQuery.executeUpdate();
//        transaction.commit();
//        session.close();
//
//        EmailSender.sendEmail(user.getEmail(), "Redirect url", "Please click here " + REDIRECT_URL);

    }

    @Override
    public void setPassword(String email, String newPassword, String confirmPassword) {
        if (newPassword != null) {
            UserValidator.validatePassword(newPassword);
            if (!newPassword.equals(confirmPassword)) {
                throw new BadRequestException("Passwords don't match");
            }
        }
        User user = getByEmail(email);
        if (user == null) {
            throw new BadRequestException("Your password is already changed");
        } else if (user.getResetToken() == null) {
            throw new BadRequestException("Your password is already changed");
        }
        user.setPassword(PasswordEncoder.encode(newPassword));
        user.setResetToken(null);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();

//        if (newPassword != null) {
//            UserValidator.validatePassword(newPassword);
//            if (!newPassword.equals(confirmPassword)) {
//                throw new BadRequestException("Passwords don't match");
//            }
//        }
//        User user = getByEmail(email);
//        if (user == null) {
//            throw new BadRequestException("Your password is already changed");
//        } else if (user.getResetToken() == null) {
//            throw new BadRequestException("Your password is already changed");
//        }
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        NativeQuery<User> nativeQuery = session.createNativeQuery
//                ("update users set password = ?,reset_token = ? where email = ?", User.class);
//        nativeQuery.setParameter(1,PasswordEncoder.encode(newPassword));
//        nativeQuery.setParameter(2,null);
//        nativeQuery.setParameter(3,email);
//        nativeQuery.executeUpdate();
//        transaction.commit();
//        session.close();


    }

    @Override
    public User updateUser(String email, String name, String surname, String year) {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(surname) && !StringUtils.isEmpty(year)) {
            UserValidator.validateFields(new User(name, surname, Integer.parseInt(year), email));
        }

        User user = getByEmail(email);
        user.setName(StringUtils.isEmpty(name) ? user.getName() : name);
        user.setSurname(StringUtils.isEmpty(surname) ? user.getSurname() : surname);
        user.setYear(StringUtils.isEmpty(year) ? user.getYear() : Integer.parseInt(year));
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        return user;

//        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(surname) && !StringUtils.isEmpty(year)) {
//            UserValidator.validateFields(new User(name, surname, Integer.parseInt(year), email));
//        }
//
//        User userDB = getByEmail(email);
//
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//
//        NativeQuery<User> nativeQuery = session.createNativeQuery
//                ("update users set first_name = ?, last_name = ?, year = ? where email = ?", User.class);
//        nativeQuery.setParameter(1, StringUtils.isEmpty(name) ? userDB.getName() : name);
//        nativeQuery.setParameter(2, StringUtils.isEmpty(surname) ? userDB.getSurname() : surname);
//        nativeQuery.setParameter(3, StringUtils.isEmpty(year) ? userDB.getYear() : year);
//        nativeQuery.setParameter(4, email);
//        nativeQuery.executeUpdate();
//        transaction.commit();
//        userDB = getByEmail(email);
//        session.close();
//        return userDB;

    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword, String confirmPassword) {
        if (!StringUtils.isEmpty(newPassword)) {
            UserValidator.validatePassword(newPassword);
            if (!newPassword.equals(confirmPassword)) {
                throw new BadRequestException("Passwords don't match");
            }
        }
        User user = getByEmail(email);
        if (!user.getPassword().equals(PasswordEncoder.encode(oldPassword))) {
            throw new BadRequestException("Wrong old password");
        }
        user.setPassword(PasswordEncoder.encode(newPassword));
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();

//        if (!StringUtils.isEmpty(newPassword)) {
//            UserValidator.validatePassword(newPassword);
//            if (!newPassword.equals(confirmPassword)) {
//                throw new BadRequestException("Passwords don't match");
//            }
//        }
//
//        User userDB = getByEmail(email);
//
//        if (!userDB.getPassword().equals(PasswordEncoder.encode(oldPassword))) {
//            throw new BadRequestException("Wrong old password");
//        }
//
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//
//        NativeQuery<User> nativeQuery = session
//                .createNativeQuery(
//                        "update users set password = ? where email = ?",
//                        User.class
//                );
//        nativeQuery.setParameter(1, PasswordEncoder.encode(newPassword));
//        nativeQuery.setParameter(2, email);
//        nativeQuery.executeUpdate();
//        transaction.commit();
//        session.close();

    }


    @Override
    public void deleteUser(int user_id) {
        AddressRepositoryJPAImpl aJpa = new AddressRepositoryJPAImpl();
        AddressRepositoryImpl addressRepository = new AddressRepositoryImpl();
        Address address = aJpa.getAddressByUserId(user_id);
        if (address != null) {
            aJpa.deleteAddress(address.getAddressId());
        }
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, user_id);
        session.delete(user);
        transaction.commit();
        session.close();


//        AddressRepositoryJPAImpl addressRepositoryJPA = new AddressRepositoryJPAImpl();
//        Address address = addressRepositoryJPA.getAddressByUserId(user_id);
//        if (address != null) {
//            addressRepositoryJPA.deleteAddress(address.getAddressId());
//        }
//
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//
//        NativeQuery<User> nativeQuery = session
//                .createNativeQuery(
//                        "delete from users where id = ?",
//                        User.class
//                );
//        nativeQuery.setParameter(1, user_id);
//        nativeQuery.executeUpdate();
//        transaction.commit();
//        session.close();
    }
}

