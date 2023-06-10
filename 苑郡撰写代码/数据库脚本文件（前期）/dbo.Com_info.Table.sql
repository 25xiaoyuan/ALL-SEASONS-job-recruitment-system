USE [recruitmentAndJobApplicationSystem]
GO
/****** Object:  Table [dbo].[Com_info]    Script Date: 2023/6/8 0:36:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Com_info](
	[Com_ID] [int] NOT NULL,
	[Com_Name] [varchar](15) NOT NULL,
	[Com_Synopsis] [varchar](400) NOT NULL,
	[Com_Address] [varchar](50) NOT NULL,
	[Com_PhoneNumber] [int] NOT NULL,
	[Com_Email] [varchar](30) NOT NULL
) ON [PRIMARY]
GO
