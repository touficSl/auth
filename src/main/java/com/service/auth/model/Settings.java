package com.service.auth.model;

import com.service.auth.builder.request.UpdateSettingsRq;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "settings", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class Settings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean ldapregisterrole;
	private String ldapdefaultrole;
	private String ldapdomain;
	private String ldapurl;
	private String ldapbasedn;

	@NotBlank
	private String apikey;
	@NotBlank
	private String apisecret;
	@NotBlank
	private String adminkey;

	private String jwtsecret;
	private long jwtexpirationms;
	private long jwtexpirationmsshort;
	private long jwtexpirationmscode;
	
	private boolean uaepassregisterrole;
	private String uaepassdefaultrole;
	private String uaepassusername;
	private String uaepasspassword;
	private String uaepassendpoint;
	private String uaepasscallbackurl;
	private String uaepassuserinfourl;
	private String uaepassstate;
	private String uaepassredirecturl;
	private String uaepassauthurl;
	private boolean uaepassuseeid;
	
	private boolean passregisterrole;
	private String passdefaultrole;
	
	private int maximuminvalidattempts;
	
	private boolean recaptchavalidation;
	private String recaptchasitekey;
	private String recaptchaapi;
	
	private String smsauthapi;
	private String smsauthusername;
	private String smsauthpassword;
	private String smsapi;
	private String smsapplicationid;
	private String smspassword;
	
	private String mailhost;
	private int mailport;
	private String mailusername;
	private String mailpassword;
	private boolean mailstarttlsenable;
	private String mailfrom;
	private boolean mailauth;
	private String mailsupport;

	private boolean isdefault;

    @Transient
    private boolean canupdate;
	
	public Settings() {
	}
	
	public Settings(boolean ldapregisterrole, String ldapdefaultrole, String ldapdomain, String ldapurl,
			String ldapbasedn, @NotBlank String apikey, @NotBlank String apisecret, @NotBlank String adminkey,
			String jwtsecret, long jwtexpirationms, long jwtexpirationmsshort, long jwtexpirationmscode, boolean uaepassregisterrole,
			String uaepassdefaultrole, String uaepassusername, String uaepasspassword, String uaepassendpoint,
			String uaepasscallbackurl, String uaepassuserinfourl, String uaepassstate, String uaepassredirecturl,
			String uaepassauthurl, boolean uaepassuseeid, boolean passregisterrole, String passdefaultrole,
			int maximuminvalidattempts, boolean recaptchavalidation, String recaptchasitekey, String recaptchaapi,
			String smsauthapi, String smsauthusername, String smsauthpassword, String smsapi, String smsapplicationid,
			String smspassword, String mailhost, int mailport, String mailusername, String mailpassword,
			boolean mailstarttlsenable, String mailfrom, boolean mailauth, String mailsupport, boolean isdefault) {
		super();
		this.ldapregisterrole = ldapregisterrole;
		this.ldapdefaultrole = ldapdefaultrole;
		this.ldapdomain = ldapdomain;
		this.ldapurl = ldapurl;
		this.ldapbasedn = ldapbasedn;
		this.apikey = apikey;
		this.apisecret = apisecret;
		this.adminkey = adminkey;
		this.jwtsecret = jwtsecret;
		this.jwtexpirationms = jwtexpirationms;
		this.jwtexpirationmsshort = jwtexpirationmsshort;
		this.jwtexpirationmscode = jwtexpirationmscode;
		this.uaepassregisterrole = uaepassregisterrole;
		this.uaepassdefaultrole = uaepassdefaultrole;
		this.uaepassusername = uaepassusername;
		this.uaepasspassword = uaepasspassword;
		this.uaepassendpoint = uaepassendpoint;
		this.uaepasscallbackurl = uaepasscallbackurl;
		this.uaepassuserinfourl = uaepassuserinfourl;
		this.uaepassstate = uaepassstate;
		this.uaepassredirecturl = uaepassredirecturl;
		this.uaepassauthurl = uaepassauthurl;
		this.uaepassuseeid = uaepassuseeid;
		this.passregisterrole = passregisterrole;
		this.passdefaultrole = passdefaultrole;
		this.maximuminvalidattempts = maximuminvalidattempts;
		this.recaptchavalidation = recaptchavalidation;
		this.recaptchasitekey = recaptchasitekey;
		this.recaptchaapi = recaptchaapi;
		this.smsauthapi = smsauthapi;
		this.smsauthusername = smsauthusername;
		this.smsauthpassword = smsauthpassword;
		this.smsapi = smsapi;
		this.smsapplicationid = smsapplicationid;
		this.smspassword = smspassword;
		this.mailhost = mailhost;
		this.mailport = mailport;
		this.mailusername = mailusername;
		this.mailpassword = mailpassword;
		this.mailstarttlsenable = mailstarttlsenable;
		this.mailfrom = mailfrom;
		this.mailauth = mailauth;
		this.mailsupport = mailsupport;
		this.isdefault = isdefault;
		this.canupdate = false;
	}

	public Settings updatesettings(UpdateSettingsRq request) {
		
		this.setCanupdate(true);
		this.uaepassregisterrole = request.isRegisteruaepassusers();
		this.ldapregisterrole = request.isRegisterldapusers();
		this.mailsupport = request.getSuppportemail();
		this.passdefaultrole = request.getPassdefaultrole();
		this.uaepassdefaultrole = request.getUaepassdefaultrole();
		this.ldapdefaultrole = request.getLdapdefaultrole();
		this.uaepassuseeid = request.isUaepasssaveeid();
		
		return this;
	}


	public Settings removeprivatedata() {
		this.adminkey = null;
		this.apisecret = null;
		this.apikey = null;
		this.recaptchaapi = null;
		this.recaptchasitekey = null;
		this.smsapi = null;
		this.smsapplicationid = null;
		this.smspassword = null;
		this.jwtsecret = null;
		this.ldapurl = null;
		this.ldapbasedn = null;
		this.ldapdomain = null;
		this.mailusername = null;
		this.mailpassword = null;
		this.uaepasspassword = null;
		this.uaepassendpoint = null;
		this.uaepasscallbackurl = null;
		this.uaepassauthurl = null;
		this.uaepassredirecturl = null;
		this.smsauthapi = null;
		this.smsauthusername = null;
		this.smsauthpassword = null;
		this.uaepassstate = null;
		this.uaepassusername = null;
		this.mailfrom = null;
		this.mailhost = null;
		this.uaepassuserinfourl = null;
		
		return this;
	}
	

	public Long getId() {
		return id;
	}

	public boolean isLdapregisterrole() {
		return ldapregisterrole;
	}

	public String getLdapdefaultrole() {
		return ldapdefaultrole;
	}

	public String getLdapdomain() {
		return ldapdomain;
	}

	public String getLdapurl() {
		return ldapurl;
	}

	public String getLdapbasedn() {
		return ldapbasedn;
	}

	public String getApikey() {
		return apikey;
	}

	public String getApisecret() {
		return apisecret;
	}

	public String getAdminkey() {
		return adminkey;
	}

	public String getJwtsecret() {
		return jwtsecret;
	}

	public long getJwtexpirationms() {
		return jwtexpirationms;
	}

	public long getJwtexpirationmsshort() {
		return jwtexpirationmsshort;
	}

	public long getJwtexpirationmscode() {
		return jwtexpirationmscode;
	}

	public boolean isUaepassregisterrole() {
		return uaepassregisterrole;
	}

	public String getUaepassdefaultrole() {
		return uaepassdefaultrole;
	}

	public String getUaepassusername() {
		return uaepassusername;
	}

	public String getUaepasspassword() {
		return uaepasspassword;
	}

	public String getUaepassendpoint() {
		return uaepassendpoint;
	}

	public String getUaepasscallbackurl() {
		return uaepasscallbackurl;
	}

	public String getUaepassuserinfourl() {
		return uaepassuserinfourl;
	}

	public String getUaepassstate() {
		return uaepassstate;
	}

	public String getUaepassredirecturl() {
		return uaepassredirecturl;
	}

	public String getUaepassauthurl() {
		return uaepassauthurl;
	}

	public boolean isUaepassuseeid() {
		return uaepassuseeid;
	}

	public boolean isPassregisterrole() {
		return passregisterrole;
	}

	public String getPassdefaultrole() {
		return passdefaultrole;
	}

	public int getMaximuminvalidattempts() {
		return maximuminvalidattempts;
	}

	public boolean isRecaptchavalidation() {
		return recaptchavalidation;
	}

	public String getRecaptchasitekey() {
		return recaptchasitekey;
	}

	public String getRecaptchaapi() {
		return recaptchaapi;
	}

	public String getSmsauthapi() {
		return smsauthapi;
	}

	public String getSmsauthusername() {
		return smsauthusername;
	}

	public String getSmsauthpassword() {
		return smsauthpassword;
	}

	public String getSmsapi() {
		return smsapi;
	}

	public String getSmsapplicationid() {
		return smsapplicationid;
	}

	public String getSmspassword() {
		return smspassword;
	}

	public String getMailhost() {
		return mailhost;
	}

	public int getMailport() {
		return mailport;
	}

	public String getMailusername() {
		return mailusername;
	}

	public String getMailpassword() {
		return mailpassword;
	}

	public boolean isMailstarttlsenable() {
		return mailstarttlsenable;
	}

	public String getMailfrom() {
		return mailfrom;
	}

	public boolean isMailauth() {
		return mailauth;
	}

	public String getMailsupport() {
		return mailsupport;
	}

	public boolean isIsdefault() {
		return isdefault;
	}

	public boolean isCanupdate() {
		return canupdate;
	}

	public void setCanupdate(boolean canupdate) {
		this.canupdate = canupdate;
	}
}
