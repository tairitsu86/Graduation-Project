IF OBJECT_ID(N'customize_event', N'U') IS NULL
    BEGIN
        CREATE TABLE [customize_event](
            [event_name] [varchar](255) NOT NULL,
            [description_template] [varchar](255) NULL,
            [variables] [varchar](MAX) NULL,
            CONSTRAINT [customize_event_pk] PRIMARY KEY([event_name])
        )
    END;
IF OBJECT_ID(N'user_state',N'U') IS NULL
    BEGIN
        CREATE TABLE [user_state](
            [platform] [varchar](255) NOT NULL,
            [user_id] [varchar](255) NOT NULL,
            [event_name] [varchar](255) NOT NULL,
            [description] [varchar](255) NULL,
            [username] [varchar](255) NULL,
            [data] [varchar](MAX) NULL,
            [parameters] [varchar](MAX) NULL,
            CONSTRAINT [user_state_pk] PRIMARY KEY([platform], [user_id])
        )
    END;
IF OBJECT_ID(N'menu',N'U') IS NULL
    BEGIN
        CREATE TABLE [menu](
            [menu_name] [varchar](255) NOT NULL,
            [description] [varchar](MAX) NULL,
            [options] [varchar](MAX) NULL,
            [parameters] [varchar](MAX) NULL,
            CONSTRAINT [menu_pk] PRIMARY KEY([menu_name])
        )
    END;
