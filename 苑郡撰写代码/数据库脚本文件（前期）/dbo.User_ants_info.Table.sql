USE [recruitmentAndJobApplicationSystem]
GO
/****** Object:  Table [dbo].[User_ants_info]    Script Date: 2023/6/8 0:36:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_ants_info](
	[User_Account] [varchar](15) NOT NULL,
	[User_Code] [varchar](20) NOT NULL,
	[User_Captcha] [varchar](6) NOT NULL
) ON [PRIMARY]
GO
