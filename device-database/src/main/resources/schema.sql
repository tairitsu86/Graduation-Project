IF OBJECT_ID(N'customize_event', N'U') IS NULL
    BEGIN
        CREATE TABLE [device](
            [device_id] [varchar](255) NOT NULL,
            [device_name] [varchar](255) NULL,
            [device_description] [varchar](255) NULL,
            [owner] [varchar](255) NULL,
            CONSTRAINT [customize_event_pk] PRIMARY KEY([device_id])
        )
    END;
IF OBJECT_ID(N'grant_permission_groups',N'U') IS NULL
	BEGIN
		CREATE TABLE [grant_permission_groups](
			[device_id] [varchar](255) NOT NULL,
			[grant_permission_group] [varchar](255) NOT NULL,
			CONSTRAINT [grant_permission_groups_fk] FOREIGN KEY([device_id]) REFERENCES [device] ([device_id])
		) ON [PRIMARY]
	END;
IF OBJECT_ID(N'grant_permission_users',N'U') IS NULL
	BEGIN
		CREATE TABLE [grant_permission_users](
			[device_id] [varchar](255) NOT NULL,
			[grant_permission_user] [varchar](255) NOT NULL,
			CONSTRAINT [grant_permission_users_fk] FOREIGN KEY([device_id]) REFERENCES [device] ([device_id])
		) ON [PRIMARY]
	END;



