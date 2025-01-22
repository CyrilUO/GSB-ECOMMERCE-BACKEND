package com.gsb.gsbecommercebackend.service.roles;

import com.gsb.gsbecommercebackend.dao.products.ProductDAO;
import com.gsb.gsbecommercebackend.dao.roles.RolesDao;
import com.gsb.gsbecommercebackend.model.rolesClass.Roles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {

    private final RolesDao rolesDao;

    public RolesService(RolesDao rolesDao){this.rolesDao = rolesDao;}

    public List<Roles> getRoles() throws Exception {
        if (rolesDao.getRoles().isEmpty()){
            throw new Exception("There is no roles in table");
        }
        return rolesDao.getRoles();
    }



}
