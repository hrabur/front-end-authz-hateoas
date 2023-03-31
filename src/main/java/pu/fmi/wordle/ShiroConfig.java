package pu.fmi.wordle;

import javax.sql.DataSource;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

  @Bean
  public Realm realm(DataSource dataSource) {
    JdbcRealm realm = new JdbcRealm();
    realm.setDataSource(dataSource);
    realm.setAuthenticationQuery("""
           select PASSWORD
           from USERS
           where EMAIL = ?
        """);
    realm.setUserRolesQuery("""
            select r.NAME
            from USERS u
            join USER_ROLE ur on ur.USER_ID = u.USER_ID
            join ROLE r on r.ROLE_ID = ur.ROLE_ID
            where u.EMAIL = ?
        """);
    realm.setPermissionsQuery("""
             select p.PERMISSION
             from ROLE_PERMISSION p
             join ROLE r on r.ROLE_ID = p.ROLE_ID
             where r.NAME = ?
        """);
    realm.setPermissionsLookupEnabled(true);
    return realm;
  }

  @Bean
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinition("/**", "anon");
    return chainDefinition;
  }
}
