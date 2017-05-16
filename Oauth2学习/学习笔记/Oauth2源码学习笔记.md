Spring Security Oauth2 + Mysql + Redis 认证授权服务器的搭建

Filter过滤器的处理流程：

实现了抽象类 AbstractAuthenticationProcessingFilter 的模板方法的
 子类：
	1、ClientCredentialsTokenEndpointFilter ： 此过滤器在 spring-security-oauth2 包
		顾名思义，客户端认证，使用请求的凭证去获取AccessToken的时候，将被该过滤器过滤。
		当客户端采用 客户端信任模式申请AccessToken的时候【发送Http到类 TokenEndpoint的控制器，映射 /oauth/token url的链接请求】，将被此过滤器过滤
	2、OAuth2ClientAuthenticationProcessingFilter：此过滤器在 spring-security-oauth2 包
		当用户已认证成功，获取AccessToken之后，使用 AccessToken 去检查有效性【/oauth/check_token】时，将被过滤
	3、UsernamePasswordAuthenticationFilter：此过滤器在 spring-security-web包
		对于登陆请求"/login"的过滤
	
上述的三个过滤器均实现了抽象类AbstractAuthenticationProcessingFilter中的模板方法: attemptAuthentication(request, response);，
在过滤器执行的过程【doFilter】中，将调用 模板方法authResult = attemptAuthentication(request, response);
而模板方法内部最底部则调用：this.getAuthenticationManager().authenticate(authRequest)方法进行数据信息的认证;

AuthenticationManager 接口：定义了一个认证方法：对根据http请求参数构造出的Authentication进行认证。
ProviderManager 类：用于管理所有实现了 AuthenticationManager 接口的类，在认证时，迭代调用 实现类，进行认证。
在认证过程中最
AbstractUserDetailsAuthenticationProvider 抽象类实现了 AuthenticationManager 接口，
并定义了模板方法 UserDetails retrieveUser(String username,	UsernamePasswordAuthenticationToken authentication);
在认证接口 AuthenticationManager 的实现类中，最重要的类是 DaoAuthenticationProvider.

DaoAuthenticationProvider 类：  实现了模板方法 retrieveUser。
    而在实现的过程中，调用了 UserDetailsService 接口的实现类方法 loadUserByUsername，
    该方法用于从目标存储地址获取 UserDetails。
    而目标存储地址，可能为 数据库、缓存、内存。
    UserDetailsService 接口 用于认证，可自定义实现类，灵活使用。
    
    
    应用级别的安全主要分为“验证( authentication) ”和“(授权) authorization ”两个部分。这也是Spring Security主要需要处理的两个部分

Authentication ”指的是建立规则( principal )的过程。规则可以是一个用户、设备、或者其他可以在我们的应用中执行某种操作的其他系统。" Authorization "指的是判断某个 principal 在我们的应用是否允许执行某个操作。
