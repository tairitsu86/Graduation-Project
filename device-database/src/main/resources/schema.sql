IF OBJECT_ID(N'devices',N'U') IS NULL
	BEGIN
		CREATE TABLE [devices](
			[device_id] [varchar](255) NOT NULL,
			[device_name] [varchar](255) NULL,
			[device_description] [varchar](255) NULL,
			[device_owner] [varchar](255) NOT NULL,
			CONSTRAINT [device_pk] PRIMARY KEY(device_id)
		)
	END;

IF OBJECT_ID(N'device_functions',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_functions](
			[device_id] [varchar](255) NOT NULL,
			[function_id] [int] NOT NULL,
			[function_name] [varchar](255) NULL,
			[function_type] [varchar](255) NOT NULL,
			CONSTRAINT [device_functions_fk] FOREIGN KEY([device_id]) REFERENCES [devices]([device_id]),
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
			CONSTRAINT [devices_parameters_fk] FOREIGN KEY([device_id],[function_id]) REFERENCES [device_functions]([device_id],[function_id]),
			CONSTRAINT [devices_parameters_pk] PRIMARY KEY([device_id],[function_id],[parameter_name])
		)
	END;

IF OBJECT_ID(N'device_permission_groups',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_permission_groups](
			[device_id] [varchar](255) NOT NULL,
			[group_id] [varchar](255) NOT NULL,
			CONSTRAINT [device_permission_groups_fk] FOREIGN KEY([device_id]) REFERENCES [devices]([device_id]),
			CONSTRAINT [device_permission_groups_pk] PRIMARY KEY([device_id],[group_id])
		)
	END;

IF OBJECT_ID(N'device_permission_users',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_permission_users](
			[device_id] [varchar](255) NOT NULL,
			[username] [varchar](255) NOT NULL,
			CONSTRAINT [device_permission_users_fk] FOREIGN KEY([device_id]) REFERENCES [devices]([device_id]),
			CONSTRAINT [device_permission_users_pk] PRIMARY KEY([device_id],[username])
		)
	END;
