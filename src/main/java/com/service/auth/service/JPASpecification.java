package com.service.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.service.auth.model.MenuAuthorization;
import com.service.auth.model.Roles;
import com.service.auth.model.Teams;
import com.service.auth.model.Tokens;
import com.service.auth.model.Users;

import jakarta.persistence.criteria.Predicate;

public class JPASpecification {

    public static Specification<Users> returnUserSpecification(String search, String sortColumn, boolean descending) {
        return (root, query, criteriaBuilder) -> {

            if (descending) 
                query.orderBy(criteriaBuilder.desc(root.get(sortColumn)));
            else 
                query.orderBy(criteriaBuilder.asc(root.get(sortColumn)));
            
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            String searchPattern = search + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get("first_name"), searchPattern),
                criteriaBuilder.like(root.get("last_name"), searchPattern),
                criteriaBuilder.like(root.get("email"), searchPattern),
                criteriaBuilder.like(root.get("mobile_no"), searchPattern),
                criteriaBuilder.like(root.get("first_name_ar"), searchPattern),
                criteriaBuilder.like(root.get("last_name_ar"), searchPattern),
                criteriaBuilder.like(root.get("userrole"), searchPattern)
            );
        };
    }
    public static Specification<Roles> returnRoleSpecification(String search, String sortColumn, boolean descending) {
        return (root, query, criteriaBuilder) -> {

            if (descending) 
                query.orderBy(criteriaBuilder.desc(root.get(sortColumn)));
            else 
                query.orderBy(criteriaBuilder.asc(root.get(sortColumn)));
            
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            String searchPattern = search + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get("userRole"), searchPattern),
                criteriaBuilder.like(root.get("auth_type"), searchPattern)
            );
        };
    }

    public static Specification<MenuAuthorization> returnMenuAuthorizationSpecification(String menuauthid, 
            boolean get, boolean post, boolean update, boolean delete, boolean configuration, String accessibleaction) {

        return (root, query, criteriaBuilder) -> {
            // Create a list of predicates (conditions)
            List<Predicate> andPredicates = new ArrayList<>(); // For AND conditions
            List<Predicate> orPredicates = new ArrayList<>();  // For OR conditions

            if (accessibleaction != null)
                andPredicates.add(criteriaBuilder.equal(root.get("accessibleaction"), accessibleaction));
            else 
                andPredicates.add(criteriaBuilder.isNull(root.get("accessibleaction")));

            
            // Check if menuauthid is provided and add it as an AND condition
            if (menuauthid != null && !menuauthid.isEmpty()) {
                andPredicates.add(criteriaBuilder.equal(root.get("menuauthId"), menuauthid));
            }

            // Add OR conditions for boolean fields
            if (get) {
                orPredicates.add(criteriaBuilder.isTrue(root.get("isget")));
            }
            if (post) {
                orPredicates.add(criteriaBuilder.isTrue(root.get("ispost")));
            }
            if (update) {
                orPredicates.add(criteriaBuilder.isTrue(root.get("isupdate")));
            }
            if (delete) {
                orPredicates.add(criteriaBuilder.isTrue(root.get("isdelete")));
            }
            if (configuration) {
                orPredicates.add(criteriaBuilder.isTrue(root.get("isconfiguration")));
            }

            if (orPredicates.size() > 0) {
	            // Combine OR predicates into one OR condition
	            Predicate orCondition = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
	
	            // Add the OR condition to the list of AND predicates
	            andPredicates.add(orCondition);
            }

            // Combine all AND predicates with an AND condition
            return criteriaBuilder.and(andPredicates.toArray(new Predicate[0]));
        };
    }
    
	public static Specification<Tokens> returnTokenSpecification(String search, String sortColumn, boolean descending) {
		return (root, query, criteriaBuilder) -> {

            if (descending) 
                query.orderBy(criteriaBuilder.desc(root.get(sortColumn)));
            else 
                query.orderBy(criteriaBuilder.asc(root.get(sortColumn)));
            
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            String searchPattern = search + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get("username"), searchPattern),
                criteriaBuilder.like(root.get("device"), searchPattern),
                criteriaBuilder.like(root.get("ip"), searchPattern),
                criteriaBuilder.like(root.get("reason"), searchPattern)
            );
        };
	}

	public static Specification<Tokens> returnUserTokenSpecification(String search, String sortColumn, boolean descending, String username) {
		return (root, query, criteriaBuilder) -> {

			Predicate usernamePredicate = criteriaBuilder.equal(root.get("username"), username);

			// Apply sorting (ascending/descending) based on the 'descending' flag
			if (descending) {
			    query.orderBy(criteriaBuilder.desc(root.get(sortColumn)));
			} else {
			    query.orderBy(criteriaBuilder.asc(root.get(sortColumn)));
			}

			// Start with the base query that filters by username
			Predicate basePredicate = usernamePredicate;

			// If search is provided, apply additional search filters on 'device', 'ip', and 'reason'
			if (search != null && !search.isEmpty()) {
			    String searchPattern = search + "%";
			    Predicate searchPredicate = criteriaBuilder.or(
			        criteriaBuilder.like(root.get("device"), searchPattern),
			        criteriaBuilder.like(root.get("ip"), searchPattern),
			        criteriaBuilder.like(root.get("reason"), searchPattern)
			    );
			    
			    // Combine the username filter with the search filter
			    basePredicate = criteriaBuilder.and(basePredicate, searchPredicate);
			}

			// Return the final combined predicate (username filter + search filter, if applicable)
			return basePredicate;
        };
	}
	public static Specification<Teams> returnTeamSpecification(String search, String sortColumn, Boolean descending) {
		return (root, query, criteriaBuilder) -> {

            if (descending) 
                query.orderBy(criteriaBuilder.desc(root.get(sortColumn)));
            else 
                query.orderBy(criteriaBuilder.asc(root.get(sortColumn)));
            
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            String searchPattern = search + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), searchPattern),
                criteriaBuilder.like(root.get("description"), searchPattern)
            );
        };
	}
}