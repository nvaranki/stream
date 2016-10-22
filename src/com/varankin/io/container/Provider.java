package com.varankin.io.container;

/**
 * Поставщик объектов.
 * 
 * @param <T> класс объектов.
 *
 * @author &copy; 2016 Николай Варанкин
 */
@FunctionalInterface
public interface Provider<T> 
{
    /**
     * @return вновь созданный объект.
     */
    T newInstance();
}
