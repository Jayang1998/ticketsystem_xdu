package gui;

import bean.UserBean;
import dao.UserDao;
import util.Md5;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui_register extends JFrame implements ActionListener
{
    //主框体的label
    private JLabel name = new JLabel("   注册账号");
    //账号框
    private JTextField id = new JTextField();
    //密码框
    private JPasswordField pwd1 = new JPasswordField();
    private JPasswordField pwd2 = new JPasswordField();

    private JTextField phone = new JTextField();
    private JTextField confirm = new JTextField();
    //登录框的label
    private JLabel l1 = new JLabel("账号:");
    //密码框的label
    private JLabel l2 = new JLabel("输入密码:");
    private JLabel l3 = new JLabel("再次输入密码:");
    private JLabel l4 = new JLabel("手机号:");
    private JLabel l5 = new JLabel("验证码");
    //登陆按钮
    private JButton register = new JButton("注册");
    private JButton back = new JButton("返回");
    private JButton confirm_ = new JButton("获取");

    private String username = null;
    private String psw1 = null;
    private String psw2 = null;
    private String confirm_num = null;
    private String phone_num = null;
    private UserBean newuser = new UserBean();
    private String newpswmd5 = null;
    private UserDao check_user_exist = new UserDao();


    JFrame register_jf;
    Container container;
    config opt = new config();
    gui_login login_jf;

    String confirm_data;

    boolean flag = false;
    boolean register_msg = false;

    public gui_register() {
        register_jf = new JFrame("TicketSystem");
        container = register_jf.getContentPane();

        //主框体icon设置
        ImageIcon chat_icon = new ImageIcon(opt.register);
        chat_icon.setImage(chat_icon.getImage().getScaledInstance(40, 40, 10));
        //整个程序的图标设置
        ImageIcon chat1_icon = new ImageIcon(opt.chat);
        register_jf.setIconImage(chat1_icon.getImage().getScaledInstance(40, 40, 10));
        //登录框icon设置
        ImageIcon login_icon = new ImageIcon(opt.login);
        login_icon.setImage(login_icon.getImage().getScaledInstance(25, 25, 10));
        //密码框icon设置
        ImageIcon pwd_icon = new ImageIcon(opt.pwd);
        pwd_icon.setImage(pwd_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon phone_icon = new ImageIcon(opt.phone);
        phone_icon.setImage(phone_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon confirm_icon = new ImageIcon(opt.confirm);
        confirm_icon.setImage(confirm_icon.getImage().getScaledInstance(25, 25, 10));

        name.setBounds(130, 10, 140, 30);
        name.setIcon(chat_icon);

        l1.setBounds(30, 55, 120, 30);
        l2.setBounds(30, 110, 120, 30);
        l3.setBounds(30, 165, 120, 30);
        l4.setBounds(30, 210, 120, 30);
        l5.setBounds(30, 255, 120, 30);
        l1.setIcon(login_icon);
        l2.setIcon(pwd_icon);
        l3.setIcon(pwd_icon);
        l4.setIcon(phone_icon);
        l5.setIcon(confirm_icon);

        id.setBounds(150, 55, 160, 30);
        pwd1.setBounds(150, 110, 160, 30);
        pwd2.setBounds(150, 165, 160, 30);
        phone.setBounds(150, 210, 160, 30);
        confirm.setBounds(220, 255, 90, 30);

//        login.addActionListener(this);
        register.setBounds(150, 300, 60, 30);
        register.addActionListener(this);
        back.setBounds(250, 300, 60, 30);
        back.addActionListener(this);
        confirm_.setBounds(150, 255, 65, 30);
        confirm_.addActionListener(this);
//        register.addActionListener(this);

        //加入到主容器中
        container.add(name);
        container.add(l1);
        container.add(l2);
        container.add(l3);
        container.add(l4);
        container.add(l5);
        container.add(register);
        container.add(back);
        container.add(confirm_);
        container.add(id);
        container.add(pwd1);
        container.add(pwd2);
        container.add(phone);
        container.add(confirm);

    }
    public void actionPerformed (ActionEvent e )
    {
        //点击 注册
        if(e.getSource() == register)
        {
            username = id.getText();
            psw1 = String.valueOf(pwd1.getPassword());
            psw2 = String.valueOf(pwd2.getPassword());
            confirm_num = confirm.getText();
            phone_num = phone.getText();

            if(!flag)  //
            {
                JOptionPane.showMessageDialog(null, "请输入验证码！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            if(username.length() < 1 && flag)
            {
                JOptionPane.showMessageDialog(null, "用户名不能少于2位！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            if(!psw1.equals(psw2) && username.length() >= 1 && flag)
            {
                JOptionPane.showMessageDialog(null, " 两次密码不一致！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            if(psw1.length() < 1 && psw1.equals(psw2) && flag)
            {
                JOptionPane.showMessageDialog(null, "密码不能少于8位！", "提示",JOptionPane.ERROR_MESSAGE);
            }

            if(confirm_num.length() != 6 && flag && psw1.equals(psw2) && username.length() >= 1 && psw1.length() >= 1)
            {
                JOptionPane.showMessageDialog(null, "请输入正确的验证码！", "提示",JOptionPane.ERROR_MESSAGE);
            }

            if(confirm_num.length() == 6 && flag && psw1.equals(psw2) && username.length() >= 1 && psw2.length() >= 1 && !confirm_num.equals(confirm_data))
            {
                JOptionPane.showMessageDialog(null, "验证码错误", "提示",JOptionPane.ERROR_MESSAGE);
            }

            if(confirm_num.length() == 6 && flag && psw1.equals(psw2) && username.length() >= 1 && psw2.length() >= 1 && confirm_num.equals(confirm_data) && check_user_exist.isExist(username))
            {
                JOptionPane.showMessageDialog(null, "该用户名已被注册", "提示",JOptionPane.ERROR_MESSAGE);
            }

            //符合注册要求
            if(confirm_num.length() == 6 && flag && psw1.equals(psw2) && username.length() >= 1 && psw2.length() >= 1 && confirm_num.equals(confirm_data) && !check_user_exist.isExist(username))
            {

                try{
                    newpswmd5 = Md5.EncoderByMd5(psw1);

                }catch(Exception registeconfiem_e){
                    registeconfiem_e.printStackTrace();
                }
                newuser.setUserName(username);
                newuser.setUserPassword(newpswmd5);

                try {
                    register_msg = check_user_exist.createUser(newuser);

                    if(register_msg)
                    {
                        JOptionPane.showMessageDialog(null, "注册成功！", "提示",JOptionPane.ERROR_MESSAGE);
                        register_jf.setVisible(false);
                        gui_use chat_jf = new gui_use(username);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "注册失败", "提示",JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ee) {
                    System.out.println("register error");
                }
            }

        }
        //点击 返回
        if(e.getSource() == back)
        {
            register_jf.setVisible(false);
            login_jf = new gui_login();
        }
        // 点击 验证
        if(e.getSource() == confirm_)
        {
            if(flag)
            {
                JOptionPane.showMessageDialog(null, "请不要重复验证！", "提示",JOptionPane.ERROR_MESSAGE);
            }
                phone_num = phone.getText();
            // 验证 只需要 手机号
            if(phone_num.length() != 11 && !flag)
            {
                JOptionPane.showMessageDialog(null, "请输入正确的11位手机号！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            if(phone_num.length() == 11 && !flag)   //尚未进行验证
            {
                //发送验证码
                Random random = new Random();
                confirm_data = String.valueOf(random.nextInt(899999) + 100000); //生成一个六位数随机数String  confirm_data
                System.out.println(confirm_data);
                try{
                    confirm confirm_r = new confirm(phone_num,confirm_data);
                    flag = true;
                }catch (Exception confiem_e){
                    confiem_e.printStackTrace();
                }
            }
        }
    }

    public void vis()
    {
        //空布局
        container.setLayout(null);
        //背景色
        container.setBackground(Color.white);
        //主窗口是否可视化
        register_jf.setVisible(true);
        //设置在屏幕中间弹出
        register_jf.setLocation(500, 300);
        //主窗口大小
        register_jf.setSize(420, 400);
        System.out.println("注册窗口构建成功!");
        //退出方式

        register_jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}