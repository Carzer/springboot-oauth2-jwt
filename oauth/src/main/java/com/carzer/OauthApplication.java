package com.carzer;

import com.carzer.model.BaseUserDTO;
import com.carzer.model.DemoBaseClientDetails;
import com.carzer.service.BaseUserDAO;
import com.carzer.service.DemoBaseClientDetailsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

@SpringBootApplication
public class OauthApplication {

	private static final Logger log = LoggerFactory.getLogger(OauthApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OauthApplication.class, args);
	}

	/**
	 * An opinionated WebApplicationInitializer to run a SpringApplication from a traditional WAR deployment.
	 * Binds Servlet, Filter and ServletContextInitializer beans from the application context to the servlet container.
	 *
	 * @link http://docs.spring.io/spring-boot/docs/current/api/index.html?org/springframework/boot/context/web/SpringBootServletInitializer.html
	 */
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OauthApplication.class);
	}

	@Autowired
	private BaseUserDAO baseUserDAO;

	@Autowired
	private DemoBaseClientDetailsDAO demoBaseClientDetailsDAO;

	@Autowired
	public void init(){
		try {
			BaseUserDTO baseUser = new BaseUserDTO();
			baseUser.setLoginName("user");
			baseUser.setPassword("user");
			baseUser.setRoles(new String[]{"ROLE_USER"});
			BaseUserDTO adminUser = new BaseUserDTO();
			adminUser.setLoginName("admin");
			adminUser.setPassword("admin");
			adminUser.setRoles(new String[]{"ROLE_USER","ROLE_ADMIN"});
			baseUserDAO.deleteAll();
			baseUserDAO.save(baseUser);
			baseUserDAO.save(adminUser);
			DemoBaseClientDetails trustedDetails = new DemoBaseClientDetails();
			trustedDetails.setClientId("trusted");
			trustedDetails.setClientSecret("secret");
			trustedDetails.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_TRUSTED_CLIENT")));
			trustedDetails.setAuthorizedGrantTypes(Arrays.asList("client_credentials","password","authorization_code","refresh_token","implicit"));
			trustedDetails.setScope(Arrays.asList("read","write"));
			trustedDetails.setAccessTokenValiditySeconds(30*60);
			trustedDetails.setRefreshTokenValiditySeconds(3*30*60);
			demoBaseClientDetailsDAO.deleteAll();
			demoBaseClientDetailsDAO.insert(trustedDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
