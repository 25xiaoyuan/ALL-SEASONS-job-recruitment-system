package softWare;

import java.net.URL;
import java.sql.*;

import java.util.Dictionary;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class practice {
    //个人信息库
    // 数据库连接信息，请根据自己的实际情况修改
    //URL指定了要连接到的MySQL数据库的位置和名称，这里是本地主机上的“job_system”数据库，使用utf-8编码，并且设置时区为亚洲上海
    public static final String URL = "jdbc:mysql://localhost:3306/job_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    public static final String USERNAME = "your_mysql_username";
    public static final String PASSWORD = "your_mysql_password";

    public static Connection conn = null;
    //创建PreparedStatement对象
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;



    //    // 用户账户信息（用户名、密码）
//    private static String[][] accounts = new String[100][2];
//    // 记录用户索引
//    private static int userIndex = 0;
    // 定义Scanner输入流读取控制台输入
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws SQLException{
        // 初始化数据库连接
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到MySQL JDBC驱动程序！");
            e.printStackTrace();
            return;
        }
        // 菜单选择入口
        System.out.println("欢迎使用招聘求职系统");
        System.out.println("1.用户登录");
        System.out.println("2.用户注册");
        System.out.println("3.退出系统");
        System.out.println("请输入数字选择操作：");

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("退出系统成功！");
                System.exit(0);
            default:
                System.out.println("无效的选项!");
        }
    }
    // 登录函数
    public static void login() throws SQLException {

        System.out.println("====用户登录====");
        System.out.println("1.在校生");
        System.out.println("2.招聘公司");
        System.out.println("3.注册管理员");
        System.out.println("++++++++++++++++++++");
        int choice=sc.nextInt();
        if(choice==1){
            //在校生

        }else if(choice==2){
            //招聘公司

        }else if(choice==3){
            //注册管理员

        }else{

        }
        System.out.println("请输入用户名:");
        String username = System.console().readLine();
        System.out.println("请输入密码:");
        String password = System.console().readLine();

//        Connection conn = null;
//        //创建PreparedStatement对象
//        PreparedStatement ps = null;
//        ResultSet rs = null;

        try {
            //连接到指定的MySQL数据库
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT id FROM users WHERE username=? AND password=?";
            //并将查询语句设置为其参数
            ps = conn.prepareStatement(sql);
            //将用户名和密码设置为查询语句中的占位符
            ps.setString(1, username);
            ps.setString(2, password);
            //执行查询
            rs = ps.executeQuery();

            //如果结果集中有记录，就说明用户名和密码正确，否则就说明登录失败。
            if (rs.next()) {
                System.out.println("登录成功！");
                //进入首页
                goTtoTheHomepage(choice);
            } else {
                System.out.println("用户名或密码错误！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            //关闭连接、语句和结果集
            conn.close();
            ps.close();
            rs.close();
        }

    }

    // 注册函数
    public static void register() {

        System.out.println("====用户注册====");
        System.out.println("1.在校生");
        System.out.println("2.招聘公司");
        System.out.println("++++++++++++++++++++");

        int choice=sc.nextInt();
        if(choice==1){
            //在校生

        }else if(choice==2){
            //招聘公司

        }else{

        }

        System.out.println("请输入用户名:");
        String username = System.console().readLine();
        System.out.println("请输入密码:");
        String password = System.console().readLine();
        System.out.println("请输入姓名:");
        String name = System.console().readLine();

        String sql = "INSERT INTO user(username, password, name) VALUES (?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, name);
            //返回受影响的行数
            //如果成功插入，则返回值为1，否则返回0
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("注册成功！");
            } else {
                System.out.println("注册失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("注册失败！");
        }

    }

    public static void goTtoTheHomepage(int number) throws SQLException {
        //在校生
        if(number==1){
            studentMenu();
        }
        //招聘公司
        if(number==2){
            companyMenu();
        }
        //注册管理员
        if(number==3){
            managerMenu();
        }

    }

    public static void studentMenu() throws SQLException {
        System.out.println("欢迎进入招聘求职系统——在校生首页");
        System.out.println("1.查看招聘信息");
        System.out.println("2.查看面试通知");
        System.out.println("3.个人中心");
        System.out.println("请输入数字选择操作：");
        int choice=sc.nextInt();
        if(choice==1){
            viewRecruitmentInformation();
        }else if(choice==2){
            int userId=scanner.nextInt();
            viewInterviewNotifications(userId);
        }else if(choice==3){
            studentPersonalCenter();
        }else{
            System.out.println("输入有误！");
        }
    }

    public static void companyMenu() throws SQLException {
        System.out.println("欢迎进入招聘求职系统——招聘公司");
        System.out.println("1.查看招聘信息");
        System.out.println("2.编辑招聘信息");
        System.out.println("3.查看简历信息");
        System.out.println("4.个人中心");
        System.out.println("请输入数字选择操作：");
        int choice=sc.nextInt();
        if(choice==1){
            viewRecruitmentInformation();
        }else if(choice==2){
            editRecruitmentInformation();
        }else if(choice==3){
            viewResumeInformation();
        }else if(choice==4){
            companyPersonalCenter();
        }else{
            System.out.println("输入有误！");
        }

    }

    public static void managerMenu() throws SQLException {
        System.out.println("欢迎进入招聘求职系统——注册管理员");
        System.out.println("1.查看招聘信息");
        System.out.println("2.编辑招聘信息");
        System.out.println("3.个人中心");
        System.out.println("请输入数字选择操作：");
        int choice=sc.nextInt();
        if(choice==1){
            viewRecruitmentInformation();
        }else if(choice==2){
            editRecruitmentInformation();
        }else if(choice==3){
            managerPersonalCenter();
        }else{
            System.out.println("输入有误！");
        }
    }

    ////查看招聘信息
    public static void viewRecruitmentInformation() throws SQLException {
        String sql="SELECT * FROM Recruit_info";
        //创建一个Statement对象用于执行SQL查询
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        //使用 ResultSetMetaData 获取所有列的列名以及相应的类型信息
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();//“md.getColumnCount()”返回 ResultSet 对象中包含的列数，
        for (int i = 1; i <= columns; ++i) {
            //“md.getColumnName(i)”和“md.getColumnTypeName(i)”分别返回第 i 列的列名和列类型
            System.out.print(md.getColumnName(i) + "(" + md.getColumnTypeName(i) + ") ");
        }
        System.out.println();
        //使用 while 循环迭代 ResultSet 中的所有行，并打印每一行数据
        while (rs.next()) {
            for (int i = 1; i <= columns; ++i) {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
        rs.close();
        stmt.close();
        conn.close();
    }

    //编辑招聘信息
    public static void editRecruitmentInformation(){

    }

    ////查看面试通知
    public static void viewInterviewNotifications(int userID)throws SQLException{
        //创建一个Statement对象用于执行SQL查询
        //通过用户ID查询
        String sql="SELECT Stu_Result FROM Stu_per_info WHERE ID=" +userID;
        PreparedStatement stmt = conn.prepareStatement(sql);
        //使用 executeQuery() 方法执行控制台输入的 SELECT 语句，注意要使用单引号 '' 将字符串型的值括起来
        ResultSet rs = stmt.executeQuery();
        //rs是有或者没有
        System.out.println("您" + rs+"面试机会");
    }
    //在校生个人中心
    public static void studentPersonalCenter() throws SQLException {
//        编辑个人信息+编辑简历信息+修改密码+退出
        System.out.println("欢迎进入招聘求职系统——在校生——个人中心");
        System.out.println("1.编辑个人信息");
        System.out.println("2.编辑简历信息");
        System.out.println("3.修改密码");
        System.out.println("4.退出");
        System.out.println("请输入数字选择操作：");

        int choice=sc.nextInt();
        if(choice==1){
            editPersonalInformation();
        }else if(choice==2){
            editResumeInformation();
        }else if(choice==3){
            changePassword();
        }else if(choice==4)
        {
            withdraw();
        }else{
            System.out.println("输入有误！");
        }
    }
    //招聘公司个人中心
    public static void companyPersonalCenter(){
        System.out.println("欢迎进入招聘求职系统——招聘公司——个人中心");
        System.out.println("1.编辑招聘公司信息");
        System.out.println("2.修改密码");
        System.out.println("3.退出");
        System.out.println("请输入数字选择操作：");

        int choice=sc.nextInt();
        if(choice==1){
            editRecruitmentCompanyInformation();
        }else if(choice==2){
            changePassword();
        }else if(choice==3){
            withdraw();
        }else{
            System.out.println("输入有误！");
        }
    }
    //注册管理员个人中心
    public static void managerPersonalCenter(){
        System.out.println("欢迎进入招聘求职系统——注册管理员——个人中心");
        System.out.println("1.修改密码");
        System.out.println("2.退出");
        System.out.println("请输入数字选择操作：");

        int choice=sc.nextInt();
        if(choice==1){
            changePassword();
        }else if(choice==2){
            withdraw();
        }else{
            System.out.println("输入有误！");
        }
    }

    //查看简历信息
    public static void viewResumeInformation(){

    }

    //发布面试通知
    public static void releaseInterviewNotice(){

    }

    //编辑个人信息
    public static void editPersonalInformation(){

    }

static String str=null;
    static Scanner scanner=new Scanner(System.in);
    ////编辑简历信息
    public static void editResumeInformation() throws SQLException {
        String name=scanner.next();
        //创建一个Statement对象用于执行SQL查询
        Statement stmt = conn.createStatement();
        System.out.println("请输入您需要修改的内容：");
        System.out.println("0:退回主界面");
        System.out.println("1:修改姓名");//public void updateName();
        System.out.println("2:修改年龄");//public void updateAge();
        System.out.println("3:修改电话");//public void updateTel();
        System.out.println("4:修改邮箱");//public void updateMailBox();
        System.out.println("5:修改求职岗位");//public void updateOffer(); 
        System.out.println("6:修改教育背景");//public void updateEducationalBackground();
        System.out.println("7：修改校内实践情况");//public void updateSchoolOption(); 
        System.out.println("8:修改个人技能");//public void updatePersonalSkills();
        System.out.println("9:修改自我评价");//public void updateSelfEvaluation();
        int choice = sc.nextInt();
        switch (choice) {
            case 0:
                System.out.println("退出系统成功！");
                System.exit(0);
            case 1:
                updateName(conn);
                break;
            case 2:
                updateAge(conn);
                break;
            case 3:
                updateTel(conn);
                break;
            case 4:
                updateMailBox(conn);
                break;
            case 5:
                updateOffer(conn);
                break;
            case 6:
                updateEducationalBackground(conn);
                break;
            case 7:
                updateSchoolOption(conn);
                break;
            case  8:
                updatePersonalSkills(conn);
                break;
            case 9:
                updateSelfEvaluation(conn);
                break;
            default:
                System.out.println("无效的选项!");
        }
    }
    //9:修改自我评价
    private static void updateSelfEvaluation(Connection conn) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Evaluation=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }
    //8:修改个人技能
    private static void updatePersonalSkills(Connection conn) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Skills=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }
    //7：修改校内实践情况
    private static void updateSchoolOption(Connection conn) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Practice=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }
    //6:修改教育背景
    private static void updateEducationalBackground(Connection conn) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Educational=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }
    //5:修改求职岗位
    private static void updateOffer(Connection conn) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Job=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }
    //4:修改邮箱
    private static void updateMailBox(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Email=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }
    //3:修改电话
    private static void updateTel(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_PhoneNumber=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }
    //2:修改年龄
    private static void updateAge(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Age=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }
    //1:修改姓名
    private static void updateName(Connection conn) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Name=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }


    //编辑招聘公司信息
    public static void editRecruitmentCompanyInformation(){

    }

    ////投递简历
    public static void submitResume(Connection conn)throws SQLException{
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入您想投递简历的招聘信息编号：");
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Delivery_record SET Del_CompanyID=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        System.out.println("请输入您想投递简历的招聘公司编号：");
        pstmt = conn.prepareStatement(
                "UPDATE Delivery_record SET Del_RecruitID=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        System.out.println("请输入您的简历编号：");
        pstmt = conn.prepareStatement(
                "UPDATE Delivery_record SET Del_ResumeID=? WHERE ID="+str);
        pstmt.setString(1, sc.next());
        pstmt.executeUpdate();
        System.out.println("更新成功！");
        pstmt.close();
    }

    //修改密码
    public static void  changePassword(){

    }

    //退出
    public static void withdraw(){

    }

}