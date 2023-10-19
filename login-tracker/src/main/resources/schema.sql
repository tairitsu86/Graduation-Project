IF OBJECT_ID(N'login_user',N'U') IS NULL
	BEGIN
		CREATE TABLE [login_user](
			[platform] [varchar](255) NOT NULL,
			[user_id] [varchar](255) NOT NULL,
			[username] [varchar](255) NOT NULL,
			CONSTRAINT [login_user_pk] PRIMARY KEY([platform] ,[user_id]),
			CONSTRAINT [login_user_unique] UNIQUE ([username])
		)
	END;