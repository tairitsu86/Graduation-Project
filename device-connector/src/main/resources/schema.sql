
IF OBJECT_ID(N'device_states',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_states](
			[device_id] [varchar](255) NOT NULL,
			[state_name] [varchar](255) NOT NULL,
			[state_value] [varchar](255) NOT NULL,
			CONSTRAINT [device_states_pk] PRIMARY KEY([device_id],[state_name])
		)
	END;

