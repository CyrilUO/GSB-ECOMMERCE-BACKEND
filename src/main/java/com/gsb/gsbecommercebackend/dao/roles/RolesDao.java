package com.gsb.gsbecommercebackend.dao.roles;


import com.gsb.gsbecommercebackend.model.rolesClass.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.gsb.gsbecommercebackend.constant.AppConstants.RolesDataSource.*;

/**
 * Ensemble des requêtes à la bdd affectée à la table roles
 */


@Repository
public class RolesDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RolesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Roles> findById(int roleId) {
        String sql = "SELECT * FROM " + ROLES_TABLE + " WHERE " + ROLE_ID + " = ?";
        try {
            Roles role = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Roles r = new Roles();
                r.setRoleId(rs.getInt(ROLE_ID));
                r.setRoleName(rs.getString(ROLE_NAME));
                return r;
            }, roleId);

            return Optional.ofNullable(role);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Aucun rôle trouvé avec l'ID : " + roleId);
            return Optional.empty();
        }
    }


    public List<Roles> getRoles() {
        String sql = "SELECT " + ROLE_ID + "," + ROLE_NAME +" FROM " + ROLES_TABLE ;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Roles role = new Roles();
            role.setRoleId(rs.getInt(ROLE_ID));
            role.setRoleName(rs.getString(ROLE_NAME));
            return role;
        });
    }
}
