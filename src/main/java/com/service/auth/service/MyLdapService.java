package com.service.auth.service;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import com.service.auth.config.LdapUser;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class MyLdapService {

    private LdapTemplate ldapTemplate;

    public MyLdapService(String ldapUrl, String baseDn, String userDn, String password) {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setBase(baseDn);
        contextSource.setUserDn(userDn);
        contextSource.setPassword(password);
        contextSource.afterPropertiesSet();

        this.ldapTemplate = new LdapTemplate(contextSource);
    }

    public List<LdapUser> searchUsers(String username, String baseDn) {
        List<LdapUser> users = new ArrayList<>();
        String filter = String.format("(&(objectClass=user)(sAMAccountName=%s))", username);

        try {
            // Use SearchControls to set the search parameters
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search all levels under base DN

            // Perform the search and get the results
            NamingEnumeration<SearchResult> results = ldapTemplate.getContextSource().getReadOnlyContext()
                    .search("", filter, searchControls);

            // Iterate over the search results
            while (results.hasMore()) {
                SearchResult searchResult = results.next();
                Attributes attrs = searchResult.getAttributes();

                // Map attributes to User object
                String name = attrs.get("givenName") != null ? attrs.get("givenName").get().toString() : null;
                String lastname = attrs.get("sn") != null ? attrs.get("sn").get().toString() : null;
                String email = attrs.get("mail") != null ? attrs.get("mail").get().toString() : null;

                LdapUser user = new LdapUser(name, lastname, email);
                users.add(user);
            }
        } catch (NamingException e) {
            System.err.println("Error during LDAP search: " + e.getMessage());
        }

        if (users.isEmpty()) {
            System.out.println("No users found for username: " + username);
        } else {
            for (LdapUser user : users) {
                System.out.println("Found user: " + user);
            }
        }

        return users;
    }
}
