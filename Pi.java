import java.math.*;
import java.lang.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import java.util.ArrayList;

public class Pi{
  static long numIterations;
  static long numThreads;
  static long itPerThread;
  static ArrayList<Thread> threads;
  static AtomicLong num = new AtomicLong(0);
  static AtomicLong den = new AtomicLong(0);

  public static void main(String[] args){
    numThreads = Integer.valueOf(args[0]);
    numIterations = Integer.valueOf(args[1]);
    itPerThread = numIterations / numThreads;
    threads = new ArrayList<Thread>();

    for(int i = 0; i < numThreads; i++){
      threads.add(new Thread(() -> {
        long count=0;
        // ThreadLocalRandom rand = new ThreadLocalRandom();
        // System.out.println("itpt: "+itPerThread);
        for(int j=0; j<itPerThread; j++){
          // System.out.println("in thread");
          double x = ThreadLocalRandom.current().nextDouble(1.000000000000000000000000000000000000000000001);
          double y = ThreadLocalRandom.current().nextDouble(1.000000000000000000000000000000000000000000001);
          // System.out.println("bottom thread");

          x=Math.pow(x,2);
          y=Math.pow(y,2);

          double result = x+y;
          if(result < 1) count++;
        }
        num.addAndGet(count);
        den.addAndGet(itPerThread);
      }
      ));
    }

    for(int i=0; i<numThreads; i++){
      threads.get(i).start();
    }
    try{
      for(int i=0; i<numThreads; i++){
        threads.get(i).join();
      }
    }
    catch(Exception e){}
    double ratio = (double)num.longValue()/(double)den.longValue();
    double pi = ratio*4;

    System.out.println("Total = "+den);
    System.out.println("Inside = "+num);
    System.out.println("Ratio = "+ratio);
    System.out.println("Pi = "+pi);




  }

  // public static void calculate(){
  //   ThreadLocalRandom rand = new ThreadLocalRandom();
  //   for(int i=0; i<itPerThread; i++){
  //     double x = rand.nextDouble(1.000000000000000000000000000000000000000000001);
  //     double y = rand.nextDouble(1.000000000000000000000000000000000000000000001);
  //
  //     x=Math.pow(x,2);
  //     y=Math.pow(y,2);
  //
  //     double result = x+y;
  //
  //     if(result<1) num.incrementAndGet();
  //     den.incrementAndGet();
  //   }
  //
  // }
}
