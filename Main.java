import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;

class Global{
    public static volatile int polz=30;
    //public static void Show()
    {
       // out.setText(Integer.toString(polz));
    }
    public  static void update(){pr.setValue(polz);}
    public  static volatile JProgressBar pr=new JProgressBar(10,90);
    public static JSlider slider = new JSlider(10, 90, 45);
    public  static volatile JLabel dout=new JLabel("Debug Info");
    public static volatile JLabel out=new JLabel("TEST");
    //This is semafor 0==free,1==first use,2== second use
    public static volatile int IsFree=0;
}

class ToTen extends  Thread {
    public boolean isLock=false;
    public boolean IsStop=false;

    @Override
    public void run() {
        while (!IsStop) {
            if ( !isLock)if(Global.polz > 10) {
                Global.polz--;

         //       Global.slider.updateUI();
      //          Global.Show();
            }
         /*   try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
           /* try {

                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        }
    }
}

class To90 extends  Thread {
    public boolean isLock=false;
    public boolean IsStop=false;

    @Override
    public void run() {
        while (!IsStop) {
            if (!isLock)if(Global.polz < 90) {
                //Global.slider.setValue(Global.slider.getValue()+1);
                Global.polz++;
              //  Global.slider.updateUI();
           //     Global.Show();

            }
           /* try {
                sleep(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        }
    }
}

class Upd extends  Thread
{
    @Override
    public void run()
    {
        while (true) {
            Global.update();
        }
    }
}


public class Main {
    public static void main(String[] args) throws InterruptedException {
        var upd=new Upd();



        var form=new JFrame();
        JPanel jPanel=new JPanel();
        form.add(jPanel);
        form.setVisible(true);
        form.setBounds(1000,500,500,500);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jPanel.add(new JButton("ПУСК1"));
        var Pusk1=new JButton("ПУСК1");
       /* Pusk1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Global.out.setText("tttt");
            }
        });*/
        var Pusk2=new JButton("ПУСК2");
        var Stop1=new JButton("СТОП1");
        var Stop2=new JButton("СТОП2");
        //jPanel.add(Global.slider);

        jPanel.add(Pusk1);
        jPanel.add(Pusk2);
        jPanel.add(Stop1);
        jPanel.add(Stop2);
        form.setLayout(new FlowLayout());
        jPanel.add(Global.dout);
        jPanel.add(Global.out);
        jPanel.add(Global.pr);
        // System.out.print(Global.polz);
        //Global.polz=10;
        //System.out.println(Global.polz);
        ToTen t1 = new ToTen();
        var t2 = new To90();
        boolean isStop=false;
        //t1.start();
        //t1.setPriority(Thread.MAX_PRIORITY);
        //t2.setPriority(Thread.MIN_PRIORITY);
       // t2.start();
        upd.start();
        Pusk1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                t1.setPriority(Thread.MIN_PRIORITY);
                if(Global.IsFree==0) {
                    t1.isLock=false;
                    Global.IsFree = 1;
                    if(!t1.isAlive()) t1.start();
                }
                else
                    Global.dout.setText("Занято потоком");
            }
        });
        Pusk2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                t2.setPriority(Thread.MAX_PRIORITY);
                if (Global.IsFree == 0) {
                    t2.isLock=false;
                    Global.IsFree = 2;
                    if(!t2.isAlive())t2.start();
                }
                else
                    Global.dout.setText("Занято потоком");

            }
        });
        Stop1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(Global.IsFree==1) {
                    t1.isLock = true;
                    //t1.interrupt();

                    Global.IsFree=0;
                }
                else if(Global.IsFree==2)
                    Global.dout.setText("Занято потоком 2");
            //    Global.out.setText(Integer.toString(Global.IsFree));
            }
        });

        Stop2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(Global.IsFree==2)
                {
                    t2.isLock=true;
                    //t2.interrupt();
                    Global.IsFree=0;
                }
                else if(Global.IsFree==1)
                    Global.dout.setText("Занято потоком 1");
           //     Global.out.setText(Integer.toString(Global.IsFree));
            }
        });
        //Thread.sleep(100);


       /* Scanner in = new Scanner(System.in);

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        while (!isStop) {


//            try {

                t1.isLock=true;
                t2.isLock=true;

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            System.out.print("Input a number: ");
            int num = in.nextInt();
            switch (num) {
                case 1:
                    t1.setPriority(Thread.MAX_PRIORITY);
                    t2.setPriority(Thread.MIN_PRIORITY);
                    break;
                case 2:
                    t2.setPriority(Thread.MAX_PRIORITY);
                    t1.setPriority(Thread.MIN_PRIORITY);
                    break;
                case 3:
                    t2.setPriority(Thread.NORM_PRIORITY);
                    t1.setPriority(Thread.NORM_PRIORITY);
                    break;
                case 4:
                    isStop=true;
                    t1.IsStop=true;
                    t2.IsStop=true;
                    t1.join();
                    t2.join();
                    break;
            }
            if(!isStop) {
                t1.isLock = false;
                t2.isLock = false;

                //t2.start();
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

        }
        System.out.println("Return 0");

*/
       // ar.setVisible(true);

    }
}
