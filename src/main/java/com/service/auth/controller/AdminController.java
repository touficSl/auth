package com.service.auth.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.auth.builder.request.MenuRoleRequest;
import com.service.auth.builder.request.RegisterRequest;
import com.service.auth.builder.request.UpdateUserReq;
import com.service.auth.service.SettingsService;
import com.service.auth.service.UserService;
import com.service.auth.service.MenuRoleService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SettingsService settingsService;
	
	@Autowired
	private MenuRoleService menuRoleService;
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<?> adminregister(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									  @Valid @RequestBody RegisterRequest request) {

		return userService.register(locale, request, true);
	}

	@RequestMapping(value = "/change/password", method = RequestMethod.POST)
	public ResponseEntity<?> adminchangepass(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "newpassword", required = true) String newpassword,
									    @RequestHeader(name = "confirmpassword", required = true) String confirmpassword) {

		return userService.adminchangepass(locale, username, newpassword, confirmpassword);
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
								  @RequestHeader(name = "username", required = true) String username,
								  @Valid @RequestBody UpdateUserReq req) {

		return userService.update(locale, username, req, true);
	}

	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public ResponseEntity<?> userinsert(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
								  @RequestHeader(name = "username", required = true) String username,
								  @Valid @RequestBody RegisterRequest req) {

		return userService.register(locale, req, true);
	}

	@RequestMapping(value = "/user/able", method = RequestMethod.POST)
	public ResponseEntity<?> able(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "affectedusername", required = true) String removeusername,
									    @RequestHeader(name = "enable", required = true) boolean enable) {

		return userService.able(locale, username, removeusername, enable);
	}
	
	@RequestMapping(value = "/user/list/count", method = RequestMethod.POST)
	public ResponseEntity<?> listcount(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

		return userService.listcount(locale);
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.POST)
	public ResponseEntity<?> userlist(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
								  @RequestHeader(name = "page", required = true) int page,
								  @RequestHeader(name = "size", required = true) int size,
								  @RequestHeader(name = "search", required = false) String search,
								  @RequestHeader(name = "sortcolumn", required = false) String sortcolumn,
								  @RequestHeader(name = "descending", required = false) boolean descending,
						          @RequestHeader(name = "draw", required = true) int draw) {

    	if (sortcolumn.equals("user_role"))
    		sortcolumn = "userrole";
		return userService.userlist(locale, page, size, search, sortcolumn, descending, draw);
	}

	@RequestMapping(value = "/role/list", method = RequestMethod.POST)
	public ResponseEntity<?> rolelist(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									  @RequestHeader(name = "all", required = false, defaultValue = "true") Boolean all,
									  @RequestHeader(name = "page", required = false, defaultValue = "0") Integer page,
									  @RequestHeader(name = "size", required = false, defaultValue = "0") Integer size,
									  @RequestHeader(name = "search", required = false) String search,
									  @RequestHeader(name = "sortcolumn", required = false) String sortcolumn,
									  @RequestHeader(name = "descending", required = false, defaultValue = "false") Boolean descending,
							          @RequestHeader(name = "draw", required = false, defaultValue = "1") Integer draw) {
		
		return userService.rolelist(locale, all, page, size, search, sortcolumn, descending, draw);
	}

	
	@RequestMapping(value = "/user/details", method = RequestMethod.POST)
	public ResponseEntity<?> details(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
	  		  						 @RequestHeader(name = "username", required = true) String username,
								     @RequestHeader(name = "searchusername", required = false) String searchusername) {

		searchusername = searchusername == null ? username : searchusername;
		return userService.details(locale, searchusername);
	}

	@RequestMapping(value = "/menu/list", method = RequestMethod.POST)
	public ResponseEntity<?> menulist(@RequestHeader(name = "Accept-Language", required = true) Locale locale) {

		return menuRoleService.menulist(locale);
	}

	@RequestMapping(value = "/role/menu/list", method = RequestMethod.POST)
	public ResponseEntity<?> rolemenulist(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
								  	      @RequestHeader(name = "role", required = true) String role) {

		return menuRoleService.rolemenulist(locale, role);
	}

	@RequestMapping(value = "/role/menu/save", method = RequestMethod.POST)
	public ResponseEntity<?> rolemenusave(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
								  		  @RequestHeader(name = "username", required = true) String username,
								  		  @Valid @RequestBody MenuRoleRequest req) {

		return menuRoleService.save(locale, username, req);
	}

	@RequestMapping(value = "/auth/type/list", method = RequestMethod.POST)
	public ResponseEntity<?> authtypelist(@RequestHeader(name = "Accept-Language", required = true) Locale locale) {

		return settingsService.authtypelist(locale);
	}

	@RequestMapping(value = "/role/menu/remove", method = RequestMethod.POST)
	public ResponseEntity<?> rolemenuremove(@RequestHeader(name = "Accept-Language", required = true) Locale locale,
								  		   @RequestHeader(name = "username", required = true) String username,
								  		   @RequestHeader(name = "role", required = true) String role) {

		return menuRoleService.remove(locale, username, role);
	}

	@RequestMapping(value = "/user/bypassldap", method = RequestMethod.POST)
	public ResponseEntity<?> userbypassldap(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
									    @RequestHeader(name = "username", required = true) String username,
									    @RequestHeader(name = "selectedusername", required = true) String selectedusername,
									    @RequestHeader(name = "bypass3dparty", required = true) boolean bypass3dparty,
									    @RequestHeader(name = "bypasspassword", required = true) String bypasspassword) {

		return userService.userbypassldap(locale, username, selectedusername, bypasspassword, bypass3dparty);
	}
}
