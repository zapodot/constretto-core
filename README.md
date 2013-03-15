constretto-ldap
===============

Constretto - support for LDAP

To use the LdapConfigurationStore:

    final InitialDirContext dirContext = ...
    final LdapConfigurationStore configurationStore = LdapConfigurationStoreBuilder.usingDirContext(dirContext).forDsn("cn=Some Person,ou=company1,c=Sweden,dc=jayway,dc=se");
    ConstrettoConfiguration constrettoConfiguration =  new ConstrettoBuilder(false).addConfigurationStore(configurationStore).getConfiguration();
    final ConfigurableType configurationObject = constrettoConfiguration.as(ConfigurableType.class);
    ...

Plans for further development
* add the ability to specify a LDAP search term and build an hierarcy of configuration properties 
* specify environment markers in DSN-s to allow configuration for more than one environment to be store in a single LDAP



