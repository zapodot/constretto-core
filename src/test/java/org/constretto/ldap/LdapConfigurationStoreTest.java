package org.constretto.ldap;

import org.constretto.exception.ConstrettoException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import static org.mockito.Mockito.*;

/**
 * @author zapodot
 */
@RunWith(MockitoJUnitRunner.class)
public class LdapConfigurationStoreTest {

    @Mock
    private Attributes attributes;

    @Mock
    private NamingEnumeration attributesNamingEnumeration;

    private LdapConfigurationStore parentLdapConfigurationStore = new LdapConfigurationStore();


    @Test(expected = ConstrettoException.class)
    public void testParseConfigurationAttributesReadFailed() throws Exception {

        when(attributes.getAll()).thenReturn(attributesNamingEnumeration);
        when(attributesNamingEnumeration.hasMore()).thenThrow(new NamingException());
        try {
            final LdapConfigurationStore ldapConfigurationStore = new LdapConfigurationStore(
                    parentLdapConfigurationStore,
                    attributes);
            ldapConfigurationStore.parseConfiguration();

        } finally {
            verify(attributes).getAll();
            verifyNoMoreInteractions(attributes);
        }
    }
}
