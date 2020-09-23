package org.example;


import org.example.dao.CityDao;
import org.example.dao.CityDaoJDBC;
import org.example.model.City;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CityDao dao = new CityDaoJDBC();

    }
}
