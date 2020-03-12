# android-example
Example project to use [Cotter's Android SDK](https://github.com/cotterapp/android-sdk)

You will need your API Keys. Create a project in https://dev.cotter.app/, and **take note of your api keys!**.

### Testing Verify Email / Phone Number
1. Create a file apiKeys.xml inside [/res/values](https://github.com/cotterapp/android-example/tree/master/app/src/main/res/values), and add the following:
```
src/main/res/values/apiKeys.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="user_id">none</string>
    <string name="api_key_id">api-key-id</string>
    <string name="api_secret_key">api-secret-key</string>
</resources>
```
2. Press `Verify email/phone`


### Testing Pin & Biometrics
1. Create a user
```
curl -XPOST \
-H 'API_KEY_ID: api-key-id' \
-H 'API_SECRET_KEY: api-secret-key' \
-H "Content-Type: application/json" \
-d '{"client_user_id": "<YOUR USER ID>"}' \
'https://www.cotter.app/api/v0/user/create'
```
2. Create a file apiKeys.xml inside [/res/values](https://github.com/cotterapp/android-example/tree/master/app/src/main/res/values), and add the following:
```
src/main/res/values/apiKeys.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="user_id">user-id</string>
    <string name="api_key_id">api-key-id</string>
    <string name="api_secret_key">api-secret-key</string>
</resources>
```
3. Press `Initialize Cotter` before doing anything. You should see all the `??` text to be filled with `true` or `false`
