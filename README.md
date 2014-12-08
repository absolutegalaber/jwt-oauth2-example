# Spring security based test project for Micro services using JWT
[![Build Status](https://travis-ci.org/absolutegalaber/jwt-oauth2-example.svg?branch=master)](https://travis-ci.org/absolutegalaber/jwt-oauth2-example)

This test project contains following setup:

1. Authorization Server: An OAuth2 server which acts as an OAuth2 Authorization Server
2. Resource Server: An MicroService which acts as OAuth2 Client and serves Secure Resources
3. Aggregation Server: An MicroService, which acts as a proxy for the Resource Server and reuses OAuth2 Tokens for calls to underlining MicroServices


## How to start

1. Make sure you have maven installed
2. Make sure you have java 1.8 installed
3. Checkout the source code
4. Run "mvn clean install"

### Start the auth server

1. Inside "auth-server" execute "mvn springBoot:run"

### Start the resource server

1. Inside "resource-server" execute "mvn springBoot:run"

### Start the aggregation server

1. Inside "aggregation-server" execute "mvn springBoot:run"


## Playing with the setup

### API documentation

The resource server and the aggregation server expose their APIs via Swagger.
Testing the calls is currently not possible because of missing OAuth integration in the Swagger UI

1. Resource Server: Open http://localhost:8088/ and change the API URL to http://localhost:8088/api-docs
1. Aggregation Server: Open http://localhost:8888/ and change the API URL to http://localhost:8888/api-docs


### Get an Access Token

All access tokens can be decoded via https://developers.google.com/wallet/digital/docs/jwtdecoder
Just copy the "access_token" content to the form and click on "Decode JWT"

#### Getting access token via GrantType password

Execute `curl -u my-trusted-client: http://localhost:8080/oauth/token -d "grant_type=password&username=user&password=testpass"`

#### Getting access token via Client Credentials

Execute `curl -u my-trusted-client-with-secret:somesecret http://localhost:8080/oauth/token -d "grant_type=client_credentials"`

#### Getting access token via authorization code

1. Open browser with URL `http://localhost:8080/oauth/authorize?client_id=my-client-with-registered-redirect&response_type=code`
2. Use username `user`, password `testpass` for login
3. Click on `Authorize`
4. Copy the code parameter from the URL
5. Execute `curl -u my-client-with-registered-redirect: http://localhost:8080/oauth/token -d "grant_type=authorization_code&code=<code from URL>"`

### Execute Requests to the resource server

#### Loading protected resource from resource server

Direct Rest GET call to a protected resource on the resource server

1. Get an access token via authorization code or password
2. Execute `curl -H "Content-Type: application/json" -H "Authorization: Bearer <your token>" http://localhost:8088/api/me`

#### Loading protected resource from aggregation server

Rest GET call to the aggregation server, which uses the presented OAuth2 Token to make a call to a protected resource on the resource server

1. Get an access token via authorization code or password
2. Execute `curl -H "Content-Type: application/json" -H "Authorization: Bearer <your token>" http://localhost:8888/api/me`



