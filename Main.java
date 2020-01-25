import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Global{
    public static volatile int polz=30;
    public static void Show()
    {
        System.out.println(polz);
    }
}

class ToTen extends  Thread {
    public boolean isLock=false;
    public boolean IsStop=false;

    @Override
    public void run() {
        while (!IsStop) {
            if (Global.polz > 10 && !isLock) {
                Global.polz--;
                Global.Show();
            }
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
            if (Global.polz < 90 && !isLock) {
                Global.polz++;
                Global.Show();
            }

        }
    }
}



public class Main {
    public static void main(String[] args) throws InterruptedException {
        // System.out.print(Global.polz);
        //Global.polz=10;
        //System.out.println(Global.polz);
        var t1 = new ToTen();
        var t2 = new To90();
        boolean isStop=false;
        t1.start();
        t2.start();
        //Thread.sleep(100);
/*        try {
            TimeUnit.SECONDS.sleep(1000l);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    t1.interrupt();
    t2.interrupt();
        System.out.printf("Stop");
 */

        Scanner in = new Scanner(System.in);

        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        while (!isStop) {

            /*try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }*/
//            try {

                t1.isLock=true;
                t2.isLock=true;
/*            }
            catch (InterruptedException ex){t1.interrupt();t2.interrupt();}*/
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
    }
}
