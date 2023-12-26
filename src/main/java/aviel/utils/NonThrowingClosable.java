package aviel.utils;

public interface NonThrowingClosable extends AutoCloseable {
    @Override
    void close();
}
