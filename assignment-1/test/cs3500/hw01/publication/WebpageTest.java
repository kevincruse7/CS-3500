package cs3500.hw01.publication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link Webpage}: unit tests to ensure that Webpages can be cited correctly and
 * otherwise behave correctly.
 */
public class WebpageTest {

  Publication stallman = new Webpage("Richard Stallman's Personal Page",
      "https://stallman.org", "September 20, 2020");

  @Test
  public void citeApa() {
    assertEquals("Richard Stallman's Personal Page. Retrieved September 20, 2020, from "
            + "https://stallman.org.",
        stallman.citeApa());
  }

  @Test
  public void citeMla() {
    assertEquals("\"Richard Stallman's Personal Page.\" Web. September 20, 2020 "
            + "<https://stallman.org>.",
        stallman.citeMla());
  }
}