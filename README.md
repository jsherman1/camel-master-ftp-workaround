camel-master-ftp-workaround
===========================

This project provides and example camel route in blueprint that works in Fuse ESB 7.1 using the Camel Master Component with a FTP Endpoint

This project contains a work around for a bug that is currently in the Fuse ESB Enterprise 7.1 release: 

    http://fusesource.com/issues/browse/ENTESB-522

Camel Router Project for Blueprint (OSGi)
=========================================

The Camel Master Component is only supported in Fuse Fabric container.  A profile should be created
as follows:

    fabric:profile-create --parents camel <profile-name>
    
    fabric:profile-edit --features camel-ftp <profile-name>

    fabric:profile-edit --bunldes mvn:com.example/camel-master-ftp-workaround <profile-name>
    
The profile can then be provisioned on a container as follows:

    fabric:container-create-child --profile <profile-name> root mycontainer
    
    or
    
    fabric:container-add-profile mycontainer <profile-name>

For more help see the Apache Camel documentation

    http://camel.apache.org/
    
For more help on Fuse Fabric see the Fabric documentation

    http://fuse.fusesource.org/fabric/index
