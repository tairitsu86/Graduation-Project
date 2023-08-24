--Login
INSERT INTO [customize_event] (event_name,[description],api_method,url_template,request_body_template)
	SELECT 'LOGIN','Login','POST','http://user-database-service/login','{"username": "${USERNAME}","password": "${PASSWORD}","fromPlatform": "${PLATFORM}","platformType":"Instant-Messaging","platformUserId":"${USER_ID}"}'
	WHERE NOT EXISTS (SELECT 1 FROM [customize_event] WHERE event_name = 'LOGIN');

INSERT INTO [customize_event_variables] (event_name,variable)
	SELECT 'LOGIN','USERNAME'
	WHERE NOT EXISTS (SELECT 1 FROM [customize_event_variables] WHERE event_name='LOGIN' AND variable='USERNAME')

INSERT INTO [customize_event_variables] (event_name,variable)
	SELECT 'LOGIN','PASSWORD'
	WHERE NOT EXISTS (SELECT 1 FROM [customize_event_variables] WHERE event_name='LOGIN' AND variable='PASSWORD')

----Sign up
--INSERT INTO [customize_event] (event_name,[description],api_method,url_template,request_body_template)
--VALUES('SIGN_UP','Sign up','POST','http://user-database-service/users/new',
--'{"username": "${USERNAME}","password": "${PASSWORD}","userDisplayName": "${USER_DISPLAY_NAME}"}'
--);
--INSERT INTO [customize_event_variables] (event_name,variable)
--VALUES('SIGN_UP','USERNAME'),('SIGN_UP','PASSWORD'),('SIGN_UP','USER_DISPLAY_NAME');
--UPDATE [customize_event] SET url_template = 'http://localhost:8000/login' WHERE event_name = 'LOGIN'
--UPDATE [customize_event] SET url_template = 'http://localhost:8000/users/new' WHERE event_name = 'SIGN_UP'

