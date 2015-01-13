eployment:

1) Running it inside an embedded Camel instance:

    mvn clean camel:run

2) Build, Install and Deploy it as OSGi bundle:

    mvn clean install

then please enter this in the Karaf Console:
	features:install camel-sql
	features:install camel-twitter
    osgi:install -s mvn:com.redhat/shopping-demo-application/1.0.0-SNAPSHOT
