package com.udea.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.udea.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.udea.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.udea.domain.User.class.getName());
            createCache(cm, com.udea.domain.Authority.class.getName());
            createCache(cm, com.udea.domain.User.class.getName() + ".authorities");
            createCache(cm, com.udea.domain.MateriaSolicitud.class.getName());
            createCache(cm, com.udea.domain.SolicitudHomologacion.class.getName());
            createCache(cm, com.udea.domain.SolicitudHomologacion.class.getName() + ".materiaSolicituds");
            createCache(cm, com.udea.domain.Materia.class.getName());
            createCache(cm, com.udea.domain.Materia.class.getName() + ".materiaSolicituds");
            createCache(cm, com.udea.domain.Materia.class.getName() + ".relacions");
            createCache(cm, com.udea.domain.Materia.class.getName() + ".materiaSemestres");
            createCache(cm, com.udea.domain.Relacion.class.getName());
            createCache(cm, com.udea.domain.HistoriaAcademica.class.getName());
            createCache(cm, com.udea.domain.Estudiante.class.getName());
            createCache(cm, com.udea.domain.Estudiante.class.getName() + ".materiaSolicituds");
            createCache(cm, com.udea.domain.Estudiante.class.getName() + ".materiaSemestres");
            createCache(cm, com.udea.domain.Estudiante.class.getName() + ".historiaAcademicas");
            createCache(cm, com.udea.domain.ProgramaAcademico.class.getName());
            createCache(cm, com.udea.domain.ProgramaAcademico.class.getName() + ".solicitudHomologacions");
            createCache(cm, com.udea.domain.ProgramaAcademico.class.getName() + ".planEstudios");
            createCache(cm, com.udea.domain.ProgramaAcademico.class.getName() + ".estudiantes");
            createCache(cm, com.udea.domain.PlanEstudios.class.getName());
            createCache(cm, com.udea.domain.PlanEstudios.class.getName() + ".estudiantes");
            createCache(cm, com.udea.domain.MateriaSemestre.class.getName());
            createCache(cm, com.udea.domain.Semestre.class.getName());
            createCache(cm, com.udea.domain.Semestre.class.getName() + ".materiaSemestres");
            createCache(cm, com.udea.domain.Semestre.class.getName() + ".historiaAcademicas");
            createCache(cm, com.udea.domain.EstadoSolicitud.class.getName());
            createCache(cm, com.udea.domain.EstadoSolicitud.class.getName() + ".solicitudHomologacions");
            createCache(cm, com.udea.domain.EstadoSemestre.class.getName());
            createCache(cm, com.udea.domain.EstadoSemestre.class.getName() + ".semestres");
            createCache(cm, com.udea.domain.EstadoSemestre.class.getName() + ".historiaAcademicas");
            createCache(cm, com.udea.domain.Tercio.class.getName());
            createCache(cm, com.udea.domain.Tercio.class.getName() + ".historiaAcademicas");
            createCache(cm, com.udea.domain.SituacionAcademica.class.getName());
            createCache(cm, com.udea.domain.SituacionAcademica.class.getName() + ".historiaAcademicas");
            createCache(cm, com.udea.domain.TipoSemestre.class.getName());
            createCache(cm, com.udea.domain.TipoSemestre.class.getName() + ".semestres");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
