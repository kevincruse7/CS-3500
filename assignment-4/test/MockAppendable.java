import java.io.IOException;

/**
 * Mock appendable object for testing playGame and render error handling.
 */
class MockAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence charSequence) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence charSequence, int i, int i1) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
