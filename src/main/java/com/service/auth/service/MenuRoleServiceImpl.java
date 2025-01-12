package com.service.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.auth.builder.request.AllowedMenu;
import com.service.auth.builder.request.MenuRoleRequest;
import com.service.auth.builder.response.MenuResponse;
import com.service.auth.builder.response.MessageResponse;
import com.service.auth.config.Constants;
import com.service.auth.model.Authorization;
import com.service.auth.model.Menu;
import com.service.auth.model.MenuAuthorization;
import com.service.auth.model.MenuRole;
import com.service.auth.model.Roles;
import com.service.auth.model.Users;
import com.service.auth.repository.AuthorizationRepository;
import com.service.auth.repository.MenuAuthorizationRepository;
import com.service.auth.repository.MenuRepository;
import com.service.auth.repository.MenuRoleRepository;
import com.service.auth.repository.RoleRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
@Transactional // This ensures the method runs within a transaction
public class MenuRoleServiceImpl implements MenuRoleService {
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	MenuRoleRepository menuRoleRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MenuAuthorizationRepository menuAuthorizationRepository;
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AuthorizationRepository authorizationRepository;

	@Override
	public ResponseEntity<?> rolemenulist(Locale locale, String role) {

		try {

			List<MenuRole> menurolelist = menuRoleRepository.findByUserRoleNoParent(role, locale.getLanguage());
			
			List<MenuResponse> response = userService.fillchildmenu(new ArrayList<MenuResponse>(), menurolelist, role, locale);
			return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}
	
	@Override
	public ResponseEntity<?> save(Locale locale, String username, @Valid MenuRoleRequest req) {

		try {
			if (req.getUserrole() == null || req.getUserrole().trim().isEmpty())
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_params", locale), 111));
			
			String userrole = req.getUserrole();
			Optional<Roles> roleopt = roleRepository.findById(userrole);
			if (roleopt.isPresent()) {
				menuRoleRepository.deleteByUserRole(userrole);
				authorizationRepository.deleteByUserRole(userrole);
				roleRepository.deleteById(userrole);
			}

			boolean require2fa = req.getRequire2fa() != null && req.getRequire2fa().equals("true") ? true : false;
			Roles role = new Roles();
			role.setUserRole(userrole);
			role.setAuth_type(req.getAuth_type());
			role.setRequire2fa(require2fa);
			role.setName(req.getName());
			role = roleRepository.save(role);
			
			Specification<MenuAuthorization> specifciation = JPASpecification.returnMenuAuthorizationSpecification(Constants.GLOBAL, false, false, false, false, false);
			List<MenuAuthorization> menuauthlist = menuAuthorizationRepository.findAll(specifciation);
			
			for (MenuAuthorization menuath : menuauthlist) {
				Authorization authorization = new Authorization(userrole, menuath.getApi(), true);
				authorizationRepository.save(authorization);
			}
			
			for (AllowedMenu allowedmenu : req.getAllowedmenulist())
				savemenurole(allowedmenu, userrole);
			
			return ResponseEntity.ok(role);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}
	
	private void savemenurole(AllowedMenu allowedmenu, String userrole) {
		if (allowedmenu.getMenuCode().equals(Constants.GET) ||
			allowedmenu.getMenuCode().equals(Constants.POST) ||
			allowedmenu.getMenuCode().equals(Constants.UPDATE) ||
			allowedmenu.getMenuCode().equals(Constants.DELETE) ||
			allowedmenu.getMenuCode().equals(Constants.CONFIGURATION)) {
			return;
		}
		
		boolean get = false, post = false, update = false, delete = false, configuration = false;
		for (AllowedMenu children : allowedmenu.getChildren()) {
			if (children.getMenuCode().equals(Constants.GET)) get = true;
			else if (children.getMenuCode().equals(Constants.POST)) post = true;
			else if (children.getMenuCode().equals(Constants.UPDATE)) update = true;
			else if (children.getMenuCode().equals(Constants.DELETE)) delete = true;
			else if (children.getMenuCode().equals(Constants.CONFIGURATION)) configuration = true;
		}
		
		List<Menu> menulist = menuRepository.findByAuthId(allowedmenu.getMenuCode());
		for (Menu menu : menulist) {

			MenuRole menurole = new MenuRole(menu, userrole, get, post, update, delete, configuration);
			menuRoleRepository.save(menurole);
		}

		Specification<MenuAuthorization> specifciation = JPASpecification.returnMenuAuthorizationSpecification(allowedmenu.getMenuCode(), get, post, update, delete, configuration);
		List<MenuAuthorization> menuauthlist = menuAuthorizationRepository.findAll(specifciation);
		
		for (MenuAuthorization menuath : menuauthlist) {
			Authorization authorization = new Authorization(userrole, menuath.getApi(), true);
			authorizationRepository.save(authorization);
		}

		for (AllowedMenu children : allowedmenu.getChildren()) 
			savemenurole(children, userrole);
	}

	@Override
	public ResponseEntity<?> menulist(Locale locale) {

		try {

			List<Menu> menulist = menuRepository.findWithNoParent(locale.getLanguage());
			
			List<MenuResponse> response = fillchildmenu(new ArrayList<MenuResponse>(), menulist, locale);
			return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}


	public List<MenuResponse> fillchildmenu(List<MenuResponse> parent, List<Menu> menulist, Locale locale) {

		if (menulist != null)
			for (Menu menu : menulist) {
				
				MenuResponse menuResponse = new MenuResponse(menu);
				
				List<MenuResponse> submenu = new ArrayList<MenuResponse>();
				List<Menu> childmenulist = menuRepository.findByParentIdAndLang(menu.getId(), locale.getLanguage());
	
				for (Menu childmenu : childmenulist) {
					MenuResponse submenuResponse = new MenuResponse(childmenu);
	
					List<Menu> subchildmenulist = menuRepository.findByParentIdAndLang(childmenu.getId(), locale.getLanguage());
	
					submenuResponse.setSubmenu(fillchildmenu(new ArrayList<MenuResponse>(), subchildmenulist, locale));
					
					submenu.add(submenuResponse);
				}
				
				menuResponse.setSubmenu(submenu);
				parent.add(menuResponse);
			}
		
		return parent; 
	}

	@Override
	public ResponseEntity<?> remove(Locale locale, String username, String userrole) {
		
		List<Users> users = userService.findByUserrole(userrole);
		if (users != null && users.size() > 0)
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exist_users_role", locale), 299));

		roleRepository.deleteById(userrole);
		menuRoleRepository.deleteByUserRole(userrole);
		authorizationRepository.deleteByUserRole(userrole);
		
		return ResponseEntity.ok(new MessageResponse(messageService.getMessage("operation_success", locale)));
	}
}
