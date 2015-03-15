package eu.thog92.thaumicthings.utils;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

/**
 * Created by Thog92 on 15/03/2015.
 */
public class ReflectionHelper
{
    public static <T, E> void setPrivateFinalWithValue(Class<? super T> c, T obj, E value, String... fieldNames)
    {
        Field field = cpw.mods.fml.relauncher.ReflectionHelper.findField(c, ObfuscationReflectionHelper.remapFieldNames(c.getName(), fieldNames));

        try
        {
            Field e = Field.class.getDeclaredField("modifiers");
            e.setAccessible(true);
            e.setInt(field, field.getModifiers() & -17);
            field.set(obj, value);
        } catch (Exception var6)
        {
            var6.printStackTrace();
        }

    }
}
