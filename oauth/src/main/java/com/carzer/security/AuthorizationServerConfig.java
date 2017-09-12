package com.carzer.security;

import com.carzer.service.DemoBaseClientDetailsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Value("${config.oauth2.resourceIds}")
    private String resourceIds;

    @Value("${config.oauth2.privateKey}")
    private String privateKey;

    @Value("${config.oauth2.publicKey}")
    private String publicKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DemoBaseClientDetailsDAO demoBaseClientDetailsDAO;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        log.info("Initializing JWT with public key:\n" + publicKey);
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
//        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
//            /***
//             ** 重写增强token方法,用于自定义一些token返回的信息
//            */
//            @Override
//            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//                String userName = authentication.getUserAuthentication().getName();
//                User user = (User) authentication.getUserAuthentication().getPrincipal();// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
//                /** 自定义一些token属性 **/
//                final Map<String, Object> additionalInformation = new HashMap<>();
//                additionalInformation.put("userName", userName);
//                additionalInformation.put("roles", user.getAuthorities());
//                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
//                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
//                return enhancedToken;
//            }
//
//        };

        accessTokenConverter.setSigningKey(privateKey);
        accessTokenConverter.setVerifierKey(publicKey);
        return accessTokenConverter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    /**
     * Defines the security constraints on the token endpoints /oauth/token_key and /oauth/check_token
     * Client credentials are required to access the endpoints
     *
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("isAnonymous() || hasRole('ROLE_TRUSTED_CLIENT')") // permitAll()
                .checkTokenAccess("hasRole('TRUSTED_CLIENT')"); // isAuthenticated()
    }

    /**
     * Defines the authorization and token endpoints and the token services
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints

                // Which authenticationManager should be used for the password grant
                // If not provided, ResourceOwnerPasswordTokenGranter is not configured
                .authenticationManager(authenticationManager)

                // Use JwtTokenStore and our jwtAccessTokenConverter
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer())
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()

                // Confidential client where client secret can be kept safe (e.g. server side)
                .withClient("confidential").secret("secret")
                .authorizedGrantTypes("client_credentials", "authorization_code", "refresh_token")
                .scopes("read", "write")
                .resourceIds(resourceIds)
                //.redirectUris("http://localhost:8080/client/")

                .and()

                // Public client where client secret is vulnerable (e.g. mobile apps, browsers)
                .withClient("public") // No secret!
                .authorizedGrantTypes("client_credentials", "implicit")
                .scopes("read")
                .resourceIds(resourceIds)
                //.redirectUris("http://localhost:8080/client/")

                .and()

                // Trusted client: similar to confidential client but also allowed to handle user password
                .withClient("trusted").secret("secret")
                .authorities("ROLE_TRUSTED_CLIENT")
                .authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token","implicit")
                .scopes("read", "write")
                .resourceIds(resourceIds)
                //.redirectUris("http://localhost:8080/client/hello")
        ;*/

        clients.withClientDetails(clientDetailsService());
    }

    @Bean
    public ClientDetailsService clientDetailsService(){
        return (clientId) -> demoBaseClientDetailsDAO.findByClientId(clientId);
    }
}