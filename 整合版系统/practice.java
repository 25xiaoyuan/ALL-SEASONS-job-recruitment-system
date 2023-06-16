package software;
import java.net.URL;
import java.sql.*;

import java.util.Scanner;

//xiaoyuan 6/9

import static sun.nio.cs.Surrogate.MAX;

public class practice{

    //个人信息库
    // 数据库连接信息，请根据自己的实际情况修改
    //URL指定了要连接到的MySQL数据库的位置和名称，这里是本地主机上的“job_system”数据库，使用utf-8编码，并且设置时区为亚洲上海
    public static final String URL = "jdbc:sqlserver://localhost;databaseName=recruitmentAndJobApplicationSystem";
    public static final String USERNAME = "LOG1";
    public static final String PASSWORD = "123";
    //自定义注册管理员的账户和密码
//    public static String managerUser="manager";
//    public static String managerPassword="123456";


//    String dbURL = "jdbc:sqlserver://localhost;databaseName=recruitmentAndJobApplicationSystem";
//    String user = "LOG1";
//    String pass = "123";


    public static Connection conn = null;
    //public static Connection conn=DriverManager.getConnection()
    //创建PreparedStatement对象
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;

    public static String userID=null;


    // 定义Scanner输入流读取控制台输入
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD );

            //conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("数据库成功连接！");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // 菜单选择入口
        while(true){
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
    }

    
    // 登录函数
    public static void login() throws SQLException {

       while(true){
           System.out.println("====用户登录====");
           System.out.println("1.在校生");
           System.out.println("2.招聘公司");
           System.out.println("3.注册管理员");
           System.out.println("++++++++++++++++++++");
           int choice=sc.nextInt();

           if(choice==1||choice==2){
               System.out.println("请输入用户名:");
               String username = sc.next();
               System.out.println("请输入密码:");
               String password = sc.next();
               try {
                   //连接到指定的MySQL数据库
                   //conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                   conn = DriverManager.getConnection(URL, USERNAME, PASSWORD );
                   String sql = "SELECT * FROM User_ants_info WHERE ID=? AND User_Code =?";
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
                       break;
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               } finally {
                   //关闭连接、语句和结果集
                   conn.close();
                   ps.close();
                   rs.close();
               }
           }else if(choice==3){
               System.out.println("请输入用户名:");
               String username = sc.next();
               System.out.println("请输入密码:");
               String password = sc.next();

               if("5".equals(username) && "123460".equals(password)){
                   System.out.println("登录成功！");
                   //进入首页
                   goTtoTheHomepage(choice);
               }else{
                   System.out.println("用户名或密码错误！");
               }

           }else{
               System.out.println("输入有误！");
           }

       }
    }

    // 注册函数
    public static void register() {

        System.out.println("====用户注册====");
        System.out.println("1.在校生");
        System.out.println("2.招聘公司");
        System.out.println("++++++++++++++++++++");


        String sql=null;
        int choice=sc.nextInt();
        if(choice==1){

            System.out.println("请输入用户名:");
            String username = sc.next();
            System.out.println("请输入密码:");
            String password = sc.next();
            System.out.println("请输入验证码：");
            String captcha= sc.next();

            try {
                sql="INSERT INTO User_ants_info (ID, User_Code, User_Captcha) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, captcha);

                //返回受影响的行数
                //如果成功插入，则返回值为1，否则返回0
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("注册成功！");
                    //在校生
                    System.out.println("请完善个人基本信息：");

                    try{

                        String id="SELECT MAX(ID) + 1 FROM Stu_per_info";
                        ps = conn.prepareStatement(id);
                        rs = ps.executeQuery();

                        String strStudentId=null;
                        while (rs.next()) {
                            int studentId = rs.getInt(1);
                            // 获取查询结果的第一列数据（ID 列）
                            strStudentId=String.valueOf(studentId);
                        }

                        sql= "INSERT INTO Stu_per_info (ID, Stu_Name, Stu_Sex,Stu_School,Stu_Academy,Stu_Major,Stu_Result) VALUES (?,?, ?, ?,?,?,?)";


                        //String id="(SELECT MAX(columnName) + 1 FROM tableName)";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, strStudentId);
                        System.out.println("姓名：");
                        ps.setString(2,sc.next());
                        System.out.println("性别：");
                        ps.setString(3,sc.next());
                        System.out.println("学校：");
                        ps.setString(4,sc.next());
                        System.out.println("学院：");
                        ps.setString(5,sc.next());
                        System.out.println("专业：");
                        ps.setString(6,sc.next());

                        ps.setString(7,"否");
                        ps.execute();
                        System.out.println("添加成功！");
                        //进入登录页面
                        login();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("注册失败！");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("注册失败！");
            }

        }else if(choice==2){

            System.out.println("请输入用户名:");
            String username = sc.next();
            System.out.println("请输入密码:");
            String password = sc.next();
            System.out.println("请输入验证码：");
            String captcha= sc.next();

            try {
                sql="INSERT INTO User_ants_info (ID, User_Code, User_Captcha) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, captcha);

                //返回受影响的行数
                //如果成功插入，则返回值为1，否则返回0
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("注册成功！");

                    //公司认证

                    System.out.println("下面进入公司认证！");

                    System.out.println("请输入编号:");
                    String number = sc.next();
                    System.out.println("请输入税务登记证号码:");
                    String taxRegistrationCertificateNumber = sc.next();
                    System.out.println("请输入组织机构代码:");
                    String organizationCode= sc.next();

                    sql = "SELECT ID, TRCN,OC FROM Com_cert_info WHERE ID = ? AND TRCN=? AND OC=?";

                    ps = conn.prepareStatement(sql);
                    ps.setString(1, number);
                    ps.setString(2, taxRegistrationCertificateNumber);
                    ps.setString(3, organizationCode);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        // 对查询结果进行处理
                        System.out.println("认证成功！");
                    }


                    //招聘公司
                    System.out.println("请完善公司的基本信息:");

                    try{

                        String id="SELECT MAX(ID) + 1 FROM Com_info";

                        ps = conn.prepareStatement(id);
                        rs = ps.executeQuery();

                        String strCompanyId=null;
                        while (rs.next()) {
                            int companyId = rs.getInt(1);
                            // 获取查询结果的第一列数据（ID 列）
                            strCompanyId=String.valueOf(companyId);
                        }
                        sql= "INSERT INTO Com_info (ID, Com_Name, Com_Synopsis,Com_Address,Com_PhoneNumber,Com_Email) VALUES (?, ?, ?,?,?,?)";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1,strCompanyId);
                        System.out.println("公司名称：");
                        ps.setString(2,sc.next());
                        System.out.println("公司简介：");
                        ps.setString(3,sc.next());
                        System.out.println("公司地址：");
                        ps.setString(4,sc.next());
                        System.out.println("公司电话：");
                        ps.setInt(5,sc.nextInt());
                        System.out.println("公司邮箱：");
                        ps.setString(6,sc.next());

                        ps.execute();
                        System.out.println("添加成功！");
                        //进入登录页面
                        login();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("注册失败！");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("注册失败！");
            }

        }else{
            System.out.println("输入有误！");
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

    public  static  void studentMainMenu(){
        System.out.println("欢迎进入招聘求职系统——在校生首页");
        System.out.println("1.查看招聘信息");
        System.out.println("2.查看面试通知");
        System.out.println("3.个人中心");
        System.out.println("请输入数字选择操作：");
    }

    public static void studentMenu() throws SQLException {
        boolean firstWhile=true;
        boolean secoundWhile=true;
        while (firstWhile){
            studentMainMenu();
            int choice=sc.nextInt();
            if(choice==1){
                viewRecruitmentInformation();
            }else if(choice==2){
                viewInterviewNotifications();
            }else if(choice==3){
                while(secoundWhile){
                    studentPerMenu();
                    secoundWhile=studentPersonalCenter(secoundWhile);
                }

            }else{
                System.out.println("输入有误！");
            }
        }

    }

//    public static void companyMenu() throws SQLException {
//        System.out.println("欢迎进入招聘求职系统——招聘公司");
//        System.out.println("1.查看招聘信息");
//        System.out.println("2.编辑招聘信息");
//        System.out.println("3.查看简历信息");
//        System.out.println("4.个人中心");
//        System.out.println("请输入数字选择操作：");
//        int choice=sc.nextInt();
//        if(choice==1){
//            viewRecruitmentInformation();
//        }else if(choice==2){
//            editRecruitmentInformation();
//        }else if(choice==3){
//            viewResumeInformation();
//        }else if(choice==4){
//            companyPersonalCenter();
//        }else{
//                System.out.println("输入有误！");
//        }
//
//    }

    ///招聘公司登录后的主菜单
    public  static  void  comMainMenu(){
        System.out.println("欢迎进入招聘求职系统——招聘公司");
        System.out.println("1.查看招聘信息");
        System.out.println("2.编辑招聘信息");
        System.out.println("3.查看简历信息");
        System.out.println("4.面试通知");
        System.out.println("5.个人中心");
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
        boolean firstWhile=true;
        boolean secondWhile=true;
        while(firstWhile){
            comMainMenu();
            int choice=sc.nextInt();
            switch (choice){
                case 1:
                    viewRecruitmentInformation();
                    break;
                case 2:
                    while(secondWhile){
                        manageRecruitMenu();
                        int comNum=sc.nextInt();
                        switch (comNum){
                            case 1:
                                insertRecruitment(conn);//新增数据
                                break;
                            case 2:
                                changeRecruitment(conn);//修改数据
                                break;
                            case 3:
                                deleteRecruitment(conn);//删除数据
                                break;
                            case 4:
                                checkRecruitment(conn);//查看数据
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
                    viewResumeInformation(conn);//
                    break;
                case 4:
                    releaseInterviewNotice(conn);
                    break;
                case 5:
                    companyPerMenu();
                    firstWhile=companyPersonalCenter(firstWhile);//若firstWhile==false，则退出循环
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }

        }

    }


//    public static void managerMenu() throws SQLException {
//        System.out.println("欢迎进入招聘求职系统——注册管理员");
//        System.out.println("1.查看招聘信息");
//        System.out.println("2.编辑招聘信息");
//        System.out.println("3.个人中心");
//        System.out.println("请输入数字选择操作：");
//        int choice=sc.nextInt();
//        if(choice==1){
//            viewRecruitmentInformation();
//        }else if(choice==2){
//            editRecruitmentInformation();
//        }else if(choice==3){
//            managerPersonalCenter();
//        }else{
//            System.out.println("输入有误！");
//        }
//    }

    ////管理员界面
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
                    manageRecruitmentInformation(conn);
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



    //查看招聘信息
    public static void viewRecruitmentInformation() throws SQLException {
        String sql="SELECT * FROM Recruit_info";
        //创建一个Statement对象用于执行SQL查询
        ps= conn.prepareStatement(sql);
        rs = ps.executeQuery();
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
        ps.close();

    }

    //编辑招聘信息
    public static void editRecruitmentInformation(){


//        System.out.println("请完善公司的基本信息:");
//        System.out.println("公司名称：");
//        String name=sc.next();
//
//        System.out.println("公司邮箱：");
//        String email=sc.next();
//
//        System.out.println("公司地址：");
//        String address=sc.next();
//
//        System.out.println("公司简介：");
//        String introduce=sc.next();
//
//        System.out.println("公司编号：");
//        String number=sc.next();
//
//        try{
//            //Connection con=practice.connectDatabase();
//            //"UPDATE tableName SET columnName = (SELECT MAX(columnName) + 1 FROM tableName)"
//            ps=conn.prepareCall("{call insert_Com_info((SELECT MAX(columnName) + 1 FROM tableName,?,?,?,?,?)}");
//            //ps.setInt(1,name);
//            ps.setString(2,name);
//            ps.setString(3,email);
//            ps.setString(4,address);
//            ps.setString(5,introduce);
//            ps.setString(6,number);
//            ps.execute();
//            System.out.println("编辑成功！");
//        }catch (SQLException e){
//            e.printStackTrace();
//        }

    }

    //查看面试通知
    public static void viewInterviewNotifications() throws SQLException {
        //创建一个Statement对象用于执行SQL查询
        //通过用户ID查询
        String sql="SELECT Stu_Result FROM Stu_per_info WHERE ID=" +userID;
        ps = conn.prepareStatement(sql);
        //使用 executeQuery() 方法执行控制台输入的 SELECT 语句，注意要使用单引号 '' 将字符串型的值括起来
        ResultSet rs = ps.executeQuery();
        //rs是有或者没有
        //结果集没有当前行。
        while(rs.next()){
            String columnValue=rs.getString("Stu_Result");
            System.out.println(columnValue);
        }
        //System.out.println("您有" + rs.getString("SQLServerResultSet")+"次面试机会");
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
            editRecruitmentCompanyInformation(conn);
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

//    //在校生个人中心
//    public static void studentPersonalCenter() throws SQLException {
//        System.out.println("欢迎进入招聘求职系统——在校生——个人中心");
//        System.out.println("1.编辑个人信息");
//        System.out.println("2.编辑简历信息");
//        System.out.println("3.修改密码");
//        System.out.println("4.退出");
//        System.out.println("请输入数字选择操作：");
//
//        int choice=sc.nextInt();
//        if(choice==1){
//            editPersonalInformation(conn);
//        }else if(choice==2){
//            editResumeInformation();
//        }else if(choice==3){
//            changePassword();
//        }else if(choice==4)
//        {
//            withdraw();
//        }else{
//            System.out.println("输入有误！");
//        }
//    }
//
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
    public static boolean studentPersonalCenter(boolean bool) throws SQLException {
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

//    //注册管理员个人中心
//    public static void managerPersonalCenter() throws SQLException {
//        System.out.println("欢迎进入招聘求职系统——注册管理员——个人中心");
//        System.out.println("1.修改密码");
//        System.out.println("2.退出");
//        System.out.println("请输入数字选择操作：");
//
//        int choice=sc.nextInt();
//        if(choice==1){
//            changePassword();
//        }else if(choice==2){
//            withdraw();
//        }else{
//            System.out.println("输入有误！");
//        }
//    }

    ////管理员个人中心菜单
    public  static  void  managerPerMenu(){
        System.out.println("欢迎进入招聘求职系统——管理员——个人中心");
        System.out.println("1.修改密码");
        System.out.println("2.退出");
        System.out.println("请输入数字选择操作：");
    }

    ////注册管理员个人中心
    public static boolean managerPersonalCenter(boolean bool) throws SQLException {
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


    //查看简历信息
    public static void viewResumeInformation(){

    }

    //发布面试通知
    public static void releaseInterviewNotice(){

    }

    //编辑个人信息
    public static void editPersonalInformation(){
        Scanner sc=new Scanner(System.in);

//        System.out.println("请完善个人基本信息：");
//        System.out.println("姓名：");
//        stu.setName(sc.next());
//        System.out.println("性别：");
//        stu.setStuSex(sc.next());
//        System.out.println("学校：");
//        stu.setStuSchool(sc.next());
//        System.out.println("学院：");
//        stu.setStuCollege(sc.next());
//        System.out.println("专业：");
//        stu.setStuMajor(sc.next());
//
//        try{
//            Connection con=practice.connectDatabase();
//            CallableStatement ps=con.prepareCall("{call insert_Stu_per_info(?,?,?,?,?)}");
//            ps.setString(1,stu.getName());
//            ps.setString(2,stu.getStuSex());
//            ps.setString(3,stu.getStuSchool());
//            ps.setString(4,stu.getStuCollege());
//            ps.setString(5,stu.getStuMajor());
//            ps.execute();
//            System.out.println("添加成功！");
//        }catch (SQLException e){
//            e.printStackTrace();
//        }

        System.out.println("请完善个人基本信息：");
        System.out.println("姓名：");
        String name=sc.next();

        System.out.println("性别：");
        String sex=sc.next();

        System.out.println("学校：");
        String school=sc.next();

        System.out.println("学院：");
        String college=sc.next();

        System.out.println("专业：");
        String major=sc.next();


        try{
            //Connection con=practice.connectDatabase();
            CallableStatement ps=conn.prepareCall("{call insert_Stu_per_info(?,?,?,?,?)}");
            ps.setString(1,name);
            ps.setString(2,sex);
            ps.setString(3,school);
            ps.setString(4,college);
            ps.setString(5,major);
            ps.execute();
            System.out.println("添加成功！");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }



    //编辑简历信息
    public static void editResumeInformation() throws SQLException {

        String name=sc.nextLine();
        //创建一个Statement对象用于执行SQL查询
        //Statement stmt = conn.createStatement();
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
        ps= conn.prepareStatement(
                "UPDATE Resume_info SET Res_Evaluation=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }
    //8:修改个人技能
    private static void updatePersonalSkills(Connection conn) throws SQLException{
        ps = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Skills=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }
    //7：修改校内实践情况
    private static void updateSchoolOption(Connection conn) throws SQLException{
        ps = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Practice=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }
    //6:修改教育背景
    private static void updateEducationalBackground(Connection conn) throws SQLException{
        ps = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Educational=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }
    //5:修改求职岗位
    private static void updateOffer(Connection conn) throws SQLException{
        ps= conn.prepareStatement(
                "UPDATE Resume_info SET Res_Job=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }
    //4:修改邮箱
    private static void updateMailBox(Connection conn) throws SQLException {
        ps = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Email=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }
    //3:修改电话
    private static void updateTel(Connection conn) throws SQLException {
        ps= conn.prepareStatement(
                "UPDATE Resume_info SET Res_PhoneNumber=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }
    //2:修改年龄
    private static void updateAge(Connection conn) throws SQLException {
        ps= conn.prepareStatement(
                "UPDATE Resume_info SET Res_Age=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }
    //1:修改姓名
    private static void updateName(Connection conn) throws SQLException{
        ps = conn.prepareStatement(
                "UPDATE Resume_info SET Res_Name=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }


    //编辑招聘公司信息
    public static void editRecruitmentCompanyInformation(){

    }

    //投递简历
    public static void submitResume() throws SQLException {
        System.out.println("请输入您想投递简历的招聘信息编号：");
        ps = conn.prepareStatement(
                "UPDATE Delivery_record SET Del_CompanyID=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        System.out.println("请输入您想投递简历的招聘公司编号：");
        ps = conn.prepareStatement(
                "UPDATE Delivery_record SET Del_RecruitID=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        System.out.println("请输入您的简历编号：");
        ps = conn.prepareStatement(
                "UPDATE Delivery_record SET Del_ResumeID=? WHERE ID="+userID);
        ps.setString(1, sc.next());
        ps.executeUpdate();
        System.out.println("更新成功！");
        ps.close();
    }

    //修改密码
    public static void  changePassword() throws SQLException {


        try {
            System.out.println("请输入验证码：");
            String captcha=sc.next();
            // 首先查询数据库判断原密码是否正确
            String sql = "SELECT * FROM User_ants_info WHERE User_Captcha=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,captcha);

            rs = ps.executeQuery();

            if (rs.next()) {

                // 如果验证码正确，则对数据库进行更新操作来修改密码
                String  str = "UPDATE User_ants_info SET User_Code = ? WHERE User_Captcha = ?";
                PreparedStatement ps = conn.prepareStatement(str);
                System.out.println("请输入新密码：");
                ps.setString(1, sc.next()); // 设置第一个占位符参数
                ps.setString(2, captcha); // 设置第二个占位符参数
                ps.executeUpdate();

                int affectedRows = ps.executeUpdate(); // 获取受影响的行数
                if (affectedRows > 0) {
                    System.out.println("密码修改成功！");
                } else {
                    System.out.println("密码修改失败！");
                }
            } else {
                System.out.println("旧密码输入错误，请重新输入！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
                rs.close();
            }
        }
    }


    ////新增招聘信息
    public static void insertRecruitment(Connection conn) throws SQLException{
        // 插入数据
        String sql = "INSERT INTO Recruit_info (ID, Rec_JobTitle, Rec_Salary,Rec_Rlease,Rec_Responsibility,Rec_Requirements,Rec_AgeRange,Rec_Benefits,Rec_WorkAddress) VALUES (?,?,?,?,?,?,?, ?, ?)";
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
        System.out.println("新增成功");

    }

    ////修改招聘信息
    public static void changeRecruitment(Connection conn) throws SQLException{
        System.out.println("请输入需要修改的招聘信息编号：");
        int id=sc.nextInt();
        // 修改数据
        System.out.println("岗位名称(Rec_JobTitle  薪资(Rec_Salary)  岗位职责(Rec_Responsibility)  任职要求(Rec_Requirements)  年龄要求(Rec_AgeRange)  福利待遇(Rec_Benefits)  工作地点(Rec_WorkAddress)");

        String sql = "UPDATE Recruit_info SET ? = ? WHERE ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("请输入需要修改的信息英文名称：");
        pstmt.setString(1, sc.next());
        System.out.println("请输入修改的信息：");
        pstmt.setString(2, sc.next());
        pstmt.setInt(3, id);

        pstmt.executeUpdate();
        System.out.println("修改成功");

    }


    ////删除招聘信息
    public static void deleteRecruitment(Connection conn) throws SQLException{
        //删除数据
        String sql = "DELETE FROM Recruit_info WHERE ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("请输入需要删除的招聘信息编号：");
        pstmt.setInt(1, sc.nextInt());
        pstmt.executeUpdate();
        System.out.println("删除成功");


    }


    ////查看招聘信息
    public static void checkRecruitment(Connection conn) throws SQLException{
        System.out.println("请输入想要查看的招聘信息编号：");
        int id=sc.nextInt();
        // 查看数据
        String sql = "SELECT * FROM Recruit_info WHERE ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt("ID") + "\t"
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


    ////管理招聘信息
    public  static  void manageRecruitmentInformation(Connection conn)throws SQLException{
        String sql="SELECT * FROM Recruit_info";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {//显示出所有招聘信息的编号和发布时间
            System.out.println(rs.getInt("ID") + "\t\t\t"
                    + rs.getString("Rec_JobTitle") + "\t\t\t"
                    + rs.getString("Rec_Salary")+"\t\t\t"
                    + rs.getString("Rec_Rlease")+"\t\t\t"
                    + rs.getString("Rec_Responsibility")+"\t\t\t"
                    + rs.getString("Rec_Requirements")+"\t\t\t"
                    + rs.getString("Rec_AgeRange")+"\t\t\t"
                    + rs.getString("Rec_Benefits")+"\t\t\t"
                    + rs.getString("Rec_WorkAddress"));
        }
        System.out.println("是否进行管理操作(Y/N)：");
        String isManage=sc.next();
        if(isManage.equals("Y")||isManage.equals("y")){
            deleteRecruitment(conn);
        }

    }


    //发布面试通知
    public static void releaseInterviewNotice (Connection conn) {

        if (null != conn) {
            try {
                String sql="SELECT *FROM Delivery_record WHERE Del_CompanyID =?";
                ps = conn.prepareStatement(sql);
                System.out.println("请输入账号:");
                String comId=sc.next();
                ps.setString(1,comId);
                rs = ps.executeQuery( );
                while (practice.rs.next()) {
                    String resume = practice.rs.getString("");
                    resume += "\t" + practice.rs.getString("");
                    resume += "\t\t" + practice.rs.getString("");

                    System.out.println("投递的简历信息：");
                    System.out.println(resume);
                }
                System.out.println("是否选择要进行面试？Y/N");
                String result = sc.next();
                if (result.equals("Y") || result.equals("y")) {
                    System.out.println("请输入要进行面试的简历编号：");
                    String id = sc.next();
                    String insertSql = "UPDATE Stu_per_info SET Stu_Result= ? WHERE ID= ?";
                    PreparedStatement fps = conn.prepareStatement(insertSql);
                    System.out.println("请发布面试通知:");
                    fps.setString(1, sc.next());
                    fps.setString(2, id);
                    fps.executeUpdate();
                    System.out.println("通知成功");
                }
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   //编辑个人信息
    public static void editPersonalInformation(Connection conn){
        try{
            String sql= "INSERT INTO Stu_per_info (ID, Stu_Name, Stu_Sex,Stu_School,Stu_Academy,Stu_Major,Stu_Result) VALUES (?,?, ?, ?,?,?,?)";
            ps=conn.prepareStatement(sql);
            System.out.println("请完善个人基本信息：");
            System.out.println("编号：");
            ps.setString(1,sc.next());
            System.out.println("姓名：");
            ps.setString(2,sc.next());
            System.out.println("性别：");
            ps.setString(3,sc.next());
            System.out.println("学校：");
            ps.setString(4,sc.next());
            System.out.println("学院：");
            ps.setString(5,sc.next());
            System.out.println("专业：");
            ps.setString(6,sc.next());
            System.out.println("投递结果：");
            ps.setString(7,sc.next());

            ps.executeUpdate();
            System.out.println("添加成功！");

            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //编辑公司信息
    public static void editRecruitmentCompanyInformation(Connection conn){

        try{
            String sql="INSERT INTO Com_info (ID, Com_Name, Com_Synopsis,Com_Address,Com_PhoneNumber,Com_Email) VALUES (?, ?, ?,?,?,?)";
            ps=conn.prepareStatement(sql);
            System.out.println("请完善公司的基本信息:");
            System.out.println("公司编号：");
            ps.setString(1,sc.next());
            System.out.println("公司名称：");
            ps.setString(2,sc.next());
            System.out.println("公司简介：");
            ps.setString(3,sc.next());
            System.out.println("公司地址：");
            ps.setString(4,sc.next());
            System.out.println("公司电话：");
            ps.setString(5,sc.next());
            System.out.println("公司邮箱：");
            ps.setString(6,sc.next());

            ps.executeUpdate();
            System.out.println("编辑成功！");


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //查看简历信息
    public static void viewResumeInformation(Connection conn) {
        System.out.println("请输入您的账号：");
        String acc = sc.next();

        if (null != practice.conn) {
            try {
                String sql="SELECT *FROM Resume_info WHERE ID =";
                ps =conn.prepareStatement(sql);
                ps.setString(1,acc);
                rs = ps.executeQuery();
                while(rs.next()) {
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

                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    //退出
    public static void withdraw(){
        System.out.println("感谢使用本系统，再见！");
        System.exit(0); // 退出系统
    }

}
