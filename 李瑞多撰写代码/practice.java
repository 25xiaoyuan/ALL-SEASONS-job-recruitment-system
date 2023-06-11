package software;
import java.net.URL;
import java.sql.*;

import java.util.Scanner;

import static javafx.application.Platform.exit;

public class practice{
    //驱动路径
    private static final String DBDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    //数据库地址
    private static final String DBURL = "jdbc:sqlserver://192.168.12.110";
    //数据库登陆名
    private static final String DBUSER = "WSZ";
    //数据库登陆用户密码
    private static final String DBPASSWORD = "WSZ";
    //创建Connection对象
    public static Connection conn = null;
    //创建PreparedStatement对象
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    public static Connection connectDatabase(){
        /**
         * 连接数据库
         */
        try {

            //加载驱动程序
            Class.forName(DBDRIVER);
            //连接数据库
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
            System.out.println("数据库连接成功" );
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // 定义Scanner输入流读取控制台输入
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {

        // 初始化数据库连接
        conn=connectDatabase();
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


        try {
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
        if (number == 1) {
            studentMenu();
        }
        //招聘公司
        if (number == 2) {
            companyMenu();
        }
        //注册管理员
        if (number == 3) {
            managerMenu();
        }

    }


    ////////////////////////////////////////////////////李瑞多负责的代码和进行修改的代码////////////////////////////////////////////////////////////////////







    ////在校生首页界面(李瑞多修改后)
    public static void studentMenu(){
        boolean studentWhile=true;
        while(studentWhile){
            System.out.println("欢迎进入招聘求职系统——在校生首页");
            System.out.println("1.查看招聘信息");
            System.out.println("2.查看面试通知");
            System.out.println("3.个人中心");
            System.out.println("请输入数字选择操作：");
            int choice=sc.nextInt();
            if(choice==1){
                viewRecruitmentInformation();
            }else if(choice==2){
                viewInterviewNotifications();
            }else if(choice==3){
                studentPerMenu();
                studentWhile=studentPersonalCenter(studentWhile);
            }else{
                System.out.println("输入有误！");
            }
        }

    }

    ////李瑞多修改后的招聘公司菜单相关方法
    ////招聘公司登录后的主菜单
    public  static  void  comMainMenu(){
        System.out.println("欢迎进入招聘求职系统——招聘公司");
        System.out.println("1.查看招聘信息");
        System.out.println("2.编辑招聘信息");
        System.out.println("3.查看简历信息");
        System.out.println("4.个人中心");
        System.out.println("请输入数字选择操作：");


    }

    ////编辑招聘信息菜单
    public  static  void  manageRecruitMenu(){
        System.out.println("1.新增招聘信息");
        System.out.println("2.修改招聘信息");
        System.out.println("3.删除招聘信息");
        System.out.println("4.查看招聘信息");
        System.out.println("5.返回");
        System.out.println("请输入数字选择操作：");
    }

    ////招聘公司界面
    public static void companyMenu() throws SQLException {
        //进入循环和退出循环的条件
        boolean firstWhile=true;
        boolean secondWhile=true;
        while(firstWhile){
            comMainMenu();//公司首页菜单
            int choice=sc.nextInt();
            switch (choice){
                case 1:
                    viewRecruitmentInformation();
                    break;
                case 2:
                    while(secondWhile){
                        manageRecruitMenu();//公司编辑招聘信息的菜单
                        int comNum=sc.nextInt();
                        switch (comNum){
                            case 1:
                                insertRecruitment();//新增数据
                                break;
                            case 2:
                                changeRecruitment();//修改数据
                                break;
                            case 3:
                                deleteRecruitment();//删除数据
                                break;
                            case 4:
                                checkRecruitment();//查看数据
                                break;
                            case 5:
                                secondWhile=false;//退出循环
                                break;
                            default:
                                System.out.println("输入有误！");
                                break;
                        }
                    }
                    break;
                case 3:
                    viewResumeInformation();//
                    break;
                case 4:
                    companyPerMenu();
                    firstWhile=companyPersonalCenter(firstWhile);//若firstWhile==false，则退出循环
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }

        }

    }

    ////管理员界面(李瑞多修改)
    public static void managerMenu() throws SQLException {
        boolean managerWhile=true;
        while(managerWhile){
            System.out.println("欢迎进入招聘求职系统——管理员");
            System.out.println("1.查看招聘信息");
            System.out.println("2.管理招聘信息");
            System.out.println("3.个人中心");
            System.out.println("请输入数字选择操作：");
            int mChoice=sc.nextInt();
            switch (mChoice){
                case 1:
                    viewRecruitmentInformation();
                    break;
                case 2:
                    manageRecruitmentInformation();
                    break;
                case 3:
                    managerPerMenu();
                    managerWhile=managerPersonalCenter(managerWhile);
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
        }


    }

    ////管理员管理招聘信息
    public  static  void manageRecruitmentInformation()throws SQLException{
        String sql="SELECT * FROM Recruit_info";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {//显示出所有招聘信息的编号和发布时间
            System.out.println(rs.getInt("Rec_ID") + "\t\t\t"
                    + rs.getString("Rec_JobTitle") + "\t\t\t"
                    + rs.getString("Rec_Salary")+"\t\t\t"
                    + rs.getString("Rec_Rlease")+"\t\t\t"
                    + rs.getString("Rec_Responsibility")+"\t\t\t"
                    + rs.getString("Rec_Requirements")+"\t\t\t"
                    + rs.getString("Rec_AgeRange")+"\t\t\t"
                    + rs.getString("Rec_Benefits")+"\t\t\t"
                    + rs.getString("Rec_WorkAddress"));
        }
        System.out.println("是否进行管理操作(Y/N)：");//管理员决定是否删除不符合条件的招聘信息
        String isManage=sc.next();
        if(isManage=="Y"||isManage=="y"){
            deleteRecruitment();
        }

    }

    ////招聘公司编辑招聘信息(4个方法)
    ////新增招聘信息
    public static void insertRecruitment() throws SQLException{
        // 插入数据
        String sql = "INSERT INTO Recruit_info (Rec_ID, Rec_JobTitle, Rec_Salary,Rec_Rlease,Rec_Responsibility,Rec_Requirements,Rec_AgeRange,Rec_Benefits,Rec_WorkAddress) VALUES (?,?,?,?,?,?,?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("请依次输入新增的信息：");
        System.out.println("编号：");
        pstmt.setInt(1, sc.nextInt());
        System.out.println("岗位名称：");
        pstmt.setString(2, sc.next());
        System.out.println("薪资：");
        pstmt.setString(3, sc.next());
        System.out.println("发布时间：");
        pstmt.setString(4, sc.next());
        System.out.println("岗位职责：");
        pstmt.setString(5, sc.next());
        System.out.println("任职要求：");
        pstmt.setString(6, sc.next());
        System.out.println("年龄要求：");
        pstmt.setString(7, sc.next());
        System.out.println("福利待遇：");
        pstmt.setString(8, sc.next());
        System.out.println("工作地点：");
        pstmt.setString(9, sc.next());
        pstmt.executeUpdate();
    }

    ////修改招聘信息
    public static void changeRecruitment() throws SQLException{
        System.out.println("请输入需要修改的招聘信息编号：");
        int id=sc.nextInt();
        // 修改数据
        System.out.println("岗位名称  薪资  岗位职责  任职要求  年龄要求  福利待遇  工作地点");
        System.out.println("请输入需要修改的信息名称：");
        String str=sc.next();
        String sql = "UPDATE Recruit_info SET "+str+"= ? WHERE Rec_ID = "+id;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("请输入修改的信息：");
        pstmt.setString(1, sc.next());

        pstmt.executeUpdate();
    }

    ////删除招聘信息
    public static void deleteRecruitment() throws SQLException{
        //删除数据
        String sql = "DELETE FROM Recruit_info WHERE Rec_ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("请输入需要删除的招聘信息编号：");
        pstmt.setInt(1, sc.nextInt());
        pstmt.executeUpdate();
    }

    ////查看招聘信息
    public static void checkRecruitment() throws SQLException{
        System.out.println("请输入想要查看的招聘信息编号：");
        int id=sc.nextInt();
        // 查看数据
        String sql = "SELECT * FROM Recruit_info WHERE Rec_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt("Rec_ID") + "\t"
                    + rs.getString("Rec_JobTitle") + "\t"
                    + rs.getString("Rec_Salary")+"\t"
                    + rs.getString("Rec_Rlease")+"\t"
                    + rs.getString("Rec_Responsibility")+"\t"
                    + rs.getString("Rec_Requirements")+"\t"
                    + rs.getString("Rec_AgeRange")+"\t"
                    + rs.getString("Rec_Benefits")+"\t"
                    + rs.getString("Rec_WorkAddress"));
        }

    }

    ////李瑞多修改后的三种用户的个人菜单界面方法
    ////在校生个人中心菜单
    public  static  void  studentPerMenu(){
        System.out.println("欢迎进入招聘求职系统——在校生——个人中心");
        System.out.println("1.编辑个人信息");
        System.out.println("2.编辑简历信息");
        System.out.println("3.修改密码");
        System.out.println("4.退出");
        System.out.println("请输入数字选择操作：");
    }

    ////在校生个人中心
    public static boolean studentPersonalCenter(boolean bool){
//        编辑个人信息+编辑简历信息+修改密码+退出
        int choice=sc.nextInt();
        if(choice==1){
            editPersonalInformation();
        }else if(choice==2){
            editResumeInformation();
        }else if(choice==3){
            changePassword();
        }else if(choice==4)
        {
            bool=false;
            //withdraw();
        }else{
            System.out.println("输入有误！");
        }
        return bool;
    }

    ////招聘公司个人中心菜单
    public  static  void  companyPerMenu(){
        System.out.println("欢迎进入招聘求职系统——招聘公司——个人中心");
        System.out.println("1.编辑招聘公司信息");
        System.out.println("2.修改密码");
        System.out.println("3.退出");
        System.out.println("请输入数字选择操作：");

    }

    ////招聘公司个人中心
    public static boolean companyPersonalCenter(boolean bool) throws SQLException {

        int choice=sc.nextInt();
        if(choice==1){
            editRecruitmentCompanyInformation();
        }else if(choice==2){
            changePassword();
        }else if(choice==3){
            bool=false;

            //withdraw();
        }else{
            System.out.println("输入有误！");
        }
        return bool;
    }

    ////管理员个人中心菜单
    public  static  void  managerPerMenu(){
        System.out.println("欢迎进入招聘求职系统——注册管理员——个人中心");
        System.out.println("1.修改密码");
        System.out.println("2.退出");
        System.out.println("请输入数字选择操作：");
    }

    ////注册管理员个人中心
    public static boolean managerPersonalCenter(boolean bool){
        int choice=sc.nextInt();
        if(choice==1){
            changePassword();
        }else if(choice==2){
            bool=false;

            //withdraw();
        }else{
            System.out.println("输入有误！");
        }
        return bool;
    }






    ////////////////////////////////////////////////////李瑞多负责的代码和进行修改的代码///////////////////////////////////////////////////////////////////



    //查看招聘信息
    public static void viewRecruitmentInformation(){

    }
    //查看面试通知
    public static void viewInterviewNotifications(){

    }
    //查看简历信息
    public static void viewResumeInformation(){

    }
    //编辑个人信息
    public static void editPersonalInformation(){

    }
    //编辑简历信息
    public static void editResumeInformation(){

    }
    //编辑招聘公司信息
    public static void editRecruitmentCompanyInformation(){

    }
    //修改密码
    public static void  changePassword(){

    }
    //退出
    public static void withdraw(){

    }
}

