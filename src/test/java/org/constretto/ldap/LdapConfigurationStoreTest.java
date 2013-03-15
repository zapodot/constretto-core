package org.constretto.ldap;

import com.sun.jndi.ldap.DefaultResponseControlFactory;
import com.sun.jndi.ldap.LdapCtxFactory;
import org.constretto.model.TaggedPropertySet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.ldap.test.LdapTestUtils;
import org.springframework.ldap.test.TestContextSourceFactoryBean;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.LdapContext;
import java.util.Collection;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

/**
 * @author sondre
 */

public class LdapConfigurationStoreTest {

    public static final int LDAP_PORT = 27389;

    @Before
    public void setUp() throws Exception {
        TestContextSourceFactoryBean testContextSourceFactoryBean = new TestContextSourceFactoryBean();
        testContextSourceFactoryBean.setLdifFile(new DefaultResourceLoader().getResource("classpath:setup_data.ldif"));
        testContextSourceFactoryBean.setDefaultPartitionSuffix("dc=se");
        testContextSourceFactoryBean.setDefaultPartitionName("jayway");
        testContextSourceFactoryBean.setSingleton(true);
        testContextSourceFactoryBean.setPrincipal(LdapTestUtils.DEFAULT_PRINCIPAL);
        testContextSourceFactoryBean.setPassword(LdapTestUtils.DEFAULT_PASSWORD);
        testContextSourceFactoryBean.setPort(LDAP_PORT);
        testContextSourceFactoryBean.afterPropertiesSet();

    }

    @Test
    public void testParseConfiguration() throws Exception {

        Hashtable<String, String> ldapEnvironment = createLdapEnvironment();

        final InitialDirContext dirContext = new InitialDirContext(ldapEnvironment);
        final LdapConfigurationStore configurationStore = LdapConfigurationStoreBuilder.usingDirContext(dirContext).forDsn("cn=Some Person,ou=company1,c=Sweden,dc=jayway,dc=se");
        final Collection<TaggedPropertySet> propertySets = configurationStore.parseConfiguration();
        assertEquals(1, propertySets.size());
        dirContext.close();

    }

    private Hashtable<String, String> createLdapEnvironment() {
        Hashtable<String, String> ldapEnvironment = new Hashtable<String, String>();
        ldapEnvironment.put(LdapContext.CONTROL_FACTORIES, DefaultResponseControlFactory.class.getName());
        ldapEnvironment.put(Context.PROVIDER_URL, String.format("ldap://localhost:%1$s", LDAP_PORT));
        ldapEnvironment.put(Context.INITIAL_CONTEXT_FACTORY, LdapCtxFactory.class.getName());
        ldapEnvironment.put(Context.SECURITY_PRINCIPAL, LdapTestUtils.DEFAULT_PRINCIPAL);
        ldapEnvironment.put(Context.SECURITY_CREDENTIALS, LdapTestUtils.DEFAULT_PASSWORD);
        ldapEnvironment.put(Context.SECURITY_PROTOCOL, "simple");
        return ldapEnvironment;
    }

}
