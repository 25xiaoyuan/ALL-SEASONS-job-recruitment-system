USE [recruitmentAndJobApplicationSystem]
GO
/****** Object:  Table [dbo].[Recruit_info]    Script Date: 2023/6/8 0:36:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Recruit_info](
	[Rec_ID] [int] NOT NULL,
	[Rec_JobTitle] [nchar](10) NOT NULL,
	[Rec_Salary] [varchar](15) NOT NULL,
	[Rec_Rlease] [varchar](15) NOT NULL,
	[Rec_Responsibility] [text] NOT NULL,
	[Rec_Requirements] [text] NOT NULL,
	[Rec_AgeRange] [varchar](20) NOT NULL,
	[Rec_Benefits] [text] NOT NULL,
	[Rec_WorkAddress] [varchar](50) NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
