package com.stephen.crawler;

import java.util.HashSet;



import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Spider
{
  private static final int MAX_PAGES_TO_SEARCH = 10;
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();
  private Map<String, Set<String>> index = new HashMap<>(); // Index to store terms and associated URLs

  /**
   * Our main launching point for the Spider's functionality. Internally it creates spider legs
   * that make an HTTP request and parse the response (the web page).
   * 
   * @param url
   *            - The starting point of the spider
   * @param searchWord
   *            - The word or string that you are searching for
   */
  public void search(String url, String searchWord)
  {
      while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
      {
          String currentUrl;
          SpiderLeg leg = new SpiderLeg();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          ExtractedText extractedText = leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
                                 // SpiderLeg
          if (extractedText != null) {
              // Extract the text from the ExtractedText object
              String extractedTextString = extractedText.getText();

              // Index the extracted text and URL
              indexPage(currentUrl, extractedTextString);
          }
          boolean success = leg.searchForWord(searchWord);
          if(success)
          {
              System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
              break;
          }
          this.pagesToVisit.addAll(leg.getLinks());
      }
      
      System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
  }


  /**
   * Returns the next URL to visit (in the order that they were found). We also do a check to make
   * sure this method doesn't return a URL that has already been visited.
   * 
   * @return
   */
  private String nextUrl()
  {
      String nextUrl;
      do
      {
          nextUrl = this.pagesToVisit.remove(0);
      } while(this.pagesVisited.contains(nextUrl));
      this.pagesVisited.add(nextUrl);
      return nextUrl;
  }
  private void indexPage(String url, String text) {
      Set<String> keywords = extractKeywords(text);
      for (String keyword : keywords) {
          if (!this.index.containsKey(keyword)) {
              this.index.put(keyword, new HashSet<>());
          }
          this.index.get(keyword).add(url);
          System.out.println(String.format("Indexed keyword: %s, URL: %s", keyword, url));
      }
  }

  /**
   * Simplifies the text for indexing by removing punctuation, converting to lowercase, and splitting into words.
   *
   * @param text The text to be simplified
   * @return A list of individual words from the text
   */
  private Set<String> extractKeywords(String text) {
      Set<String> keywords = new HashSet<>();
      String[] tokens = text.toLowerCase().split("[\\s,.;:?!\"'()]");
      for (String token : tokens) {
          if (!token.isEmpty()) {
              keywords.add(token);
          }
      }
      return keywords;
  }
  }