package com.service.auth.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "user_id"),
		@UniqueConstraint(columnNames = "username") })
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;

	@Size(max = 30)
	private String first_name;

	@Size(max = 30)
	private String last_name;

	@NotBlank
	@Size(max = 300)
	private String username;

	private String password;

	@NotBlank
	@Size(max = 20)
	@Column(name = "user_role")
	private String userrole;


	@Size(max = 50)
	@Email
	private String email;

	@Size(max = 20)
	private String mobile_no;

	private Date hire_date;

	private Double salary;

	@Size(max = 30)
	private String first_name_ar;

	@Size(max = 30)
	private String last_name_ar;

	private Date date_time;
	
	private String otp;

	@Size(max = 10)
	private String changepasswordcode;

	@Size(max = 600)
	private String changeemailcode;

	@Size(max = 600)
	private String changemobilecode;
	
	private boolean enable;
	
	private boolean locked;
	
	private int invalidattempts;
    
    private boolean bypass3rdpartyauth;

	@Size(max = 200)
	private String longitude;
	
	@Size(max = 200)
	private String lattitude;

    @Transient 
    private List<Authorization> authorizedapis;

    @Transient 
	private String parentrole;

    @Transient 
	private String team;
    
	public Users() {
	}
	
	public void linkrole(Roles role) {
		this.parentrole = role.getParentrole();
		this.team = role.getTeam();
	}

	public Users(@NotBlank @Size(max = 30) String first_name, @NotBlank @Size(max = 30) String last_name,
			@NotBlank @Size(max = 300) String username, @NotBlank @Size(max = 20) String user_role, @Size(max = 50) @Email String email, @Size(max = 20) String mobile_no, @Size(max = 200) String longitude, @Size(max = 200) String lattitude) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.userrole = user_role;
		this.email = email;
		this.first_name_ar = first_name;
		this.last_name_ar = last_name;
		this.enable = true;
		this.date_time = new Date();
		this.locked = false;
		this.invalidattempts = 0;
		this.mobile_no = mobile_no;
		this.longitude = longitude;
		this.lattitude = lattitude;
	}

	public Users(@NotBlank @Size(max = 30) String first_name, @NotBlank @Size(max = 30) String last_name,
			@NotBlank @Size(max = 300) String username, @NotBlank @Size(max = 20) String user_role, 
			@Size(max = 50) @Email String email, @Size(max = 20) String mobile_no, Date hire_date, 
			Double salary, @Size(max = 50) String password, boolean bypass3rdpartyauth, @Size(max = 200) String longitude, @Size(max = 200) String lattitude) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.userrole = user_role;
		this.email = email;
		this.first_name_ar = first_name;
		this.last_name_ar = last_name;
		this.enable = true;
		this.mobile_no = mobile_no;
		this.hire_date = hire_date;
		this.salary = salary;
		this.password = password;
		this.date_time = new Date();
		this.locked = false;
		this.invalidattempts = 0;
		this.bypass3rdpartyauth = bypass3rdpartyauth;
		this.longitude = longitude;
		this.lattitude = lattitude;
	}

	
	public Users updateUser(String first_name, String last_name, Date hiredate, Double salary, String first_name_ar, String last_name_ar, String longitude, String lattitude) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.first_name_ar = first_name_ar;
		this.last_name_ar = last_name_ar;
		this.hire_date = hiredate;
		this.salary = salary;
		this.longitude = longitude;
		this.lattitude = lattitude;
		return this;
	}

	public Users adminUpdateUser(@NotBlank @Size(max = 30) String first_name, @NotBlank @Size(max = 30) String last_name,
			@NotBlank @Size(max = 300) String username, @NotBlank @Size(max = 20) String user_role, 
			@Size(max = 50) @Email String email, @Size(max = 20) String mobile_no, Date hire_date, 
			Double salary, @Size(max = 50) String password, boolean bypass3rdpartyauth) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.userrole = user_role;
		this.email = email;
		this.first_name_ar = first_name;
		this.last_name_ar = last_name;
		this.enable = true;
		this.mobile_no = mobile_no;
		this.hire_date = hire_date;
		this.salary = salary;
		this.password = password;
		this.bypass3rdpartyauth = bypass3rdpartyauth;
		
		return this;
	}

	public Long getUser_id() {
		return user_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getUser_role() {
		return userrole;
	}

	public String getEmail() {
		return email;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public Date getHire_date() {
		return hire_date;
	}

	public Double getSalary() {
		return salary;
	}

	public String getFirst_name_ar() {
		return first_name_ar;
	}

	public String getLast_name_ar() {
		return last_name_ar;
	}

	public Date getDate_time() {
		return date_time;
	}

	public String getOtp() {
		return otp;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser_role(String user_role) {
		this.userrole = user_role;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public void setFirst_name_ar(String first_name_ar) {
		this.first_name_ar = first_name_ar;
	}

	public void setLast_name_ar(String last_name_ar) {
		this.last_name_ar = last_name_ar;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public List<Authorization> getAuthorizedapis() {
		return authorizedapis;
	}

	public void setAuthorizedapis(List<Authorization> authorizedapis) {
		this.authorizedapis = authorizedapis;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getChangepasswordcode() {
		return changepasswordcode;
	}

	public void setChangepasswordcode(String changepasswordcode) {
		this.changepasswordcode = changepasswordcode;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getInvalidattempts() {
		return invalidattempts;
	}

	public void setInvalidattempts(int invalidattempts) {
		this.invalidattempts = invalidattempts;
	}

	public String getChangeemailcode() {
		return changeemailcode;
	}

	public void setChangeemailcode(String changeemailcode) {
		this.changeemailcode = changeemailcode;
	}

	public String getChangemobilecode() {
		return changemobilecode;
	}

	public void setChangemobilecode(String changemobilecode) {
		this.changemobilecode = changemobilecode;
	}

	public boolean isBypass3rdpartyauth() {
		return bypass3rdpartyauth;
	}

	public void setBypass3rdpartyauth(boolean bypass3rdpartyauth) {
		this.bypass3rdpartyauth = bypass3rdpartyauth;
	}

	public String getParentrole() {
		return parentrole;
	}

	public void setParentrole(String parentrole) {
		this.parentrole = parentrole;
	}

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
