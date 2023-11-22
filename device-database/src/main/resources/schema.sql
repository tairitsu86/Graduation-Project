IF OBJECT_ID(N'device',N'U') IS NULL
	BEGIN
		CREATE TABLE [device](
			[device_id] [varchar](255) NOT NULL,
			[device_name] [varchar](255) NULL,
			[description] [varchar](MAX) NULL,
			[owner] [varchar](255) NOT NULL,
			CONSTRAINT [device_pk] PRIMARY KEY(device_id)
		)
	END;

IF OBJECT_ID(N'device_permission_groups',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_permission_groups](
			[device_id] [varchar](255) NOT NULL,
			[group_id] [varchar](255) NOT NULL,
			CONSTRAINT [device_permission_groups_fk] FOREIGN KEY([device_id]) REFERENCES [device]([device_id]),
			CONSTRAINT [device_permission_groups_pk] PRIMARY KEY([device_id],[group_id])
		)
	END;

IF OBJECT_ID(N'device_permission_users',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_permission_users](
			[device_id] [varchar](255) NOT NULL,
			[username] [varchar](255) NOT NULL,
			CONSTRAINT [device_permission_users_fk] FOREIGN KEY([device_id]) REFERENCES [device]([device_id]),
			CONSTRAINT [device_permission_users_pk] PRIMARY KEY([device_id],[username])
		)
	END;
