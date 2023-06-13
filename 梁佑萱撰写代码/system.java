package software1;

import java.sql.*;
import java.util.Scanner;

public class system {

    static Scanner sc=new Scanner(System.in);

    private static final String DEDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DEURL = "jdbc:sqlserver://localhost:1433;DatabaseName=";
    private static final String DBUSER = "LOG1";
    private static final String DBMM = "1";
    public static Connection conn = null;
    public static Statement stmt = null;
    public static ResultSet rs = null;


    //数据库连接
    public static Connection connectDatabase() {
        try{
            Class.forName(DEDRIVER);
            conn= DriverManager.getConnection(DEURL,DBUSER,DBMM);
            System.out.println("数据库连接成功！");
            return conn;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {

        // 菜单选择入口
        //System.out.println("欢迎使用招聘求职系统");
        System.out.println("1.查看简历信息");
        System.out.println("2.发布面试通知");
        System.out.println("3.编辑个人信息");
        System.out.println("4.编辑招聘公司信息：");
        int num=sc.nextInt();
        switch (num){
            case 1:
                viewResumeInformation();
                break;
            case 2:
                releaseInterviewNotice();
                break;
            case 3:
                editPersonalInformation();
                break;
            case 4:
                editPersonalInformation();
                break;
            default:
                System.out.println("输入有误！");
                break;
        }

    }

    //查看简历信息
    public static void viewResumeInformation(){
        System.out.println("请输入您的账号：");
        String acc = sc.next();
        conn = connectDatabase();
        if (null != conn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT *FROM Resume_info WHERE ID ="+acc);
                while (rs.next()) {
                    String resume = rs.getString("Res_Name");
                    resume += "\t" + rs.getString("Res_Age");
                    resume += "\t\t" + rs.getString("Res_PhoneNumber");
                    resume += "\t\t" + rs.getString("Res_Email");
                    resume += "\t\t" + rs.getString("Res_Job");
                    resume += "\t\t" + rs.getString("Res_Educational");
                    resume += "\t\t" + rs.getString("Res_Practice");
                    resume += "\t\t" + rs.getString("Res_Skills");
                    resume += "\t\t" + rs.getString("Res_Evaluation");
                    System.out.println("您的简历信息为：");
                    System.out.println(resume);
                    break;

                }

                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    //发布面试通知
    public static void releaseInterviewNotice(){
        Connection con = connectDatabase();
        if (null != conn) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT *FROM Delivery_record WHERE Del_CompanyID = 1" );
                while (rs.next()) {
                    String resume = rs.getString("");
                    resume += "\t" + rs.getString("");
                    resume += "\t\t" + rs.getString("");

                    System.out.println("投递的简历信息：");
                    System.out.println(resume);
                }
                System.out.println("是否选择要进行面试？Y/N");
                String result = sc.next();
                if (result.equals("Y") || result.equals("y")) {
                    System.out.println("请输入要进行面试的简历编号：");
                    String id = sc.next();
                    String insertSql = "UPDATE Stu_per_info SET Stu_Result= ‘是’ WHERE ID=" + id;
                    PreparedStatement ps = con.prepareStatement(insertSql);
                }
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //编辑个人信息
    public static void editPersonalInformation(){

        Scanner sc=new Scanner(System.in);

        try{

            Connection con=connectDatabase();
            CallableStatement ps=con.prepareCall("{call insert_Stu_per_info(?,?,?,?,?)}");
            System.out.println("请完善个人基本信息：");
            System.out.println("姓名：");
            ps.setString(1,sc.next());
            System.out.println("性别：");
            ps.setString(2,sc.next());
            System.out.println("学校：");
            ps.setString(3,sc.next());
            System.out.println("学院：");
            ps.setString(4,sc.next());
            System.out.println("专业：");
            ps.setString(5,sc.next());

            ps.execute();
            System.out.println("添加成功！");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //编辑招聘公司信息
    public static void editRecruitmentCompanyInformation(){
        Scanner sc=new Scanner(System.in );

        try{
            Connection con=connectDatabase();

            CallableStatement ps=con.prepareCall("{call insert_Com_info(?,?,?,?,?,?)}");
            System.out.println("请完善公司的基本信息:");
            System.out.println("公司名称：");
            ps.setString(1,sc.next());
            System.out.println("公司邮箱：");
            ps.setString(2,sc.next());
            System.out.println("公司地址：");
            ps.setString(3,sc.next());
            System.out.println("公司简介：");
            ps.setString(4,sc.next());
            System.out.println("公司编号：");
            ps.setString(5,sc.next());
            ps.execute();
            System.out.println("编辑成功！");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
