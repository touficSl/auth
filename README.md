# Introduction
- This service will be used by all the other services to authenticate and authorize users to use the APIs, and contains API key and secret to also
authenticate the API

# Documentation
- User roles
	- Admin (has access to all projects)
	- Manager (has access to manager inspection project)
	- Inspector (has access to inspection project)
	- Police (has access to police project)
	- Other roles (has access to specific project)
- Authentication types by user role
	- LDAP
	- PASSWORD
	- UAEPASS (not implemented yet)
- Security levels (database)
	- JWT Token for each event (login, OTP, Change pass)
	- API authorization
	- Menu access 
- Token expiry date
	- Change password token 15 minutes
	- Access token 2 hours
	- Refresh token 24 hours
- Project Features
	- User can authenticate using LDAP or PASSWORD
	- We can remove 2 factor authentication by user role
	
	

- Development team should fill the menu and menu_authorization by default
- When role created or updated, 
	- insert/update role
	- delete/insert in menu_role table
	- for loop menu list, get menu_authorization table by menu_auth_id and by get / post / update / delete / configuration
	- insert into authorization table all the allowed menu_authorization list