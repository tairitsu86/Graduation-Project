IF OBJECT_ID(N'device_state_history',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_state_history](
			[device_id] [varchar](255) NOT NULL,
			[alter_time] [datetime2](6) NOT NULL,
			[state_id] [int] NOT NULL,
			[state_value] [varchar](255) NOT NULL,
			[executor] [varchar](255) NULL,
			CONSTRAINT [device_state_history_pk] PRIMARY KEY([device_id],[alter_time],[state_id])
		)
	END;

IF OBJECT_ID(N'device_data',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_data](
			[device_id] [varchar](255) NOT NULL,
			[states] [varchar](MAX) NULL,
			[functions] [varchar](MAX) NULL,
			CONSTRAINT [device_data_pk] PRIMARY KEY([device_id])
		)
	END;


