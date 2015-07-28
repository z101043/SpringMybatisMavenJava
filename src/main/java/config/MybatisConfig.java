package config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 루트 설정용 클래스.
 * 이 클래스는 스프링의 root-context.xml 의 역할을 대신한다.
 * @author mj
 *
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfig {
    
    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;
    
    @Value("${jdbc.url}") 
    private String jdbcUrl;
    
    @Value("${jdbc.username}")
    private String jdbcUsername;
    
    @Value("${jdbc.password}")
    private String jdbcPassword;

    /*
     * 프로퍼티 홀더는 다른 빈들이 사용하는 프로퍼티들을 로딩하기 때문에, static 메소드로 실행된다.
     * 다른 일반 빈들이 만들어지기전에 먼저 만들어져야 한다.
     * @return
     */
    @Bean
    public static PropertyPlaceholderConfigurer properties() {
      PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();

      Resource[] resources =
          new ClassPathResource[] {new ClassPathResource("configuration/db/config.properties")};
      ppc.setLocations(resources);
      ppc.setIgnoreResourceNotFound(false);
      ppc.setIgnoreUnresolvablePlaceholders(false);

      return ppc;
    }
    
    
    @Bean(destroyMethod = "close")
    public DataSource dataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.jdbcDriverClassName);
        dataSource.setUrl(this.jdbcUrl);
        dataSource.setUsername(this.jdbcUsername);
        dataSource.setPassword(this.jdbcPassword);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }
    
    @Bean
    public DataSourceInitializer dataSourceInitializer() throws Exception{
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new PathMatchingResourcePatternResolver().getResources("classpath*:db/*.sql"));
        //databasePopulator.addScript(new ClassPathResource("db/*.sql"));
        databasePopulator.setIgnoreFailedDrops(true);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(this.dataSource());
        initializer.setDatabasePopulator(databasePopulator);

        return initializer;
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception 
    {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.dataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage("org.ranestar.test.domain");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:sql/mybatis/mapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    
    @Bean
    public SqlSession sqlSession() throws Exception
    {
        SqlSession sqlSession = new SqlSessionTemplate(this.sqlSessionFactory());
        return sqlSession;
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager(){
    	DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    	transactionManager.setDataSource(this.dataSource());
    	return transactionManager;
    }
    
    @Bean
    public TransactionTemplate transactionTemplate(){
    	TransactionTemplate transactionTemplate = new TransactionTemplate();
    	transactionTemplate.setTransactionManager(this.transactionManager());
    	return transactionTemplate;
    }
    
    /**
     * MyBatis 를 사용하지 않고, 쌩 jdbcTemplate 를 이용해서 데이터베이스를 조회하는 DAO 를 만들기 위한 Bean.
     * 실전에서는 사용할 일이 거의 없다. 예제를 위해서 넣은 코드.
     * @return
     */
     /*
    @Bean
    public JdbcTemplate jdbcTemplate()
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(this.dataSource());
        return jdbcTemplate;
    }
    */
}
