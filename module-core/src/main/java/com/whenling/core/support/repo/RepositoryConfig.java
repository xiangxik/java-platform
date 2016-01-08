package com.whenling.core.support.repo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableJpaRepositories(basePackages = { "com.whenling" }, repositoryImplementationPostfix = "Impl")
@EnableJpaAuditing
@EnableSpringDataWebSupport
public class RepositoryConfig {

}
