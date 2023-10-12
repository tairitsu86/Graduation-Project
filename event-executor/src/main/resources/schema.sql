IF OBJECT_ID(N'communication_config', N'U') IS NULL
    BEGIN
        CREATE TABLE [communication_config](
            [event_name] [varchar](255) NOT NULL,
            [method_type] [varchar](255) NOT NULL,
            [url_template] [varchar](255) NOT NULL,
            [header_template] [varchar](255) NULL,
            [body_template] [varchar](MAX) NULL,
            CONSTRAINT [communication_config_pk] PRIMARY KEY([event_name]),
            CONSTRAINT [communication_config_method_type_check] CHECK (([method_type]='DELETE' OR [method_type]='PATCH' OR [method_type]='PUT' OR [method_type]='POST' OR [method_type]='GET' OR [method_type]='MQ'))
        )
    END;
IF OBJECT_ID(N'notify_config', N'U') IS NULL
    BEGIN
        CREATE TABLE [notify_config](
            [event_name] [varchar](255) NOT NULL,
            [respond_template] [varchar](MAX) NULL,
            CONSTRAINT [notify_config_pk] PRIMARY KEY([event_name])
        )
    END;
IF OBJECT_ID(N'notify_variable', N'U') IS NULL
    BEGIN
        CREATE TABLE [notify_variable](
            [event_name] [varchar](255) NOT NULL,
			[variable_name] [varchar](255) NOT NULL,
            [notify_variable_type] [varchar](255) NOT NULL CONSTRAINT [notify_variable_type_check] CHECK([notify_variable_type] = 'SINGLE' OR [notify_variable_type] = 'ARRAY'),
            [json_path] [varchar](255) NOT NULL,
            CONSTRAINT [notify_variable_pk] PRIMARY KEY([event_name],[variable_name]),
            CONSTRAINT [notify_variable_fk] FOREIGN KEY([event_name]) REFERENCES [notify_data]([event_name])
        )
    END;
