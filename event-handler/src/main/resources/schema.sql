IF OBJECT_ID(N'customize_event', N'U') IS NULL
    BEGIN
        CREATE TABLE [customize_event](
            [event_name] [varchar](255) NOT NULL,
            [description] [varchar](255) NULL,
            CONSTRAINT [customize_event_pk] PRIMARY KEY([event_name])
        )
    END;
IF OBJECT_ID(N'customize_event_variables', N'U') IS NULL
    BEGIN
        CREATE TABLE [customize_event_variables](
			[event_name] [varchar](255) NOT NULL,
			[variable] [varchar](255) NOT NULL,
			[variables_order] [int] NOT NULL,
			CONSTRAINT [customize_event_variables_fk] FOREIGN KEY([event_name])REFERENCES [customize_event] ([event_name]),
			CONSTRAINT [event_variable_unique] UNIQUE([event_name], [variable])
		)
    END;
IF OBJECT_ID(N'user_state',N'U') IS NULL
    BEGIN
        CREATE TABLE [user_state](
            [platform] [varchar](255) NOT NULL,
            [user_id] [varchar](255) NOT NULL,
            [event_name] [varchar](255) NOT NULL,
            [username] [varchar](255) NULL,
            CONSTRAINT [user_state_pk] PRIMARY KEY([platform], [user_id])
        )
    END;
IF OBJECT_ID(N'user_state_data',N'U') IS NULL
	BEGIN
		CREATE TABLE [user_state_data](
			[platform] [varchar](255) NOT NULL,
			[user_id] [varchar](255) NOT NULL,
			[data] [varchar](255) NOT NULL,
			[data_order] [int] NOT NULL,
			CONSTRAINT [user_state_data_fk] FOREIGN KEY([platform], [user_id]) REFERENCES [user_state] ([platform], [user_id])
		) ON [PRIMARY]
	END;
IF OBJECT_ID(N'user_state_variables',N'U') IS NULL
	BEGIN
		CREATE TABLE [dbo].[user_state_variables](
			[platform] [varchar](255) NOT NULL,
			[user_id] [varchar](255) NOT NULL,
			[variable] [varchar](255) NOT NULL,
			[variable_key] [varchar](255) NOT NULL,
			CONSTRAINT [variable_key_unique] UNIQUE([platform], [user_id], [variable_key]),
			CONSTRAINT [user_state_variables_fk] FOREIGN KEY([platform], [user_id]) REFERENCES [user_state] ([platform], [user_id])
		)
	END;



