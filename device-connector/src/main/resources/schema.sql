IF OBJECT_ID(N'device_info',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_info](
			[device_id] [varchar](255) NOT NULL,
			[device_name] [varchar](255) NULL,
			[device_owner] [varchar](255) NOT NULL,
			CONSTRAINT [device_info_pk] PRIMARY KEY(device_id)
		)
	END;

IF OBJECT_ID(N'device_states',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_states](
			[device_id] [varchar](255) NOT NULL,
			[state_name] [varchar](255) NOT NULL,
			[state_value] [varchar](255) NULL,
			CONSTRAINT [device_states_fk] FOREIGN KEY([device_id]) REFERENCES [device_info]([device_id]),
			CONSTRAINT [device_states_pk] PRIMARY KEY([device_id],[state_name])
		)
	END;

IF OBJECT_ID(N'device_functions',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_functions](
			[device_id] [varchar](255) NOT NULL,
			[function_id] [int] NOT NULL,
			[function_name] [varchar](255) NULL,
			[function_type] [varchar](255) NOT NULL,
			CONSTRAINT [device_functions_fk] FOREIGN KEY([device_id]) REFERENCES [device_info]([device_id]),
			CONSTRAINT [function_type_check] CHECK([function_type] = 'STATE' OR [function_type] = 'CONTROL'),
			CONSTRAINT [device_functions_pk] PRIMARY KEY([device_id],[function_id])
		)
	END;

IF OBJECT_ID(N'device_function_parameters',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_function_parameters](
			[device_id] [varchar](255) NOT NULL,
			[function_id] [int] NOT NULL,
			[parameter_name] [varchar](255) NOT NULL,
			[parameter_range] [varchar](255) NULL,
			CONSTRAINT [device_info_parameters_fk] FOREIGN KEY([device_id],[function_id]) REFERENCES [device_functions]([device_id],[function_id]),
			CONSTRAINT [device_info_parameters_pk] PRIMARY KEY([device_id],[function_id],[parameter_name])
		)
	END;