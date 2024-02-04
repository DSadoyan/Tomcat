package com.digi.repository;

import com.digi.model.Address;
import com.digi.util.MyDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRepositoryImpl implements AddressRepository {
    public Address saveAddress(Address address) {


        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into address values (0,?,?,?,?,?)");
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setString(4, address.getHome());
            preparedStatement.setInt(5, address.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return address;
    }

    public Address getAddressByUserId(int userId) {


        Connection connection = MyDataSource.getConnection();
        Address address = null;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from address where user_id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("address_id");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String home = resultSet.getString("home");
                address = new Address(id, country, city, street, home, userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return address;
    }

    public void deleteAddress(int address_id) {

        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from address where address_id = ?");
            preparedStatement.setInt(1, address_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}









