package com.company.service;

import com.company.domain.Member;
import com.company.domain.Role;
import com.company.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findOneByUsername(username);
		if (member == null) {
			log.error("用户不存在");
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		return new UserRepositoryUserDetails(member);
	}

	/**
	 *  注意该类的层次结构，继承了Member并实现了UserDetails接口，继承是为了使用Member的username和password信息
	 */
	private final static class UserRepositoryUserDetails extends Member implements UserDetails {
		private static final long serialVersionUID = 1L;
		private UserRepositoryUserDetails(Member member) {
			super(member);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			Role role = new Role();
			return role.getRoles();
		}

		@Override
		public String getUsername() {
			return super.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

	}

}
