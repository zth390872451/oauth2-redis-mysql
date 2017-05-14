package com.company.domain;


import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Entity - 会员
 * @author umeox
 */
@Entity
@Table(name = "ux_member")
public class Member extends BaseEntity {
	
	private static final long serialVersionUID = 100007152668889L;


	private String username;

	/**
	 * 密码
	 */
	private String password;


	public Member(Member member){
		super();
		this.username = member.getUsername();
		this.password = member.getPassword();
	}
	
	public Member() {
	}
	

	public Member(Long id) {
		super.setId(id);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
