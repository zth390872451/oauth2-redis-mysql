

/*******************************认证流程服务相关信息【BasicAuthenticationFilter类中涉及的Bean的相关解读】***********************************/

FilterChainProxy：一个过滤器执行的工具类。
class FilterChainProxy{
    拥有 List<SecurityFilterChain> filterChains;过滤器链的列表【包含多个过滤器链】
    而过滤器链 FilterChain 是包含过滤器的集合。
    处理流程：
    1、迭代过滤器链列表，选择合适的过滤器链
    2、迭代过滤器链，选择合适的过滤器进行过滤
    for (SecurityFilterChain chain : filterChains) {
        //寻找合适的过滤器链
    	if (chain.matches(request)) {
           for (Filter filter : filterChains) {
                void doFilter(ServletRequest request, ServletResponse response){
                    //迭代查找匹配的过滤器
                    if(filter.matches(HttpServletRequest request)){
                        filter.doFilter(request, response)
                    }                   
                }
           }			    
    	}
    }
 
在Oauth认证的过程中，重要的过滤器是：BasicAuthenticationFilter，对向Oauth2服务器
申请AccessToken的Http请求进行过滤操作，过滤操作中，主要是进行数据校验(认证)，保护信息安全。

BasicAuthenticationFilter 简要处理逻辑:
    1、处理 包含 Basic 请求头的 HTTP request请求, 并将结果存入 SecurityContextHolder
    2、Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ== Basic后的字符为 username:password base64编码的字符串
    3、调用认证管理器的authenticate方法对http请求进行认证，并返回认证结果
    4、认证成功，则提交给下一个过滤器，继续过滤；认证失败则抛出异常。
    5、后续过滤器全部认证成功，则映射到具体的控制器的接口【TokenEndpoint类的 oauth/token接口】进行处理
基础认证过滤器类 ：BasicAuthenticationFilter：
		doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain)
			{
				认证器提供者管理类：ProviderManager
					{
						//对认证器集合进行迭代选择合适的 AuthenticationProvider，认证的过程是：AuthenticationProvider 调用内部的业务类【有默认实现类，也可自定义】进行数据校验
						for (AuthenticationProvider provider : getProviders()){
							Authentication result = provider.authenticate(authentication);
						}
						认证器提供者：AuthenticationProvider
						    使用默认的实现类：DaoAuthenticationProvider 的 retrieveUser() 模板方法
						        UserDetails retrieveUser(username,authentication){
						             //使用 UserDetailsService实现类，从指定存储介质【数据库(Mysql、Oracle)、缓存(Redis)、内存(Memory)】中 加载数据进行校验。
						            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
						        }
					}
					
/*******************************授权流程服务相关配置【TokenEndpoint类中/oauth/token接口涉及的Bean的相关解读】***********************************/
		
TokenEndpoint：
    oauth/token 接口：
        1、进行认证逻辑【UserDetailsService.loadUserByUsername】
        2、认证成功，根据认证类型grant_type，则调用相应的凭证生成器 TokenGrant 根据认证信息，生成 AccessToken,并存储在指定的存储介质【1、数据库(Mysql|Oracle) 2、内存 3、Redis】中。
            2.1、TokenGrant接口：
                2.1.1、抽象类：AbstractTokenGranter：
                2.1.2、具体子类:
                       a、AuthorizationCodeTokenGranter：处理 grant_type 为 authorization_code 的请求
                       b、ClientCredentialsTokenGranter：处理 grant_type 为 client_credentials 的请求
                       c、ResourceOwnerPasswordTokenGranter:处理 grant_type 为 client_credentials 的请求
                       d、ImplicitTokenGranter：处理 grant_type 为 implicit 的请求
                       e、RefreshTokenGranter：处理 grant_type 为 refresh_token 的请求
        3、认证失败
        
TokenStore：配置AccessToken的存储方式【可选存储方式如下】：
	  1、InMemoryTokenStore
	  2、JdbcTokenStore
	  3、JwtTokenStore
	  4、RedisTokenStore




 
