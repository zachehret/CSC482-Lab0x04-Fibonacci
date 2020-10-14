package edu.dsu.zehret.csc482;

import java.util.ArrayList;

public class Main {

    private static long[] _cache = new long[100];

    private static ArrayList<Result> results = new ArrayList<Result>();

    private static final int MAX_FIB_X = 20;

    public static void main(String[] args) throws InterruptedException {

        for(int i = 0 ; i < _cache.length; i++) 
            _cache[i] = -1;

        // Sequential
        for (int i = 1; i <= MAX_FIB_X; i++) {
            long startTime = System.currentTimeMillis();
            long t = fibLoop(i);
            long endTime = System.currentTimeMillis();
            results.add(new Result("Loop F(" + i + ")", t, endTime - startTime, 1));
        }

        // Recursive
        for (int i = 1; i <= MAX_FIB_X; i++) {
            long startTime = System.currentTimeMillis();
            long t = fibRecur(i);
            long endTime = System.currentTimeMillis();
            results.add(new Result("Recur F(" + i + ")", t, endTime - startTime, 1));
        }

        //Cache
        for (int i = 1; i <= MAX_FIB_X; i++) {
            long startTime = System.currentTimeMillis();
            long t = fibCacheHelper(i);
            long endTime = System.currentTimeMillis();
            results.add(new Result("Cache F(" + i + ")", t, endTime - startTime, 1));
        }

        // Fib Matrix...

        ArrayList<ArrayList<String>> resultsTable = new ArrayList<ArrayList<String>>();
        resultsTable.add(0, new ArrayList<String>());
        resultsTable.get(0).add("Test Name");
        resultsTable.get(0).add("Value");
        resultsTable.get(0).add("Itererations");
        resultsTable.get(0).add("Avg. Time");
        for (int i = 0; i < results.size(); i++) {
            resultsTable.add(i + 1, new ArrayList<String>());
            resultsTable.get(i + 1).add(results.get(i).getTestName());
            resultsTable.get(i + 1).add("" + results.get(i).getValue());
            resultsTable.get(i + 1).add("" + results.get(i).getIterations());
            resultsTable.get(i + 1).add("" + results.get(i).getAverageTimePerIteration());
        }
        drawTable(resultsTable, 16, true);
    }

    public static long fibRecur(long x) throws InterruptedException {
        Thread.sleep(1);
        if(x == 0 || x == 1) return x;
        return fibRecur(x-1) + fibRecur(x-2);
    }

    public static long fibLoop(long x) throws InterruptedException {
        if(x == 0 || x == 1) return x;

        int a = 0, b = 1;
        for(int i = 2; i < x; i++) {
            int newValue = a + b;
            a = b;
            b = newValue;
            Thread.sleep(1);
        }
        return b;
    }

    public static long fibCacheHelper(int x) throws InterruptedException{
        if(x < 2) return x;
        // if fib(x) is in table return what's in table
        if(_cache[x] != -1) {
            return _cache[x];
        }
        Thread.sleep(1);
        // before returning result, store in table
        _cache[x] = fibCacheHelper(x-1) + fibCacheHelper(x-2);
        return _cache[x];
    }

    public static long fibCache(int x) throws InterruptedException {
        dumpCache();
        return fibCacheHelper(x);
    }

    public static void dumpCache() {
        for(int i = 0 ; i < _cache.length; i++) 
            _cache[i] = -1;
    }


    public static String padString(String str, int len, char filler, int mode) {
        if(str.length() > len) {
            str.substring(0,len-1);
        }
        while(str.length() < len) {
            if(mode == FormatMode.APPEND)
                str+=filler;
            else if(mode == FormatMode.PREPEND)
                str = filler + str;
        }
        return str;
    }

    public static void drawTable(ArrayList<ArrayList<String>> str, int cellWidth, boolean hasHeader) throws IndexOutOfBoundsException {
        boolean headerPrinted = false;
        System.out.println(" " + padString("", str.get(0).size()*cellWidth+str.get(0).size()*3+1, '#', FormatMode.APPEND));
        for(ArrayList<String> row : str) {
            for(String col : row) {
                System.out.print(" | " + padString(col, cellWidth, ' ', FormatMode.PREPEND));
            }
            System.out.println(" | "); //End the line.
            if(hasHeader && !headerPrinted) {
                System.out.println(" " + padString("", str.get(0).size()*cellWidth+str.get(0).size()*3+1, '#', FormatMode.APPEND));
                headerPrinted = true;
            }
        }
        System.out.println(" " + padString("", str.get(0).size()*cellWidth+str.get(0).size()*3+1, '#', FormatMode.APPEND));
    }
}
class Result {
    private String testName;
    private long timeTaken;
    private long iterations;
    private long value;
    public Result(String testName, long value, long timeTaken, long iterations) {
        this.testName = testName;
        this.timeTaken = timeTaken;
        this.iterations = iterations;
        this.value = value;
    }
    public String getTestName() {
        return this.testName;
    }
    public long getTimeTaken() {
        return this.timeTaken;
    }
    public long getIterations() {
        return this.iterations;
    }
    public float getAverageTimePerIteration() {
        return (float)this.timeTaken/this.iterations;
    }
    public long getValue() {
        return this.value;
    }
}
class FormatMode {
    public static final int PREPEND = 0;
    public static final int APPEND = 1;
}