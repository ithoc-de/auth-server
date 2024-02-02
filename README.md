# OAuth 2.0 Authorization Server

## API Documentation
http://localhost:18081/swagger-ui/index.html#/

## Code Flow
1. Client Credentials Flow: 
https://auth0.com/docs/get-started/authentication-and-authorization-flow/client-credentials-flow

2. Call Your API Using the Client Credentials Flow: 
https://auth0.com/docs/get-started/authentication-and-authorization-flow/call-your-api-using-the-client-credentials-flow

3. Validate JSON Web Token: 
https://auth0.com/docs/secure/tokens/json-web-tokens/validate-json-web-tokens

## CORS
To test that CORS is disabled, you can use the following JavaScript code.
```javascript
function token() {
    const apiEndpoint = 'https://ithoc-auth-server.azurewebsites.net/token';

    const body = {
        grant_type: "client_credentials",
        client_id: "team-1",
        client_secret: "...",
        audience: "web-blog-rest-api-java"
    }

    fetch(apiEndpoint, {
        method: 'POST', // Specify the method
        headers: {
            'Content-Type': 'application/json' // Specify the type of content expected
        },
        body: JSON.stringify(body) // Convert the JavaScript object to a JSON string
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // or response.text() if the response is not JSON
        })
        .then(data => {
            console.log('Success:', data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
```