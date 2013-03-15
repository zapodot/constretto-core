package org.constretto.ldap;

import org.constretto.ConfigurationStore;
import org.constretto.exception.ConstrettoException;
import org.constretto.model.TaggedPropertySet;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.*;

/**
 * @author zapodot
 */
public class LdapConfigurationStore implements ConfigurationStore {

    private Attributes attributes;

    public LdapConfigurationStore(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<TaggedPropertySet> parseConfiguration() {
        final NamingEnumeration<? extends Attribute> attributesAll = attributes.getAll();
        Map<String, String> properties = new HashMap<String, String>(attributes.size());
        try {
            while (attributesAll.hasMore()) {
                final Attribute attribute = attributesAll.next();
                properties.put(attribute.getID(), attribute.get().toString());

            }

        } catch (NamingException e) {
            throw new ConstrettoException("Could not read attributes from LDAP");
        }
        List<TaggedPropertySet> taggedPropertySets = new ArrayList<TaggedPropertySet>(1);
        taggedPropertySets.add(new TaggedPropertySet(properties, getClass()));
        return taggedPropertySets;
    }

}
