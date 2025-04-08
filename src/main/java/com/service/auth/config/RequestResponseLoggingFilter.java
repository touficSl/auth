package com.service.auth.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Pattern;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.service.auth.builder.response.MessageResponse;
import com.service.auth.model.Settings;
import com.service.auth.model.Users;
import com.service.auth.service.APIAuthCheckService;
import com.service.auth.service.MessageService;
import com.service.auth.service.SettingsService;
import com.service.auth.service.UserService;

@Component
@Order(1)
public class RequestResponseLoggingFilter implements Filter {

	@Autowired
	APIAuthCheckService apiAuthCheckService;

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private SettingsService settingsService;

    private static final Pattern DANGEROUS_PATTERN = Pattern.compile("(<script.*?>.*?</script>|javascript:|eval\\(|alert\\()");
	    
    @Override
    public void doFilter(
      ServletRequest request, 
      ServletResponse response, 
      FilterChain chain) throws ServletException, IOException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("X-Frame-Options", "DENY");
        res.setHeader("X-Content-Type-Options", "nosniff");
        
        String requestURI = req.getRequestURI();
        System.out.println( "Logging Request  " + req.getMethod() + " ; " + requestURI);
        

        
        // Headers
        Enumeration<String> headerNames = req.getHeaderNames();

        String lang = req.getHeader("Accept-Language"); 
		Locale locale = new Locale(lang == null ? Constants.DEFAULT_LANG : lang);
		
		res.setCharacterEncoding("UTF-8");
        if (headerNames != null) {

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = req.getHeader(headerName);
                
                // Check for dangerous patterns in the header values
                if (headerValue != null && DANGEROUS_PATTERN.matcher(headerValue).find()) {

           			MessageResponse errorResponse = new MessageResponse(messageService.getMessage("invalid_request_xss", locale), 101);

                    res.setContentType("application/json");
                    res.setStatus(HttpStatus.OK.value());
                    res.getWriter().write(Utils.convertObjectToJson(errorResponse));
                    return;  
                }
            }

            String apikey = req.getHeader("apikey");
            String secretkey = req.getHeader("apisecret"); 
            String adminkeyh = req.getHeader("admin"); 

       		if (!apiAuthCheckService.authenticate(apikey, secretkey)) { 

       			MessageResponse errorResponse = new MessageResponse(messageService.getMessage("api_auth", locale), 101);

                res.setContentType("application/json");
                res.setStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value());
                res.getWriter().write(Utils.convertObjectToJson(errorResponse));
       	        return;
       		}
       	    Settings settings = settingsService.returndefaultSettings();
       		String adminkey = settings.getAdminkey();

       		if (requestURI.contains(Constants.ADMIN_PATH))
	            if (adminkeyh == null || !adminkeyh.equals(adminkey)) {
	
	       			MessageResponse errorResponse = new MessageResponse(messageService.getMessage("not_authorized_msg", locale), 139);
	
	                res.setContentType("application/json");
	                res.setStatus(HttpStatus.UNAUTHORIZED.value());
	                res.getWriter().write(Utils.convertObjectToJson(errorResponse));
	       	        return;
	            	
	            }
       		
       		// Authorize specific APIs - Check if the request URI matches any of the excluded paths
            for (String excludedPath : Constants.EXCLUDED_PATHS) {
                if (requestURI.contains(excludedPath)) {
                    chain.doFilter(request, response);
                    return;
                }
            }

            String username = req.getHeader("username"); 
            String token = req.getHeader("token"); 

			String url = req.getRequestURL().toString();
            ResponseEntity<?> spsaauth = userService.validateToken(locale, token, username, url);
            if (spsaauth.getBody() instanceof Users) { // Success

				chain.doFilter(request, response);
				System.out.println("Logging Response : " + res.getContentType());
				return;
			}

			res.setContentType("application/json");
			res.setStatus(HttpStatus.OK.value());
			res.getWriter().write(Utils.convertObjectToJson(spsaauth));
			return;
        }
        
        chain.doFilter(request, response);
        System.out.println("Logging Response : " + res.getContentType());
    }

}