package com.nosql;

@Configuration
@EnableCaching
public class CacheCOnfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("persons");
    }
}
