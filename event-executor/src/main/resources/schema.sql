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
IF OBJECT_ID(N'respond_config', N'U') IS NULL
    BEGIN
        CREATE TABLE [respond_config](
            [event_name] [varchar](255) NOT NULL,
            [respond_type] [varchar](255) NULL,
            [respond_data] [varchar](MAX) NULL,
            CONSTRAINT [respond_config_pk] PRIMARY KEY([event_name])
        )
    END;

