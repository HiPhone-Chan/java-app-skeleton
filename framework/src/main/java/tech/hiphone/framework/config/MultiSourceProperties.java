package tech.hiphone.framework.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "multi")
public class MultiSourceProperties {

    private final Map<String, MultiDataSource> dataSource = new HashMap<>();

    public Map<String, MultiDataSource> getDataSource() {
        return dataSource;
    }

    public static class MultiDataSource {

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
