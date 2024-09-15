This framework tests multiple functionalities of dropbox api such as reading files, uploading files,
setting the profile photo and many others.
Tools used: Rest assured, Selenium, TestNG, Jackson, Extent spark reporter and other java utils.


1. First part is gaining the code by using selenium webdriver in headless mode, 
by hitting the url with specific data
provided after redirection to designated url the code has been parsed.

2. Obtaing OAUTH2 access token by sending apikey, apisecret, and code.

3. Complete test flow is straight forward with included negative and positive scenarios
both soft and hard assertions are used.
All utility actions are dynamic such as creating complex payloads and transfering 
data from test to test.

4. Extent reports are created upon every test run as html file.




