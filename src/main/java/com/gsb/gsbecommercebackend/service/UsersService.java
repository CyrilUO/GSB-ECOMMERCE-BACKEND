package com.gsb.gsbecommercebackend.service;
import com.gsb.gsbecommercebackend.dao.UsersDAO;
import com.gsb.gsbecommercebackend.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersDAO usersDAO;

    public UsersService (UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public List<Users> getAllUsers() throws Exception{
        if (usersDAO.getAllUsers().isEmpty()){
            throw new Exception();
        }
        return usersDAO.getAllUsers();
    }

    public Users addUser(Users users) {
        return usersDAO.addUser(users);
    }

    public Users updateUser(Users users) {
        return usersDAO.updateUser(users);
    }

    public void deleteUser(int id) {
        usersDAO.deleteUser(id);
    }

}
