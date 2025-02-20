package com.gsb.gsbecommercebackend.dao.users;

import com.gsb.gsbecommercebackend.customExceptions.users.*;
import com.gsb.gsbecommercebackend.model.usersClass.Users;
import com.gsb.gsbecommercebackend.model.rolesClass.Roles;
import com.gsb.gsbecommercebackend.model.builder.UsersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gsb.gsbecommercebackend.constant.AppConstants.UserDataSource.*;
import static com.gsb.gsbecommercebackend.constant.AppConstants.RolesDataSource.*;

@Repository
public class UsersDAO {

    private final JdbcTemplate jdbcTemplate;



    @Autowired
    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Users> getAllUsers() {
        String sql = "SELECT u.*, r." + ROLE_ID + ", r." + ROLE_NAME +
                " FROM " + USERS_TABLE + " u " +
                "LEFT JOIN " + ROLES_TABLE + " r ON u." + ROLE_ID + " = r." + ROLE_ID;

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                UsersBuilder usersBuilder = new UsersBuilder();

                return usersBuilder
                        .withId(rs.getInt(USER_ID))
                        .withSurname(rs.getString(USER_SURNAME))
                        .withName(rs.getString(USER_NAME))
                        .withEmail(rs.getString(USER_EMAIL))
                        .withPassword(rs.getString(USER_PASSWORD))
                        .withDateCreation(rs.getTimestamp(USER_DATE_CREATION).toLocalDateTime())
                        .withModifiedAt(rs.getTimestamp(USER_MODIFIED_AT).toLocalDateTime())
                        .withRole(new Roles(rs.getInt(ROLE_ID), rs.getString(ROLE_NAME)))
                        .build();
            });
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la récupération de tous les utilisateurs.";
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }


    public Users getUserDataById(int userId) {
        String sql = "SELECT u.*, r." + ROLE_ID + ", r." + ROLE_NAME +
                " FROM " + USERS_TABLE + " u " +
                "LEFT JOIN " + ROLES_TABLE + " r ON u." + ROLE_ID + " = r." + ROLE_ID +
                " WHERE u." + USER_ID + " = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                UsersBuilder usersBuilder = new UsersBuilder();

                return usersBuilder
                        .withId(rs.getInt(USER_ID))
                        .withSurname(rs.getString(USER_SURNAME))
                        .withName(rs.getString(USER_NAME))
                        .withEmail(rs.getString(USER_EMAIL))
                        .withPassword(rs.getString(USER_PASSWORD))
                        .withDateCreation(rs.getTimestamp(USER_DATE_CREATION).toLocalDateTime())
                        .withModifiedAt(rs.getTimestamp(USER_MODIFIED_AT).toLocalDateTime())
                        .withRole(new Roles(rs.getInt(ROLE_ID), rs.getString(ROLE_NAME)))
                        .build();
            }, userId);
        } catch (EmptyResultDataAccessException e) {
            String errorMessage = "Utilisateur introuvable pour l'ID : " + userId;
            System.err.println(errorMessage);
            throw new DaoException(errorMessage, e);
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la récupération de l'utilisateur avec l'ID : " + userId;
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }


    public Users addUser(Users users) {
        String sql = "INSERT INTO " + USERS_TABLE + " (" +
                USER_NAME + ", " +
                USER_SURNAME + ", " +
                USER_EMAIL + ", " +
                USER_PASSWORD + ", " +
                ROLE_ID + ") " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            // Vérifie que le rôle est correctement initialisé
            if (users.getRole() == null || users.getRole().getRoleId() == 0) {
                throw new DaoException("Rôle non fourni ou invalide.");
            }

            jdbcTemplate.update(sql,
                    users.getUserName(),
                    users.getUserSurname(),
                    users.getUserEmail(),
                    users.getUserPassword(),
                    users.getRole().getRoleId());
            return users;
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de l'ajout de l'utilisateur avec l'email : " + users.getUserEmail();
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }


