package com.varankin.io.container;

/**
 * Поставщик объектов.
 * 
 * @param <T> класс объектов.
 *
 * @author &copy; 2012 Николай Варанкин
 */
public interface Provider<T> 
{
    /**
     * @return вновь созданный объект.
     */
    T newInstance();
}
