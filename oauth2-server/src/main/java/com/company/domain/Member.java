package com.company.domain;


import javax.persistence.*;
import java.io.Serializable;


/**
 * Entity - 会员
 * @author umeox
 */
@Entity
@Table(name = "ux_member")
public class Member implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;

	public Member(Member member){
		super();
		this.username = member.getUsername();
		this.password = member.getPassword();
	}
	
	public Member() {

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
