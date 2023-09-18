IF OBJECT_ID(N'api_data', N'U') IS NULL
    BEGIN
        CREATE TABLE [api_data](
            [event_name] [varchar](255) NOT NULL,
            [api_method] [varchar](255) NOT NULL,
            [request_body_template] [varchar](MAX) NULL,
            [url_template] [varchar](255) NOT NULL,
            CONSTRAINT [api_data_pk] PRIMARY KEY([event_name]),
            CONSTRAINT [api_method_check] CHECK (([api_method]='DELETE' OR [api_method]='PATCH' OR [api_method]='PUT' OR [api_method]='POST' OR [api_method]='GET'))
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