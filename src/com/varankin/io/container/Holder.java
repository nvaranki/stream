package com.varankin.io.container;

/**
 * Поставщик хранимого объекта.
 * 
 * @param <T> класс объектов.
 *
 * @author &copy; 2016 Николай Варанкин
 */
@FunctionalInterface
public interface Holder<T>
{
    /**
     * @return ранее созданный объект.
     */
    T getInstance();
}
