package tech.hiphone.commons.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Map<String, Multi> multi = new HashMap<>();

    public Map<String, Multi> getMulti() {
        return multi;
    }

    public static class Multi {

        private final DataSourceProperties dataSourceProperties = new DataSourceProperties();
        private final JpaProperties jpaProperties = new JpaProperties();

        public DataSourceProperties getDataSourceProperties() {
            return dataSourceProperties;
        }

        public JpaProperties getJpaProperties() {
            return jpaProperties;
        }

    }
}
