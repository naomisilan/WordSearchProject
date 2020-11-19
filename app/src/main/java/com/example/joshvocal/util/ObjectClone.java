package com.example.joshvocal.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectClone {

    private ObjectClone() {
    }

    public static Object deepCopy(Object oldObj) throws Exception {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);

            // Serialize and pass the object
            oos.writeObject(oldObj);
            oos.flush();

            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);

            // Return the new object
            return ois.readObject();
        } catch (Exception e) {
            System.out.println("Exception in util.ObjectClone = " + e);
            throw (e);
        } finally {
            oos.close();
            ois.close();
        }
    }
}
