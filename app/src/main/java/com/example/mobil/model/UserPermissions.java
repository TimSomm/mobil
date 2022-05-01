package com.example.mobil.model;

import java.util.Arrays;
import java.util.List;

public class UserPermissions {

    // User Role-ok
    public static final String ROLE_QUEST = "role_quest";
    private static final String ROLE_NORMAL = "role_normal";
    private static final String ROLE_ADMIN = "role_admin";
    private static final String ROLE_PREMIUM = "role_premium";

    // Valid User Role-ok
    private static final List<String> VALID_ROLES = Arrays.asList(
            ROLE_QUEST,
            ROLE_NORMAL,
            ROLE_ADMIN,
            ROLE_PREMIUM
    );

    // User tevékenységek, amiket csekkolni kell
    private static final String RIGHT_ADD_USER = "add_user";
    private static final String RIGHT_MODIFY_USER = "modify_user";
    private static final String RIGHT_DELETE_USER = "delete_user";
    private static final String RIGHT_ADD_COURSE = "add_course";
    private static final String RIGHT_MODIFY_COURSE = "modify_course";
    private static final String RIGHT_DELETE_COURSE = "delete_course";

    // Valid User tevékenységek
    private static final List<String> VALID_RIGHTS = Arrays.asList(
            RIGHT_ADD_USER,
            RIGHT_MODIFY_USER,
            RIGHT_DELETE_USER,
            RIGHT_ADD_COURSE,
            RIGHT_MODIFY_COURSE,
            RIGHT_DELETE_COURSE
    );

    /**
     * Visszaadja, hogy meyik rolenak mihez van joga
     *
     * @param right a jogkör (tevékenység), amit ellenőrzünk
     * @param role a role, akinek megnézzük, hogy van-e joga
     * @return boolean
     */
    protected static boolean roleHasRight(String right, String role) throws Exception {

        if (!VALID_RIGHTS.contains(right) || !VALID_ROLES.contains(role)) {
            throw new Exception("Role or right is not valid");
        }

        switch (right) {
            case RIGHT_ADD_USER:
                switch (role) {
                    case ROLE_ADMIN:
                        return true;
                }
            case RIGHT_MODIFY_USER:
                switch (role) {
                    case ROLE_ADMIN:
                        return true;
                }
            case RIGHT_DELETE_USER:
                switch (role) {
                    case ROLE_ADMIN:
                        return true;
                }
            case RIGHT_ADD_COURSE:
                switch (role) {
                    case ROLE_ADMIN:
                    case ROLE_PREMIUM:
                        return true;
                }
            case RIGHT_DELETE_COURSE:
                switch (role) {
                    case ROLE_ADMIN:
                        return true;
                }
            case RIGHT_MODIFY_COURSE:
                switch (role) {
                    case ROLE_ADMIN:
                        return true;
                }
            default:
                return false;
        }
    }
}
