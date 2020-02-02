package com.carpool.server.serviceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Service;

import com.carpool.server.entity.LdapAuthPOJO;
import com.carpool.server.service.ILDAPService;
import com.carpool.server.utils.CommonUtility;

@Service
public class LDAPServiceImpl implements ILDAPService {
	
	@Autowired
	private Environment environment;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public HashMap<String, String> authenticate(String username,String password) {
		String method = "authenticate :: main method ::";
		logger.info(method + " starts...");
		final String ldapURL = environment.getProperty("ldap.url");
		final String ldapDomain = environment.getProperty("ldap.domain");
		String keys = environment.getProperty("ldap.parameters");
		String[] keyList= keys.split(",");
		HashMap<String, String> ldapAuth = null;
		try {
			ldapAuth = authenticateVIALDAP(username, password, keyList, ldapURL, ldapDomain);
		} catch (Exception e) {
			logger.error(method,e);
		}
		logger.info(method + " ends...");
		return ldapAuth;
	}

	public HashMap<String, String> authenticateVIALDAP(final String username, final String password, final String[] keyList, final String ldapURL, final String ldapDomain) throws NoSuchMethodException,
	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		LdapAuthPOJO ldapAuth = new LdapAuthPOJO();
		HashMap<String, String> response = new HashMap<>();
		final ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider = new ActiveDirectoryLdapAuthenticationProvider(
				ldapDomain, ldapURL);
		final Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		final SimpleGrantedAuthority role = new SimpleGrantedAuthority("test");
		grantedAuthorities.add(role);
		final UserDetails userDetails = new User(username, password, grantedAuthorities);
		final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password);
		final Method retrieveItems = activeDirectoryLdapAuthenticationProvider.getClass()
				.getDeclaredMethod("doAuthentication", UsernamePasswordAuthenticationToken.class);
		retrieveItems.setAccessible(true);
		final DirContextOperations dirContextOperations = (DirContextOperations) retrieveItems
				.invoke(activeDirectoryLdapAuthenticationProvider, usernamePasswordAuthenticationToken);
		ldapAuth.setAuthenticated(true);
		if (!CommonUtility.isNullObject(keyList)) {
			for (String attrKey : keyList) {
				attrKey = CommonUtility.isNotEmpty(attrKey) ? attrKey.trim() : attrKey;
				final Object value = dirContextOperations.getObjectAttribute(attrKey);
				if (null != value && value instanceof String) {
					response.put(attrKey, (String) value);
				} else if (!(value instanceof String)) {
					logger.warn("Object value found for the key in the AD response " + attrKey + " value " + value);
				} else {
					logger.warn("Value not found for key in the AD response " + attrKey + " value " + value);
				}
			}
		} else {
			logger.error("Additional attributes for the LDAP authentication are empty");
		}
		return response;
	}
}