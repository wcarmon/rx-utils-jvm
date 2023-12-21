package io.github.wcarmon.rx;

import io.reactivex.rxjava3.core.Observable;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/** Makes a collection observable */
public interface ObservableEntitiesModel<E> {

    /**
     * Add one non-null entity to the model
     *
     * @param e
     * @return this table model
     */
    ObservableEntitiesModel<E> add(E e);

    /**
     * @param entities
     * @return this table model
     */
    ObservableEntitiesModel<E> addAll(@Nullable Collection<E> entities);

    /**
     * remove all entities from the model
     *
     * @return this table model
     */
    ObservableEntitiesModel<E> clear();

    /**
     * @return never null, sometimes empty
     */
    List<E> getData();

    /**
     * @return true when there are no entities in this model
     */
    boolean isEmpty();

    Observable<List<E>> observe();

    /**
     * atomically clear and addAll, nullsafe
     *
     * @param entities
     * @return this table model
     */
    default ObservableEntitiesModel<E> replace(@Nullable Collection<E> entities) {
        clear();

        if (entities != null && !entities.isEmpty()) {
            return addAll(entities);
        }

        return this;
    }
}
