USE [recruitmentAndJobApplicationSystem]
GO
/****** Object:  Table [dbo].[Stu_per_info]    Script Date: 2023/6/8 0:36:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Stu_per_info](
	[Stu_Name] [varchar](20) NOT NULL,
	[Stu_Sex] [varchar](2) NOT NULL,
	[Stu_School] [varchar](50) NOT NULL,
	[Stu_Academy] [varchar](30) NOT NULL,
	[Stu_Major] [varchar](30) NOT NULL,
	[Stu_Result] [varchar](2) NOT NULL
) ON [PRIMARY]
GO
