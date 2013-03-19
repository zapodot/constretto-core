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
								.addDsn("cn=Kaare Nilsen,dc=constretto,dc=org") // maps all attributes without prefix
								.addDsn("sidekick", "cn=Jon-Anders Teigen,dc=constretto,dc=org") // maps LDAP attributes with prefix "sidekick"
								.addUsingSearch("dc=constretto,dc=org", "(&(cn=K*)(objectClass=inetOrgPerson))", "uid")
								    // Adds all LDAP objects matching the query to configuration attributes prefixed with the value of the "uid" attribute
								.done();

    ConstrettoConfiguration constrettoConfiguration =  new ConstrettoBuilder(false)
								.addConfigurationStore(configurationStore)
								.getConfiguration();
    final ConfigurableType configurationObject = constrettoConfiguration.as(ConfigurableType.class);
    ...

Plans for further development
-----------------------------
* specify environment markers in DSN-s to allow configuration for more than one environment to be store in a single LDAP
* merge project in to fork of the Constretto repository and create pull request to solve [CC-56](https://constretto.jira.com/browse/CC-56)

Credits
-------------
* The [Constretto project](http://constretto.org/) is created and maintained by Kåre Nilsen and his colleagues at Arktekk AS. 



