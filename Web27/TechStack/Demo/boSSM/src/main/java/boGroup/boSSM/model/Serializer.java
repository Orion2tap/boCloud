package boGroup.boSSM.model;

import java.util.ArrayList;

public interface Serializer<T> {
    ArrayList<String> serialize(T model);
}
