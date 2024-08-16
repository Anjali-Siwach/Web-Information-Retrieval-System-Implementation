package com.stephen.crawler;
import java.util.Scanner;

public class SpiderTest
{
    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args)
    {
    	Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the starting URL: ");
        String startUrl = scanner.nextLine();

        System.out.print("Enter the search word: ");
        String searchWord = scanner.nextLine();

        Spider spider = new Spider();
        spider.search(startUrl, searchWord);
        scanner.close();
    }
}