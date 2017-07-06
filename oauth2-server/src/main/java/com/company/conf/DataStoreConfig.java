package com.company.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class DataStoreConfig {

    public static final String REDIS_CACHE_NAME="redis_cache_name";//不为null即可
    public static final String REDIS_PREFIX ="redis_cache_prefix";//不为null即可
    public static final Long EXPIRE =60*60L;//缓存有效时间

    /**
     * 配置用以存储用户认证信息的缓存
     */
    @Bean
    RedisCache redisCache(RedisTemplate redisTemplate){
        RedisCache redisCache = new RedisCache(REDIS_CACHE_NAME,REDIS_PREFIX.getBytes(),redisTemplate,EXPIRE);
        return redisCache;
    }
    /**
     *
     * 创建UserDetails存储服务的Bean：使用Redis作为缓存介质
     * UserDetails user = this.userCache.getUserFromCache(username)
     */
    @Bean
    public UserCache userCache(RedisCache redisCache) throws Exception {
        UserCache userCache = new SpringCacheBasedUserCache(redisCache);
        return userCache;
    }

    /**
     * 配置AccessToken的存储方式：此处使用Redis存储
     * Token的可选存储方式
     * 1、InMemoryTokenStore
     * 2、JdbcTokenStore
     * 3、JwtTokenStore
     * 4、RedisTokenStore
     * 5、JwkTokenStore
     */
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
