package com.infoshareacademy.servlets.team;

import com.infoshareacademy.model.User;
import com.infoshareacademy.repository.TeamRepository;
import com.infoshareacademy.repository.UserRepository;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/removefromteam")
public class removeEmployeesFromTeamServlet extends HttpServlet {

    @Inject
    private UserRepository userRepository;

    @Inject
    private TeamRepository teamRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setRequestDispatcher(req, resp);
    }

    private void setRequestDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        RequestDispatcher view;
        if (req.getSession().getAttribute("username") != null){

            String[] employeesChosenForRemovalFromTeam = req.getParameterValues("selectedUsersToRemoveFromTeam");
            removeUsersFromTeamFormHandler(req, employeesChosenForRemovalFromTeam);

            view = getServletContext().getRequestDispatcher("/teamView.jsp");

            resp.sendRedirect(req.getContextPath() + "/team");
        }
        else {
            view = getServletContext().getRequestDispatcher("/badrequest_404");
        }
        view.forward(req, resp);
    }

    private void removeUsersFromTeamFormHandler(HttpServletRequest req, String[] employeesToRemoveFromTeam) {

        User loggedTeamLeader = userRepository.findByEmail(req.getSession().getAttribute("username").toString());

        List<String> chosenEmployeesIdList = new ArrayList<>(Arrays.asList(employeesToRemoveFromTeam));

        List<String>remainingUsers = loggedTeamLeader.getTeam().getUserEmail();
        remainingUsers.removeAll(chosenEmployeesIdList);

        loggedTeamLeader.getTeam().setUserEmail(remainingUsers);
        teamRepository.update(loggedTeamLeader.getTeam());

        List<User> usersToRemoveFromTeam = chosenEmployeesIdList.stream()
                .map(employee -> userRepository.findById(Integer.parseInt(employee)))
                .filter(employee -> employee.getLevelOfAccess()==1)
                .collect(Collectors.toList());

        for(User user : usersToRemoveFromTeam){
            user.setTeam(null);
            userRepository.update(user);
        }

    }
}
