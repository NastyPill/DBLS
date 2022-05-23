package tks.service.utils;

import tks.model.exceptions.EntityNotFoundException;

import java.util.List;

public class ServiceUtils {

    public static void checkSize(List<? extends Object> list) {
        if (list.size() == 0) {
            throw new EntityNotFoundException();
        }
    }

    public static void checkNull(Object object) {
        if (object == null) {
            throw new EntityNotFoundException();
        }
    }
}
