### SpringSecurity-OAuth2- Authorization and Resource Server

###### To Get Authorization Code-

> Go to Browser and access URI -

http://<authorization_server>/<application_context>/oauth/authorize?response_type=<response_type>&client_id=<Client_id>

example-
```
For MI6 client-
http://localhost:8080/SpringSecurity-OAuth2/oauth/authorize?response_type=code&client_id=MI6

For MI5 client-
http://localhost:8080/SpringSecurity-OAuth2/oauth/authorize?response_type=code&client_id=MI5
```

###### To Get Access Token-

> Go to Any Rest client and 'post' with Client-credentials-

http://<authorization_server>/<application_context>/oauth/token?grant_type=authorization_code&code=<grant_code_in_step_1>

example-

```
For MI6 client, add MI6 credentials to request and access-
http://localhost:8080/SpringSecurity-OAuth2/oauth/token?grant_type=authorization_code&code=xyzcode
```

###### To Access End-Point-

> Go to Any Rest client and do 'get'-

http://<authorization_server>/<application_context>/<end_point>?access_token=<access_token_granted_in_step_2>

example-

```
http://localhost:8080/SpringSecurity-OAuth2/chief/internationCriminals?access_token=xyzaccesstoken
```

###### To get new access token-

> Go to Any Rest client and do 'post' with client credentials-

http://<authorization_server>/<application_context>/oauth/token?grant_type=refresh_token&refresh_token=<refresh_token_granted_in_step_2>


example-

```
http://localhost:8080/SpringSecurity-OAuth2/oauth/token?grant_type=refresh_token&refresh_token=abcrefereshtoken
```
