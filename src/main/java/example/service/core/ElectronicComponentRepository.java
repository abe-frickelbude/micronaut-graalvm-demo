package example.service.core;


import example.model.ElectronicComponent;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.List;

public interface ElectronicComponentRepository {

    ElectronicComponent find(final Long id);

    Page<ElectronicComponent> find(final Pageable pageable);

    List<ElectronicComponent> findAll();

    ElectronicComponent save(final ElectronicComponent component);

    void delete(final Long id);
}
