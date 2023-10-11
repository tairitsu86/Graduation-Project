IF OBJECT_ID(N'device_state_history',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_state_history](
			[device_id] [varchar](255) NOT NULL,
			[alter_time] [datetime2](6) NOT NULL,
			[state_name] [varchar](255) NOT NULL,
			[state_value] [varchar](255) NOT NULL,
			[executor] [varchar](255) NOT NULL,
			CONSTRAINT [device_state_history_pk] PRIMARY KEY([device_id],[alter_time],[state_name])
		)
	END;


