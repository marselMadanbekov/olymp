package olymp.mental_arithmetic.security;

public class SecurityConstants {
    public static final String ERROR_PAGES = "/error/**";
    public static final String AUTH_URLS = "/auth/**";
    public static final String STATIC_RESOURCES_URL = "/static/**";
    public static final String SUPER_ADMIN_URLS = "/super-admin/**";
    public static final String GENERAL_SUPER_ADMIN_AND_OWNER_URLS = "/super-admin-owner/**";
    public static final String GENERAL_OWNER_AND_ADMIN_URLS = "/owner-admin/**";
    public static final String OWNER_URLS = "/owner/**";
    public static final String ADMIN_URLS = "/admin/**";
    public static final String TEACHER_URLS = "/teacher/**";
    public static final String PUPIL_URLS = "/pupil/**";
    public static final String SECRET = "SecretKeyGenJWTMarselMadanbekovKubanychbekovichSecretKeyGenJWTMarselMadanbekovKubanychbekovich";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 600_000_000;
}
