constretto-ldap
===============

Constretto - support for LDAP

To use the LdapConfigurationStore:

    import javax.naming.directory.InitialDirContext;
    import org.constretto.ConstrettoBuilder;
    import org.constretto.ConstrettoConfiguration;
    ...
    
    final InitialDirContext dirContext = ...
    final LdapConfigurationStore configurationStore = LdapConfigurationStoreBuilder
								.usingDirContext(dirContext)
								.forDsn("cn=Some Person,ou=company1,c=Sweden,dc=jayway,dc=se");
    ConstrettoConfiguration constrettoConfiguration =  new ConstrettoBuilder(false)
								.addConfigurationStore(configurationStore)
								.getConfiguration();
    final ConfigurableType configurationObject = constrettoConfiguration.as(ConfigurableType.class);
    ...

Plans for further development
-----------------------------
* add the ability to specify a LDAP search term and build an hierarcy of configuration properties 
* specify environment markers in DSN-s to allow configuration for more than one environment to be store in a single LDAP
* merge project in to fork of the Constretto repository and create pull request to solve [CC-56](https://constretto.jira.com/browse/CC-56)

Credits
-------------
* The [Constretto project](http://constretto.org/) is created and maintained by Kåre Nilsen and his colleagues at Arktekk AS. 
* Ldap sample template has been borrowed from the Spring LDAP project (retrieved from https://src.springframework.org/svn/spring-ldap/trunk/test/integration-tests/src/test/resources/setup_data.ldif )



