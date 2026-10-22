package com.exemple.config;

import com.exemple.dao.DaoApi;
import com.exemple.dao.DaoFile;
import com.exemple.dao.IDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DaoAliasConfig {

    @Bean
    @Primary
    public IDao daoAlias() {
        // Pointe vers l'implémentation API
        return new DaoFile();
    }
}