IF OBJECT_ID(N'api_data', N'U') IS NULL
    BEGIN
        CREATE TABLE [api_data](
            [event_name] [varchar](255) NOT NULL,
            [api_method] [varchar](255) NOT NULL,
            [url_template] [varchar](255) NOT NULL,
            [request_body_template] [varchar](MAX) NULL,
            CONSTRAINT [api_data_pk] PRIMARY KEY([event_name]),
            CONSTRAINT [api_data_api_method_check] CHECK (([api_method]='DELETE' OR [api_method]='PATCH' OR [api_method]='PUT' OR [api_method]='POST' OR [api_method]='GET'))
        )
    END;
IF OBJECT_ID(N'mq_data', N'U') IS NULL
    BEGIN
        CREATE TABLE [mq_data](
            [event_name] [varchar](255) NOT NULL,
            [event_body_template] [varchar](MAX) NULL,
            [topic] [varchar](255) NOT NULL,
            CONSTRAINT [mq_data_pk] PRIMARY KEY([event_name])
        )
    END;
IF OBJECT_ID(N'notify_data', N'U') IS NULL
    BEGIN
        CREATE TABLE [notify_data](
            [event_name] [varchar](255) NOT NULL,
            [respond_template] [varchar](MAX) NULL,
            CONSTRAINT [notify_data_pk] PRIMARY KEY([event_name])
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
