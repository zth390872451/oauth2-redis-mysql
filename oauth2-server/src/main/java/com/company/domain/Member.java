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
	
	public final static int PUSH_TYPE_DEFAULT = 0;
	public final static int PUSH_TYPE_JPUSH = 1;
	
	/**
	 * 手机（账号：mobile/email）
	 */
	private String mobile; 
	
	/**
	 * 电话号码(海外)
	 */
	private String tel;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickName; 
	
	
	private String locationCode;//主动定位/录音随机码
	
	private String appId;
	
	private String token;//IOS的极光推送需要，IOS的手机标识

	/**
	 * 推送类型(null或0走默认的推送，1走极光推送)
	 */
	private Integer pushType;
	
	/**
	 * oauth2认证的clientId
	 */
	private String clientId;
	

	
	private String name;
	private String email;
	private String address;
	private String birthday;
	private String cap;
	private String familyName;
	private String hobbies;
	private String profession;
	
	/**
	 * 头像--微话饼&k3
	 * 系统图标（1-8数字，自定义头像）
	 */
	private String avatar;
	

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public Member(Member member){
		super();
		this.mobile = member.getMobile();
		this.password = member.getPassword();
	}
	
	public Member() {
	}
	

	public Member(Long id) {
		super.setId(id);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getLocationCode() {
		return locationCode;
	}


	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getCap() {
		return cap;
	}


	public void setCap(String cap) {
		this.cap = cap;
	}


	public String getFamilyName() {
		return familyName;
	}


	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}


	public String getHobbies() {
		return hobbies;
	}


	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}


	public String getProfession() {
		return profession;
	}


	public void setProfession(String profession) {
		this.profession = profession;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public static int getPushTypeDefault() {
		return PUSH_TYPE_DEFAULT;
	}

	public static int getPushTypeJpush() {
		return PUSH_TYPE_JPUSH;
	}

	public Integer getPushType() {
		return pushType;
	}
}
