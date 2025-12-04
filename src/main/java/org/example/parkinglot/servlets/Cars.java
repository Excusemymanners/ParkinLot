package org.example.parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.ejb.CarsBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        List<CarDto> cars=carsBean.findAllCars();
        request.setAttribute("cars", cars);
        request.setAttribute("numberOfFreeParkingSpots", 10);
        request.getRequestDispatcher("/WEB-INF/pages/cars.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        // 1. Folosim getParameterValues (plural) pentru a primi un Array de String-uri
        // (pentru că pot fi bifate mai multe checkbox-uri)
        String[] carIdsAsString = request.getParameterValues("car_ids");

        if (carIdsAsString != null) {
            List<Long> carIds = new ArrayList<>();

            // 2. Iterăm prin array-ul primit
            // Folosim un nume diferit pentru variabila din buclă (carIdAsString vs carIdsAsString)
            for (String carIdAsString : carIdsAsString) {
                carIds.add(Long.parseLong(carIdAsString));
            }

            // 3. Apelăm metoda de ștergere din Bean
            carsBean.deleteCarsByIds(carIds);
        }

        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}