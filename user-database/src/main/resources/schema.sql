IF OBJECT_ID(N'user',N'U') IS NULL
	BEGIN
		CREATE TABLE [user](
			[username] [varchar](255) NOT NULL,
			[json_web_token] [varchar](255) NULL,
			[password] [varchar](255) NOT NULL,
			[permission] [int] NOT NULL,
			[user_display_name] [varchar](255) NOT NULL,
			CONSTRAINT [user_pk] PRIMARY KEY([username])
		)
	END;