/* Le SQLBuilder permet de construire des requ^$etes dynamiques basées sur l'update partielles des champs
* rentrés par l'utilisateur */

    public void updateUser(Users users) {
        String sql = "UPDATE " + USERS_TABLE + " SET " +
                USER_SURNAME + " = ?, " +
                USER_NAME + " = ?, " +
                USER_EMAIL + " = ?, " +
                USER_PASSWORD + " = ?, " +
                ROLE_ID + " = COALESCE(?, " + ROLE_ID + ") " + // 🔹 NE PAS écraser l'ancien rôle si NULL
                "WHERE " + USER_ID + " = ?";


        try {
            System.out.println("Exécution de la requête avec :");
            System.out.println("Nom : " + users.getUserSurname());
            System.out.println("Prénom : " + users.getUserName());
            System.out.println("Email : " + users.getUserEmail());
            System.out.println("Mot de passe : " + users.getUserPassword());
            System.out.println("RoleId : " + (users.getRole() != null ? users.getRole().getRoleId() : "null"));
            System.out.println("UserId : " + users.getUserId());

            int rowsUpdated = jdbcTemplate.update(sql,
                    users.getUserSurname(),
                    users.getUserName(),
                    users.getUserEmail(),
                    users.getUserPassword().isEmpty() ? null : users.getUserPassword(),
                    users.getRole() != null ? users.getRole().getRoleId() : null,
                    users.getUserId());

            System.out.println("Lignes mises à jour : " + rowsUpdated);

            if (rowsUpdated == 0) {
                throw new DaoException("Aucun utilisateur trouvé avec l'ID : " + users.getUserId());
            }
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la mise à jour de l'utilisateur avec l'ID : " + users.getUserId();
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }



    public void deleteUser(int id) {
        String sql = "DELETE FROM " + USERS_TABLE + " WHERE " + USER_ID + " = ?";

        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la suppression de l'utilisateur avec l'ID : " + id;
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }

    public Optional<Users> findById(int id) {
        String sql = "SELECT u.*, r." + ROLE_ID + ", r." + ROLE_NAME +
                " FROM " + USERS_TABLE + " u " +
                "LEFT JOIN " + ROLES_TABLE + " r ON u." + ROLE_ID + " = r." + ROLE_ID +
                " WHERE u." + USER_ID + " = ?";

        try {
            Users user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UsersBuilder()
                    .withId(rs.getInt(USER_ID))
                    .withSurname(rs.getString(USER_SURNAME))
                    .withName(rs.getString(USER_NAME))
                    .withEmail(rs.getString(USER_EMAIL))
                    .withPassword(rs.getString(USER_PASSWORD))
                    .withDateCreation(rs.getTimestamp(USER_DATE_CREATION).toLocalDateTime())
                    .withModifiedAt(rs.getTimestamp(USER_MODIFIED_AT) != null
                            ? rs.getTimestamp(USER_MODIFIED_AT).toLocalDateTime()
                            : null) // Si le champ est null, retournez null
                    .withRole(rs.getInt(ROLE_ID) != 0
                            ? new Roles(rs.getInt(ROLE_ID), rs.getString(ROLE_NAME))
                            : null) // Gérer les rôles inexistants
                    .build(), id);

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Aucun utilisateur trouvé avec l'ID : " + id);
            return Optional.empty();
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la recherche de l'utilisateur avec l'ID : " + id;
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }



    public Optional<Users> findByEmail(String email) {
        String sql = "SELECT u.*, r." + ROLE_ID + ", r." + ROLE_NAME +
                " FROM " + USERS_TABLE + " u " +
                "LEFT JOIN " + ROLES_TABLE + " r ON u." + ROLE_ID + " = r." + ROLE_ID +
                " WHERE u." + USER_EMAIL + " = ?";

        try {
            Users user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                UsersBuilder usersBuilder = new UsersBuilder();

                return usersBuilder
                        .withId(rs.getInt(USER_ID))
                        .withSurname(rs.getString(USER_SURNAME))
                        .withName(rs.getString(USER_NAME))
                        .withEmail(rs.getString(USER_EMAIL))
                        .withPassword(rs.getString(USER_PASSWORD))
                        .withDateCreation(rs.getTimestamp(USER_DATE_CREATION).toLocalDateTime())
                        .withModifiedAt(rs.getTimestamp(USER_MODIFIED_AT).toLocalDateTime())
                        .withRole(new Roles(rs.getInt(ROLE_ID), rs.getString(ROLE_NAME)))
                        .build();
            }, email);

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Aucun utilisateur trouvé avec l'email : " + email);
            return Optional.empty();
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la recherche de l'utilisateur avec l'email : " + email;
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }


    public List<Map<String, Object>> getUsersStatByDay() {
        String sql = "SELECT DATE(" + USER_DATE_CREATION + ") AS creation_date, COUNT(*) AS user_count " +
                "FROM " + USERS_TABLE + " " +
                "GROUP BY DATE(" + USER_DATE_CREATION + ") " +
                "ORDER BY creation_date";

        try {
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la récupération des statistiques des utilisateurs par jour.";
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }


    public String getUserNameByUserId(int userId) {
        String sql = "SELECT CONCAT(" + USER_NAME + ", ' ', " + USER_SURNAME + ") AS fullName " +
                "FROM " + USERS_TABLE + " WHERE " + USER_ID + " = ?";

        try {
            return jdbcTemplate.queryForObject(sql, String.class, userId);
        } catch (EmptyResultDataAccessException e) {
            String errorMessage = "Aucun utilisateur trouvé avec l'ID : " + userId;
            System.err.println(errorMessage);
            return "Utilisateur inconnu";
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la récupération du nom de l'utilisateur avec l'ID : " + userId;
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }

}
