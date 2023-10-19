IF OBJECT_ID(N'device_state_history',N'U') IS NULL
	BEGIN
		CREATE TABLE [device_state_history](
			[device_id] [varchar](255) NOT NULL,
			[alter_time] [datetime2](6) NOT NULL,
			[state_name] [varchar](255) NOT NULL,
			[state_value] [varchar](255) NOT NULL,
			[executor] [varchar](255) NOT NULL,
			CONSTRAINT [device_state_history_pk] PRIMARY KEY([device_id],[alter_time],[state_name])
		)
	END;


--SELECT d2.[state_name] AS stateName, d2.[state_value] AS stateValue, d2.[executor]
--FROM device_state_history AS d2
--JOIN (
--	SELECT DISTINCT [device_id], [state_name], MAX([alter_time]) AS max_time
--	FROM device_state_history
--	WHERE device_id = ?1
--	GROUP BY [device_id], [state_name]
--) AS d1
--ON d1.device_id = d2.device_id AND d1.state_name = d2.state_name AND d1.max_time = d2.alter_time