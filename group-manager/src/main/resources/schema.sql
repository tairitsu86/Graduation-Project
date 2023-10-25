IF OBJECT_ID(N'group_table', N'U') IS NULL
    BEGIN
        CREATE TABLE [group_table](
            [group_id] [varchar](255) NOT NULL,
            [group_name] [varchar](255) NOT NULL,
            [description] [varchar](MAX) NOT NULL,
            [visible] [bit] NOT NULL,
            [join_actively] [bit] NOT NULL,
            CONSTRAINT [group_pk] PRIMARY KEY([group_id])
        )
    END;

IF OBJECT_ID(N'member', N'U') IS NULL
    BEGIN
        CREATE TABLE [member](
            [group_id] [varchar](255) NOT NULL,
            [username] [varchar](255) NOT NULL,
			[group_role] [varchar](255) NOT NULL,
            CONSTRAINT [member_pk] PRIMARY KEY([group_id],[username]),
            CONSTRAINT [member_fk] FOREIGN KEY([group_id]) REFERENCES [group_table]([group_id]),
			CONSTRAINT [group_role_check] CHECK ([group_role] = 'OWNER' OR [group_role] = 'MANAGER' OR [group_role] = 'MEMBER')
        )
    END;