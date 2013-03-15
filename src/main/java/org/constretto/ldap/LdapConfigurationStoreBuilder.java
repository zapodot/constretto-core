package org.constretto.ldap;

import org.constretto.exception.ConstrettoException;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapName;

/**
 * @author sondre
 */
public class LdapConfigurationStoreBuilder {

    private DirContext dirContext;

    private LdapConfigurationStoreBuilder(final DirContext dirContext) {
        this.dirContext = dirContext;
    }

    public static LdapConfigurationStoreBuilder usingDirContext(final DirContext dirContext) {
        return new LdapConfigurationStoreBuilder(dirContext);
    }

    public LdapConfigurationStore forDsn(final String distinguishedName) {
        try {
            final Attributes attributes = dirContext.getAttributes(createName(distinguishedName));
            return new LdapConfigurationStore(attributes);
        } catch (NamingException e) {
            throw new ConstrettoException(String.format("Could not find LDAP attributes for DSN \"%1$s\"", distinguishedName), e);
        }
    }

    private Name createName(final String distinguishedName) {
        try {
            return new LdapName(distinguishedName);
        } catch (InvalidNameException e) {
            throw new IllegalArgumentException(String.format("DSN \"%1$s\" not found", distinguishedName));
        }
    }


}
