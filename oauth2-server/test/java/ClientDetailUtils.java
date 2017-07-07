import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public class ClientDetailUtils {

    //client_id 和 client_secret 生成
	public static void main(String[] args) {
		String password = "123456";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		System.out.println(hashedPassword);
	}

    /*
	插入数据库
	INSERT INTO `redis-oauth2`.`oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('client_auth_mode', '', '$2a$10$ei2IVxChXOklD2QPR4l4TOdEE8CdlREIEy775v5GfMnpSBX1misoy', 'read,write', 'client_credentials,refresh_token', NULL, 'USER', '3600', NULL, NULL, NULL);
	*/
}
