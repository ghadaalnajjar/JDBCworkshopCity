package org.example.dao;

import org.example.db.MySQLConnection;
import org.example.exception.MySQLConnectionException;
import org.example.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDaoJDBC implements CityDao {

    @Override
    public City findById(int id) {
        City city = new City();
        String queryFindById = "select * from city where id = ?;";

        try (
                Connection connection = MySQLConnection.mySQLGetConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryFindById)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("name"));
                city.setCountryCode(resultSet.getString("Country_Code"));
                city.setDistrict(resultSet.getString("District"));
                city.setPopulation(resultSet.getInt("Population"));
            }

        } catch (SQLException | MySQLConnectionException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> cityList = new ArrayList<>();
        String queryFindByCode = "Select * from city where country_Code = ?;";

        try (
                Connection connection = MySQLConnection.mySQLGetConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryFindByCode)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cityList.add(new City(resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Country_Code"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population")));
            }

        } catch (SQLException | MySQLConnectionException e) {
            e.printStackTrace();
        }
        return cityList;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> cityList = new ArrayList<>();
        String findByNameQuery = " Select * from city where name like '%?%';";

        try (
                Connection connection = MySQLConnection.mySQLGetConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(findByNameQuery);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                cityList.add(new City(resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Country_Code"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population")));
            }
        } catch (SQLException | MySQLConnectionException e) {
            e.printStackTrace();
        }
        return cityList;
    }

    @Override
    public List<City> findAll() {
        List<City> cityList = new ArrayList<>();
        String findAllQuery = "Select * from city;";

        try (
                Connection connection = MySQLConnection.mySQLGetConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(findAllQuery)) {

            while (resultSet.next()) {
                cityList.add(new City(resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Country_Code"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population")));
            }
        } catch (SQLException | MySQLConnectionException e) {
            e.printStackTrace();
        }
        return cityList;
    }

    @Override
    public City add(City city) {
        if (city.getId() != 0) {
            throw new IllegalArgumentException(" Exception to city is invalid ");
        }
        String addQuery = " Insert into city (Name , Country_code, District, Population) values (?, ?, ?, ?);";

        try (
                Connection connection = MySQLConnection.mySQLGetConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            preparedStatement.execute();

        } catch (SQLException | MySQLConnectionException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public City update(City city) {
        if (city.getId() == 0) {
            throw new IllegalArgumentException("Exception you cannot Update city because it is not yet existed");
        }
        String updateCityQuery = " Update city set name = '?', Country_Code = '?', District = '?', Population = ? where id = ?;";

        try (
                Connection connection = MySQLConnection.mySQLGetConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateCityQuery)) {
            preparedStatement.setInt(1, city.getId());
            preparedStatement.setString(2, city.getName());
            preparedStatement.setString(3, city.getCountryCode());
            preparedStatement.setString(4, city.getDistrict());
            preparedStatement.setInt(5, city.getPopulation());

            preparedStatement.execute();
        } catch (SQLException | MySQLConnectionException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public int delete(City city) {
        if (city.getId() == 0) {
            throw new IllegalArgumentException("Exception you cannot DELETE city because it is not yet existed");
        }
        String deleteCityQuery = "Delete from city where id = ?;";
        int result = 0;
        try (
                Connection connection = MySQLConnection.mySQLGetConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteCityQuery)) {
            preparedStatement.setInt(1, city.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException | MySQLConnectionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
