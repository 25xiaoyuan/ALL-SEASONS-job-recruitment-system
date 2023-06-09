USE [recruitmentAndJobApplicationSystem]
GO
/****** Object:  Table [dbo].[Resume_info]    Script Date: 2023/6/8 0:36:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Resume_info](
	[Res_ID] [int] NOT NULL,
	[Res_Name] [varchar](15) NOT NULL,
	[Res_Age] [int] NOT NULL,
	[Res_PhoneNumber] [int] NOT NULL,
	[Res_Email] [varchar](30) NOT NULL,
	[Res_Job] [varchar](15) NOT NULL,
	[Res_Educational] [text] NOT NULL,
	[Res_Practice] [text] NOT NULL,
	[Res_Skills] [text] NOT NULL,
	[Res_Evaluation] [text] NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
