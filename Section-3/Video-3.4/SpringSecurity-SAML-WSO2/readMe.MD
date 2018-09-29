### SpringSecurity-SAML-WSO2

> Part#1 - IDP config and Metadata

1. Download and start WSO2 using wso2server.bat(sh) based on OS.
2. Login using user 'admin' and password 'admin' => https://localhost:9443/carbon/admin/login.jsp
3. Go to "Resident" under "Identity Provider"
4. Change the `Home Realm Identifier` from "localhost" to `localhost_WSO2` ( this can be any name as long as you keep same `entityID` in `ws02.xml` file under metadata folder of the project. )
5. Verify in same tab : Inbound Authentication Configuration => SAML2 Web SSO Configuration => `Identity Provider Entity Id` must also be same i.e. `localhost_WSO2`
6. Click on "Download SAML Metadata", rename the file to wso2.xml and replace `wso2.xml` file under metadata folder of the project.

> Part#2 - Debugging
1. Go to server directory  => edit /repository/conf/log4j.properties 
    Enable 
    `log4j.logger.org.wso2.carbon.identity.sso.saml=DEBUG`
    `log4j.logger.org.wso2.carbon.identity.sso.agent=DEBUG`
    logs will be shown in terminal/command-prompt

> Part#3 - Keystore

1. Default keystore configuration in wso2, open file <carbon.home>/repository/conf/carbon.xml and search jks, should be something 
        `<KeyStore>

            <!-- Keystore file location-->

            <Location>${carbon.home}/repository/resources/security/wso2carbon.jks</Location>

            <!-- Keystore type (JKS/PKCS12 etc.)-->

            <Type>JKS</Type>

            <!-- Keystore password-->

            <Password>wso2carbon</Password>

            <!-- Private Key alias-->

            <KeyAlias>wso2carbon</KeyAlias>

            <!-- Private Key password-->

            <KeyPassword>wso2carbon</KeyPassword>

        </KeyStore>`

2.  Go to Location: ${carbon.home}/repository/resources/security/ 
3.  Here we have wso2 keystore available. 
4.  We need to generate a new keystore for our application and import public certificate from wso2-keystore
5.  We need to update wso2-keystore with the public certificate of our new keystore created in above step.

6. Create a new keystore for SP. keytool comes with JDK, make sure the paths are set to run following command. Note - alias/password/myKeystore name can be changed but make sure to change `bean-keyManager` in securityContext.xml in project, if not sure run as is.
   

>  `keytool -genkeypair -alias mycustomkeys -keypass password -keystore myKeystore.jks`

7. Export public certificate from ws02-keystore. Alias/password/keystore-name from Step-1
>  `keytool -export -keystore wso2carbon.jks -rfc -alias wso2carbon -file pub_IDP.cer`

8. Import generated pub_IDP.cer into myKeystore.jks
>  `keytool  -import  -v  -trustcacerts  -alias wso2carbon -file  pub_IDP.cer  -keystore myKeystore.jks`

9. Delete pub_IDP.cer

10. Export public certificate from myKeystore.jks . Alias/password/keystore-name from step-6
>  `keytool -export -keystore myKeystore.jks -rfc -alias mycustomkeys -file pub_SP.cer`

11. Import generated pub_SP.cer into wso2carbon.jks
>  `keytool  -import  -v  -trustcacerts  -alias mycustomkeys -file  pub_SP.cer  -keystore wso2carbon.jks`

12. Delete pub_SP.cer

13. Replace myKeystore.jks in project code - resources/security

14. Clean and build and Run the project

> Part#4 - SP Metadata to IDP

1. Login to WSO2 admin console
2. Service Providers -> Add
3. Give any name > sample/test/bla/newserviceprovider
4. Click Register and Go to Inbound Authentication Configuration -> SAML2 Web SSO Configuration -> click Configure
5. Either upload SP metadata by downloading from "http://localhost:8080/SpringSecurity-SAML-WSO2/saml/metadata" Or Simply choose "URL Configuration" and set the URL to "http://localhost:8080/SpringSecurity-SAML-WSO2/saml/metadata", click upload
6. Verify its reflecting under Service Providers-> List-> <SP> -> Inbound Authentication Configuration -> SAML2 Web SSO Configuration -> Issuer

> Part#5 - Login and Logout
1. Try hitting http://localhost:8080/SpringSecurity-SAML-WSO2/ and it should redirect to WSO2 login page.
2. Login using admin/admin
3. Do Local and Global Logout
4. Create more users and roles in WSO2 as per your need.
