package com.example;

import org.apache.camel.Endpoint;
import org.fusesource.fabric.camel.MasterComponent;
import org.fusesource.fabric.camel.MasterEndpoint;
import org.fusesource.fabric.groups.Group;
import org.fusesource.fabric.groups.ZooKeeperGroupFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * This is a temporary fix for a bug in the MasterComponent in version 7.1 of fabric
 */
public class MyMasterComponent extends MasterComponent {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> params) throws Exception {
        int idx = remaining.indexOf(':');
        if (idx <= 0) {
            throw new IllegalArgumentException("Missing : in URI so can't split the group name from the actual URI for '" + remaining + "'");
        }
        // we are registering a regular endpoint
        String name = remaining.substring(0, idx);
        String fabricPath = getFabricPath(name);
        String childUri = remaining.substring(idx + 1);
         
		// Quickfix for adding uri parameters to the child uri.
        String paramQueryString = "?";
        if (params != null){
            for (String param : params.keySet()) {
                paramQueryString += String.format("%s=%s&", param, params.get(param));
            }
        }

        paramQueryString = paramQueryString.replaceAll(".$","");
        log.info("Appending querystring " + paramQueryString + " to childUri " + childUri );
        // Quickfix end
		
        Group group = ZooKeeperGroupFactory.create(getZkClient(), fabricPath);
        return new MasterEndpoint(uri, this, name, group, childUri + paramQueryString);
    }

}